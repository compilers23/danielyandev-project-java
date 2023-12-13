package contracts;

import java.util.Queue;

public interface IGenerator {
    void generateBinFile(Queue<String> assemblyCommands, String fileName);
}
