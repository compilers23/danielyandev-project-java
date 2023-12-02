package converter;

import contracts.IConverter;
import nodes.Node;
import nodes.NumberNode;
import nodes.OperationNode;

import java.util.LinkedList;
import java.util.Queue;

public class Converter implements IConverter {
    private final Queue<Node> queue;

    private final Queue<String> assemblyQueue = new LinkedList<>();

    public Converter(Queue<Node> queue) {
        this.queue = queue;
    }

    @Override
    public void convertToAssembly() {
        while (!queue.isEmpty()) {
            Node node = queue.poll();

            if (node instanceof NumberNode) {
                int value = ((NumberNode) node).getValue();

                assemblyQueue.add("pushq $" + value);
            } else if (node instanceof OperationNode) {
                String operation = ((OperationNode) node).getOperation();

                // in case of operations we pop 2 top values from stack,
                // perform operation and push back to the top of the stack

                String operationCode = "";
                switch (operation) {
                    case "+":
                        operationCode = "addq";
                        break;
                    case "-":
                        operationCode = "subq";
                        break;
                    case "*":
                        operationCode = "imulq";
                        break;
                }

                assemblyQueue.add("popq  %rax");
                assemblyQueue.add("popq  %rbx");
                assemblyQueue.add(operationCode + " %rbx, %rax");
                assemblyQueue.add("pushq %rax");
            }
        }
    }

    public Queue<String> getAssemblyQueue() {
        return assemblyQueue;
    }
}
