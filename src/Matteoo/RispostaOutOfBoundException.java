package src.Matteoo;

public class RispostaOutOfBoundException extends RuntimeException {
    /**
     * costruttore per creare un'eccezione personalizzata lanciata nel caso l'attributo risposta superi i 250 caratteri o sia vuota
     */
    public RispostaOutOfBoundException() {
        super("La stringa è più lunga di 250 caratteri o è vuota");
    }
}
