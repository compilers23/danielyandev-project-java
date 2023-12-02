package tokenizer;

import contracts.ITokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer implements ITokenizer {
    private String forthCode;

    public Tokenizer(String forthCode) {
        this.forthCode = forthCode;
    }
    @Override
    public List<String> tokenize() {
        List<String> tokens = new ArrayList<>();

        // Define regular expressions for numbers, operations, and Forth commands
        String numberPattern = "-?\\d+";
        String operatorPattern = "[+*]";
        String commandPattern = "dup|swap|nip|tuck|drop|over|cr";

        String regex = String.format("(%s|%s|%s|\\S)", numberPattern, operatorPattern, commandPattern);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(forthCode);

        // Tokenize the Forth code
        while (matcher.find()) {
            tokens.add(matcher.group());
        }

        return tokens;
    }
}
