# Assignment

you write a program which is a forth like language compiler.

your program will open a file supplied to it as a commandline argument.
let's say the program file is called test.txt.
then if you call your program like this:

```
./prg test.txt
```

it will open the file and read it.
it will convert the file to test.s file which contains assembly

then it sould call 'as' assembler to compile the source to test.o object file.
then it should call 'ld' linker to executable.
the file can contain

* numbers
* math operations: +, -, *
* some forth commands: swap, nip, tuck, dup, drop, over
* ".s" means: print what is at the top of the stack


# Solution

This project contains a compiler which can be simply used with make command

```
make FILE=source.txt [OUTPUT=my_program]
```

It will read provided file path, compile the code and output to specified output path

#### Note: FILE is required. OUTPUT is optional and by default it's "program"

# Advanced usage

If you want to go step by step you'll have to:

### Compile java code

```
javac Compiler.java
```

### Generate assembly file

```
java Compiler /path/to/source.txt
```

This will generate `source.s` file

### Generate object file

```
as -o output.o source.s
```

This will generate `output.o` file

### Generate binary file

```
ld -o output output.o -lc -dynamic-linker /lib64/ld-linux-x86-64.so.2
```

This will generate `output` file that is your compiled source code

### Run the result

```
./output
```
