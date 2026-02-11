#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>
#include <sys/wait.h>

int main(int argc, char *argv[]) {
    int fd[2]; //[0] --> lectura, [1] --> escritura
    int numero;
    pid_t pid;

    if(pipe(fd) == -1) {
        perror("Pipe");
        return 1;
    }

    pid = fork();

    if(pid == 0) {
        close(fd[1]);
        
        for(int i=0; i<10; i++) {
            read(fd[0], &numero, sizeof(int));
            printf("Printado numero %d\n", numero);
        }

        close(fd[0]);
        return 0;
    } else {
        close(fd[0]);
        srand(time(NULL));

        for(int i=0; i<10; i++) {
            numero=rand() % 100;
            write(fd[1], &numero, sizeof(int));
            sleep(1);
        }
        close(fd[1]);
        wait(NULL);
        return 0;
    }
}