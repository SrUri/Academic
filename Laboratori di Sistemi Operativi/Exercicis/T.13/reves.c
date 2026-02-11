#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>

int main(int argc, char *argv[]) {
    int fd;
    char buffer;

    if (argc != 2) {
        printf("Uso: %s <nombre_archivo>\n", argv[0]);
        return 1;
    }

    if((fd=open(argv[1], O_RDONLY, 0)) < 0) {
        perror("Error opening file");
        return 1;
    }

    lseek(fd, 0, SEEK_END);

    while((lseek(fd, -1, SEEK_CUR)) >= 0) {
        read(fd, &buffer, 1);
        write(STDOUT_FILENO, &buffer, 1);
        lseek(fd, -1, SEEK_CUR);
    }
    
    write(STDOUT_FILENO, "\n", 1);

    close(fd);
}