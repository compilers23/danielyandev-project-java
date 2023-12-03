import converter.Converter;
import exceptions.CompilationErrorException;
import nodes.Node;
import parser.Parser;
import tokenizer.Tokenizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class Main {
    public static void main(String[] args) throws CompilationErrorException {
        String forthCode = "2 7 * dup nip 4 - swap tuck drop over + .s cr";
        Tokenizer tokenizer = new Tokenizer(forthCode);
        List<String> tokens = tokenizer.tokenize();

        Parser parser = new Parser(tokens);
        Queue<Node> commandQueue = parser.parse();

        if (parser.getErrors().size() > 0) {
            throw new CompilationErrorException();
        }

        Converter converter = new Converter(commandQueue);
        converter.convertToAssembly();

        Queue<String> assemblyCommands = converter.getAssemblyQueue();

//        while (!assemblyCommands.isEmpty()) {
//            System.out.println(assemblyCommands.poll());
//        }

        writeToFile(assemblyCommands);


//        printQueue(commandQueue);

//        for (String token: tokens) {
//            System.out.println(token);
//        }

    }

    private static void writeToFile(Queue<String> assemblyCommands) {
        Path path = Paths.get("./source.s");

        String[] start = {
                ".data",
                "fmt: .asciz \"%d\\n\"", // format string for printing an integer followed by a newline
                "newline: .ascii \"\\n\"", // ASCII code for newline
                ".text",
                ".global _start",
                "",
                "_start:"
        };

        String[] end = {
                "",
                "movq %rax, %rdi",
                "movq $60, %rax",
                "syscall"
        };

        try {
            Files.write(path, Arrays.asList(start));
            Files.write(path, assemblyCommands, StandardOpenOption.APPEND);
            Files.write(path, Arrays.asList(end), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}