all: create-bin-dir build

create-bin-dir:
	mkdir -p "bin"

build:
	javac -d bin -sourcepath src src/Compiler.java
