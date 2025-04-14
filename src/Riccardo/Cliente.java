package src.Riccardo;
import src.Matteoo.Recensione;
import src.Nico.Ristorante;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Utente{

    //attributi
    private List<Ristorante2> preferiti;

    //costruttori
    public Cliente(int id, String password, String nome,String cognome, String username, String domicilio, String ruolo){

        super(id,password,nome,cognome,username,domicilio,ruolo);
        this.preferiti = new ArrayList<>();
    }
    //metodi
    public void cercaRistorante(){
        //chiedere a ciro
    }

    //metodo per aggiungere ai preferiti il ristorante
    public void aggiungiPreferito(Ristorante2 ristorante){
        if(!preferiti.contains(ristorante)){
            preferiti.add(ristorante);
        }else{
            System.out.println("Ristorante già inserito nei preferiti!");
        }

    }

    //metodo per rimuovere dai preferiti il ristorante
    public void rimuoviPreferito(Ristorante2 ristorante){
        if(preferiti.contains(ristorante)) {
            preferiti.remove(ristorante);
        }else{
            System.out.println("Ristorante non inserito nei preferiti!");
        }
    }

    //metodo per visualizzare la lista dei ristoranti preferiti
    public void visualizzaPreferiti(){
        if(preferiti.size() > 0) {
            for (Ristorante2 ristorante : preferiti) {
                System.out.println(preferiti);
            }
        }else{
            System.out.println("Nessun ristorante contenuto nei preferiti");
        }
    }

    public void aggiugniRecensione(Ristorante2 ristorante, int stelle, String descrizione){
        Recensione recensione = new Recensione(descrizione, stelle);
        ristorante.recensisciRistorante(recensione);
    }

    public void modificaRecensione(Recensione recensione, String descrizione, int stelle){
        recensione.setDescrizione(descrizione);
        recensione.setNumeroStelle(stelle);
    }

    public void rimuoviRecensione(Recensione recensione){
        recensione.setDescrizione(null);
        recensione.setNumeroStelle(0);
    }

}
