package tokenizer;

import constants.Constants;
import contracts.ITokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer implements ITokenizer {
    private final String forthCode;

    public Tokenizer(String forthCode) {
        this.forthCode = forthCode;
    }
    @Override
    public List<String> tokenize() {
        List<String> tokens = new ArrayList<>();

        String regex = String.format("(%s|%s|%s|\\S)", Constants.numberRegex, Constants.operatorRegex, Constants.commandRegex);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(forthCode);

        // Tokenize the Forth code
        while (matcher.find()) {
            tokens.add(matcher.group());
        }

        return tokens;
    }
}
