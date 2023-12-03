package constants;

public class Constants {
    public static final String numberRegex = "-?\\d+";
    public static final String operatorRegex = "[+*-]";
    public static final String commandRegex = "dup|swap|nip|tuck|drop|over|cr|\\.s";
    public static final String commentRegex = "\\([^)]*\\)";
}
