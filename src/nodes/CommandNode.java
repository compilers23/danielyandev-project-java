package nodes;

public class CommandNode extends Node {
    String command;

    public CommandNode(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
