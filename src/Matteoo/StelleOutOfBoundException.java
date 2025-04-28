package src.Matteoo;

public class StelleOutOfBoundException extends RuntimeException {
    public StelleOutOfBoundException() {
        super("il numero di stelle inserito è minore di 1 o maggiore di 5");
    }
}
