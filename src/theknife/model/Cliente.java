package src.theknife.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe cliente definisce il cliente del sistema, estende la classe utente
 *
 * @version 1.0
 * @Author Strazzullo Ciro Andrea, 763603, VA
 * @Author Riccardo Giovanni Rubini, 761126, VA
 * @Author Matteo Mongelli, 760960, VA 
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
     * Costruttore base della classe cliente
     *
     * @param id          id associato al cliente
     * @param password    password associata al cliente
     * @param nome        nome associato al cliente
     * @param cognome     cognome associato al cliente
     * @param username    username associato al cliente
     * @param dataNascita data di nascita associata al cliente
     * @param domicilio   domicilio associato al cliente
     * @throws Exception eccezione lanciata quando non si inseriscono dati validi
     */
    public Cliente(int id, String password, String nome, String cognome, String username, String dataNascita, String domicilio) throws Exception {
        super(id, password, nome, cognome, username, dataNascita, domicilio);
        this.preferiti = new ArrayList<>();
        this.recensioniMesse = new ArrayList<>();
    }


    /**
     * Costruttore senza data di nascita della classe cliente
     *
     * @param id        id associato al cliente
     * @param password  password associata al cliente
     * @param nome      nome associato al cliente
     * @param cognome   cognome associato al cliente
     * @param username  username associato al cliente
     * @param domicilio domicilio associato al cliente
     * @throws Exception eccezione lanciata quando non si inseriscono dati validi
     */
    public Cliente(int id, String password, String nome, String cognome, String username, String domicilio) throws Exception {

        super(id, password, nome, cognome, username, domicilio);
        this.preferiti = new ArrayList<>();
        this.recensioniMesse = new ArrayList<>();
    }

    /**
     * Costruttore base della classe cliente senza password cifrata, utilizzato per il recupero da file
     * per settare la password si utilizza il metodo setPassword
     *
     * @param id          id associato al cliente
     * @param nome        nome associato al cliente
     * @param cognome     cognome associato al cliente
     * @param username    username associato al cliente
     * @param dataNascita data di nascita associata al cliente
     * @param domicilio   domicilio associato al cliente
     * @param b           booleano settato a true per indicare che si tratta di un costruttore senza password
     * @throws Exception eccezione lanciata quando non si inseriscono dati validi
     */
    public Cliente(int id, String nome, String cognome, String username, String dataNascita, String domicilio, boolean b) throws Exception {
        super(id, nome, cognome, username, dataNascita, domicilio, b);
        this.preferiti = new ArrayList<>();
        this.recensioniMesse = new ArrayList<>();
    }


    /**
     * Costruttore senza data di nascita della classe cliente e password cifrata, utilizzato per il recupero da file
     * per settare la password si utilizza il metodo setPassword
     *
     * @param id        id associato al cliente
     * @param nome      nome associato al cliente
     * @param cognome   cognome associato al cliente
     * @param username  username associato al cliente
     * @param domicilio domicilio associato al cliente
     * @param b         booleano settato a true per indicare che si tratta di un costruttore senza password
     * @throws Exception eccezione lanciata quando non si inseriscono dati validi
     */
    public Cliente(int id, String nome, String cognome, String username, String domicilio, boolean b) throws Exception {
        super(id, nome, cognome, username, domicilio, b);
        this.preferiti = new ArrayList<>();
        this.recensioniMesse = new ArrayList<>();
    }


    /**
     * Costruttore senza id della classe cliente
     *
     * @param password    password associata al cliente
     * @param nome        nome associato al cliente
     * @param cognome     cognome associato al cliente
     * @param username    username associato al cliente
     * @param dataNascita data di nascita associata al cliente
     * @param domicilio   domicilio associata al cliente
     * @throws Exception eccezione lanciata quando non si inseriscono dati validi
     */
    public Cliente(String password, String nome, String cognome, String username, String dataNascita, String domicilio) throws Exception {
        super(password, nome, cognome, username, dataNascita, domicilio);
        this.preferiti = new ArrayList<>();
        this.recensioniMesse = new ArrayList<>();
    }


    /**
     * Costruttore senza id e data di nascita della classe cliente
     *
     * @param password  password associata al cliente
     * @param nome      nome associato al cliente
     * @param cognome   cognome associato al cliente
     * @param username  username associato al cliente
     * @param domicilio domicilio associato al cliente
     * @throws Exception eccezione lanciata quando non si inseriscono dati validi
     */
    public Cliente(String password, String nome, String cognome, String username, String domicilio) throws Exception {
        super(password, nome, cognome, username, domicilio);
        this.preferiti = new ArrayList<>();
        this.recensioniMesse = new ArrayList<>();
    }


    /**
     * Metodo per aggiungere il ristorante nei preferiti
     *
     * @param ristorante ristorante da aggiungere ai preferiti
     */
    public void aggiungiPreferito(Ristorante ristorante) {
        if (!preferiti.contains(ristorante)) {
            preferiti.add(ristorante);
        }
    }

    /**
     * Metodo per rimuovere il ristorante dai preferiti
     *
     * @param ristorante ristorante da rimuovere dai preferiti
     */
    public void rimuoviPreferito(Ristorante ristorante) {
        preferiti.remove(ristorante);
    }

    /**
     * Metodo per visualizzare i preferiti del cliente
     *
     * @return restituisce lista preferiti
     */
    public List<Ristorante> visualizzaPreferiti() {
        return preferiti;
    }

    /**
     * Metodo per far aggiungere al cliente una recensione
     *
     * @param ristorante  ristorante su cui si vuole lasciare la recensione
     * @param stelle      numero di stelle della recensione
     * @param descrizione descrizione della recensione
     * @throws Exception se si verifica un errore durante la creazione della recensione
     */
    public void aggiungiRecensione(Ristorante ristorante, int stelle, String descrizione) throws Exception {

        Recensione recensione = new Recensione(descrizione, stelle);
        recensioniMesse.add(recensione);
        ristorante.recensisciRistorante(recensione);
    }

    /**
     * Metodo per modificare una recensione
     *
     * @param recensione recensione da modificare
     * @param txt        testo della recensione
     * @param stelle     numero di stelle della recensione
     */
    public void modificaRecensione(Recensione recensione, String txt, int stelle) {
        recensione.setDescrizione(txt);
        recensione.setNumeroStelle(stelle);
    }

    /**
     * Metodo per rimuovere una recensione
     *
     * @param ristorante ristorante da cui si vuole rimuovere la recensione
     * @param recensione recensione da rimuovere
     */
    public void rimuoviRecensione(Ristorante ristorante, Recensione recensione) {
        if (recensioniMesse.contains(recensione)) {
            recensioniMesse.remove(recensione);
            ristorante.rimuoviRecensione(recensione);
        }
    }

    /**
     * Get lista di recensioni messe
     *
     * @return restituisce la lista delle recensioni lasciate dal cliente
     */
    public List<Recensione> getRecensioniMesse() {
        return recensioniMesse;
    }

    /**
     * Set lista di recensioni messe
     *
     * @param recensioniMesse lista delle recensioni lasciate dal cliente
     */
    public void setRecensioniMesse(List<Recensione> recensioniMesse) {
        this.recensioniMesse = recensioniMesse;
    }

    /**
     * Metodo per inserire la lista dei ristoranti preferiti
     *
     * @param preferiti lista dei ristoranti preferiti dal cliente
     */
    public void setPreferiti(List<Ristorante> preferiti) {
        this.preferiti = preferiti;
    }

    /**
     * Metodo per ottenere il ruolo
     *
     * @return restituisce il ruolo cliente
     */
    @Override
    public String getRuolo() {
        return "Cliente";
    }

    public String toString() {
        return "Cliente{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", cognome='" + getCognome() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", domicilio='" + getDomicilio() + '\'' +
                ", preferiti=" + preferiti +
                ", recensioniMesse=" + recensioniMesse +
                '}';
    }

    /**
     * Metodo per confrontare due clienti
     *
     * @param obj oggetto da confrontare
     * @return restituisce true se i due clienti hanno lo stesso id, false altrimenti
     */

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}