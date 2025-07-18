package src.theknife.model.exception;

/**
 * classe eccezione che gestisce i casi in cui la password venga inserita con meno di 6 caratteri
 * @version 1.0
 * @Author Strazzullo Ciro Andrea, 763603, VA
 * @Author Riccardo Giovanni Rubini, 761126, VA
 * @Author Matteo Mongelli, 760960, VA 
 */
public class InvalidPasswordException extends RuntimeException {
    /**
     * Costruttore per creare un'eccezione personalizzata lanciata nel caso la password sia inferiore a 6 caratteri
     */
    public InvalidPasswordException() {
        super("la password non puo' avere meno di 6 caratteri");
    }
}
