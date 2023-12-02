package converter;

import contracts.IConverter;
import nodes.CommandNode;
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

                assemblyQueue.add("popq  %rax"); // pop first value
                assemblyQueue.add("popq  %rbx"); // pop second value
                assemblyQueue.add(operationCode + " %rbx, %rax"); // perform operation
                assemblyQueue.add("pushq %rax"); // push to stack
            } else if (node instanceof CommandNode) {
                // available commands
                // dup|swap|nip|tuck|drop|over|cr
                String command = ((CommandNode) node).getCommand();

                switch (command) {
                    case "dup":
                        assemblyQueue.add("movq (%rsp), %rax"); // copy the value from the top of the stack to %rax
                        assemblyQueue.add("pushq %rax"); // push to stack
                        break;
                    case "swap":
                        assemblyQueue.add("popq  %rax"); // pop first value
                        assemblyQueue.add("popq  %rbx"); // pop second value
                        assemblyQueue.add("pushq %rax"); // push to stack first
                        assemblyQueue.add("pushq %rbx"); // push to stack second
                        break;
                    case "nip":
                        break;
                    case "tuck":
                        break;
                    case "drop":
                        break;
                    case "over":
                        break;
                }
            }
        }
    }

    public Queue<String> getAssemblyQueue() {
        return assemblyQueue;
    }
}
