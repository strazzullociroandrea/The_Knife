package src.model.exception;

public class InvalidNomeException extends RuntimeException {
    public InvalidNomeException() {
        super("il nome e il cognome non possono essere stringhe vuote");
    }
}
