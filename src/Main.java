import converter.Converter;
import nodes.CommandNode;
import nodes.Node;
import nodes.NumberNode;
import nodes.OperationNode;
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
    public static void main(String[] args) {
        String forthCode = "2 3 +";
//        String forthCode = "3 4 * dup nip 5 - swap tuck 2 / drop over + . cr";
        Tokenizer tokenizer = new Tokenizer(forthCode);
        List<String> tokens = tokenizer.tokenize();

        Parser parser = new Parser(tokens);
        Queue<Node> commandQueue = parser.parse();

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

    private static void printQueue(Queue<Node> queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }


    private static void writeToFile(Queue<String> assemblyCommands) {
        Path path = Paths.get("./source.s");

        String[] start = {
                ".section .data",
                ".section .text",
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