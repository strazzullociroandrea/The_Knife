package src.model.exception;

/**
 * Classe eccezione personalizzata realizzata per gestire i casi in cui la risposta testuale associata alla recensione sia vuota o superi i 250 caratteri
 * @version 1.0
 *  * @Author Strazzullo Ciro Andrea
 *  * @Author Riccardo Giovanni Rubini
 *  * @Author Matteo Mongelli
 *  * @Author Nicolò Valter Girardello
 */

public class RispostaOutOfBoundException extends RuntimeException {
    /**
     * costruttore per creare un'eccezione personalizzata lanciata nel caso l'attributo risposta superi i 250 caratteri o sia vuota
     */
    public RispostaOutOfBoundException() {
        super("La stringa è più lunga di 250 caratteri o è vuota");
    }
}
