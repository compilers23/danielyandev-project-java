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

public class Compiler {
    public static void main(String[] args) throws CompilationErrorException {

        // Check if a file path is provided as a command-line argument
        if (args.length == 0) {
            System.out.println("Usage: java Compiler <file-path>");
            System.exit(1); // Exit with an error code
        }

        // Get the file path from the command-line argument
        String filePath = args[0];

        // if provided not an absolute path
        // we need to find path from root dir
        if (filePath.charAt(0) != '/') {
            filePath = "../" + filePath;
        }

        try {
            // Read all bytes from the file into a byte array
            byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));

            // Convert the byte array to a String
            String forthCode = new String(fileBytes);

            compile(forthCode);

        } catch (IOException e) {
            // Handle IO exception, e.g., file not found
            e.printStackTrace();
        }

    }

    private static void compile(String forthCode) throws CompilationErrorException {
        // tokenize code
        Tokenizer tokenizer = new Tokenizer(forthCode);
        List<String> tokens = tokenizer.tokenize();

        // parse tokens
        Parser parser = new Parser(tokens);
        Queue<Node> commandQueue = parser.parse();

        // don't continue if there were errors while parsing
        if (parser.getErrors().size() > 0) {
            throw new CompilationErrorException();
        }

        // convert commands to assembly
        Converter converter = new Converter(commandQueue);
        converter.convertToAssembly();

        // write assembly code to output file
        Queue<String> assemblyCommands = converter.getAssemblyQueue();
        writeToFile(assemblyCommands);
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