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
    public void cercaRistorante(){

    }
    public void aggiungiPreferito(){

    }

    public void rimuoviPreferito(){

    }
    public void visualizzaPreferiti(){

    }
    public void aggiugniRecensione(){

    }
    public void modificaRecensione(){

    }
    public void rimuoviRecensione(){

    }
}
