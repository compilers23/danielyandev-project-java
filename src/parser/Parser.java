package parser;

import contracts.IParser;
import nodes.CommandNode;
import nodes.Node;
import nodes.NumberNode;
import nodes.OperationNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Parser implements IParser {
    private final List<String> tokens;

    private final Queue<Node> commandQueue = new LinkedList<>();

    private final List<String> errors = new ArrayList<>();

    public Parser(List<String> tokens) {
        this.tokens = tokens;
    }

    @Override
    public Queue<Node> parse() {
        parseTokens();
        return commandQueue;
    }


    private void parseTokens() {

        for (String token: tokens) {
            if (isNumeric(token)) {
                // If the token is a number, create a NumberNode and push to queue
                commandQueue.add(
                        new NumberNode(Integer.parseInt(token))
                );

            } else if (isOperator(token)) {
                // If the token is an operator, create an OperationNode
                commandQueue.add(
                        new OperationNode(token)
                );
            } else if (isCommand(token)) {
                // If the token is a command, create a CommandNode
                commandQueue.add(
                        new CommandNode(token)
                );
            } else {
                // Handle other cases as needed
                String message = "Unexpected token: " + token;
                System.err.println(message);
                errors.add(message);
            }
        }

    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+");
    }

    private boolean isOperator(String str) {
        return str.matches("[+*-]"); // Add other operators as needed
    }

    private boolean isCommand(String str) {
        // You can add conditions to identify command tokens
        return str.matches("dup|swap|nip|tuck|drop|over|cr|\\.s"); // Modify as needed, todo move to constants
    }

    public List<String> getErrors() {
        return errors;
    }

}
