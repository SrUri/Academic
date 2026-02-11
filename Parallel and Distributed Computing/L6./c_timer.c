#include <sys/time.h>

double get_cur_time() {
    struct timeval tiempo;
    gettimeofday(&tiempo, NULL);
    return (double)tiempo.tv_sec + (double)tiempo.tv_usec / 1e6;
}
