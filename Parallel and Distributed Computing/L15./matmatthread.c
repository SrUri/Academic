#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/time.h>
#include <omp.h>

static double GFLOPS(int n1, int n2, int n3, double t) {
    return (2.0 * (double)n1 * (double)n2 * (double)n3) / (t * 1e9);
}

static double temps() {
    struct timeval t;
    gettimeofday(&t, NULL);
    return t.tv_sec + t.tv_usec * 1e-6;
}

void ini_matriu(double *M, int tamany) {
    for (int i = 0; i < tamany; i++) {
        M[i] = ((double)rand() / RAND_MAX);
    }
}

void matriu_zero(double *M, int tamany) {
    for (int i = 0; i < tamany; i++) {
        M[i] = 0.0;
    }
}

void matmatijk(int ldA, int ldB, int ldC, double *A, double *B, double *C, int N1, int N2, int N3) {
    for (int i = 0; i < N1; i++) {
        for (int j = 0; j < N3; j++) {
            for (int k = 0; k < N2; k++) {
                C[i * ldC + j] += A[i * ldA + k] * B[k * ldB + j];
            }
        }
    }
}

void matmatjik(int ldA, int ldB, int ldC, double *A, double *B, double *C, int N1, int N2, int N3) {
    for (int j = 0; j < N3; j++) {
        for (int i = 0; i < N1; i++) {
            for (int k = 0; k < N2; k++) {
                C[i * ldC + j] += A[i * ldA + k] * B[k * ldB + j];
            }
        }
    }
}

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

void matmatkij(int ldA, int ldB, int ldC, double *A, double *B, double *C, int N1, int N2, int N3) {
    for (int k = 0; k < N2; k++) {
        for (int i = 0; i < N1; i++) {
            double a = A[i * ldA + k];
            for (int j = 0; j < N3; j++) {
                C[i * ldC + j] += a * B[k * ldB + j];
            }
        }
    }
}

void matmatjki(int ldA, int ldB, int ldC, double *A, double *B, double *C, int N1, int N2, int N3) {
    for (int j = 0; j < N3; j++) {
        for (int k = 0; k < N2; k++) {
            double b = B[k * ldB + j];
            for (int i = 0; i < N1; i++) {
                C[i * ldC + j] += A[i * ldA + k] * b;
            }
        }
    }
}

void matmatkji(int ldA, int ldB, int ldC, double *A, double *B, double *C, int N1, int N2, int N3) {
    for (int k = 0; k < N2; k++) {
        for (int j = 0; j < N3; j++) {
            double b = B[k * ldB + j];
            for (int i = 0; i < N1; i++) {
                C[i * ldC + j] += A[i * ldA + k] * b;
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

    #pragma omp parallel for schedule(static)
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

#ifdef TEST 

int main(void) {
    int Ns[2] = {1024, 2048};
    int dimensio = 256;

    struct { 
        int NT, NTROW, NTCOL;
    } examples[] = {{1, 1, 1}, {2, 1, 2}, {4, 2, 2}, {8, 2, 4}};

    printf("N   | mode           | time (s) | Gflops   | SpeedUp  | Eff\n");

    for (int i = 0; i < 2; i++) {
        int N = Ns[i];
        int ldA = N, ldB = N, ldC = N;
        size_t tamany = (size_t)N * N;

        double *A = malloc(sizeof(double) * tamany);
        double *B = malloc(sizeof(double) * tamany);
        double *C = malloc(sizeof(double) * tamany);

        ini_matriu(A, tamany);
        ini_matriu(B, tamany);
        matriu_zero(C, tamany);

        double temps1 = temps();
        matmatblock(ldA, ldB, ldC, A, B, C, N, N, N, dimensio, dimensio, dimensio);
        double temps2 = temps();
        double temps_final = temps2 - temps1;
        double GFL = GFLOPS(N, N, N, temps_final);

        printf("%4d| %-14s | %8.3f | %8.2f | %8.2f | %5.2f\n", N, "block(seq)", temps_final, GFL, 1.0, 1.0);

        for (int c = 0; c < (int)(sizeof(examples) / sizeof(examples[0])); c++) {
            int NT = examples[c].NT;

            omp_set_num_threads(NT);
            matriu_zero(C, tamany);

            double temps1 = temps();
            matmatthread(ldA, ldB, ldC, A, B, C, N, N, N, dimensio, dimensio, dimensio, examples[c].NTROW, examples[c].NTCOL);
            double temps2 = temps();
            double temps_final2 = temps2 - temps1;
            double GFL = GFLOPS(N, N, N, temps_final2);
            double speedup = temps_final / temps_final2; 
            double eff = speedup / (double)NT;

            char caracters[32];
            snprintf(caracters, sizeof(caracters), "threads=%d", NT);
            printf("%4d| %-14s | %8.3f | %8.2f | %8.2f | %5.2f\n", N, caracters, temps_final2, GFL, speedup, eff);
        }
        free(A); free(B); free(C);
    }
    {
        int N = 8, dimensions = 2;
        int ld = N;
        size_t tamany = (size_t)N * N;
        double *A = malloc(sizeof(double) * tamany);
        double *B = malloc(sizeof(double) * tamany);
        double *C = malloc(sizeof(double) * tamany);

        ini_matriu(A, tamany);
        ini_matriu(B, tamany);
        matriu_zero(C, tamany);

        double temps1 = temps();
        matmatblock(ld, ld, ld, A, B, C, N, N, N, dimensions, dimensions, dimensions);
        double temps2 = temps();
        double temps_final = temps2 - temps1;

        printf("\n N=8 block=2 time=%.6f s\n", temps_final);
        int celdas[][2] = {{1, 1}, {1, 2}, {2, 2}, {2, 4}};

        for (int g = 0; g < 4; g++) {
            int NTROW = celdas[g][0], NTCOL = celdas[g][1];
            int NT = NTROW * NTCOL;

            omp_set_num_threads(NT);
            matriu_zero(C, tamany);

            double temps1 = temps();
            matmatthread(ld, ld, ld, A, B, C, N, N, N, dimensions, dimensions, dimensions, NTROW, NTCOL);
            double temps2 = temps();
            double temps_final2 = temps2 - temps1;
            double speedup = temps_final / temps_final2;
            double eff = speedup / (double)NT;

            printf("celda %dx%d NT=%d time=%.6f speedup=%.3f eff=%.3f\n", NTROW, NTCOL, NT, temps_final2, speedup, eff);
        }
        free(A); free(B); free(C);
    }
    return 0;
}

#endif
