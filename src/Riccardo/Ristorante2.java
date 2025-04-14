package src.Riccardo;
import src.Matteoo.Recensione;
import java.util.ArrayList;

/**
 * Classe Ristorante che rappresenta un ristorante presente nella piattaforma TheKnife.
 */
public class Ristorante2 {
    private int id;
    private String nome, nazione, citta, indirizzo, tipoCucina;
    private boolean delivery, prenotazioneOnline;
    private double minPrezzo, maxPrezzo, latitudine, longitudine;
    private static int idCounter = 0;
    private ArrayList<Recensione> recensioni;

    // ========================= COSTRUTTORI ========================= //

    public Ristorante2(int id, String nome, String nazione, String citta, String indirizzo, String tipoCucina,
                       boolean delivery, boolean prenotazioneOnline, double minPrezzo, double maxPrezzo) throws Exception {
        this.id = id;
        inizializzaCampi(nome, nazione, citta, indirizzo, tipoCucina, delivery, prenotazioneOnline, minPrezzo, maxPrezzo);
    }
    public Ristorante2(String nome, String nazione, String citta, String indirizzo, String tipoCucina,
                       boolean delivery, boolean prenotazioneOnline, double minPrezzo, double maxPrezzo) throws Exception {
        this.id = idCounter++;
        inizializzaCampi(nome, nazione, citta, indirizzo, tipoCucina, delivery, prenotazioneOnline, minPrezzo, maxPrezzo);
    }

    private void inizializzaCampi(String nome, String nazione, String citta, String indirizzo, String tipoCucina,
                                  boolean delivery, boolean prenotazioneOnline, double minPrezzo, double maxPrezzo) throws Exception {
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
        try {
            double[] data = ReverseGeocoding.getLatitudineLongitudine(indirizzo + ", " + citta + ", " + nazione);
            if (data[0] != -1 && data[1] != -1) {
                this.latitudine = data[0];
                this.longitudine = data[1];
            }
        } catch (Exception e) {
            throw new Exception("Errore durante il geocoding: indirizzo non valido");
        }
    }

    // ========================= GETTER / SETTER ========================= //

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getNazione() { return nazione; }
    public String getCitta() { return citta; }
    public String getIndirizzo() { return indirizzo; }
    public String getTipoCucina() { return tipoCucina; }
    public boolean isDelivery() { return delivery; }
    public boolean isPrenotazioneOnline() { return prenotazioneOnline; }
    public double getMinPrezzo() { return minPrezzo; }
    public double getMaxPrezzo() { return maxPrezzo; }

    public void setNome(String nome) { this.nome = nome; }
    public void setNazione(String nazione) { this.nazione = nazione; }
    public void setCitta(String citta) { this.citta = citta; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }
    public void setTipoCucina(String tipoCucina) { this.tipoCucina =
            tipoCucina; }
    public void setDelivery(boolean delivery) { this.delivery = delivery; }
    public void setPrenotazioneOnline(boolean prenotazioneOnline) { this.prenotazioneOnline = prenotazioneOnline; }
    public void setMinPrezzo(double minPrezzo) { this.minPrezzo = minPrezzo; }
    public void setMaxPrezzo(double maxPrezzo) { this.maxPrezzo = maxPrezzo; }

    public ArrayList<Recensione> getRecensioni() { return this.recensioni; }


    public void recensisciRistorante(Recensione recensione) {
        this.recensioni.add(recensione);
    }

    public void rimuoviRecensione(Recensione recensione) {
        this.recensioni.remove(recensione);
    }

    public void modificaRecensione(Recensione recensione, String nuovoTesto, int nuoveStelle) {
        recensione.modifica(nuovoTesto, nuoveStelle);
    }

    public int getValutazioneMedia() {
        if (recensioni.isEmpty()) return 0;
        int somma = 0;
        for (Recensione r : recensioni) {
            somma += r.getNumeroStelle();
        }
        return somma / recensioni.size();
    }

    public String getFasciaPrezzo() {
        if (maxPrezzo < 20) return "Economico";
        if (maxPrezzo <= 50) return "Medio";
        return "Costoso";
    }

    public boolean isVicino(String indirizzo) throws Exception {
        try {
            double[] data = ReverseGeocoding.getLatitudineLongitudine(indirizzo);
            if (data[0] == -1 || data[1] == -1)
                throw new Exception("Coordinate non valide.");
            double distanza = calcolaDistanza(this.latitudine, this.longitudine, data[0], data[1]);
            return distanza <= 10000; // 10 km
        } catch (Exception e) {
            throw new Exception("Errore durante il geocoding: " + e.getMessage());
        }
    }
    public String visualizzaRistorante() {
        return "Ristorante: " + nome + "\n" +
                "Luogo: " + indirizzo + ", " + citta + ", " + nazione + "\n" +
                "Cucina: " + tipoCucina + "\n" +
                "Fascia di prezzo: " + minPrezzo + "€ - " + maxPrezzo + "€\n" +
                "Servizi: Delivery = " + delivery + ", Prenotazione online = " + prenotazioneOnline + "\n" +
                "Media voti: " + getValutazioneMedia() + " su " + recensioni.size() + " recensioni\n";
    }

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
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Ristorante2 that)) return false;
        return this.id == that.id;
    }

    @Override
    public String toString() {
        return visualizzaRistorante();
}
}

