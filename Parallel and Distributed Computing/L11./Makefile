CC      = mpicc
CFLAGS  = -O2 -Wall -std=c99
TARGET  = laplace_nb
OBJS    = main_nb.o laplace_nb.o

all: $(TARGET)

$(TARGET): $(OBJS)
	$(CC) $(CFLAGS) -o $@ $^

%.o: %.c
	$(CC) $(CFLAGS) -c $<

clean:
	rm -f $(TARGET) $(OBJS)
