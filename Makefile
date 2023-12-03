all: create-bin-dir compile generate-assembly-file generate-object-file generate-bin-file


create-bin-dir:
	mkdir -p "bin"

compile:
	cd src && javac Compiler.java -d ../bin

generate-assembly-file:
	cd bin && java Compiler $(FILE)

generate-object-file:
	cd bin && as -o output.o source.s

generate-bin-file:
	cd bin && ld -o output output.o -lc -dynamic-linker /lib64/ld-linux-x86-64.so.2 && cp output ../output
