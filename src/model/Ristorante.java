package src.model;


import src.Ciro.ReverseGeocoding;
import src.Matteoo.Recensione;

import java.util.ArrayList;
import java.util.List;

public class Ristorante<idcont> {
    private String nome;
    private String nazione;
    private String citta;
    private String indirizzo;
    private double lat;
    private double lng;
    private double min;
    private double max;
    private boolean delivery;
    private boolean prenotazione;
    private String tipoCucina;
    private ArrayList<Ristorante> rispos;
    private ArrayList<Ristorante> ristp;
    private ArrayList<Ristorante> risprez;
    private ArrayList<Ristorante> risdel;
    private ArrayList<Ristorante> risrpren;
    private ArrayList<Ristorante> risrec;
    private static double mrec;
    private int idcont = 0;


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
     * @return double lat
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
     * @return double log
     */
    public double getLng() {
        return lng;
    }
    public void setLng(double lng) {
        this.lng = lng;
    }

    /**
     * Metodo per ottenere il minimo che si vuole spendere nel ristorante
     *
     * @return int min
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
     * @return int max
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
     * @return boolean delivery
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
     * @return boolean prenotazione
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

    /**
     * Metodo per ottenere la media delle recensioni
     *
     * @return double mrec
     */
    public double getValutazioneMedia() {

        return mrec;
    }
    public void setValutazioneMedia(double mrec) {

        this.mrec = mrec;
    }

    /**
     * Metodo per ottenere un id univoco per ogni ristorante
     *
     * @return int idcont
     */

    public int getId() {
        return idcont;
    }

    public void setId(int id) {
        this.idcont = id;
    }
    /**
     * Metodo per ottenere un array con tutti i ristoranti corrispondenti ai criteri di ricerca sulla posizione
     *
     * @return ArrayList<Ristorante> rispos
     */
    public ArrayList<Ristorante> getRispostePosizione() {
        return rispos;
    }
    public void setRispostePosizione(ArrayList<Ristorante> RipsostePosizione) {
        this.rispos = RipsostePosizione;
    }

    /**
     * Metodo per ottenere un array con tutti i ristoranti corrispondenti ai criteri di ricerca sulla tipologia di cucina
     *
     * @return ArrayList<Ristorante> ristp
     */
    public ArrayList<Ristorante> getRisposteTipoCucina() {
        return ristp;
    }
    public void setRisposteTipoCucina(ArrayList<Ristorante> RisposteTipoCucina) {
        this.ristp = RisposteTipoCucina;
    }

    /**
     * Metodo per ottenere un array con tutti i ristoranti corrispondenti ai criteri di ricerca sulla fascia di prezzo
     *
     * @return ArrayList<Ristorante> risprez
     */
    public ArrayList<Ristorante> getRispostePrezzo() {
        return risprez;
    }
    public void setRispostePrezzo(ArrayList<Ristorante> RispostePrezzo) {
        this. risprez= RispostePrezzo;
    }

    /**
     * Metodo per ottenere un array con tutti i ristoranti corrispondenti ai criteri di ricerca sulla disponibilità alla delivery
     *
     * @return ArrayList<Ristorante> risdel
     */
    public ArrayList<Ristorante> getRisposteDelivery() {
        return risdel;
    }
    public void setRisposteDelivery(ArrayList<Ristorante> RisposteDelivery) {
        this.risdel =RisposteDelivery;
    }

    /**
     * Metodo per ottenere un array con tutti i ristoranti corrispondenti ai criteri di ricerca sulla disponibilità alla prenotazione
     *
     * @return ArrayList<Ristorante> risrpren
     */
    public ArrayList<Ristorante> getRispostePrenotazione() {
        return risrpren;
    }
    public void setRispostePrenotazione(ArrayList<Ristorante> RispostePrenotazione) {
        this.risrpren =RispostePrenotazione;
    }

    /**
     * Metodo per ottenere un array con tutti i ristoranti corrispondenti ai criteri di ricerca sulle recensioni
     *
     * @return ArrayList<Ristorante> risrec
     */
    public ArrayList<Ristorante> getRisposteRecensioni() {
        return risrec;
    }
    public void setRisposteRecensioni(ArrayList<Ristorante> RisposteRecensioni) {
        this.risrec =RisposteRecensioni;
    }

    /**
     * Metodo per ottenere la fascia di prezzo
     *
     * @return String min + "-" + max
     */
    public String getFasciaPrezzo() {
        return min + "-" + max;
    }
    /*fine fase preparatoria
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     inizio fase sviluppo funzioni*/
    private double calcolaDistanza(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public boolean isVicino(String indirizzo) throws Exception {
        try {
            double[] data = ReverseGeocoding.getLatitudineLongitudine(indirizzo);
            if (data[0] == -1 || data[1] == -1)
                throw new Exception("Coordinate non valide.");
            double distanza = calcolaDistanza(this.lat, this.lng, data[0], data[1]);
            return distanza <= 10000; // 10 km
        } catch (Exception e) {
            throw new Exception("Errore durante il geocoding: " + e.getMessage());
        }
    }
    //attributi
    private  List<Recensione> recensioni = new ArrayList<>();
    //metodi
    public  void aggiungiRecensione(Recensione r) {
        recensioni.add(r);
    }

    public  List<Recensione> getRecensioni() {
        return recensioni;
    }

    public  void rimuoviRecensione(Recensione r) {
        recensioni.remove(r);
    }

    public  double valutazioneMedia() {
        if (recensioni.isEmpty()) return 0;
        int somma = 0;
        for (Recensione r : recensioni) {
            somma += r.getStelle();
        }
        return (double) somma / recensioni.size();
    }

    public int numeroRecensioni() {
        return recensioni.size();
    }

    public ArrayList<String> visualizzaRistorante (ArrayList<Ristorante> lista, boolean isVicino, String indirizzo, String citta, String nazione) throws Exception {
        ArrayList<String> risultati = new ArrayList<>();


        risultati.add("Nome: "+nome);
        risultati.add("Indirizzo: "+nazione + " "+ citta + " "+ indirizzo);
        risultati.add("Fascia di prezzo: "+min+" "+max);
        risultati.add("tipologie di cucina: "+tipoCucina);
        if (delivery == true) {
            risultati.add("Disponibile alla delivery");
        }
        else {
            risultati.add("Non disponibile alla delivery");
        }
        if (prenotazione == true) {
            risultati.add("Disponibile alla prenotazione");
        }
        else {
            risultati.add("Non disponibile alla prenotazione");
        }

        return risultati;
    }

    public ArrayList<Ristorante> cercaRistorantePosizione(ArrayList<Ristorante> lista, boolean isVicino, String indirizzo, String citta, String nazione) throws Exception {
        ArrayList<Ristorante> risultatixPosizione = new ArrayList<>();

        for (Ristorante r : lista) {
            boolean posizioneOk = r.isVicino(indirizzo +" "+ citta +" "+ nazione);
            if (posizioneOk) {
                risultatixPosizione.add(r);
            }
        }

        return risultatixPosizione;
    }

    public static ArrayList<Ristorante> cercaRistoranteTipoCucina(ArrayList<Ristorante> lista, String tipoCucina) {
        ArrayList<Ristorante> risultatixTipoCucina = new ArrayList<>();

        for (Ristorante r : lista) {
            boolean cucinaOk = r.getTipoCucina().equalsIgnoreCase(tipoCucina);
            if (cucinaOk) {
                risultatixTipoCucina.add(r);
            }
        }

        return risultatixTipoCucina;
    }

    public static ArrayList<Ristorante> cercaRistorantePrezzo(ArrayList<Ristorante> lista, double min, double max) {
        ArrayList<Ristorante> risultatixPrezzo = new ArrayList<>();

        for (Ristorante r : lista) {
            boolean prezzominOk = r.getMin() >= min;
            boolean prezzomaxOk = r.getMax() <= max;
            if (prezzominOk && prezzomaxOk) {
                risultatixPrezzo.add(r);
            }
        }

        return risultatixPrezzo;
    }

    public static ArrayList<Ristorante> cercaRistoranteDelivery(ArrayList<Ristorante> lista,boolean delivery) {
        ArrayList<Ristorante> risultatixDelivery = new ArrayList<>();

        for (Ristorante r : lista) {


            boolean deliveryOk = !delivery || r.isDelivery();

            if (deliveryOk) {
                risultatixDelivery.add(r);
            }
        }

        return risultatixDelivery;
    }

    public static ArrayList<Ristorante> cercaRistorantePrenotazione(ArrayList<Ristorante> lista, boolean prenotazione) {
        ArrayList<Ristorante> risultatixPrenotazione = new ArrayList<>();

        for (Ristorante r : lista) {
            boolean prenotazioneOk = !prenotazione || r.isPrenotazione();
            if (prenotazioneOk) {
                risultatixPrenotazione.add(r);
            }
        }

        return risultatixPrenotazione;
    }

    public static ArrayList<Ristorante> cercaRistoranteRecensioni(ArrayList<Ristorante> lista, double mediaStelle) {
        ArrayList<Ristorante> risultatixRecensioni = new ArrayList<>();

        for (Ristorante r : lista) {
            boolean MrecOK = r.valutazioneMedia() >= mrec;
            if (MrecOK) {
                risultatixRecensioni.add(r);
            }
        }

        return risultatixRecensioni;
    }

    public Ristorante( String nome, String nazione, String citta, String indirizzo, double lat, double lng,
                       double min, double max, boolean delivery, boolean prenotazione, String tipoCucina,
                       ArrayList<Ristorante> risultatixRecensioni, ArrayList<Ristorante>risultatixPrenotazione,
                       ArrayList<Ristorante>risultatixDelivery, ArrayList<Ristorante>risultatixPrezzo,
                       ArrayList<Ristorante>risultatixTipoCucina, ArrayList<Ristorante> risultaixPosizione) {
        this.nome        = nome;
        this.nazione     = nazione;
        this.citta       = citta;
        this.indirizzo   = indirizzo;
        this.lat         = lat;
        this.lng         = lng;
        this.min         = min;
        this.max         = max;
        this.delivery    = delivery;
        this.prenotazione= prenotazione;
        this.tipoCucina  = tipoCucina;
        this.rispos = risultaixPosizione;
        this.ristp = risultatixTipoCucina;
        this.risprez = risultatixPrezzo;
        this.risdel = risultatixDelivery;
        this.risrpren = risultatixPrenotazione;
        this.risrec = risultatixRecensioni;
    }

    public Ristorante( int id, String nome, String nazione, String citta, String indirizzo, double lat, double lng,
                       double min, double max, boolean delivery, boolean prenotazione, String tipoCucina,
                       ArrayList<Ristorante> risultatixRecensioni, ArrayList<Ristorante>risultatixPrenotazione,
                       ArrayList<Ristorante>risultatixDelivery, ArrayList<Ristorante>risultatixPrezzo,
                       ArrayList<Ristorante>risultatixTipoCucina, ArrayList<Ristorante> risultaixPosizione) {
        this.idcont      = id;
        this.nome        = nome;
        this.nazione     = nazione;
        this.citta       = citta;
        this.indirizzo   = indirizzo;
        this.lat         = lat;
        this.lng         = lng;
        this.min         = min;
        this.max         = max;
        this.delivery    = delivery;
        this.prenotazione= prenotazione;
        this.tipoCucina  = tipoCucina;
        this.rispos = risultaixPosizione;
        this.ristp = risultatixTipoCucina;
        this.risprez = risultatixPrezzo;
        this.risdel = risultatixDelivery;
        this.risrpren = risultatixPrenotazione;
        this.risrec = risultatixRecensioni;
    }

    public static ArrayList<Ristorante> cercaRistorante( ArrayList<Ristorante> risultatixRecensioni, ArrayList<Ristorante>risultatixPrenotazione,
                                                         ArrayList<Ristorante>risultatixDelivery, ArrayList<Ristorante>risultatixPrezzo,
                                                         ArrayList<Ristorante>risultatixTipoCucina, ArrayList<Ristorante> risultaixPosizione) {
        ArrayList<Ristorante> risultati = new ArrayList<>();
        risultati.addAll(risultatixRecensioni);
        risultati.addAll(risultatixPrenotazione);
        risultati.addAll(risultatixDelivery);
        risultati.addAll(risultatixPrezzo);
        risultati.addAll(risultatixTipoCucina);
        risultati.addAll(risultaixPosizione);
        return risultati;

    }
}