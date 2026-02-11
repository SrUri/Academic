#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>

extern void laplace(float*, float*, float*, float*, int, int, int);

int main(int argc, char* argv[])
{
    const int N = 400, Niter = 8000;
    int ID, numprocessos;

    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &ID);
    MPI_Comm_size(MPI_COMM_WORLD, &numprocessos);

    if (N % numprocessos != 0) {
        if (ID==0) fprintf(stderr, "N ha de ser divisible per numprocessos\n");
        MPI_Abort(MPI_COMM_WORLD, 1);
    }

    int files = N / numprocessos, LD = N;
    size_t tamany = (size_t)files * LD * sizeof(float);

    float *A = (float*)malloc(tamany);
    float *B = (float*)malloc(tamany);
    float *daprev = (float*)malloc(N*sizeof(float));
    float *danext = (float*)malloc(N*sizeof(float));

    if (ID==0) printf("Inici numprocessos=%d (files=%d)\n", numprocessos, files);

    double t0 = MPI_Wtime();
    laplace(A, B, daprev, danext, N, LD, Niter);
    double t1 = MPI_Wtime();

    struct {int p, q; const char *lab;} P[] = {{1, 1, "A[1][1]"},{1, 398, "A[1][398]"}, {398, 1, "A[398][1]"},{398, 398, "A[398][398]"}, {199, 199, "A[199][199]"},{199, 200, "A[199][200]"}, {200, 199, "A[200][199]"},{200, 200, "A[200][200]"}};
    int m = ID * files;
    MPI_Barrier(MPI_COMM_WORLD);

    for (int k=0; k<8; ++k){
        int p = P[k].p, q = P[k].q;
        if (p >= m && p < m+files){
            float v = A[(p-m) * LD + q];
            printf("P%d  %s = %.6f\n", ID, P[k].lab, v);
        }
    }

    double secuencia = 0.0;
    if (argc > 1) secuencia = atof(argv[1]);

    if (ID == 0) {
        printf("Temps numprocessos = %d : %.2f s\n", numprocessos, t1 - t0);
        if (secuencia > 0.0) {
            double Sp = secuencia / (t1 - t0);
            double Ep = Sp / numprocessos;
            printf("Sp = %.2f - Ep = %.2f\n", Sp, Ep);
        } else {
            printf("Passa T1 com argument per a Sp/Ep: \n");
        }
    }

    free(A); free(B); free(daprev); free(danext);
    MPI_Finalize();
    return 0;
}