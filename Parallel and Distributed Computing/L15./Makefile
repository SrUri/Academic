CC = gcc
CFLAGS = -Wall -Wextra -O3
LDFLAGS = -pthread -lm

TARGET = matmatthread
SOURCE = matmatthread.c

all: $(TARGET)

$(TARGET): $(SOURCE)
	$(CC) $(CFLAGS) -o $(TARGET) $(SOURCE) $(LDFLAGS)

clean:
	rm -f $(TARGET)

run: $(TARGET)
	./$(TARGET)

.PHONY: all clean run

