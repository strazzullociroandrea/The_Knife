package src.theknife.model.exception;

/**
 * Classe eccezione che gestisce la creazione delle classi ristorante
 * @version 1.0
 * @Author Strazzullo Ciro Andrea, 763603, VA
 * @Author Riccardo Giovanni Rubini, 761126, VA
 * @Author Matteo Mongelli, 760960, VA 
 */
public class CreaRistoranteException extends RuntimeException {
    /**
     * Costruttore per creare un'eccezione personalizzata lanciata nel caso la creazione di un ristorante non vada a buon fine
     */
    public CreaRistoranteException() {
        super("Non è stato possibile completare la creazione di un ristorante");
    }
}
