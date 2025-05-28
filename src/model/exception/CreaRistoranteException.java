package src.model.exception;

/**
 * Classe eccezione che gestisce la creazione delle classi ristorante
 * @version 1.0
 * @Author Strazzullo Ciro Andrea
 * @Author Riccardo Giovanni Rubini
 * @Author Matteo Mongelli
 */
public class CreaRistoranteException extends RuntimeException {
    /**
     * Costruttore per creare un'eccezione personalizzata lanciata nel caso la creazione di un ristorante non vada a buon fine
     */
    public CreaRistoranteException() {
        super("Non è stato possibile completare la creazione di un ristorante");
    }
}
