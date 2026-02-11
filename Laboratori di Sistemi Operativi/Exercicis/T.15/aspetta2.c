#include <signal.h>
#include <stdio.h>
#include <unistd.h>

void gestor_senyals(int sig) {
    if(sig == SIGINT) {
        printf("He rebut la senyal SIGINT\n");
        fflush(stdout);
    } else if(sig == SIGUSR1) {
        printf("He rebut la senyal SIGUSR1\n");
        fflush(stdout);
    }
}

int main() {
    pid_t pid;
    pid = fork();

    if(pid < 0) {
        perror("Error fork");
        return 1;
    } else if(pid == 0) {
        printf("PID: %d\n", getpid());
        signal(SIGINT, gestor_senyals);
        signal(SIGUSR1, gestor_senyals);
        while(1) {
            pause();
        }
    } else {
        sleep(5);
        kill(pid, SIGUSR1);
        sleep(1);
        kill(pid, SIGINT);
        sleep(1);
        getchar();
        printf("Recibido");
        fflush(stdout);
        kill(pid, SIGTERM);
        while(1) {
            pause();
        }
    }
    return 0;
}