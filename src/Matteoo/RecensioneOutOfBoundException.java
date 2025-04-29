package src.Matteoo;

public class RecensioneOutOfBoundException extends RuntimeException {
    /**
     *costruttore vuoto per creare un'eccezione che viene lanciata quando si imposta un attributo descrizione che supera i 250 caratteri o è vuota
     */
    public RecensioneOutOfBoundException() {
        super("La stringa è più lunga di 250 caratteri o è vuota");
    }

}
