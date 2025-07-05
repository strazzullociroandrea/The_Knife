package src.theknife.model.exception;

/**
 * classe eccezione che gestisce i casi in cui nome e cognome siano stringhe vuote
 * @version 1.0
 * @Author Strazzullo Ciro Andrea, 763603, VA
 * @Author Riccardo Giovanni Rubini, 761126, VA
 * @Author Matteo Mongelli, 760960, VA 
 */
public class InvalidNomeException extends RuntimeException {
    /**
     * Costruttore per creare un'eccezione personalizzata lanciata nel caso il nome o il cognome siano stringhe vuote
     */
    public InvalidNomeException() {
        super("il nome e il cognome non possono essere stringhe vuote");
    }
}
