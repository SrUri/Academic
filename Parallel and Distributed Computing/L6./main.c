#include <stdio.h>
#include <stdlib.h>
#include <omp.h>

double get_cur_time();
double maxsum(int N, int LD, double *A, int NT);

int main() {
    int N_nombres[] = {800, 1600, 2400, 3200, 4000, 4800, 5400};
    int N_threads[] = {1, 2, 4, 8};

    for (int x = 0; x < 7; x++) {
        int N = N_nombres[x];
        int LD = N;
        double T1 = 0.0;
        double *A = (double *)malloc(sizeof(double) * LD * LD);

        srand(0);
        for (int i = 0; i < N * LD; i++) {
            A[i] = rand() % 100;
        }
        printf("\n==== N = %d ====\n", N);

        for (int y = 0; y < 4; y++) {
            int NT = N_threads[y];
            double t_inicial = get_cur_time();
            double resultado = maxsum(N, LD, A, NT);
            double t_final = get_cur_time();
            double tiempo = t_final - t_inicial;
            printf("NT = %d | Resultado = %.3f | Tiempo = %.6f s\n", NT, resultado, tiempo);

            if (NT == 1) {
                T1 = tiempo;
            }
            else {
                double speedup = T1 / tiempo;
                double eficiencia = speedup / NT;
                printf(" → Speed-up: %.3f | Eficiencia: %.3f\n", speedup, eficiencia);
            }
        }
        free(A);
    }
    return 0;
}
