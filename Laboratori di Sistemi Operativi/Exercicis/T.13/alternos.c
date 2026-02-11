#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>

#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>

int main (int argc, char *argv[]) {
    int fd;
    char buffer;

    if (argc < 2) {
        printf("Uso: %s <nombre_archivo>\n", argv[0]);
        return 1;
    }

    if ((fd = open(argv[1], O_RDONLY)) < 0){
        perror("Error opening file");
        return 1;
    } 
    
    while (read(fd, &buffer, 1) > 0) {
        write(STDOUT_FILENO, &buffer, 1);
        lseek(fd, 1, SEEK_CUR);
    }
    
    close(fd);
    
    return 0;
}