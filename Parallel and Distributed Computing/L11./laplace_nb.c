#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>

void laplace_nb(float *A, float *B, float *daprev, float *danext, int N, int LD, int Niter)
{
    int ID, numprocessos;
    MPI_Comm_rank(MPI_COMM_WORLD, &ID);
    MPI_Comm_size(MPI_COMM_WORLD, &numprocessos);

    int files_proces = N / numprocessos;
    int fila = ID * files_proces;
    int previ = (ID == 0) ? MPI_PROC_NULL : ID - 1;
    int seguent = (ID == numprocessos - 1) ? MPI_PROC_NULL : ID + 1;

    for (int i = 0; i < files_proces; i++) {
        int z = fila + i;
        for (int j = 0; j < N; j++) {
            if (z == 0) A[i * LD + j] = (float) j;
            else if (z == N-1) A[i * LD + j] = (float)(N - j - 1);
            else if (j == 0) A[i * LD + j] = (float) z;
            else if (j == N-1) A[i * LD + j] = (float)(N - z - 1);
            else  A[i * LD + j] = 0.0f;
        }
    }

    MPI_Request request[4];
    MPI_Status status[4];

    for (int it = 0; it < Niter; ++it) {
        MPI_Irecv(daprev, N, MPI_FLOAT, previ, 1, MPI_COMM_WORLD, &request[0]);
        MPI_Irecv(danext, N, MPI_FLOAT, seguent, 0, MPI_COMM_WORLD, &request[1]);
        MPI_Isend(&A[0], N, MPI_FLOAT, previ, 0, MPI_COMM_WORLD, &request[2]);
        MPI_Isend(&A[(files_proces - 1) * LD], N, MPI_FLOAT, seguent, 1, MPI_COMM_WORLD, &request[3]);

        for (int i = 2; i < files_proces - 2 ; i++) {
            for (int j = 1; j < N - 1; j++) {
                B[i * LD + j] = 0.25f * (A[(i - 1)* LD + j] + A[(i + 1) * LD + j] + A[i * LD + j - 1] + A[i * LD + j + 1]);
            }
        }

        MPI_Waitall(4, request, status);

        if (files_proces >= 4) {
            int i = 1;
            for (int j = 1; j < N - 1; j++) {
                B[i * LD + j] = 0.25f * (A[(i - 1)* LD + j] + A[(i + 1)* LD + j] + A[i * LD + j - 1] + A[i * LD + j + 1]);
            }
            i = files_proces-2;
            for (int j = 1; j < N - 1; j++) {
                B[i * LD + j] = 0.25f * (A[(i - 1)* LD + j] + A[(i + 1) * LD + j] + A[i * LD + j - 1] + A[i * LD + j + 1]);
            }
        }

        if (previ != MPI_PROC_NULL) {
            int i = 0;
            for (int j = 1; j < N-1; j++) {
                B[i * LD + j] = 0.25f * (daprev[j] + A[(i + 1)* LD + j] + A[i * LD + j - 1] + A[i * LD + j + 1]);
            }
        }

        if (seguent != MPI_PROC_NULL) {
            int i = files_proces - 1;
            for (int j = 1; j < N-1; j++) {
                B[i * LD + j] = 0.25f * (A[(i - 1)* LD + j] + danext[j] + A[i * LD + j - 1] + A[i * LD + j + 1]);
            }
        }

        for (int i = (previ == MPI_PROC_NULL ? 1 : 0); i < files_proces - (seguent == MPI_PROC_NULL ? 1 : 0); i++)
            for (int j = 1; j < N-1; j++) {
                A[i * LD + j] = B[i * LD + j];
            }
    }
}