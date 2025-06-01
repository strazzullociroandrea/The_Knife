package src.model;


import src.model.exception.RecensioneOutOfBoundException;
import src.model.exception.StelleOutOfBoundException;
import src.model.util.ReverseGeocoding;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Ristorante che rappresenta un ristorante presente nella piattaforma TheKnife.
 * @version 1.0
 *
 * @Author Strazzullo Ciro Andrea
 * @Author Riccardo Giovanni Rubini
 * @Author Matteo Mongelli
 */
public class Ristorante {

    /**
     * ID del ristorante, univoco per ogni ristorante.
     */
    private int id;
    /**
     * Stringhe per memorizzare le informazioni del ristorante.
     */
    private String nome, nazione, citta, indirizzo, tipoCucina;
    /**
     * Booleani per memorizzare le informazioni sui servizi offerti dal ristorante.
     */
    private boolean delivery, prenotazioneOnline;
    /**
     * double per memorizzare le informazioni sui prezzi del ristorante.
     */
    private double minPrezzo, maxPrezzo;
    /**
     * Contatore statico per generare ID univoci per i ristoranti.
     */
    private static int idCounter = 0;
    /**
     * ArrayList per memorizzare le recensioni associate al ristorante.
     */
    private ArrayList<Recensione> recensioni;


    /**
     * Costruttore per creare un ristorante con ID specificato.
     * @param id                 ID del ristorante
     * @param nome               Nome del ristorante
     * @param nazione            Nazione del ristorante
     * @param citta              Città del ristorante
     * @param indirizzo          Indirizzo del ristorante
     * @param tipoCucina         Tipo di cucina del ristorante
     * @param delivery           Indica se il ristorante offre servizio di delivery
     * @param prenotazioneOnline Indica se il ristorante offre prenotazione online
     * @param minPrezzo          Prezzo minimo del ristorante
     * @param maxPrezzo          Prezzo massimo del ristorante
     */
    public Ristorante(int id, String nome, String nazione, String citta, String indirizzo, String tipoCucina,
                      boolean delivery, boolean prenotazioneOnline, double minPrezzo, double maxPrezzo) {
        this.id = id;
        this.inizializzaCampi(nome, nazione, citta, indirizzo, tipoCucina, delivery, prenotazioneOnline, minPrezzo, maxPrezzo);
       //Gestione unicità dell'id
        if (this.id >= idCounter) {
            idCounter = this.id + 1;
        }
    }

    /**
     * Costruttore per creare un ristorante con ID specificato.
     * @param nome               Nome del ristorante
     * @param nazione            Nazione del ristorante
     * @param citta              Città del ristorante
     * @param indirizzo          Indirizzo del ristorante
     * @param tipoCucina         Tipo di cucina del ristorante
     * @param delivery           Indica se il ristorante offre servizio di delivery
     * @param prenotazioneOnline Indica se il ristorante offre prenotazione online
     * @param minPrezzo          Prezzo minimo del ristorante
     * @param maxPrezzo          Prezzo massimo del ristorante
     */
    public Ristorante(String nome, String nazione, String citta, String indirizzo, String tipoCucina,
                      boolean delivery, boolean prenotazioneOnline, double minPrezzo, double maxPrezzo) {
        this.id = idCounter++;
        this.inizializzaCampi(nome, nazione, citta, indirizzo, tipoCucina, delivery, prenotazioneOnline, minPrezzo, maxPrezzo);
    }

    /**
     * Metodo univoco per inizializzare i campi del ristorante.
     * @param nome               Nome del ristorante
     * @param nazione            Nazione del ristorante
     * @param citta              Città del ristorante
     * @param indirizzo          Indirizzo del ristorante
     * @param tipoCucina         Tipo di cucina del ristorante
     * @param delivery           Indica se il ristorante offre servizio di delivery
     * @param prenotazioneOnline Indica se il ristorante offre prenotazione online
     * @param minPrezzo          Prezzo minimo del ristorante
     * @param maxPrezzo          Prezzo massimo del ristorante
     */
    private void inizializzaCampi(String nome, String nazione, String citta, String indirizzo, String tipoCucina,
                                  boolean delivery, boolean prenotazioneOnline, double minPrezzo, double maxPrezzo)  {
        this.nome = nome;
        this.nazione = nazione;
        this.citta = citta;
        this.indirizzo = indirizzo;
        this.tipoCucina = tipoCucina;
        this.delivery = delivery;
        this.prenotazioneOnline = prenotazioneOnline;
        this.minPrezzo = minPrezzo;
        this.maxPrezzo = maxPrezzo;
        this.recensioni = new ArrayList<>();
    }


    /**
     * Metodo per ottenere l'ID del ristorante.
     * @return id univoco del ristorante
     */
    public int getId() {
        return this.id;
    }

    /**
     * Metodo per ottenere il nome del ristorante.
     * @return nome del ristorante
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Metodo per ottenere la nazione del ristorante.
     * @return nazione del ristorante
     */
    public String getNazione() {
        return this.nazione;
    }

    /**
     * Metodo per ottenere la città del ristorante.
     * @return città del ristorante
     */
    public String getCitta() {
        return this.citta;
    }

    /**
     * Metodo per ottenere l'indirizzo del ristorante.
     * @return indirizzo del ristorante
     */
    public String getIndirizzo() {
        return this.indirizzo;
    }

    /**
     * Metodo per ottenere il tipo di cucina del ristorante.
     * @return tipo di cucina del ristorante
     */
    public String getTipoCucina() {
        return this.tipoCucina;
    }

    /**
     * Metodo per determinare se è presente il servizio di delivery nel ristorante.
     * @return true disponibile, false altrimenti
     */
    public boolean isDelivery() {
        return this.delivery;
    }

    /**
     * Metodo per determinare se è presente il servizio di prenotazione online nel ristorante.
     * @return true disponibile, false altrimenti
     */
    public boolean isPrenotazioneOnline() {
        return this.prenotazioneOnline;
    }

    /**
     * Metodo per ottenere il prezzo minimo del ristorante.
     * @return prezzo minimo del ristorante
     */
    public double getMinPrezzo() {
        return this.minPrezzo;
    }

    /**
     * Metodo per ottenere il prezzo massimo del ristorante.
     * @return prezzo massimo del ristorante
     */
    public double getMaxPrezzo() {
        return this.maxPrezzo;
    }

    /**
     * Metodo per ottenere la latitudine del ristorante
     * @return latitudine del ristorante
     * @throws Exception eccezione lanciata se il geocoding fallisce
     */
    public double getLatitudine() throws Exception {
        try {
            double[] data = ReverseGeocoding.getLatitudineLongitudine(this.indirizzo + ", " + this.citta + ", " + this.nazione);
            if (data[0] != -1 && data[1] != -1) {
                return data[0];
            }
        } catch (Exception e) {
            throw new Exception("Errore durante il geocoding: indirizzo non valido");
        }
        return -1;
    }
    /**
     * Metodo per ottenere la longitudine del ristorante
     * @return longitudine del ristorante
     * @throws Exception eccezione lanciata se il geocoding fallisce
     */
    public double getLongitudine() throws Exception {
        try {
            double[] data = ReverseGeocoding.getLatitudineLongitudine(this.indirizzo + ", " + this.citta + ", " + this.nazione);
            if (data[0] != -1 && data[1] != -1) {
                return data[1];
            }
        } catch (Exception e) {
            throw new Exception("Errore durante il geocoding: indirizzo non valido");
        }
        return -1;
    }

    /**
     * Metodo per modificare il nome del ristorante
     * @param nome nuovo nome del ristorante
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Metodo per modificare la nazione del ristorante.
     * @param nazione nuova nazione del ristorante
     */
    public void setNazione(String nazione) {
        this.nazione = nazione;
    }

    /**
     * Metodo per modificare la città del ristorante.
     * @param citta nuova città del ristorante
     */
    public void setCitta(String citta) {
        this.citta = citta;
    }

    /**
     * Metodo per modificare l'indirizzo del ristorante.
     * @param indirizzo nuovo indirizzo del ristorante
     */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    /**
     * Metodo per modificare il tipo di cucina del ristorante.
     * @param tipoCucina nuovo tipo di cucina del ristorante
     */
    public void setTipoCucina(String tipoCucina) {
        this.tipoCucina = tipoCucina;
    }

    /**
     * Metodo per modificare il servizio di delivery del ristorante.
     * @param delivery true se disponibile, false altrimenti
     */
    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    /**
     * Metodo per modificare il servizio di prenotazione online del ristorante
     * @param prenotazioneOnline true se disponibile, false altrimenti
     */
    public void setPrenotazioneOnline(boolean prenotazioneOnline) {
        this.prenotazioneOnline = prenotazioneOnline;
    }

    /**
     * Metodo per modificare il prezzo minimo del ristorante.
     * @param minPrezzo nuovo prezzo minimo del ristorante
     * @throws IllegalArgumentException Eccezione lanciata se il prezzo minimo è maggiore al prezzo massimo
     */
    public void setMinPrezzo(double minPrezzo) throws IllegalArgumentException{
       if(minPrezzo > this.maxPrezzo)
           throw new IllegalArgumentException("Il prezzo minimo non può essere maggiore al prezzo massimo");
        this.minPrezzo = minPrezzo;
    }

    /**
     * Metodo per modificare il prezzo massimo del ristorante.
     * @param maxPrezzo nuovo prezzo massimo del ristorante
     * @throws IllegalArgumentException Eccezione lanciata se il prezzo massimo è inferiore al prezzo minimo
     */
    public void setMaxPrezzo(double maxPrezzo) throws IllegalArgumentException{
       if(maxPrezzo < this.minPrezzo)
           throw new IllegalArgumentException("Il prezzo massimo non può essere inferiore al prezzo minimo");
        this.maxPrezzo = maxPrezzo;
    }

    /**
     * Metodo per ottenere tutte le recensioni del ristorante
     * @return arraylist di recensioni
     */
    public ArrayList<Recensione> getRecensioni() {
        return this.recensioni;
    }

    /**
     * Metodo per recensire il ristorante
     * @param recensione recensione da aggiungere
     */
    public void recensisciRistorante(Recensione recensione) {
        this.recensioni.add(recensione);
    }

    /**
     * Metodo per rimuovere una recensione dall'elenco
     * @param recensione recensione da rimuovere
     */
    public void rimuoviRecensione(Recensione recensione) {
        this.recensioni.remove(recensione);
    }

    /**
     * Metodo per modificare una recensione
     * @param recensione  recensione da modificare
     * @param nuovoTesto  nuovo testo della recensione
     * @param nuoveStelle nuovo numero di stelle della recensione
     * @throws StelleOutOfBoundException Eccezione se il numero di stelle è fuori dai limiti
     * @throws RecensioneOutOfBoundException Eccezione se il testo della recensione è vuoto o supera i 250 caratteri
     */
    public void modificaRecensione(Recensione recensione, String nuovoTesto, int nuoveStelle) throws StelleOutOfBoundException, RecensioneOutOfBoundException {
        if( nuovoTesto.length() > 250 || nuovoTesto.isEmpty())
            throw new RecensioneOutOfBoundException();
        else  recensione.setDescrizione(nuovoTesto);

        if (nuoveStelle > 0 && nuoveStelle <= 5)
            recensione.setNumeroStelle(nuoveStelle);
        else throw new StelleOutOfBoundException();
    }

    /**
     * Metodo per ottenere la valutazione media del ristorante
     * @return valutazione media del ristorante
     * @throws  ArithmeticException Eccezione se si verifica una divisione per zero
     */
    public double getValutazioneMedia() throws ArithmeticException{
        if (this.recensioni.isEmpty()) return 0;
        double somma = 0;
        for (Recensione r : this.recensioni) {
            somma += r.getStelle();
        }
        try {
            return somma / this.recensioni.size();
        }catch(ArithmeticException e ) {
            throw new ArithmeticException("Divisione per zero");
        }
    }

    /**
     * Metodo per ottenere la fascia di prezzo del ristorante
     * @return fascia di prezzo del ristorante "Economico, "Medio", "Costoso"
     */
    public String getFasciaPrezzo() {
        if (this.maxPrezzo < 20) return "Economico";
        else if (this.maxPrezzo <= 50) return "Medio";
        else  return "Costoso";
    }

    /**
     * Metodo per verificare se il ristorante è vicino ad un indirizzo di 10Km
     * @param indirizzo indirizzo da confrontare
     * @return true se il ristorante è vicino, false altrimenti
     * @throws Exception Eccezione se il geocoding fallisce
     */
    public boolean isVicino(String indirizzo) throws Exception {
        try {
            double[] data = ReverseGeocoding.getLatitudineLongitudine(indirizzo);
            if (data[0] == -1 || data[1] == -1)
                throw new Exception("Coordinate non valide.");
            double distanza = calcolaDistanza(this.getLatitudine(), this.getLongitudine(), data[0], data[1]);
            return distanza <= 10000; // 10 km
        } catch (Exception e) {
            throw new Exception("Errore durante il geocoding: " + e.getMessage());
        }
    }
    /**
     * Metodo per visualizzare le informazioni del ristorante in modo leggibile
     * @return stringa complementare al metodo toString
     */
    public String visualizzaRistorante() {
        return "Ristorante: " + this.nome + "\n" +
                "Luogo: " + this.indirizzo + ", " + this.citta + ", " + this.nazione + "\n" +
                "Cucina: " + this.tipoCucina + "\n" +
                "Fascia di prezzo: " + this.minPrezzo + "€ - " + this.maxPrezzo + "€\n" +
                "Servizi: Delivery = " + this.delivery + ", Prenotazione online = " + this.prenotazioneOnline + "\n" +
                "Media voti: " + this.getValutazioneMedia() + " su " + this.recensioni.size() + " recensioni\n";
    }

    /**
     * Metodo per calcolare il valore di haversine
     * link al codice https://www.baeldung.com/java-find-distance-between-points
     * @param val valore da calcolare
     * @return valore di haversine
     */
    private double haversine(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

    /**
     * Metodo per calcolare la distanza tra due punti geografici utilizzando la formula dell'emisenoverso
     * utilizzata per il calcolo della distanza tra due punti sulla superficie terrestre.
     * link al codice: https://www.baeldung.com/java-find-distance-between-points
     * @param lat1 latitudine del primo punto
     * @param lon1 longitudine del primo punto
     * @param lat2 latitudine del secondo punto
     * @param lon2 longitudine del secondo punto
     * @return distanza
     */
    private double calcolaDistanza(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000;//Raggio terrestre in metri, valore approssimativo
        //Conversione distanze in radianti
        double dLat = Math.toRadians((lat2 - lat1));
        double dLong = Math.toRadians((lon2 - lon1));
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        double a = haversine(dLat) + Math.cos(lat1) * Math.cos(lat2) * haversine(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; //Distanza in metri
    }

    /**
     * Metodo per verificare se due ristoranti sono uguali
     * @param o oggetto da confrontare
     * @return true se i ristoranti sono uguali, false altrimenti
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ( o == null || !(o instanceof Ristorante r)) return false;
        return this.id == r.getId();
    }


    /**
     * Metodo per visualizzare il dettaglio del ristorante
     * @return stringa di dettaglio complementare al metodo visualizzaRistorante
     */
    @Override
    public String toString() {
        return this.visualizzaRistorante();
    }

    /**
     * Metodo static per ricercare i ristoranti in base alla locazione
     * @param elenco lista di ristoranti
     * @param locazione locazione da cercare nel formato "via, citta, nazione"
     * @return lista filtrata
     * @throws Exception  eccezione lanciata se l'elenco è null o la locazione è vuota
     */
    public static List<Ristorante> perLocazione(List<Ristorante> elenco, String locazione) throws Exception {
        if (elenco == null || locazione.isEmpty()) {
            throw new IllegalArgumentException("Elenco o locazione non possono essere nulli");
        }
        List<Ristorante> tmp = new ArrayList<>();
        for (Ristorante r : elenco) {
            if(r.isVicino(locazione)){
                tmp.add(r);
            }
        }
        return tmp;
    }

    /**
     * Metodo statico per la ricerca di ristoranti in base alla locazione e al tipo di cucina
     * @param elenco lista dei ristoranti
     * @param tipoCucina tipo cucina ricercata
     * @param locazione locazione da cercare nel formato "via, citta, nazione"
     * @return lista filtrata
     * @throws Exception eccezione lanciata se l'elenco è null o la locazione è vuota
     */
    public static List<Ristorante> perTipoCucina(List<Ristorante> elenco, String tipoCucina,String locazione) throws Exception {
        if (elenco == null || tipoCucina.isEmpty() || locazione.isEmpty()) {
            throw new IllegalArgumentException("Elenco/tipo di cucina/locazione non possono essere nulli");
        }
        List<Ristorante> tmp = Ristorante.perLocazione(elenco, locazione);
        List<Ristorante> tmp2 = new ArrayList<>();
        for (Ristorante r : tmp) {
            if (r.getTipoCucina().equalsIgnoreCase(tipoCucina)) {
                tmp2.add(r);
            }
        }
        return tmp2;
    }

    /**
     * Metodo statico per la ricerca di ristoranti in base alla locazione e alla fascia di prezzo (min e max)
     * @param elenco lista dei ristoranti
     * @param min prezzo minimo da cercare
     * @param max prezzo massimo da cercare
     * @param locazione locazione da cercare nel formato "via, citta, nazione"
     * @return lista filtrata
     * @throws Exception eccezione lanciata se l'elenco è null o la locazione è vuota
     */
    public static List<Ristorante> perFasciaPrezzo(List<Ristorante> elenco, double min, double max,String locazione) throws Exception{
        if (elenco == null || locazione.isEmpty()) {
            throw new IllegalArgumentException("Elenco/locazione non possono essere nulli");
        }
        List<Ristorante> tmp = Ristorante.perLocazione(elenco, locazione);
        List<Ristorante> tmp2 = new ArrayList<>();
        for (Ristorante r : tmp) {
            if ((min <= 0 || r.getMinPrezzo() >= min) &&
                    (max <= 0 || r.getMaxPrezzo() <= max)) {
                tmp2.add(r);
            }
        }

        return tmp2;
    }

    /**
     * Metodo statico per la ricerca di ristoranti in base alla locazione e alla possibilità di fare delivery o meno
     * @param elenco lista dei ristoranti
     * @param delivery  true se si vuole il ristorante che offre il servizio di delivery, false altrimenti
     * @param locazione locazione da cercare nel formato "via, citta, nazione"
     * @return lista filtrata
     * @throws Exception eccezione lanciata se l'elenco è null o la locazione è vuota
     */
    public static List<Ristorante> perDelivery(List<Ristorante> elenco, boolean delivery, String locazione ) throws Exception{
        if( elenco == null || locazione.isEmpty()) {
            throw new IllegalArgumentException("Elenco/locazione non possono essere nulli");
        }
        List<Ristorante> tmp = Ristorante.perLocazione(elenco, locazione);
        List<Ristorante> tmp2 = new ArrayList<>();
        for (Ristorante r : tmp) {
            if (r.isDelivery() == delivery) {
                tmp2.add(r);
            }
        }
        return tmp2;
    }

    /**
     * Metodo statico per la ricerca di ristoranti in base alla locazione e alla possibilità di avere la prenotazione online o meno
     * @param elenco lista dei ristoranti
     * @param prenotazioneOnline  true se si vuole il ristorante che offre il servizio di prenotazione online, false altrimenti
     * @param locazione locazione da cercare nel formato "via, citta, nazione"
     * @return lista filtrata
     * @throws Exception eccezione lanciata se l'elenco è null o la locazione è vuota
     */
    public static List<Ristorante> perPrenotazioneOnline(List<Ristorante> elenco, boolean prenotazioneOnline, String locazione) throws Exception  {
        if (elenco == null || locazione.isEmpty()) {
            throw new IllegalArgumentException("Elenco/locazione non possono essere nulli");
        }
        List<Ristorante> tmp = Ristorante.perLocazione(elenco, locazione);
        List<Ristorante> tmp2 = new ArrayList<>();
        for (Ristorante r : tmp) {
            if (r.isPrenotazioneOnline() == prenotazioneOnline) {
                tmp2.add(r);
            }
        }
        return tmp2;
    }

    /**
     * Metodo statico per la ricerca di ristoranti in base alla locazione e alla media delle stelle minima
     * @param elenco lista dei ristoranti
     * @param minimoStelle  numero minimo di stelle da cercare
     * @param locazione locazione da cercare nel formato "via, citta, nazione"
     * @return lista filtrata
     * @throws Exception eccezione lanciata se l'elenco è null o la locazione è vuota
     */
    public static List<Ristorante> perMediaStelle(List<Ristorante> elenco, double minimoStelle, String locazione) throws Exception {
        if( elenco == null || locazione.isEmpty()) {
            throw new IllegalArgumentException("Elenco/locazione non possono essere nulli");
        }
        List<Ristorante> tmp = Ristorante.perLocazione(elenco, locazione);
        List<Ristorante> tmp2 = new ArrayList<>();
        for (Ristorante r : tmp) {
            if( r.getValutazioneMedia() >= minimoStelle) {
                tmp2.add(r);
            }
        }
        return tmp2;
    }

    /**
     * Metodo statico per la ricerca di ristoranti in base a più parametri
     * @param elenco lista dei ristoranti
     * @param locazione locazione da cercare nel formato "via, citta, nazione"
     * @param tipoCucina tipo cucina da cercare
     * @param minPrezzo prezzo minimo da cercare
     * @param maxPrezzo prezzo massimo da cercare
     * @param vuoiDelivery true se si vuole che la ricerca tramite filtro comprenda il servizio di delivery, false altrimenti
     * @param delivery  true se si vuole il ristorante che offre il servizio di delivery, false altrimenti
     * @param vuoiPrenotazione true se si vuole che la ricerca tramite filtro comprenda il servizio di prenotazione, false altrimenti
     * @param prenotazione true se si vuole il ristorante che offre il servizio di prenotazione online, false altrimenti
     * @param minStelle numero minimo di stelle da cercare
     * @return  lista filtrata
     * @throws Exception eccezione lanciata se l'elenco è null o la locazione è vuota
     */
    public static List<Ristorante> combinata(List<Ristorante> elenco,
                                             String locazione,
                                             String tipoCucina,
                                             double minPrezzo,
                                             double maxPrezzo,
                                             boolean vuoiDelivery,
                                             boolean delivery,
                                             boolean vuoiPrenotazione,
                                             boolean prenotazione,
                                             int minStelle) throws Exception {
        if (elenco == null) {
            throw new IllegalArgumentException("L'elenco non può essere nullo.");
        }
        if (locazione == null || locazione.trim().isEmpty()) {
            throw new IllegalArgumentException("La locazione non può essere vuota.");
        }

        // Filtro obbligatorio per locazione
        List<Ristorante> tmp = Ristorante.perLocazione(elenco, locazione);

        // Filtro per tipo di cucina, se specificato
        if (tipoCucina != null && !tipoCucina.trim().isEmpty()) {
            tmp = Ristorante.perTipoCucina(tmp, tipoCucina,locazione);
        }

        // Filtro per fascia di prezzo
        tmp = Ristorante.perFasciaPrezzo(tmp, minPrezzo, maxPrezzo,locazione);

        // Filtro per servizio delivery se richiesto
        if (vuoiDelivery) {
            tmp = Ristorante.perDelivery(tmp, delivery,locazione);
        }

        // Filtro per servizio prenotazione online se richiesto
        if (vuoiPrenotazione) {
            tmp = Ristorante.perPrenotazioneOnline(tmp, prenotazione,locazione);
        }

        // Filtro per media stelle
        if (minStelle > 0) {
            tmp = Ristorante.perMediaStelle(tmp, minStelle, locazione);
        }

        return tmp;
    }

}
