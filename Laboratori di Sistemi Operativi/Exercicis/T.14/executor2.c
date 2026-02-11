#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <sys/wait.h>

int main (int argc, char *argv[]) {
    pid_t pid;

    if (argc < 2) {
        printf("Uso: ./programa <ruta_ejecutable>\n");
        exit(1);
    }

    pid=fork();

    if(pid == 0) {
        execlp(argv[1], argv[1], NULL);
        exit(1);
    } else if (pid > 0) {
        waitpid(pid, NULL, 0);
    } else {
        return 1;
    }
}