package src.Nico;

import java.util.Scanner;

public class Ristorante {
    private String nome;
    private String nazione;
    private String citta;
    private String indirizzo;
    private int lat;
    private int log;
    private int fasciaPrezzo;
    private boolean delivery;
    private boolean prenotazione;
    private String tipoCucina;

    /**
     * Metodo per ottenere il nome del ristorante
     * @return string nome
     */
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    /**
     * Metodo per ottenere la nazione del ristorante
     * @return string nazione
     */
    public String getNazione() {
        return nazione;
    }

    public void setNazione(String nazione) {
        this.nazione = nazione;
    }
    /**
     * Metodo per ottenere la città del ristorante
     * @return string città
     */
    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }
    /**
     * Metodo per ottenere il nome del ristorante
     * @return string indirizzo
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
    /**
     * Metodo per ottenere la latitudine del ristorante
     * @return int lat
     */
    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }
    /**
     * Metodo per ottenere la longitudine del ristorante
     * @return int log
     */
    public int getLog() {
        return log;
    }

    public void setLog(int log) {
        this.log = log;
    }
    /**
     * Metodo per ottenere la fascia di prezzo del ristorante
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
     * @return string nome
     */
    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }
    /**
     * Metodo per ottenere il nome del ristorante
     * @return string nome
     */
    public boolean isPrenotazione() {
        return prenotazione;
    }

    public void setPrenotazione(boolean prenotazione) {
        this.prenotazione = prenotazione;
    }
    /**
     * Metodo per ottenere il tipo di cucina del ristorante
     * @return string tipoCucina
     */
    public String getTipoCucina() {
        return tipoCucina;
    }

    public void setTipoCucina(String tipoCucina) {
        this.tipoCucina = tipoCucina;
    }
    /*fine fase preparatoria
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     inizio fase sviluppo funzioni*/
    public static String cercaRistorante(String tipoCucina, int fasciaPrezzo, boolean delivery, boolean prenotazione/*, int stelle*/, int lat, int log, String nazione, String citta, String indirizzo) {
        Scanner var1 = new Scanner(System.in);
        System.out.println("Scegli in base a cosa vuoi cercare il ristorante:");
        System.out.println("1 Posizione\n2 Tipo di cucina\n3 Fascia di prezzo\n4 Disponibilità di servizi di delivery\n5 Necessita' di prenotazione");
        int scelta = var1.nextInt();
        if (scelta < 6) {
        switch (scelta) {
            case 1 :
                System.out.println("piselli");
            case 2 :
                ;
            case 3 :
                ;
            case 4 :
                ;
            case 5 :
                ;
        }
    }
        return tipoCucina;
    }
}