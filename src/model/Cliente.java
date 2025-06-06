package src.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe cliente
 * @version 1.0
 * @Author Strazzullo Ciro Andrea
 * @Author Riccardo Giovanni Rubini
 * @Author Matteo Mongelli
 */
public class Cliente extends Utente {

    /**
     * Lista dei ristoranti prefetiti dal cliente
     */
    private List<Ristorante> preferiti;
    /**
     * Lista delle recensioni del cliente
     */
    private List<Recensione> recensioniMesse;


    /**
     *Costruttore base della classse cliente
     * @param id id associato al cliente
     * @param password password associata al cliente
     * @param nome nome associato al cliente
     * @param cognome cognome associato al cliente
     * @param username username associato al cliente
     * @param dataNascita data di nascita associata al cliente
     * @param domicilio domicilio associato al cliente
     *
     */
    public Cliente(int id, String password, String nome,String cognome, String username, String dataNascita, String domicilio){

        super(id,password,nome,cognome,username,dataNascita,domicilio);
        this.preferiti = new ArrayList<>();
        this.recensioniMesse = new ArrayList<>();
    }


    /**
     *Costruttore senza data di nascita della classe cliente
     * @param id id associato al cliente
     * @param password password associata al cliente
     * @param nome nome associato al cliente
     * @param cognome cognome associato al cliente
     * @param username username associato al cliente
     * @param domicilio domicilio associato al cliente
     */
    public Cliente(int id, String password, String nome,String cognome, String username, String domicilio)
    {

        super(id,password,nome,cognome,username,domicilio);
        this.preferiti = new ArrayList<>();
        this.recensioniMesse = new ArrayList<>();
    }


    /**
     *Costruttore senza id della classe cliente
     * @param password password associata al cliente
     * @param nome nome associato al cliente
     * @param cognome cognome associato al cliente
     * @param username username associato al cliente
     * @param dataNascita data di nascita associata al cliente
     * @param domicilio domicilio associata al cliente
     */
    public Cliente(String password, String nome,String cognome, String username, String dataNascita, String domicilio)
    {
        super(password,nome,cognome,username,dataNascita,domicilio);
        this.preferiti = new ArrayList<>();
        this.recensioniMesse = new ArrayList<>();
    }


    /**
     *Costruttore senza id e data di nascita della classe cliente
     * @param password password associata al cliente
     * @param nome nome associato al cliente
     * @param cognome cognome associato al cliente
     * @param username username associato al cliente
     * @param domicilio domicilio associato al cliente
     */
    public Cliente(String password, String nome,String cognome, String username, String domicilio)
    {
        super(password,nome,cognome,username, domicilio);
        this.preferiti = new ArrayList<>();
        this.recensioniMesse = new ArrayList<>();
    }


    /**
     * Metodo per aggiungere il ristorante nei preferiti
     * @param ristorante
     */
    public void aggiungiPreferito(Ristorante ristorante){
        if(!preferiti.contains(ristorante)){
            preferiti.add(ristorante);
        }
    }

    /**
     * metodo per rimuovere il ristorante dai preferiti
     * @param ristorante
     */

    public void rimuoviPreferito(Ristorante ristorante){
        if(preferiti.contains(ristorante)) {
            preferiti.remove(ristorante);
        }
    }

    /**
     * Metodo per visualizzare i preferiti del cliente
     * @return restituisce lista preferiti
     */
    public List<Ristorante> visualizzaPreferiti(){
        return preferiti;
    }

    /**
     * metodo per far aggiungere al cliente una recensione
     * @param ristorante
     * @param stelle
     * @param descrizione
     */

    public void aggiungiRecensione(Ristorante ristorante, int stelle, String descrizione){
        Recensione recensione = new Recensione(descrizione, stelle);
        recensioniMesse.add(recensione);
        ristorante.recensisciRistorante(recensione);
    }
    /**
     * metodo per modificare una recensione
     * @param recensione
     * @param txt
     * @param stelle
     */
    public Recensione modificaRecensione(Recensione recensione,String txt, int stelle){
        recensione.setDescrizione(txt);
        recensione.setNumeroStelle(stelle);
        return recensione;
    }

    /**
     * metodo per rimuovere una recensione
     * @param ristorante
     * @param recensione
     */
    public void rimuoviRecensione(Ristorante ristorante, Recensione recensione) {
        if (recensioniMesse.contains(recensione)) {
            recensioniMesse.remove(recensione);
            ristorante.rimuoviRecensione(recensione);
        }
    }

    /**
     * get lista di recensioni messe
     * @return restituisce la lista delle recensioni lasciate dal cliente
     */
    public List<Recensione> getRecensioniMesse() {
        return recensioniMesse;
    }
    /**
     * set lista di recensioni messe
     * @param recensioniMesse lista delle recensioni lasciate dal cliente
     */
    public void setRecensioniMesse(List<Recensione> recensioniMesse) {
        this.recensioniMesse = recensioniMesse;
    }

    /**
     * metodo per inserire la lista dei ristoranti preferiti
     * @param preferiti
     */
    public void setPreferiti(List<Ristorante> preferiti) {
        this.preferiti = preferiti;
    }
    /**
     * metodo per ottenere il ruolo
     * @return restituisce il ruolo cliente
     */

    @Override
    public String getRuolo() {
        return "Cliente";
    }

}
