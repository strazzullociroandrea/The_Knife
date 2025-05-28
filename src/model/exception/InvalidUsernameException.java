package src.model.exception;

/**
 * Classe eccezione che gestisce i casi in cui l'username sia vuoto o con più di 18 caratteri
 * @version 1.0
 * @Author Strazzullo Ciro Andrea
 * @Author Riccardo Giovanni Rubini
 * @Author Matteo Mongelli
 */
public class InvalidUsernameException extends RuntimeException {
    /**
     * Costruttore per creare un'eccezione personalizzata lanciata nel caso l'username sia nullo o abbia più di 18 caratteri
     */
    public InvalidUsernameException() {
        super("l'username non puo' essere nullo e deve avere un massimo di 18 caratteri");
    }
}
