package src.Riccardo;
import src.Matteoo.Recensione;
import src.Nico.Ristorante;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Utente{

    //attributi
    private List<Ristorante> preferiti;

    //costruttori
    public Cliente(int id, String password, String nome,String cognome, String username, String domicilio, String ruolo){

        super(id,password,nome,cognome,username,domicilio,ruolo);
        this.preferiti = new ArrayList<>();
    }

    //metodi
    public void cercaRistorante(ArrayList<Ristorante> elencoRistoranti, String chiaveRicerca) {
        ArrayList<Ristorante> risultati = Ristorante.cercaRistorante(elencoRistoranti, chiaveRicerca);
        if (risultati.isEmpty()) {
            System.out.println("Nessun ristorante trovato.");
        } else {
            System.out.println("Risultati per " + chiaveRicerca + ": ");
            for (Ristorante ris : risultati) {
                System.out.println(ris.toString());
            }
        }
    }

    //metodo per aggiungere ai preferiti il ristorante

    /**
     * Metodo per aggiungere il ristorante nei preferito
     * @param ristorante
     */
    public void aggiungiPreferito(Ristorante ristorante){
        if(!preferiti.contains(ristorante)){
            preferiti.add(ristorante);
        }else{
            System.out.println("Ristorante già inserito nei preferiti!");
        }

    }

    //metodo per rimuovere dai preferiti il ristorante

    /**
     * metodo per rimuovere il ristorante dai preferiti
     * @param ristorante
     */
    public void rimuoviPreferito(Ristorante ristorante){
        if(preferiti.contains(ristorante)) {
            preferiti.remove(ristorante);
        }else{
            System.out.println("Ristorante non inserito nei preferiti!");
        }
    }

    //metodo per visualizzare la lista dei ristoranti preferiti

    /**
     * metodo per visualizzare la lista dei preferiti
     */
    public void visualizzaPreferiti(){
        if(preferiti.size() > 0) {
            for (Ristorante ristorante : preferiti) {
                System.out.println(preferiti);
            }
        }else{
            System.out.println("Nessun ristorante contenuto nei preferiti");
        }
    }

    /**
     * metodo per far aggiungere al cliente una recensione
     * @param ristorante
     * @param stelle
     * @param descrizione
     */
    public void aggiungiRecensione(Ristorante ristorante, int stelle, String descrizione){
        Recensione recensione = new Recensione(descrizione, stelle);
        recensione.setDescrizione(descrizione);
        recensione.setNumeroStelle(stelle);
        ristorante.recensisciRistorante(recensione);
    }

    public void modificaRecensione(Recensione recensione, String descrizione, int stelle){
        recensione.modificaRispStelle(descrizione,stelle);
    }

    public void rimuoviRecensione(Recensione recensione){
        //quando avrò il metodo farò questa bella cosa
    }

}
