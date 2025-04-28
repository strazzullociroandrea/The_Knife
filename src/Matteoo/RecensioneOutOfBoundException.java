package src.Matteoo;

public class RecensioneOutOfBoundException extends RuntimeException {
    public RecensioneOutOfBoundException() {
        super("La stringa è più lunga di 250 caratteri o è vuota");
    }

}
