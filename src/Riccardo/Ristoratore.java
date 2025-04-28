package src.Riccardo;
import src.Matteoo.GestoreRecensioni;
import src.Matteoo.Recensione;
import src.Nico.Ristorante;

import java.util.ArrayList;
import java.util.List;


public class Ristoratore extends Utente {

    //attributi
    private List<Ristorante> ristorantiGestiti;


    //costruttori
    //costruttore base
    public Ristoratore(int id, String password, String nome,String cognome, String username, String dataNascita, String domicilio) {

        super(id,password,nome,cognome,username,dataNascita,domicilio);
        this.ristorantiGestiti = new ArrayList<>();
    }

    //costruttore senza data nascita
    public Ristoratore(int id, String password, String nome,String cognome, String username, String domicilio) {

        super(id,password,nome,cognome,username,domicilio);
        this.ristorantiGestiti = new ArrayList<>();
    }

    //costruttore senza id
    public Ristoratore(String password, String nome,String cognome, String username, String dataNascita, String domicilio) {

        super(password,nome,cognome,username,dataNascita,domicilio);
        this.ristorantiGestiti = new ArrayList<>();
    }

    //costruttore senza id e data nascita
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
     * @param lat
     * @param log
     * @param delivery
     * @param prenotazione
     * @param tipoCucina
     */
    public void creaRistorante(String id, String nome, String nazione, String citta, String indirizzo,
                               double lat, double log, boolean delivery, boolean prenotazione, String tipoCucina) {
        Ristorante nuovoRistorante = new Ristorante (id, nome, nazione, citta, indirizzo,lat, log, delivery,prenotazione,tipoCucina);
        ristorantiGestiti.add(nuovoRistorante);
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
        if (recensione.getRisposta() == null) {
            recensione.setRisposta(risposta);
        } else {
            System.out.println("Risposta già presente: "+recensione.getRisposta());
        }
    }

    /**
     * metodo per visualizzare media stelle e numero delle recensioni
     * @param ristorante
     */
    public static void visualizzaRiepilogo(GestoreRecensioni ristorante) {
        System.out.println("media delle stelle: "+ristorante.mediaStelle());
        System.out.println("numero recensioni "+ristorante.numeroRecensioni());
    }

    @Override
    public String getRuolo(){
        return "Ristoratore";
    }
}

