package src.Riccardo;
import src.Matteoo.Recensione;
import src.Nico.Ristorante;

import java.util.ArrayList;

public class Cliente extends Utente{

    //attributi
    private ArrayList<Ristorante> preferiti;
    private ArrayList<Recensione> recensioniMesse;

    //costruttori

    //costruttore base
    public Cliente(int id, String password, String nome,String cognome, String username, String dataNascita, String domicilio){

        super(id,password,nome,cognome,username,dataNascita,domicilio);
        this.preferiti = new ArrayList<>();
        this.recensioniMesse = new ArrayList<>();
    }

    //costruttore senza data nascita
    public Cliente(int id, String password, String nome,String cognome, String username, String domicilio)
    {

        super(id,password,nome,cognome,username,domicilio);
        this.preferiti = new ArrayList<>();
        this.recensioniMesse = new ArrayList<>();
    }

    //costruttore senza id
    public Cliente(String password, String nome,String cognome, String username, String dataNascita, String domicilio)
    {
        super(password,nome,cognome,username,dataNascita,domicilio);
        this.preferiti = new ArrayList<>();
        this.recensioniMesse = new ArrayList<>();
    }

    //costruttore senza id e data nascita
    public Cliente(String password, String nome,String cognome, String username, String domicilio)
    {
        super(password,nome,cognome,username, domicilio);
        this.preferiti = new ArrayList<>();
        this.recensioniMesse = new ArrayList<>();
    }

    //metodi

    /**
     *
     * @param lista
     * @param tipoCucina
     * @param min
     * @param max
     * @param delivery
     * @param prenotazione
     * @param lat
     * @param log
     * @return
     */

    public static ArrayList<Ristorante> cercaRistorante(ArrayList<Ristorante> lista, String tipoCucina,
                                double min, double max, boolean delivery,
                                boolean prenotazione, double lat, double log) {
        ArrayList<Ristorante> risultati = Ristorante.cercaRistorante(lista, tipoCucina,min,max,delivery,prenotazione,lat,log);
        if (risultati.isEmpty()) {
            System.out.println("Nessun ristorante trovato.");
        } else {
            System.out.println("Risultati: ");
            for(Ristorante ris : risultati) {
                System.out.println(ris.toString());
            }
        }
        return risultati;
    }

    /**
     * Metodo per aggiungere il ristorante nei preferito
     * @param ristorante
     */
    public void aggiungiPreferito(Ristorante ristorante){
        if(!preferiti.contains(ristorante)){
            preferiti.add(ristorante);
        }else{
            System.out.println("Ristorante già inserito nei preferiti");
        }

    }

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
        recensioniMesse.add(recensione);
        ristorante.addRecensione(recensione);
    }

    /**
     * metodo per modificare una recensione
     * @param recensione
     * @param descrizione
     * @param stelle
     */
    public void modificaRecensione(Recensione recensione, String descrizione, int stelle){
        recensione.modificaRispStelle(descrizione,stelle);
    }

    /**
     * metodo per rimuovere una recensione
     * @param ristorante
     * @param recensione
     */
    public void rimuoviRecensione(Ristorante ristorante, Recensione recensione) {
        if (recensioniMesse.contains(recensione)) {
                recensioniMesse.remove(recensione);
                ristorante.removeRecensione(recensione);
                System.out.println("Recensione rimossa");
        } else {
                System.out.println("Recensione non trovata");
            }
        }

    /**
     * get lista di recensioni messe
      * @return
     */
    public ArrayList<Recensione> getRecensioniMesse() {
        return recensioniMesse;
    }

    /**
     * metodo per ottenere il ruolo
     * @return
     */

    @Override
    public String getRuolo() {
        return "Cliente";
    }

}


