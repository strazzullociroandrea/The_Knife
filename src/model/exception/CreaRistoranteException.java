package src.model.exception;

public class CreaRistoranteException extends RuntimeException {
    public CreaRistoranteException() {
        super("Non è stato possibile completare la creazione di un ristorante");
    }
}
