#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>
#include <sys/types.h>

int main() {
    int fd[2];
    pid_t pid;
    
    if (pipe(fd) == -1) {
        perror("Pipe failed");
        exit(1);
    }

    pid = fork();
    
    if (pid < 0) {
        perror("Fork failed");
        exit(1);
    }

    if(pid > 0) {
        close(fd[0]);
        srand(time(NULL));
        for (int i = 0; i < 10; i++) {
            int num = rand() % 100;
            write(fd[1], &num, sizeof(int));
            printf("Sent number: %d\n", num);
        }
        close(fd[1]);
    } 
    else {
        close(fd[1]);
        for (int i = 0; i < 10; i++) {
            int num=0;
            read(fd[0], &num, sizeof(int));
            printf("Received number: %d\n", num);
        }
        close(fd[0]);
    }
    return 0; 
}