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

                String command = ((CommandNode) node).getCommand();

                switch (command) {
                    case "dup":
                        asmDup();
                        break;
                    case "swap":
                        asmSwap();
                        break;
                    case "nip":
                        asmSwap();
                        asmDrop();
                        break;
                    case "tuck":
                        asmSwap();
                        asmOver();
                        break;
                    case "drop":
                        asmDrop();
                        break;
                    case "over":
                        asmOver();
                        break;
                    case "cr":
                        asmCr();
                        break;
                    case ".s":
                        asmPrintTop();
                        break;
                }
            }
        }
    }

    public Queue<String> getAssemblyQueue() {
        return assemblyQueue;
    }

    private void asmDup() {
        assemblyQueue.add("movq (%rsp), %rax"); // copy the value from the top of the stack to %rax
        assemblyQueue.add("pushq %rax"); // push to stack
    }

    private void asmSwap() {
        assemblyQueue.add("popq  %rax"); // pop first value
        assemblyQueue.add("popq  %rbx"); // pop second value
        assemblyQueue.add("pushq %rax"); // push to stack first
        assemblyQueue.add("pushq %rbx"); // push to stack second
    }

    private void asmDrop() {
        assemblyQueue.add("popq %rax"); // pop the top item from the stack
    }

    private void asmOver() {
        assemblyQueue.add("popq  %rax"); // pop first value
        assemblyQueue.add("popq  %rbx"); // pop second value
        assemblyQueue.add("pushq %rbx"); // push to stack second
        assemblyQueue.add("pushq %rax"); // push to stack first
        assemblyQueue.add("pushq %rbx"); // push to stack second
    }

    private void asmCr() {
        assemblyQueue.add("movq $1, %rax"); // syscall: write
        assemblyQueue.add("movq $1, %rdi"); // file descriptor: STDOUT_FILENO
        assemblyQueue.add("lea newline, %rsi"); // pointer to the newline character
        assemblyQueue.add("movq $1, %rdx"); // number of bytes to write (1 byte for newline)
        assemblyQueue.add("syscall");
    }

    private void asmPrintTop() {
        assemblyQueue.add("movq (%rsp), %rsi"); // move result of your calculation to rsi
        assemblyQueue.add("movq $0, %rax");
        assemblyQueue.add("pushq %rax");
        assemblyQueue.add("lea fmt(%rip), %rdi"); // another argument for printf
        assemblyQueue.add("call printf");
        assemblyQueue.add("mov $0, %edi"); // file descriptor 0 (stdout)
        assemblyQueue.add("call fflush"); // flush the buffer
    }
}
