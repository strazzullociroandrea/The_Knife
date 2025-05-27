package src.model.exception;

/**
 * Classe eccezione che gestisce i casi in cui l'username sia vuoto o con più di 18 caratteri
 * @version 1.0
 * @Author Strazzullo Ciro Andrea
 * @Author Riccardo Giovanni Rubini
 * @Author Matteo Mongelli
 */
public class InvalidUsernameException extends RuntimeException {
    public InvalidUsernameException() {
        super("l'username non puo' essere nullo e deve avere un massimo di 18 caratteri");
    }
}
