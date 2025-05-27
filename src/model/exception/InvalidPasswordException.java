package src.model.exception;

/**
 * classe eccezione che gestisce i casi in cui la password venga inserita con meno di 6 caratteri
 * @version 1.0
 * @Author Strazzullo Ciro Andrea
 * @Author Riccardo Giovanni Rubini
 * @Author Matteo Mongelli
 */
public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("la password non puo' avere meno di 6 caratteri");
    }
}
