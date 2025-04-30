package src.Nico;


import src.Ciro.ReverseGeocoding;
import src.Matteoo.Recensione;

import java.util.ArrayList;
import java.util.List;

import static src.Nico.Ristorante.GestoreRecensioni.mediaStelle;

public class Ristorante {
    private String nome;
    private String nazione;
    private String citta;
    private String indirizzo;
    private double lat;
    private double log;
    private double min;
    private double max;
    private boolean delivery;
    private boolean prenotazione;
    private String tipoCucina;
    private static double mrec;
    private static int idcont = 0;
    private static int id = 0;
    private static int id1 = 0;
    private static int id2 = 0;
    private static int id3 = 0;
    private static int id4 = 0;
    private static int id5 = 0;
    private static int id6 = 0;

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
     * Metodo per ottenere il la media delle recensioni
     *
     * @return double mrec
     */
    public double getMrec() {
        return mrec;
    }

    public void setMrec(double mrec) {
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
        this.idcont = idcont;
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
            double distanza = calcolaDistanza(this.lat, this.log, data[0], data[1]);
            return distanza <= 10000; // 10 km
        } catch (Exception e) {
            throw new Exception("Errore durante il geocoding: " + e.getMessage());
        }
    }
    public class GestoreRecensioni {
        //attributi
        private static List<Recensione> recensioni = new ArrayList<>();

        //metodi
        public static void aggiungiRecensione(Recensione r) {
            recensioni.add(r);
        }

        public static List<Recensione> getRecensioni() {
            return recensioni;
        }

        public static double mediaStelle() {
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

    }

    public class CercaRistorantePosizione {
        private static int idcont = 0;
        private int instanceId;

        public CercaRistorantePosizione(int instanceId) {
            this.instanceId = instanceId;
        }

        public CercaRistorantePosizione() {
            this.instanceId = ++idcont;
        }
    }

    public class CercaRistoranteTipoCucina {
        private static int idcont = 0;
        private int instanceId;

        public CercaRistoranteTipoCucina(int instanceId) {
            this.instanceId = instanceId;
        }

        public CercaRistoranteTipoCucina() {
            this.instanceId = ++idcont;
        }
    }

    public class CercaRistorantePrezzo {
        private static int idcont = 0;
        private int instanceId;

        public CercaRistorantePrezzo(int instanceId) {
            this.instanceId = instanceId;
        }

        public CercaRistorantePrezzo() {
            this.instanceId = ++idcont;
        }
    }

    public class CercaRistoranteDelivery {
        private static int idcont = 0;
        private int instanceId;

        public CercaRistoranteDelivery(int instanceId) {
            this.instanceId = instanceId;
        }

        public CercaRistoranteDelivery() {
            this.instanceId = ++idcont;
        }
    }

    public class CercaRistorantePrenotazione {
        private static int idcont = 0;
        private int instanceId;

        public CercaRistorantePrenotazione(int instanceId) {
            this.instanceId = instanceId;
        }

        public CercaRistorantePrenotazione() {
            this.instanceId = ++idcont;
        }
    }

    public class CercaRistoranteRecensioni {
        private static int idcont = 0;
        private int instanceId;

        public CercaRistoranteRecensioni(int instanceId) {
            this.instanceId = instanceId;
        }

        public CercaRistoranteRecensioni() {
            this.instanceId = ++idcont;
        }
    }

    public class CercaRistorante {
        private static int idcont = 0;
        private int instanceId;

        public CercaRistorante(int instanceId) {
            this.instanceId = instanceId;
        }

        public CercaRistorante() {
            this.instanceId = ++idcont;
        }
    }

    public ArrayList<Ristorante> cercaRistorantePosizione(ArrayList<Ristorante> lista, boolean isVicino, String indirizzo, String citta, String nazione) throws Exception {
        ArrayList<Ristorante> risultatixPosizione = new ArrayList<>();

        for (Ristorante r : lista) {
            boolean posizioneOk = isVicino(indirizzo + citta + nazione);
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
            boolean MrecOK = mediaStelle() <= mrec;
            if (MrecOK) {
                risultatixRecensioni.add(r);
            }
        }

        return risultatixRecensioni;
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