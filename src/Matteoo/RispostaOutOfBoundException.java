package src.Matteoo;

public class RispostaOutOfBoundException extends RuntimeException {
    public RispostaOutOfBoundException() {
        super("La stringa è più lunga di 250 caratteri o è vuota");
    }
}
