package parser;

import contracts.IParser;

import java.util.List;

public class Parser implements IParser {
    private final List<String> tokens;
    private int currentTokenIndex;

    public Parser(List<String> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
    }

    public void parse() {
        while (currentTokenIndex < tokens.size()) {
            parseToken();
        }
    }

    private void parseToken() {
        String token = tokens.get(currentTokenIndex);

        switch (token) {
            case "+":
                // Handle addition
                System.out.println("Addition");
                break;
            case "-":
                // Handle subtraction
                System.out.println("Subtraction");
                break;
            case "*":
                // Handle multiplication
                System.out.println("Multiplication");
                break;
            case "/":
                // Handle division
                System.out.println("Division");
                break;
            case ".":
                // Handle printing the top of the stack
                System.out.println("Print Top of Stack");
                break;
            case "cr":
                // Handle carriage return
                System.out.println("Carriage Return");
                break;

            case "dup":
                // Handle dup
                System.out.println("Duplication");
                break;
            case "swap":
                // Handle swap
                System.out.println("Swap");
                break;
            case "nip":
                // Handle nip
                System.out.println("Nip");
                break;
            case "tuck":
                // Handle tuck
                System.out.println("Tuck");
                break;
            case "drop":
                // Handle drop
                System.out.println("Drop");
                break;
            case "over":
                // Handle over
                System.out.println("Over");
                break;
            // Add cases for other commands, numbers, etc.

            default:
                // Assume it's a number
                try {
                    int number = Integer.parseInt(token);
                    System.out.println("Number: " + number);
                } catch (NumberFormatException e) {
                    System.err.println("Unexpected token: " + token);
                }
        }

        // Move to the next token
        currentTokenIndex++;
    }
}
