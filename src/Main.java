import tokenizer.Tokenizer;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String forthCode = "5 3 * dup + . cr";
        Tokenizer tokenizer = new Tokenizer();
        List<String> tokens = tokenizer.tokenize(forthCode);

        // Display the tokens
        for (String token : tokens) {
            System.out.println(token);
        }
    }
}