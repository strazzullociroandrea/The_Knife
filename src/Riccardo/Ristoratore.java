package src.Riccardo;
import src.Matteoo.GestoreRecensioni;
import src.Matteoo.Recensione;
import src.Nico.Ristorante;

import java.util.ArrayList;
import java.util.List;


public class Ristoratore extends Utente {

    //attributi
    private List<Ristorante> ristorantiGestiti = new ArrayList<>();
    private Ristorante ristorante;

    //costruttori
    public Ristoratore(int id, String password, String nome,String cognome, String username, String domicilio, String ruolo)
    {

        super(id,password,nome,cognome,username,domicilio,ruolo);

    }

    //metodi
    //metodo per creare ristorante
    /*public void creaRistorante(String nome, String nazione, String citta, String indirizzo, String tipoCucina,
                               boolean delivery, boolean prenotazioneOnline, double minPrezzo, double maxPrezzo) {
        this.ristorante = new Ristorante (nome, nazione, citta, indirizzo,tipoCucina,delivery, prenotazioneOnline, minPrezzo,maxPrezzo);
        ristorantiGestiti.add(ristorante);
    }*/

    //metodo per aggiungere ristorante
    public void aggiungiRistorante(Ristorante ristorante) {
        ristorantiGestiti.add(ristorante);
    }

    //metodo per prendere lista ristoranti gestiti
    public List<Ristorante> getRistorantiGestiti() {
        return ristorantiGestiti;
    }

    //metodo per prendere lista visualizzare le recensioni
    public void visualizzaRecensioni(Ristorante ristorante) {
        if (ristorantiGestiti.contains(ristorante)) {
            for (Recensione r : ristorante.getRecensioni()) {
                System.out.println(r+ "numero stelle: "+r.getStelle());
            }
        }else{
            System.out.println("il ristorante non è gestito da te ");
        }
    }

    //metodo per rispondere alle recensioni
    public void rispondiRecensione(Recensione recensione, String risposta) {
        if (recensione.getRisposta() == null) {
            recensione.setRisposta(risposta);
        } else {
            System.out.println("Risposta già presente: "+recensione.getRisposta());
        }

    }

    //metodo per visualizzare media stelle e numero delle recensioni
    public static void visualizzaRiepilogo(GestoreRecensioni ristorante) {
        System.out.println("media delle stelle: "+ristorante.mediaStelle());
        System.out.println("numero recensioni "+ristorante.numeroRecensioni());
    }

}

