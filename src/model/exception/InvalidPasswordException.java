package src.model.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("la password non puo' avere meno di 6 caratteri");
    }
}
