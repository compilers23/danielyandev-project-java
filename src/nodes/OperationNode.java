package nodes;

public class OperationNode extends Node {
    String operation;

    public OperationNode(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
