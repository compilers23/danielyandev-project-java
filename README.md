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
make all
```

It will compile the project into bin directory, then you can use bin/Compiler to compile your source file

```
java -cp bin Compiler /path/to/something.txt
```

or

```
cd bin
java Compiler /path/to/something.txt
```

Note: This command will generate a file called "something", which you can run

### Run the result

```
./something
```

# Syntax

<table>
<tr>
    <th>Input</th>
    <th>Result</th>
    <th>Example</th>
</tr>

<tr>
    <td>[number]</td>
    <td>Push number to stack</td>
    <td>5</td>
</tr>
<tr>
    <td>+</td>
    <td>Add first and second elements in stack and push to the top</td>
    <td>5 6 + (will do 6 + 5)</td>
</tr>
<tr>
    <td>-</td>
    <td>Subtract second element in stack from the first and push to the top</td>
    <td>5 6 - (will do 6 - 5)</td>
</tr>
<tr>
    <td>*</td>
    <td>Multiply first and second elements in stack and push to the top</td>
    <td>5 6 * (will do 6 * 5)</td>
</tr>
<tr>
    <td>.s</td>
    <td>Print top element of stack</td>
    <td>5 .s (will print 5)</td>
</tr>
<tr>
    <td>cr</td>
    <td>Move cursor to the next line</td>
    <td>5 .s cr (will print 5 and move to a new line)</td>
</tr>
<tr>
    <td>dup</td>
    <td>Duplicate top element of stack</td>
    <td>5 dup (first and second elements of stack will be 5)</td>
</tr>
<tr>
    <td>swap</td>
    <td>Swap first two elements in stack</td>
    <td>5 6 swap (stack was {5, 6}, after swap it will be {6, 5})</td>
</tr>
<tr>
    <td>nip</td>
    <td>Delete second element from stack</td>
    <td>5 6 nip (stack was {5, 6}, after nip it will be {6})</td>
</tr>
<tr>
    <td>drop</td>
    <td>Delete first element from stack</td>
    <td>5 6 drop (stack was {5, 6}, after drop it will be {5})</td>
</tr>
<tr>
    <td>over</td>
    <td>Copy second element from stack and push to top</td>
    <td>5 6 over (stack was {5, 6}, after over it will be {5, 6, 5})</td>
</tr>
<tr>
    <td>tuck</td>
    <td>Perform commands swap and over</td>
    <td>5 6 tuck (stack was {5, 6}, after tuck it will be {6, 5, 6})</td>
</tr>
<tr>
    <td>(comment string)</td>
    <td>Put comments in code</td>
    <td>5 6 + (this is a comment describing addition process and will not be compiled)</td>
</tr>
</table>

# Authors
- [Ruben Danielyan](https://github.com/danielyandev)
- [Tigran Asatryan](https://github.com/atg1996)
