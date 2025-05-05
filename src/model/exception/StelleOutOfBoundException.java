package src.model.exception;

/**
 * Classe eccezione personalizzata realizzata per gestire i casi in cui il numero di stelle associate alla Recensione sia minore di 1 o maggiore di 5
 * @version 1.0
 *  * @Author Strazzullo Ciro Andrea
 *  * @Author Riccardo Giovanni Rubini
 *  * @Author Matteo Mongelli
 *  * @Author Nicolò Valter Girardello
 */

public class StelleOutOfBoundException extends RuntimeException {
    /**
     * costruttore per creare un'eccezione personalizzata lanciata nel caso in cui l'attributo stelle non sia maggiore di zero e minore o uguale a 5
     */
    public StelleOutOfBoundException() {
        super("il numero di stelle inserito è minore di 1 o maggiore di 5");
    }
}
