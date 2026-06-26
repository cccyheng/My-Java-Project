CC = D:/mingw64/bin/gcc
CFLAGS = -g
LDFLAGS = -g
OBJS = main.o course.o student.o teacher.o
TARGET = main.exe

$(TARGET): $(OBJS)
	$(CC) $(LDFLAGS) -o $@ $^

%.o: %.c
	$(CC) $(CFLAGS) -c -o $@ $<

clean:
	del /Q $(OBJS) $(TARGET) 2>nul

.PHONY: clean
