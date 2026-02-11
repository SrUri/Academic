#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>

int main(int argc, char *argv[]) {
    int fd;
    pid_t pid;

    if ((fd = open("archivo.txt", O_CREAT | O_WRONLY | O_TRUNC, 0644)) < 0) {
        perror("Error opening");
        return 1;
    }

    if((pid=fork()) < 0) {
        perror("Error fork");
        return 1;
    }

    if(pid > 0) {
        write(fd, "Father\n", 7);
    }

    if(pid == 0) {
        write(fd, "Son\n", 4);
    }
    close(fd);
}