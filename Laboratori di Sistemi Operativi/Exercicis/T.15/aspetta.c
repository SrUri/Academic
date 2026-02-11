#include <signal.h>
#include <stdio.h>
#include <unistd.h>

void gestor_señales(int sig) {
    if(sig == SIGINT) {
        printf("He rebut la senyal SIGINT\n");
        fflush(stdout);
    } else if(sig == SIGUSR1) {
        printf("He rebut la senyal SIGUSR1\n");
        fflush(stdout);
    }
}

int main() {
    printf("PID: %d\n", getpid());

    signal(SIGINT, gestor_señales);
    signal(SIGUSR1, gestor_señales);

    while(1) {
        pause();
    }
    
    return 0;
}