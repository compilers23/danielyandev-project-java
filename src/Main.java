import parser.Parser;
import tokenizer.Tokenizer;

import java.util.List;

public class Main {
    public static void main(String[] args) {
//        String forthCode = "5 3 * dup + . cr";
        String forthCode = "3 4 * dup nip 5 - swap tuck 2 / drop over + . cr";
        Tokenizer tokenizer = new Tokenizer();
        List<String> tokens = tokenizer.tokenize(forthCode);

        Parser parser = new Parser(tokens);
        parser.parse();

//        // Display the tokens
//        for (String token : tokens) {
//            System.out.println(token);
//        }

    }
}