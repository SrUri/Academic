#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>

void laplace(float *A, float *B, float *daprev, float *danext, int N, int LD, int Niter)
{
    int ID, numprocessos;
    MPI_Comm_rank(MPI_COMM_WORLD, &ID);
    MPI_Comm_size(MPI_COMM_WORLD, &numprocessos);
    MPI_Status status;

    int files_proces = N / numprocessos;
    int fila = ID * files_proces;
    int fila_local = files_proces;
    int previ = (ID == 0) ? MPI_PROC_NULL : ID - 1;
    int seguent = (ID == numprocessos - 1) ? MPI_PROC_NULL : ID + 1;

    for (int i = 0; i < fila_local; i++) {
        int z = fila + i;
        for (int j = 0; j < N; j++) {
            if (z == 0) A[i * LD + j] = (float)j;
            else if (z == N - 1) A[i * LD + j] = (float)(N - j - 1);
            else if (j == 0) A[i * LD + j] = (float)z;
            else if (j == N - 1) A[i * LD + j] = (float)(N - z - 1);
            else A[i * LD + j] = 0.0f;
        }
    }

    for (int iter = 0; iter < Niter; iter++) {
        if (iter % 1000 == 0 && ID == 0) {
            printf("[Iter %d] okay\n", iter);
            fflush(stdout);
        }
        
        //DEADLOCKS - HALO EXCH
        MPI_Sendrecv(&A[0], N, MPI_FLOAT, previ, 0, daprev, N, MPI_FLOAT, previ, 1, MPI_COMM_WORLD, &status);
        MPI_Sendrecv(&A[(fila_local - 1)* LD], N, MPI_FLOAT, seguent, 1, danext, N, MPI_FLOAT, seguent, 0, MPI_COMM_WORLD, &status);

        for (int i = 1; i < fila_local - 1; i++) {
            for (int j = 1; j < N - 1; j++) {
                B[i * LD + j] = 0.25f * (A[(i - 1)* LD + j] + A[(i + 1)* LD + j] + A[i * LD + j - 1] + A[i * LD + j + 1]);
            }
        }

        if (ID != 0) {
            int i = 0;
            for (int j = 1; j < N - 1; j++) {
                B[i * LD + j] = 0.25f * (daprev[j] + A[(i + 1)* LD + j] + A[i * LD + j - 1] + A[i * LD + j + 1]);
            }
        }

        if (ID != numprocessos - 1) {
            int i = fila_local - 1;
            for (int j = 1; j < N - 1; j++) {
                B[i * LD + j] = 0.25f * (A[(i - 1) * LD + j] + danext[j] + A[i * LD + j - 1]  + A[i * LD + j + 1]);
            }
        }

        for (int i = (ID == 0 ? 1 : 0); i < fila_local - (ID == numprocessos - 1 ? 1 : 0); i++)
            for (int j = 1; j < N - 1; j++) {
                A[i * LD + j] = B[i * LD + j];
            }
    }
}