package src.model;

import src.Ciro.ReverseGeocoding;
import src.Matteoo.Recensione;
import java.util.ArrayList;
import java.util.List;

public class Ristorante<idcont> {
    private static String nome;
    private static  String nazione;
    private static String citta;
    private static String indirizzo;
    private static double lat;
    private static double lng;
    private static double min;
    private static double max;
    private static boolean delivery;
    private static boolean prenotazione;
    private static String tipoCucina;
    private ArrayList<Ristorante> rispos;
    private ArrayList<Ristorante> ristp;
    private ArrayList<Ristorante> risprez;
    private ArrayList<Ristorante> risdel;
    private ArrayList<Ristorante> risrpren;
    private ArrayList<Ristorante> risrec;
    private static double mrec;
    private int idcont = 0;
    private static List<Recensione> recensioni = new ArrayList<>();

    /**
     * Costruttore senza id univoco che costruisce tutti gli oggetti vuoti in modo che non ci siano problemi eseguendo più ricerche di seguito
     */
    public Ristorante( String nome, String nazione, String citta, String indirizzo, double lat, double lng,
                       double min, double max, boolean delivery, boolean prenotazione, String tipoCucina,
                       ArrayList<Ristorante> risultatixRecensioni, ArrayList<Ristorante>risultatixPrenotazione,
                       ArrayList<Ristorante>risultatixDelivery, ArrayList<Ristorante>risultatixPrezzo,
                       ArrayList<Ristorante>risultatixTipoCucina, ArrayList<Ristorante> risultaixPosizione) {
        this.nome        = nome;
        this.nazione     = nazione;
        this.citta       = citta;
        this.indirizzo   = indirizzo;
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
    /**
     * Costruttore senza id univoco che costruisce tutti gli oggetti vuoti in modo che non ci siano problemi eseguendo più ricerche di seguito
     */
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
    public static double getMin() {
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
    public static double getMax() {
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
    public static boolean isDelivery() {
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
    public static boolean isPrenotazione() {
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
    public static String getTipoCucina() {
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
     * @return ArrayList<Ristorante> risrec
     */
    public ArrayList<Ristorante> getRisposteRecensioni(List<Recensione> recensioni) {
        return risrec;
    }
    public void setRisposteRecensioni(ArrayList<Ristorante> RisposteRecensioni) {
        this.risrec =RisposteRecensioni;
    }

    /**
     * Metodo per ottenere la fascia di prezzo
     * @param min
     * @param max
     * @return String min + "-" + max
     */
    public String getFasciaPrezzo(int min, int max) {
        return min + "-" + max;
    }

    private static double calcolaDistanza(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
    /**
     * Metodo per ottenere se il ristorante è vicino o no a una determinata posizione
     *
     * @param indirizzo
     * @return Boolean distanza <= 10Km
     */
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
    /**
     * Metodo per aggiungere una recensione a uno specifico ristorante
     * @param recensioni
     * @param r
     */
    public  void aggiungiRecensione(Recensione r, List<Recensione> recensioni) {
        recensioni.add(r);
    }
    /**
     * Metodo per vedere tutte le recensioni di un ristorante
     * @param recensioni
     * @return List<Recensione> recensioni
     */
    public  List<Recensione> getRecensioni(List<Recensione> recensioni) {
        return recensioni;
    }
    /**
     * Metodo per rimuovere una recensione fatta a un ristorante
     * @param recensioni
     * @param r
     */
    public  void rimuoviRecensione(Recensione r, List<Recensione> recensioni) {
        recensioni.remove(r);
    }
    /**
     * Metodo per ottenere la media delle valutazioni date a un rstorante da tutti gli utenti
     *
     * @return Double somma / recensioni.size()
     */
    public static double valutazioneMedia() {
        if (recensioni.isEmpty()) return 0;
        int somma = 0;
        for (Recensione r : recensioni) {
            somma += r.getStelle();
        }
        double media = (double) somma / recensioni.size();
        return media;
    }
    /**
     * Metodo per ottenere il numero di recensioni di un ristorante specifico
     * @param recensioni
     * @return int recensioni.size()
     */
    public int numeroRecensioni(List<Recensione> recensioni) {
        return recensioni.size();
    }
    /**
     * Metodo per ottenere visualizzare un ristorante determinato
     *
     * @param lista
     * @param isVicino
     * @param nome
     * @param nazione
     * @param citta
     * @param indirizzo
     * @param tipoCucina
     * @param max
     * @param min
     * @param prenotazione
     * @param delivery
     * @return ArrayList<String> risultati
     */
    public ArrayList<String> visualizzaRistorante (ArrayList<Ristorante> lista, boolean isVicino, String indirizzo,
                                                       String citta, String nazione, String nome, String tipoCucina,
                                                       int min, int max, boolean delivery, boolean prenotazione, ArrayList<String> risultati) throws Exception {
        ArrayList<String> visualizzato = new ArrayList<>();
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

        return visualizzato;
    }
    /**
     * Metodo per eseguire una ricerca dei ristoranti vicini a una posizione geografica data dall'utente
     *
     * @param lista
     * @param nazione
     * @param citta
     * @param indirizzo
     * @param isVicino
     * @return ArrayList<String> risultatixPosizione
     */
    public ArrayList<String> cercaRistorantePosizione(ArrayList<Ristorante> lista, boolean isVicino, String indirizzo, String citta, String nazione) {
        ArrayList<String> risultatixPosizione = new ArrayList<String>();
            boolean posizioneOk = false;
            try {
                posizioneOk = isVicino(indirizzo +" "+ citta +" "+ nazione);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (posizioneOk) {
                risultatixPosizione.add(Ristorante.nome);
            }

        return risultatixPosizione;
    }
    /**
     * Metodo per cercare un ristorante in base alle tipologie di cucine che servono
     * @param lista
     * @param tipoCucina
     * @return ArrayList<String> risultatixTipoCucina
     */
    public static ArrayList<String> cercaRistoranteTipoCucina(ArrayList<Ristorante> lista, String tipoCucina) {
        ArrayList<String> risultatixTipoCucina = new ArrayList<>();
            boolean cucinaOk = getTipoCucina().equalsIgnoreCase(tipoCucina);
            if (cucinaOk) {
                risultatixTipoCucina.add(Ristorante.nome);
            }

        return risultatixTipoCucina;
    }
    /**
     * Metodo per cercare un ristorante in base alla fascia di prezzo
     * @param lista
     * @param max
     * @param min
     * @return ArrayList<String> risultatixPrezzo
     */
    public static ArrayList<String> cercaRistorantePrezzo(ArrayList<Ristorante> lista, double min, double max) {
        ArrayList<String> risultatixPrezzo = new ArrayList<>();

            boolean prezzominOk = getMin() >= min;
            boolean prezzomaxOk = getMax() <= max;
            if (prezzominOk && prezzomaxOk) {
                risultatixPrezzo.add(Ristorante.nome);
            }

        return risultatixPrezzo;
    }
    /**
     * Metodo per cercare un ristorante in base alla disponibilità a fare delivery
     * @param lista
     * @param delivery
     * @return ArrayList<String> risultatixDelivery
     */
    public static ArrayList<String> cercaRistoranteDelivery(ArrayList<Ristorante> lista,boolean delivery) {
        ArrayList<String> risultatixDelivery = new ArrayList<>();
            boolean deliveryOk = !delivery || isDelivery();
            if (deliveryOk) {
                risultatixDelivery.add(Ristorante.nome);
            }

        return risultatixDelivery;
    }
    /**
     * Metodo per cercare un ristorante in base alla disponibilità ad accettare le prenotazioni
     *
     * @return ArrayList<String> risultatixPrenotazione
     */
    public static ArrayList<String> cercaRistorantePrenotazione(ArrayList<Ristorante> lista, boolean prenotazione) {
        ArrayList<String> risultatixPrenotazione = new ArrayList<>();
            boolean prenotazioneOk = !prenotazione || isPrenotazione();
            if (prenotazioneOk) {
                risultatixPrenotazione.add(Ristorante.nome);
            }

        return risultatixPrenotazione;
    }
    /**
     * Metodo per cercare un ristorante in base alle recensioni ricevute
     * @param lista
     * @param mediaStelle
     * @return ArrayList<String> risultatixRecensioni
     */
    public static ArrayList<String> cercaRistoranteRecensioni(ArrayList<Ristorante> lista, double mediaStelle) {
        ArrayList<String> risultatixRecensioni = new ArrayList<>();
            boolean MrecOK = valutazioneMedia() >= mrec;
            if (MrecOK) {
                risultatixRecensioni.add(Ristorante.nome);
            }

        return risultatixRecensioni;
    }
    /**
     * Metodo per poter effettuare la ricerca dei ristoranti usando più di un criterio di ricerca
     * @return ArrayList<String> risultati
     */
    public ArrayList<String> cercaRistorante(ArrayList<Ristorante> lista) {
        ArrayList<String> risultati = new ArrayList<>();
        risultati.addAll(cercaRistorantePrenotazione(lista, prenotazione));
        risultati.addAll(cercaRistoranteRecensioni(lista, mrec));
        risultati.addAll(cercaRistoranteDelivery(lista, delivery));
        risultati.addAll(cercaRistorantePrezzo(lista, min, max));
        risultati.addAll(cercaRistoranteTipoCucina(lista, tipoCucina));
        risultati.addAll(cercaRistorantePosizione(lista, true, indirizzo, citta, nazione));
        return risultati;
    }
}