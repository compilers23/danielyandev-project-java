import converter.Converter;
import exceptions.CompilationErrorException;
import fs.Generator;
import nodes.Node;
import parser.Parser;
import tokenizer.Tokenizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        String fileName = extractFileName(filePath);

        try {
            // Read all bytes from the file into a byte array
            byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));

            // Convert the byte array to a String
            String forthCode = new String(fileBytes);
            compile(forthCode, fileName);

        } catch (IOException e) {
            // Handle IO exception, e.g., file not found
            e.printStackTrace();
        }

    }

    private static String extractFileName(String filePath) {
        // Create a Path object from the file path
        Path path = Paths.get(filePath);

        // Get the file name (including extension) from the path
        String fullFileName = path.getFileName().toString();

        // Find the first occurrence of the dot in the file name
        int firstDotIndex = fullFileName.indexOf(".");

        // Check if a dot was found
        if (firstDotIndex != -1) {
            return fullFileName.substring(0, firstDotIndex);
        } else {
            return fullFileName; // No dot found, return the entire file name
        }
    }

    private static void compile(String forthCode, String fileName) throws CompilationErrorException {
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

        Generator generator = new Generator();
        generator.generateBinFile(assemblyCommands, fileName);
    }
}
