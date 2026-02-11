#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include <time.h>

static double GFLOPS(int n, double t) {
    return (2.0 * n * n * n) / (t * 1e9);
}

double temps() {
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

#ifdef TEST

int main() {
    printf("N \t ijk \t jik \t ikj \t kij \t jki \t kji \t BLOCK \n");

    for (int N = 256; N <= 1536; N += 256) {
        double *A = malloc(N * N * sizeof(double));
        double *B = malloc(N * N * sizeof(double));
        double *C = malloc(N * N * sizeof(double));
        
        ini_matriu(A, N * N);
        ini_matriu(B, N * N);
        printf("%d", N);

        void (*funcs[]) (int, int, int, double*, double*, double*, int, int, int) = {matmatijk, matmatjik, matmatikj, matmatkij, matmatjki, matmatkji};

        for(int v = 0; v < 6; v++) {
            matriu_zero(C, N * N);
            double temps1 = temps();
            funcs[v](N, N, N, A, B, C, N, N, N);
            double temps2 = temps();
            printf("\t%.2f", GFLOPS(N, temps2 - temps1));
        }
        
        matriu_zero(C, N * N);
        int tam_bloc = 64;
        double temps1 = temps();
        matmatblock(N, N, N, A, B, C, N, N, N, tam_bloc, tam_bloc, tam_bloc);
        double temps2 = temps();
        printf("\t%.2f\n", GFLOPS(N, temps2 - temps1));
        free(A); free(B); free(C);
    }
    return 0;
}

#endif