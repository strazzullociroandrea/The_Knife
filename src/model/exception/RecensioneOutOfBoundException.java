package src.model.exception;

/**
 * Classe eccezione personalizzata realizzata per gestire i casi in cui vengano effettuate recensioni vuote o più lunghe di 250 caratteri
 * @version 1.0
 * @Author Strazzullo Ciro Andrea
 * @Author Riccardo Giovanni Rubini
 * @Author Matteo Mongelli
 */
public class RecensioneOutOfBoundException extends RuntimeException {
    /**
     *costruttore vuoto per creare un'eccezione che viene lanciata quando si imposta un attributo descrizione che supera i 250 caratteri o è vuota
     */
    public RecensioneOutOfBoundException() {
        super("La stringa è più lunga di 250 caratteri o è vuota");
    }

}
