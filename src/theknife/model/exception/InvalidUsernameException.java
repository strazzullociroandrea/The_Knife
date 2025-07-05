package src.theknife.model.exception;

/**
 * Classe eccezione che gestisce i casi in cui l'username sia vuoto o con più di 18 caratteri
 * @version 1.0
 * @Author Strazzullo Ciro Andrea, 763603, VA
 * @Author Riccardo Giovanni Rubini, 761126, VA
 * @Author Matteo Mongelli, 760960, VA 
 */
public class InvalidUsernameException extends RuntimeException {
    /**
     * Costruttore per creare un'eccezione personalizzata lanciata nel caso l'username sia nullo o abbia più di 18 caratteri
     */
    public InvalidUsernameException() {
        super("l'username non puo' essere nullo e deve avere un massimo di 18 caratteri");
    }
}
