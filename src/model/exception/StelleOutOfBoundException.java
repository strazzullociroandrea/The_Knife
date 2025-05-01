package src.model.exception;

public class StelleOutOfBoundException extends RuntimeException {
    /**
     * costruttore per creare un'eccezione personalizzata lanciata nel caso in cui l'attributo stelle non sia maggiore di zero e minore o uguale a 5
     */
    public StelleOutOfBoundException() {
        super("il numero di stelle inserito è minore di 1 o maggiore di 5");
    }
}
