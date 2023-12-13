all: create-bin-dir compile

create-bin-dir:
	mkdir -p "bin"

compile:
	javac -d bin -sourcepath src src/Compiler.java
