package src.Matteoo;

public class Recensione {
   //attributi

    private String descrizione;
    private int stelle;
    private String risposta;
    private int id;
    private static int idCounter = 0;

    /**
     *
     * @param descrizione il contenuto della recensione, max 250 caratteri
     * @param stelle il numero di stelle attribuite al ristorante nella recensione, da 1 a 5
     */
    //costruttori
    public Recensione(String descrizione, int stelle) {
        this.descrizione = descrizione;
        this.stelle = stelle;
        this.id = idCounter++;
    }

    /**
     *
     * @param descrizione il contenuto della recensione, max 250 caratteri
     * @param stelle il numero di stelle attribuite al ristorante nella recensione, da 1 a 5
     * @param id l'id associato alla recensione
     */
    public Recensione(String descrizione, int stelle, int id) {
        this.descrizione = descrizione;
        this.stelle = stelle;
        this.id = id;
        idCounter++;
    }

    /**
     *
     * @param nStelle il numero di stelle associate alla recensione tramite il metodo
     * @throws StelleOutOfBoundException eccezione lanciata se il numero di stelle è minore di 0 o maggiore di 5
     */
    public void setNumeroStelle(int nStelle) throws StelleOutOfBoundException {
        if (nStelle > 0 && nStelle <= 5)
            this.stelle = nStelle;
        else throw new StelleOutOfBoundException("Il massimo numero di stelle è 5 e il minimo è 1");
    }

    /**
     *
     * @param txt la descrizione associata alla recensione assegnata tramite metodo
     * @throws RecensioneOutOfBoundException eccezione lanciata se il numero di caratteri supera 250
     */
    public void setDescrizione(String txt) throws RecensioneOutOfBoundException {
        if( txt.length() > 250)
            throw new RecensioneOutOfBoundException("Il massimo di caratteri per la recensione o la risposta è di 250 caratteri");
        else this.descrizione = txt;
    }

    /**
     *
     * @param risposta stringa associata alla risposta alla recensione tramite metodo dal Ristoratore
     * @throws RispostaOutOfBoundException eccezione lanciata se il numero di caratteri della risposta supera 250
     */
    public void setRisposta(String risposta) throws RispostaOutOfBoundException {

        if(risposta.length() > 250)
            throw new RispostaOutOfBoundException("Il massimo di caratteri per la risposta è di 250 caratteri");
        else this.risposta = risposta;
    }

    /**
     *
     * @param id l'id associato alla recensione tramite il metodo
     */

    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return restituisce l'id associato alla recensione
     */

    public int getId() {
        return id;
    }

    /**
     *
     * @return restituisce il contenuto testuale della recensione
     */

    public String getDescrizione() {
        return descrizione;
    }

    /**
     *
     * @return restituisce il numero di stelle associato alla recensione
     */
    public int getStelle() {
        return stelle;
    }

    /**
     *
     * @return restituisce la risposta testuale associata alla recensione
     */
    public String getRisposta() {
        return risposta;
    }

    /**
     *
     * @return
     */
    public boolean delete();

    @Override
    public String toString() {
        return "Recensione{" +
                "descrizione='" + descrizione + '\'' +
                ", stelle=" + stelle +
                ", risposta='" + risposta + '\'' +
                ", id=" + id +
                '}';
    }
}

