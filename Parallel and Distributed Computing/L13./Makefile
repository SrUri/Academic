CC      := gcc
CFLAGS  := -O3 -std=c11 -Wall -Wextra -pedantic -march=native
LDFLAGS :=
TARGET  := matmat2
SRCS    := matmat2.c
OBJS    := $(SRCS:.c=.o)

all: $(TARGET)

$(TARGET): $(OBJS)
	$(CC) $(CFLAGS) $(OBJS) -o $@ $(LDFLAGS)

%.o: %.c
	$(CC) $(CFLAGS) -c $< -o $@

clean:
	rm -f $(TARGET) $(OBJS)

.PHONY: all clean

