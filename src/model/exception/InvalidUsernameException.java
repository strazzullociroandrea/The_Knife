package src.model.exception;

public class InvalidUsernameException extends RuntimeException {
    public InvalidUsernameException() {
        super("l'username non puo' essere nullo e deve avere un massimo di 18 caratteri");
    }
}
