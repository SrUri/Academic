CC      = mpicc
CFLAGS  = -O2 -Wall -std=c99
TARGET  = laplace_mpi
OBJS    = main.o laplace.o

all: $(TARGET)

$(TARGET): $(OBJS)
	$(CC) $(CFLAGS) -o $@ $^

%.o: %.c
	$(CC) $(CFLAGS) -c $<

clean:
	rm -f $(TARGET) $(OBJS)