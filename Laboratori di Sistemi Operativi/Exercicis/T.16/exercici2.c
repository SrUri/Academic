#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>
#include <sys/wait.h>

int main(int argc, char *argv[]) {
    pid_t pid;
    int fd[2]; //[0] --> lectura, [1] --> escritura
    int mult;
    int numero;

    if(pipe(fd) == -1) {
        perror("Pipe");
        return 1;
    }

    pid = fork();

    if(pid == 0) {
        close(fd[1]);
        while(1) {
            read(fd[0], &numero, sizeof(int));
            printf("Numero recibido %d\n", numero);
            if(numero==0) {
                break;
            }
        }
        close(fd[0]);

    } else {
        close(fd[0]);
        while(1) {
            scanf("%d", &numero);
            mult=numero*numero;
            write(fd[1], &mult, sizeof(int));
            if(numero==0) {
                break;
            }
        }
        close(fd[1]);
        wait(NULL);
    }
    return 0;
}