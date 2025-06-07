package src.model;

import src.model.exception.CreaRistoranteException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe Ristoratore
 *
 * @version 1.0
 * @Author Strazzullo Ciro Andrea
 * @Author Riccardo Giovanni Rubini
 * @Author Matteo Mongelli
 */
public class Ristoratore extends Utente {

    /**
     * Lista ristoranti gestiti dal ristoratore
     */
    private List<Ristorante> ristorantiGestiti;

    /**
     * Costruttore della classe Ristoratore
     *
     * @param id          id associato al ristoratore
     * @param password    password associata al ristoratore
     * @param nome        nome associato al ristoratore
     * @param cognome     cognome associato al ristoratore
     * @param username    username associato al ristoratore
     * @param dataNascita data di nascita del ristoratore
     * @param domicilio   domicilio del ristoratore
     * @throws Exception eccezione lanciata quando non si inseriscono dati validi
     */
    public Ristoratore(int id, String password, String nome, String cognome, String username, String dataNascita, String domicilio) throws Exception {
        super(id, password, nome, cognome, username, dataNascita, domicilio);
        this.ristorantiGestiti = new ArrayList<>();
    }

    /**
     * Costruttore della classe Ristoratore senza data di nascita
     *
     * @param id        id associato al ristoratore
     * @param password  password associata al ristoratore
     * @param nome      nome associato al ristoratore
     * @param cognome   cognome associato al ristoratore
     * @param username  username associato al ristoratore
     * @param domicilio domicilio del ristoratore
     * @throws Exception eccezione lanciata quando non si inseriscono dati validi
     */
    public Ristoratore(int id, String password, String nome, String cognome, String username, String domicilio) throws Exception {
        super(id, password, nome, cognome, username, domicilio);
        this.ristorantiGestiti = new ArrayList<>();
    }


    /**
     * Costruttore della classe Ristoratore senza id
     *
     * @param password    password associata al ristoratore
     * @param nome        nome associato al ristoratore
     * @param cognome     cognome associato al ristoratore
     * @param username    username associato al ristoratore
     * @param dataNascita data di nascita del ristoratore
     * @param domicilio   domicilio del ristoratore
     * @throws Exception eccezione lanciata quando non si inseriscono dati validi
     */
    public Ristoratore(String password, String nome, String cognome, String username, String dataNascita, String domicilio) throws Exception {
        super(password, nome, cognome, username, dataNascita, domicilio);
        this.ristorantiGestiti = new ArrayList<>();
    }


    /**
     * Costruttore della classe Ristoratore senza id e data di nascita
     *
     * @param password  password associata al ristoratore
     * @param nome      nome associato al ristoratore
     * @param cognome   cognome associato al ristoratore
     * @param username  username associato al ristoratore
     * @param domicilio domicilio del ristoratore
     * @throws Exception eccezione lanciata quando non si inseriscono dati validi
     */
    public Ristoratore(String password, String nome, String cognome, String username, String domicilio) throws Exception {
        super(password, nome, cognome, username, domicilio);
        this.ristorantiGestiti = new ArrayList<>();
    }

    /**
     * Metodo per creare un ristorante da ristoratore (lo aggiunge in automatico alla lista dei gestiti)
     *
     * @param nome         nome del ristorante
     * @param nazione      nazione del ristorante
     * @param citta        città del ristorante
     * @param indirizzo    indirizzo del ristorante
     * @param delivery     indica se il ristorante offre servizio di delivery
     * @param prenotazione indica se il ristorante accetta prenotazioni
     * @param tipoCucina   tipo di cucina del ristorante
     * @param prenotazioneOnline indica se il ristorante accetta prenotazioni online
     * @param minPrezzo prezzo minimo nel menù del ristorante
     * @param maxPrezzo prezzo massimo nel menù del ristorante
     */
    public Ristorante creaRistorante(String nome, String nazione, String citta, String indirizzo,
                                     boolean delivery, boolean prenotazione, String tipoCucina, boolean prenotazioneOnline, double minPrezzo, double maxPrezzo) throws CreaRistoranteException {
        try {
            Ristorante nuovoRistorante = new Ristorante(nome, nazione, citta, indirizzo, tipoCucina,
                    delivery, prenotazioneOnline, minPrezzo, maxPrezzo);
            ristorantiGestiti.add(nuovoRistorante);
            return nuovoRistorante;
        } catch (Exception e) {
            throw new CreaRistoranteException();
        }
    }

    /**
     * Metodo per aggiungere ristorante
     *
     * @param ristorante ristorante da aggiungere alla lista dei ristoranti gestiti
     */
    public void aggiungiRistorante(Ristorante ristorante) {
        ristorantiGestiti.add(ristorante);
    }

    /**
     * Metodo per eliminare il ristorante
     *
     * @param ristorante ristorante da eliminare dalla lista dei ristoranti gestiti
     */
    public void eliminaRistorante(Ristorante ristorante) {
        ristorantiGestiti.remove(ristorante);
    }

    /**
     * Metodo per prendere lista ristoranti gestiti
     *
     * @return lista dei ristoranti gestiti dal ristoratore
     */
    public List<Ristorante> getRistorantiGestiti() {
        return ristorantiGestiti;
    }

    /**
     * Metodo per rispondere alle recensioni
     *
     * @param recensione recensione a cui si vuole rispondere
     * @param risposta   risposta da dare alla recensione
     */
    public void rispondiRecensione(Recensione recensione, String risposta) {
        if (risposta == null) {
            try (Scanner s = new Scanner(System.in)) {
                System.out.println("Inserire la risposta: ");
                risposta = s.nextLine();
                recensione.setRisposta(risposta);
                System.out.println("Risposta aggiornata ");
            } catch (Exception e) {
                System.err.println(e);
            }
        } else {
            System.out.println("Risposta già presente: " + recensione.getRisposta());
            System.out.println("Vuoi inserire una nuova risposta?(si/no)");

            try (Scanner s = new Scanner(System.in)) {
                String r = s.nextLine();
                if (r.equalsIgnoreCase("si") || r.equalsIgnoreCase("sì")) {
                    System.out.println("Inserire la risposta: ");
                    risposta = s.nextLine();
                    recensione.setRisposta(risposta);
                    System.out.println("Risposta aggiornata ");
                } else if (r.equalsIgnoreCase("no")) {
                    System.out.println("Risposta non modificata: " + recensione.getRisposta());
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    /**
     * Metodo per visualizzare media stelle di un ristorante e il numero delle recensioni
     *
     * @param ristorante ristorante di cui si vuole visualizzare il riepilogo
     */
    public static void visualizzaRiepilogo(Ristorante ristorante) {
        int c = 0;
        System.out.println("media delle stelle: " + ristorante.getValutazioneMedia());
        for (Recensione r : ristorante.getRecensioni()) {
            c++;
        }
        System.out.println("numero recensioni: " + c);
    }

    /**
     * Metodo per impostare la lista dei ristoranti gestiti
     *
     * @param ristorantiGestiti lista di ristoranti gestiti dal ristoratore
     */
    public void setRistorantiGestiti(List<Ristorante> ristorantiGestiti) {
        this.ristorantiGestiti = ristorantiGestiti;
    }

    /**
     * Metodo per visualizzare il ruolo del ristoratore
     *
     * @return Restituisce "Ristoratore"
     */
    @Override
    public String getRuolo() {
        return "Ristoratore";
    }
}
