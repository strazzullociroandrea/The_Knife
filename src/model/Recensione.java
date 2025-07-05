package src.model;

import src.model.exception.RecensioneOutOfBoundException;
import src.model.exception.RispostaOutOfBoundException;
import src.model.exception.StelleOutOfBoundException;

/**
 * Classe per la gestione delle recensioni che gli utenti possono dare ai ristoranti
 *
 * @version 1.0
 * @Author Strazzullo Ciro Andrea
 * @Author Riccardo Giovanni Rubini
 * @Author Matteo Mongelli
 */
public class Recensione {

    /**
     * Il contenuto testuale della recensione, di tipo String
     */
    private String descrizione;

    /**
     * Il numero di stelle associate alla recensione (da 1 a 5), di tipo int
     */
    private int stelle;

    /**
     * La risposta testuale associata alla recensione che può dare il ristoratore, di tipo String
     */
    private String risposta;

    /**
     * Il codice identificativo univoco della recensione, di tipo int
     */
    private int id;

    /**
     * Il contatore statico degli id delle recensioni, di tipo int
     */
    private static int idCounter = 0;

    /**
     * Costruttore per creare una recensione impostando attributi descrizione e stelle
     *
     * @param descrizione il contenuto della recensione, max 250 caratteri
     * @param stelle      il numero di stelle attribuite al ristorante nella recensione, da 1 a 5
     */
    public Recensione(String descrizione, int stelle) {
        this.descrizione = descrizione;
        this.stelle = stelle;
        this.id = idCounter++;
    }

    /**
     * Costruttore per creare una recensione impostando attributi descrizione, stelle e id
     *
     * @param descrizione il contenuto della recensione, max 250 caratteri
     * @param stelle      il numero di stelle attribuite al ristorante nella recensione, da 1 a 5
     * @param id          l'id associato alla recensione
     * @throws Exception lanciata se il numero di stelle è minore di 0 o maggiore di 5, o se la descrizione supera i 250 caratteri
     */
    public Recensione(String descrizione, int stelle, int id) throws Exception {
        setDescrizione(descrizione);
        setNumeroStelle(stelle);
        setId(id);
        //Gestione unicità dell'id
        if (this.id >= idCounter) {
            idCounter = this.id + 1;
        }
    }

    /**
     * Costruttore per creare una recensione impostando gli attributi descrizione, stelle, risposta e id
     *
     * @param descrizione il contenuto della recensione, max 250 caratteri
     * @param stelle      il numero di stelle attribuite al ristorante nella recensione, da 1 a 5
     * @param risposta    la risposta associata alla recensione
     * @param id          l'id associato alla recensione
     * @throws Exception lanciata se il numero di stelle è minore di 0 o maggiore di 5, o se la descrizione supera i 250 caratteri
     */
    public Recensione(String descrizione, int stelle, String risposta, int id) throws Exception {
        setDescrizione(descrizione);
        setNumeroStelle(stelle);
        setRisposta(risposta);
        setId(id);
        //Gestione unicità dell'id
        if (this.id >= idCounter) {
            idCounter = this.id + 1;
        }
    }

    /**
     * Costruttore per creare una recensione impostando gli attributi descrizione, stelle e risposta
     *
     * @param descrizione il contenuto della recensione, max 250 caratteri
     * @param stelle      il numero di stelle attribuite al ristorante nella recensione, da 1 a 5
     * @param risposta    la risposta associata alla recensione
     * @throws Exception lanciata se il numero di stelle è minore di 0 o maggiore di 5, o se la descrizione supera i 250 caratteri
     */
    public Recensione(String descrizione, int stelle, String risposta) throws Exception {
        setDescrizione(descrizione);
        setNumeroStelle(stelle);
        setRisposta(risposta);
        this.id = idCounter++;
    }

    /**
     * Metodo per modificare l'attributo stelle di una recensione
     *
     * @param nStelle il numero di stelle associate alla recensione tramite il metodo
     * @throws StelleOutOfBoundException eccezione lanciata se il numero di stelle è minore di 0 o maggiore di 5
     */
    public void setNumeroStelle(int nStelle) throws StelleOutOfBoundException {
        if (nStelle >= 0 && nStelle <= 5)
            this.stelle = nStelle;
        else
            throw new StelleOutOfBoundException();
    }

    /**
     * Metodo per modificare l'attributo descrizione di una recensione
     *
     * @param txt la descrizione associata alla recensione assegnata tramite metodo
     * @throws RecensioneOutOfBoundException eccezione lanciata se il numero di caratteri supera 250
     */
    public void setDescrizione(String txt) throws RecensioneOutOfBoundException {
        if (txt.length() > 250 || txt.isEmpty())
            throw new RecensioneOutOfBoundException();
        else this.descrizione = txt;
    }

    /**
     * Metodo per modificare l'attributo risposta di una recensione
     *
     * @param risposta stringa associata alla risposta alla recensione tramite metodo dal Ristoratore
     * @throws RispostaOutOfBoundException eccezione lanciata se il numero di caratteri della risposta supera 250
     */
    public void setRisposta(String risposta) throws RispostaOutOfBoundException {
        if (risposta.length() > 250 || risposta.isEmpty())
            throw new RispostaOutOfBoundException();
        else this.risposta = risposta;
    }

    /**
     * Metodo per modificare l'attributo id di una recensione
     *
     * @param id l'id associato alla recensione tramite il metodo
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Metodo per ottenere l'attributo id di una recensione
     *
     * @return restituisce l'id associato alla recensione
     */
    public int getId() {
        return id;
    }

    /**
     * Metodo per ottenere l'attributo descrizione di una recensione
     *
     * @return restituisce il contenuto testuale della recensione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Metodo per ottenere l'attributo stelle di una recensione
     *
     * @return restituisce il numero di stelle associato alla recensione
     */
    public int getStelle() {
        return stelle;
    }

    /**
     * Metodo per ottenere l'attributo risposta di una recensione
     *
     * @return restituisce la risposta testuale associata alla recensione
     */
    public String getRisposta() {
        return risposta == null ? "Non data" : risposta;
    }

    /**
     * Metodo per ottenere la rappresentazione testuale della recensione
     *
     * @return una stringa che rappresenta la recensione, con descrizione, stelle, risposta e id
     */
    @Override
    public String toString() {
        return "Recensione{" +
                "descrizione='" + descrizione + '\'' +
                ", stelle=" + stelle +
                ", risposta='" + risposta + '\'' +
                ", id=" + id +
                '}';
    }

    /**
     * Metodo per confrontare due recensioni
     * @param obj l'oggetto da confrontare con la recensione corrente
     * @return true se gli oggetti sono uguali (stesso id), false altrimenti
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Recensione that = (Recensione) obj;
        return this.id == that.id;
    }
}

