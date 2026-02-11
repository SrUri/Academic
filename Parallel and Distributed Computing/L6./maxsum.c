#include <omp.h>
#include <math.h>

double maxsum(int N, int LD, double *A, int NT) {
    
    double global_maxima = 0.0;
    omp_set_num_threads(NT);

    #pragma omp parallel
    {
        double local_maxima = 0.0;
        #pragma omp for schedule(static)
        for (int i = 0; i < N; i++) {
            double suma = 0.0;
            for (int j = 0; j < N; j++) {
                suma += sqrt(A[i * LD + j]);
            }
            if (suma > local_maxima) {
                local_maxima = suma;
            }
        }

        #pragma omp critical
        {
            if (local_maxima > global_maxima) {
                global_maxima = local_maxima;
            }
        }
    }
    return global_maxima;
}