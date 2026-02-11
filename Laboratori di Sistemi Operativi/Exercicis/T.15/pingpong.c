#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>

void manejador(int sig) {

}

int main() {
    pid_t pid;
    signal(SIGUSR1, manejador);

    pid = fork();

    if (pid < 0) {
        perror("Fork falló");
        exit(1);
    }

    if (pid == 0) {
        while(1) {
            pause();
            printf("HIJO: ¡Recibido! Te la devuelvo, papá.\n");
            int padre = getppid();
            kill(padre, SIGUSR1);
        }
    }

    else {
        sleep(1);
        printf("PADRE: ¡Empieza el juego!\n");
        kill(pid, SIGUSR1);
        while(1) {
            pause();
            printf("PADRE: ¡Recibido! Tuh turno, hijo.\n");
            sleep(1);
            kill(pid, SIGUSR1);
        }
    }
    return 0;
}