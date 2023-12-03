package exceptions;

public class CompilationErrorException extends Exception{
    public CompilationErrorException() {
        super("Compilation error: check tokens");
    }
}
