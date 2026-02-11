#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>

int main(int argc, char *argv[]) {
    int fd;
    pid_t pid;

    if((pid=fork()) < 0) {
        perror("Error fork");
        return 1;
    }

    if(pid > 0) {
        if ((fd = open("test.txt", O_CREAT | O_WRONLY | O_TRUNC, 0644)) < 0) {
            perror("Error opening");
            return 1;
        } else {
            write(fd, "Father\n", 7);
            close(fd);
        }
    }

    if(pid == 0) {
        if ((fd = open("test.txt", O_CREAT | O_WRONLY | O_TRUNC, 0644)) < 0) {
            perror("Error opening");
            return 1;
        } else {
            write(fd, "Son\n", 4);
            close(fd);
        }
    }
}