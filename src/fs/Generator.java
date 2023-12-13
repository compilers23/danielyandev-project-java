package fs;

import contracts.IGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Queue;

public class Generator implements IGenerator {
    @Override
    public void generateBinFile(Queue<String> assemblyCommands, String fileName) {
        generateAssemblyFile(assemblyCommands, fileName);
        generateObjectFile(fileName);

        // once object file is ready it can be used to generate bin file,
        // run ld -o something something.o -lc -dynamic-linker /lib64/ld-linux-x86-64.so.2
        String command = "ld -o " + fileName + " " + fileName + ".o -lc -dynamic-linker /lib64/ld-linux-x86-64.so.2";
        execCommand(command);
    }

    private void generateAssemblyFile(Queue<String> assemblyCommands, String fileName) {
        String pathStr = "./" + fileName + ".s";
        Path path = Paths.get(pathStr);

        String[] start = {
                ".data",
                "fmt: .asciz \"%d\"", // format string for printing an integer
                "newline: .ascii \"\\n\"", // ASCII code for newline
                ".text",
                ".global _start",
                "_start:"
        };

        String[] end = {
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
    private void generateObjectFile(String fileName) {
        // run as -o something.o something.s
        String command = "as -o " + fileName +".o " + fileName +".s";
        execCommand(command);
    }

    private void execCommand(String command) {
        System.out.println("Executing command: " + command);
        try {
            // Running the above command
            Runtime run  = Runtime.getRuntime();
            Process process = run.exec(command);
            int exitStatus = process.waitFor();
            System.out.println("Exit status: " + exitStatus);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
