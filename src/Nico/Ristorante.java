package src.Nico;

import src.Ciro.ReverseGeocoding;

import java.util.ArrayList;

public class Ristorante {
    private String nome;
    private String nazione;
    private String citta;
    private String indirizzo;
    private double lat;
    private double log;
    private static double min;
    private static double max;
    private boolean delivery;
    private boolean prenotazione;
    private String tipoCucina;

    /**
     * Metodo per ottenere il nome del ristorante
     *
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
     *
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
     *
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
     *
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
     *
     * @return int lat
     */
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * Metodo per ottenere la longitudine del ristorante
     *
     * @return int log
     */
    public double getLog() {
        return log;
    }

    public void setLog(double log) {
        this.log = log;
    }

    /**
     * Metodo per ottenere il minimo che si vuole spendere nel ristorante
     *
     * @return int fasciaPrezzo
     */
    public double getMin() {
        return min;
    }

    public void setMin(double Min) {
        this.min = Min;
    }

    /**
     * Metodo per ottenere il massimo che si vuole spendere nel ristorante
     *
     * @return int fasciaPrezzo
     */
    public double getMax() {
        return max;
    }

    public void setMax(double Max) {
        this.max = Max;
    }

    /**
     * Metodo per ottenere se il ristorante ha anche la funzione di delivery
     *
     * @return string nome
     */
    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    /**
     * Metodo per ottenere se il ristorante accetta prenotazioni
     *
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
     *
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
    public static ArrayList<Ristorante> cercaRistorante(ArrayList<Ristorante> lista, String tipoCucina,
                                                        double min, double max, boolean delivery,
                                                        boolean prenotazione, double lat, double log) {
        ArrayList<Ristorante> risultati = new ArrayList<>();

        for (Ristorante r : lista) {
            boolean posizioneOk = true ;
            boolean cucinaOk = r.getTipoCucina().equalsIgnoreCase(tipoCucina);
            boolean prezzominOk = r.getMin() >= min ;
            boolean prezzomaxOk = r.getMax() <= max;
            boolean deliveryOk = !delivery || r.isDelivery();
            boolean prenotazioneOk = !prenotazione || r.isPrenotazione();

            if (posizioneOk && cucinaOk && prezzominOk && prezzomaxOk && deliveryOk && prenotazioneOk) {
                risultati.add(r);
            }
        }

        return risultati;
    }
    public boolean isVicino(String indirizzo) throws Exception {
        try {
            double[] data = ReverseGeocoding.getLatitudineLongitudine(indirizzo);
            if (data[0] == -1 || data[1] == -1)
                throw new Exception("Coordinate non valide.");
            double distanza = calcolaDistanza(this.lat, this.log, data[0], data[1]);
            return distanza <= 10000; // 10 km
        } catch (Exception e) {
            throw new Exception("Errore durante il geocoding: " + e.getMessage());
        }
    }
    private double calcolaDistanza(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;}
}



