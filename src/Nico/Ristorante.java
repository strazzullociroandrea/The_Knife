package src.Nico;

public class Ristorante {
    private String nome;
    private String nazione;
    private String citta;
    private String indirizzo;
    private String lat;
    private String log;
    private int fasciaPrezzo;
    private boolean delivery;
    private boolean prenotazione;
    private String tipoCucina;

    /**
     * Metodo per ottenere il nome del ristorante
     * @return stringa nome
     */
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    /**
     * Metodo per ottenere la nazione del ristorante
     * @return stringa nazione
     */
    public String getNazione() {
        return nazione;
    }

    public void setNazione(String nazione) {
        this.nazione = nazione;
    }
    /**
     * Metodo per ottenere la città del ristorante
     * @return stringa città
     */
    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }
    /**
     * Metodo per ottenere il nome del ristorante
     * @return stringa indirizzo
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
    /**
     * Metodo per ottenere la latitudine del ristorante
     * @return stringa lat
     */
    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
    /**
     * Metodo per ottenere la longitudine del ristorante
     * @return stringa log
     */
    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
    /**
     * Metodo per ottenere la fascia di prezo del ristorante
     * @return int fasciaPrezzo
     */
    public int getFasciaPrezzo() {
        return fasciaPrezzo;
    }

    public void setFasciaPrezzo(int fasciaPrezzo) {
        this.fasciaPrezzo = fasciaPrezzo;
    }
    /**
     * Metodo per ottenere il nome del ristorante
     * @return stringa nome
     */
    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }
    /**
     * Metodo per ottenere il nome del ristorante
     * @return stringa nome
     */
    public boolean isPrenotazione() {
        return prenotazione;
    }

    public void setPrenotazione(boolean prenotazione) {
        this.prenotazione = prenotazione;
    }
    /**
     * Metodo per ottenere il tipo di cucina del ristorante
     * @return stringa tipoCucina
     */
    public String getTipoCucina() {
        return tipoCucina;
    }

    public void setTipoCucina(String tipoCucina) {
        this.tipoCucina = tipoCucina;
    }


}