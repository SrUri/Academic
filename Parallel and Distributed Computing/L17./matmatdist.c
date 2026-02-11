/* I EXECUTE THIS IN MAC M1 WITH: "mpicc -Xpreprocessor -fopenmp -I$(brew --prefix libomp)/include -L$(brew --prefix libomp)/lib matmatdist.c -o matmat_test -lomp"
 *
 * THEN, IF TEST 1: "mpirun -np 4 ./matmat_test", IF TEST 2: "mpirun -np 1 ./matmat_test 2048 1 1",
 * OR "mpirun -np 2 ./matmat_test 2048 1 2", OR "mpirun -np 4 ./matmat_test 2048 2 2", OR "mpirun -np 8 ./matmat_test 2048 2 4,
 * AND THE SAME FOR N = 2048, 4096 AND 6144"
*/

#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>
#include <omp.h>
#include <string.h>
#include <math.h>

void matmatikj(int ldA, int ldB, int ldC, double *A, double *B, double *C, int N1, int N2, int N3) {
    for (int i = 0; i < N1; i++) {
        for (int k = 0; k < N2; k++) {
            double a = A[i * ldA + k];
            for (int j = 0; j < N3; j++) {
                C[i * ldC + j] += a * B[k * ldB + j];
            }
        }
    }
}

void matmatblock(int ldA, int ldB, int ldC, double *A, double *B, double *C, int N1, int N2, int N3, int dbA, int dbB, int dbC) {
    for (int i = 0; i < N1; i += dbA) {
        int M = (i + dbA > N1) ? (N1 - i) : dbA;
        for (int j = 0; j < N3; j += dbC) {
            int N = (j + dbC > N3) ? (N3 - j) : dbC;
            for (int k = 0; k < N2; k += dbB) {
                int P = (k + dbB > N2) ? (N2 - k) : dbB;
                matmatikj(ldA, ldB, ldC, &A[i * ldA + k], &B[k * ldB + j], &C[i * ldC + j], M, P, N);
            }
        }
    }
}

void matmatthread(int ldA, int ldB, int ldC, double *A, double *B, double *C, int N1, int N2, int N3, int dbA, int dbB, int dbC, int NTROW, int NTCOL) {
    int files = (N1 + NTROW - 1) / NTROW;
    int columnes = (N3 + NTCOL - 1) / NTCOL;

    #pragma omp parallel for schedule(static) num_threads(NTROW * NTCOL)
    for (int i = 0; i < NTROW; i++) {
        for (int j = 0; j < NTCOL; j++) {
            int fila_0 = i * files;
            int fila_N = (fila_0 + files > N1) ? N1 : fila_0 + files;
            int columna_0 = j * columnes;
            int columna_N = (columna_0 + columnes > N3) ? N3 : columna_0 + columnes;

            if (fila_0 >= fila_N || columna_0 >= columna_N) continue;

            double *A_sub = &A[fila_0 * ldA];
            double *B_sub = &B[columna_0];
            double *C_sub = &C[fila_0 * ldC + columna_0];

            matmatblock(ldA, ldB, ldC, A_sub, B_sub, C_sub, fila_N - fila_0, N2, columna_N - columna_0, dbA, dbB, dbC);
        }
    }
}

void matmatdist(MPI_Comm GridComm, int LDA, int LDB, int LDC, double *A, double *B, double *C, int N1, int N2, int N3, int DB1, int DB2, int DB3, int NTrow, int NTcol) {
    int rang, numprocessos;
    
    MPI_Comm_rank(GridComm, &rang);
    MPI_Comm_size(GridComm, &numprocessos);

    int dim[2], periodes[2], coords[2];

    MPI_Cart_get(GridComm, 2, dim, periodes, coords);

    int fila = coords[0]; 
    int colum = coords[1]; 
    int np_files = dim[0]; 
    int np_columnes = dim[1]; 

    MPI_Comm row_comm, col_comm;
    int dim_rest[2]; dim_rest[0] = 0; dim_rest[1] = 1;

    MPI_Cart_sub(GridComm, dim_rest, &row_comm);

    dim_rest[0] = 1; dim_rest[1] = 0;

    MPI_Cart_sub(GridComm, dim_rest, &col_comm);

    int N1_local = N1 / np_files; 
    int N2_local_A = N2 / np_columnes; 
    int N2_local_B = N2 / np_files; 
    int N3_local = N3 / np_columnes; 

    double *buff_A = (double *)malloc(N1_local * DB2 * sizeof(double));
    double *buff_B = (double *)malloc(DB2 * N3_local * sizeof(double));

    //kBLOCKING FACTOR - ScaLAPACK 3 PDGEMM
    for (int k = 0; k < N2; k += DB2) {
        int ancho_actual_k = (k + DB2 > N2) ? (N2 - k) : DB2;
        int columna_index = k / N2_local_A;

        if (colum == columna_index) {
            int local_offset_k = k % N2_local_A;
            for (int i = 0; i < N1_local; i++) {
                memcpy(&buff_A[i * ancho_actual_k], &A[i * LDA + local_offset_k], ancho_actual_k * sizeof(double));
            }
        }
        MPI_Bcast(buff_A, N1_local * ancho_actual_k, MPI_DOUBLE, columna_index, row_comm);
        
        int fila_index = k / N2_local_B;
        if (fila == fila_index) {
            int local_offset_k = k % N2_local_B;
            for (int i = 0; i < ancho_actual_k; i++) {
                memcpy(&buff_B[i * N3_local], &B[(local_offset_k + i) * LDB], N3_local * sizeof(double));
            }
        }
        MPI_Bcast(buff_B, ancho_actual_k * N3_local, MPI_DOUBLE, fila_index, col_comm);
        
        matmatthread(ancho_actual_k, N3_local, LDC, buff_A, buff_B, C, N1_local, ancho_actual_k, N3_local, DB1, DB2, DB3, NTrow, NTcol);
    }
    free(buff_A);
    free(buff_B);
    MPI_Comm_free(&row_comm);
    MPI_Comm_free(&col_comm);
}

#ifdef TEST

void print_matriu(const char* nom, double* M, int files, int columnes) {
    printf("%s:\n", nom);
    for (int i=0; i < files; i++) {
        for (int j=0; j < columnes; j++) {
            printf("%6.1f ", M[i * columnes + j]);
        }
        printf("\n");
    }
    printf("\n");
}

int main(int argc, char *argv[]) {
    MPI_Init(&argc, &argv);

    int rank, numprocessos;
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &numprocessos);

    int test = 1;
    int N1, N2, N3, DB;
    int celdas_dim[2] = {0, 0};

    if (argc >= 4) {
        test = 2;
        N1 = N2 = N3 = atoi(argv[1]);
        celdas_dim[0] = atoi(argv[2]);
        celdas_dim[1] = atoi(argv[3]);
        DB = 256;
    } else {
        N1 = 2; N2 = 4; N3 = 4; DB = 1;
        MPI_Dims_create(numprocessos, 2, celdas_dim);
    }

    MPI_Comm GridComm;
    int periodes[2] = {0, 0};
    int reordenar = 1;

    MPI_Cart_create(MPI_COMM_WORLD, 2, celdas_dim, periodes, reordenar, &GridComm);

    int coords[2];
    MPI_Cart_coords(GridComm, rank, 2, coords);
    int fila = coords[0];
    int colum = coords[1];

    int N1_local = N1 / celdas_dim[0];
    int N2_local_A = N2 / celdas_dim[1];
    int N2_local_B = N2 / celdas_dim[0];
    int N3_local = N3 / celdas_dim[1];

    double *local_A = (double *)calloc(N1_local * N2_local_A, sizeof(double));
    double *local_B = (double *)calloc(N2_local_B * N3_local, sizeof(double));
    double *local_C = (double *)calloc(N1_local * N3_local, sizeof(double));
    double *global_A = NULL, *global_B = NULL, *global_C = NULL;

    if (test == 1) {
        if (rank == 0) {
            global_A = (double *)malloc(N1 * N2 * sizeof(double));
            global_B = (double *)malloc(N2 * N3 * sizeof(double));
            global_C = (double *)malloc(N1 * N3 * sizeof(double));

            for(int i = 0; i < N1 * N2; i++) {
                global_A[i] = (double)i;
            }

            for(int i = 0; i < N2 * N3; i++) { 
                global_B[i] = 10.0 + (double)i;
            }
            
            print_matriu("A Global", global_A, N1, N2);
            print_matriu("B Global", global_B, N2, N3);

            for(int i=0; i < N1_local; i++) {
                for(int j = 0; j < N2_local_A; j++) {
                    local_A[i * N2_local_A + j] = global_A[(fila * N1_local + i)* N2 + (colum * N2_local_A + j)];
                }
            }

            for(int i = 0; i < N2_local_B; i++) {
                for(int j = 0; j < N3_local; j++) {
                    local_B[i * N3_local + j] = global_B[(fila * N2_local_B + i)* N3 + (colum * N3_local + j)];
                }
            }

            for(int p = 1; p < numprocessos; p++) {
                int p_coords[2];
                MPI_Cart_coords(GridComm, p, 2, p_coords);

                for(int i = 0; i < N1_local; i++) {
                    int g_fila = p_coords[0] * N1_local + i;
                    int g_columna = p_coords[1] * N2_local_A;
                    MPI_Send(&global_A[g_fila * N2 + g_columna], N2_local_A, MPI_DOUBLE, p, 0, GridComm);
                }

                for(int i=0; i < N2_local_B; i++) {
                    int g_fila = p_coords[0] * N2_local_B + i;
                    int g_columna = p_coords[1] * N3_local;
                    MPI_Send(&global_B[g_fila * N3 + g_columna], N3_local, MPI_DOUBLE, p, 1, GridComm);
                }
            }
        } else {
            for(int i = 0; i < N1_local; i++) {
                MPI_Recv(&local_A[i * N2_local_A], N2_local_A, MPI_DOUBLE, 0, 0, GridComm, MPI_STATUS_IGNORE);
            }

            for(int i=0; i<N2_local_B; i++) {
                MPI_Recv(&local_B[i * N3_local], N3_local, MPI_DOUBLE, 0, 1, GridComm, MPI_STATUS_IGNORE);
            }
        }
    } 
    else {
        srand(rank);
        for(int i = 0; i < N1_local * N2_local_A; i++) {
            local_A[i] = (double)(rand() % 10);
        }

        for(int i = 0; i < N2_local_B * N3_local; i++) {
            local_B[i] = (double)(rand() % 10);
        }
    }

    int thread_conf[4][2] = {{1,1}, {2,1}, {2,2}, {4,2}};
    int num_conf = (test == 1) ? 1 : 4;

    if (rank == 0 && test == 2) {
        printf("\n Threads \t secs \t\t GFlops \n");
    }

    for (int t = 0; t < num_conf; t++) {
        int nt_fila = thread_conf[t][0];
        int nt_columna = thread_conf[t][1];

        omp_set_num_threads(nt_fila * nt_columna);
        memset(local_C, 0, N1_local * N3_local * sizeof(double));

        MPI_Barrier(GridComm);
        double temps_0 = MPI_Wtime();
        matmatdist(GridComm, N2_local_A, N3_local, N3_local, local_A, local_B, local_C, N1, N2, N3, DB, DB, DB, nt_fila, nt_columna);
        MPI_Barrier(GridComm);
        double temps_1 = MPI_Wtime();

        if (test == 2 && rank == 0) {
            double temps = temps_1 - temps_0;
            double gflops = (2.0 * pow(N1, 3)) / temps / 1e9;
            printf("(%d, %d) \t\t %7.4f \t %7.2f \n", nt_fila, nt_columna, temps, gflops);
        }
    }

    if (test == 1) {
        if (rank == 0) {
            for(int i = 0; i < N1_local; i++) {
                for(int j = 0; j < N3_local; j++) {
                    global_C[(fila * N1_local + i) * N3 + (colum * N3_local + j)] = local_C[i * N3_local + j];
                }
            }

            for(int p = 1; p < numprocessos; p++) {
                int p_coords[2];
                MPI_Cart_coords(GridComm, p, 2, p_coords);
                for(int i = 0; i < N1_local; i++) {
                    int g_fila = p_coords[0] * N1_local + i;
                    int g_columna = p_coords[1] * N3_local;
                    MPI_Recv(&global_C[g_fila * N3 + g_columna], N3_local, MPI_DOUBLE, p, 2, GridComm, MPI_STATUS_IGNORE);
                }
            }
            printf("Resultat: \n");
            print_matriu("C Global", global_C, N1, N3);
            printf("Correct: \n");
            printf(" 116.0  122.0  128.0  134.0 \n");
            printf(" 372.0  394.0  416.0  438.0 \n");
            
            free(global_A); free(global_B); free(global_C);
        } else {
            for(int i = 0; i < N1_local; i++) {
                MPI_Send(&local_C[i * N3_local], N3_local, MPI_DOUBLE, 0, 2, GridComm);
            }
        }
    }
    free(local_A); free(local_B); free(local_C);
    MPI_Finalize();
    return 0;
}

#endif