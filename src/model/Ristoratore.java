package src.model;

import src.model.exception.CreaRistoranteException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe Ristoratore
 * @version 1.0
 * @Author Strazzullo Ciro Andrea
 * @Author Riccardo Giovanni Rubini
 * @Author Matteo Mongelli
 */
public class Ristoratore extends Utente {

    //attributi
    /**
     * Lista ristoranti gestiti dal ristoratore
     */
    private List<Ristorante> ristorantiGestiti;


    //costruttori
    //costruttore base

    /**
     *Costruttore della classe Ristoratore
     * @param id id associato al ristoratore
     * @param password password associata al ristoratore
     * @param nome nome associato al ristoratore
     * @param cognome cognome associato al ristoratore
     * @param username username associato al ristoratore
     * @param dataNascita data di nascita del ristoratore
     * @param domicilio domicilio del ristoratore
     */
    public Ristoratore(int id, String password, String nome,String cognome, String username, String dataNascita, String domicilio) {
        super(id,password,nome,cognome,username,dataNascita,domicilio);
        this.ristorantiGestiti = new ArrayList<>();
    }

    //costruttore senza data nascita

    /**
     *Costruttore della classe Ristoratore senza data di nascita
     * @param id id associato al ristoratore
     * @param password password associata al ristoratore
     * @param nome nome associato al ristoratore
     * @param cognome cognome associato al ristoratore
     * @param username username associato al ristoratore
     * @param domicilio domicilio del ristoratore
     */
    public Ristoratore(int id, String password, String nome,String cognome, String username, String domicilio) {

        super(id,password,nome,cognome,username,domicilio);
        this.ristorantiGestiti = new ArrayList<>();
    }

    //costruttore senza id

    /**
     *Costruttore della classe Ristoratore senza id
     * @param password password associata al ristoratore
     * @param nome nome associato al ristoratore
     * @param cognome cognome associato al ristoratore
     * @param username username associato al ristoratore
     * @param dataNascita data di nascita del ristoratore
     * @param domicilio domicilio del ristoratore
     */
    public Ristoratore(String password, String nome,String cognome, String username, String dataNascita, String domicilio) {

        super(password,nome,cognome,username,dataNascita,domicilio);
        this.ristorantiGestiti = new ArrayList<>();
    }

    //costruttore senza id e data nascita

    /**
     *Costruttore della classe Ristoratore senza id e data di nascita
     * @param password password associata al ristoratore
     * @param nome nome associato al ristoratore
     * @param cognome cognome associato al ristoratore
     * @param username username associato al ristoratore
     * @param domicilio domicilio del ristoratore
     */
    public Ristoratore(String password, String nome,String cognome, String username, String domicilio) {

        super(password,nome,cognome,username, domicilio);
        this.ristorantiGestiti = new ArrayList<>();
    }

    //metodi
    /**
     *
     * metodo per creare un ristorante da ristoratore (lo aggiunge in automatico alla lista dei gestiti)
     * @param nome
     * @param nazione
     * @param citta
     * @param indirizzo
     * @param delivery
     * @param prenotazione
     * @param tipoCucina
     */
    public Ristorante creaRistorante(String nome, String nazione, String citta, String indirizzo,
                                     boolean delivery, boolean prenotazione, String tipoCucina, boolean prenotazioneOnline, double minPrezzo, double maxPrezzo) throws CreaRistoranteException {
        try{
            Ristorante nuovoRistorante = new Ristorante (nome, nazione, citta, indirizzo, tipoCucina,
                    delivery, prenotazioneOnline, minPrezzo, maxPrezzo);
            ristorantiGestiti.add(nuovoRistorante);
            return nuovoRistorante;
        }catch(Exception e){
            throw new CreaRistoranteException();
        }
    }

    /**
     * metodo per aggiungere ristorante
     * @param ristorante
     */
    public void aggiungiRistorante(Ristorante ristorante) {
        ristorantiGestiti.add(ristorante);
    }

    /**
     * metodo per eliminare il ristorante
     * @param ristorante
     */
    public void eliminaRistorante(Ristorante ristorante) {
        if (ristorantiGestiti.contains(ristorante)) {
            ristorantiGestiti.remove(ristorante);
            System.out.println("Ristorante rimosso");
        } else {
            System.out.println("Il ristorante non è gestito da te");
        }
    }

    /**
     * metodo per prendere lista ristoranti gestiti
     * @return
     */
    public List<Ristorante> getRistorantiGestiti() {
        return ristorantiGestiti;
    }

    /**
     *metodo per visualizzare recensione di un singolo ristorante
     * @param ristorante
     */

    public void visualizzaRecensioneRistorante(Ristorante ristorante) {
        if (ristorantiGestiti.contains(ristorante)) {
            for (Recensione r : ristorante.getRecensioni()) {
                System.out.println(r+ " numero stelle: " + r.getStelle());
            }
        }else{
            System.out.println("il ristorante non è gestito da te ");
        }
    }

    /**
     * metodo per visualizzare le recensioni di tutti i ristoranti
     */
    public void visualizzaTutteRecensioni() {
        for (Ristorante ristorante : ristorantiGestiti) {
            System.out.println("Recensioni per il ristorante: " + ristorante.getNome());
            for (Recensione recensione : ristorante.getRecensioni()) {
                System.out.println(recensione + " - numero stelle: " + recensione.getStelle());
            }
        }
    }

    /**
     * metodo per rispondere alle recensioni
     * @param recensione
     * @param risposta
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
     * metodo per visualizzare media stelle di un ristorante e il numero delle recensioni
     * @param ristorante
     */
    public static void visualizzaRiepilogo(Ristorante ristorante) {
        int c=0;
        System.out.println("media delle stelle: "+ristorante.getValutazioneMedia());
        for(Recensione r: ristorante.getRecensioni()){
            c++;
        }
        System.out.println("numero recensioni: "+c);
    }

    /**
     * Metodo per impostare la lista dei ristoranti gestiti
     * @param ristorantiGestiti lista di ristoranti gestiti dal ristoratore
     */
    public void setRistorantiGestiti(List<Ristorante> ristorantiGestiti){
        this.ristorantiGestiti = ristorantiGestiti;
    }
    /**
     * Metodo per visualizzare il ruolo del ristoratore
     * @return Restituisce "Ristoratore"
     */
    @Override
    public String getRuolo(){
        return "Ristoratore";
    }
}
