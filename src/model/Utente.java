package src.model;

import src.model.exception.InvalidNomeException;
import src.model.exception.InvalidPasswordException;
import src.model.exception.InvalidUsernameException;
import src.model.util.PasswordUtil;


/**
 * Classe Utente
 *
 * @version 1.0
 * @Author Strazzullo Ciro Andrea
 * @Author Riccardo Giovanni Rubini
 * @Author Matteo Mongelli
 */
public abstract class Utente {

    /**
     * Id associato all'utente
     */
    private int id;

    /**
     * Password cifrata dell'utente
     */
    private String passwordCifrata;

    /**
     * Nome associato all'utente
     */
    private String nome;

    /**
     * Cognome associato all'utente
     */
    private String cognome;

    /**
     * Username associato all'utente
     */
    private String username;

    /**
     * Domicilio associato all'utente
     */
    private String domicilio;

    /**
     * Data di nascita dell'utente
     */
    private String dataNascita;

    /**
     * Counter per ottenere un id unico
     */
    private static int idCounter = 0;

    /**
     * Costruttore base della classe Utente
     *
     * @param id          id associato all'utente
     * @param password    password associata all'utente
     * @param nome        nome associato all'utente
     * @param cognome     cognome associato all'utente
     * @param username    username associato all'utente
     * @param dataNascita data di nascita associata all'utente
     * @param domicilio   domicilio associato all'utente
     * @throws Exception eccezione lanciata quando non si inseriscono dati validi
     */
    public Utente(int id, String password, String nome, String cognome, String username, String dataNascita, String domicilio) throws Exception {
        setId(id);
        setPasswordCifrata(password);
        setNome(nome);
        setCognome(cognome);
        setUsername(username);
        setDomicilio(domicilio);
        setDataNascita(dataNascita);
        //Gestione unicità dell'id
        if (this.id >= idCounter) {
            idCounter = this.id + 1;
        }
    }

    /**
     * Costruttore base della classe Utente senza password cifrata, utilizzato per il recupero da file
     * per aggiungere la password utilizzare il metodo setPassword
     *
     * @param id          id associato all'utente
     * @param nome        nome associato all'utente
     * @param cognome     cognome associato all'utente
     * @param username    username associato all'utente
     * @param dataNascita data di nascita associata all'utente
     * @param domicilio   domicilio associato all'utente
     * @param b           booleano settato a true per indicare che si tratta di un costruttore senza password
     * @throws Exception eccezione lanciata quando non si inseriscono dati validi
     */
    public Utente(int id, String nome, String cognome, String username, String dataNascita, String domicilio, boolean b) throws Exception {
        setId(id);
        setNome(nome);
        setCognome(cognome);
        setUsername(username);
        setDomicilio(domicilio);
        setDataNascita(dataNascita);
        //Gestione unicità dell'id
        if (this.id >= idCounter) {
            idCounter = this.id + 1;
        }
    }

    /**
     * Costruttore senza data di nascita della classe Utente e password cifrata, utilizzato per il recupero da file
     * per aggiungere la password utilizzare il metodo setPassword
     *
     * @param id        id associato all'utente
     * @param nome      nome associato all'utente
     * @param cognome   cognome associato all'utente
     * @param username  username associato all'utente
     * @param domicilio domicilio associato all'utente
     * @param b         booleano settato a true per indicare che si tratta di un costruttore senza password
     * @throws Exception eccezione lanciata quando non si inseriscono dati validi
     */
    public Utente(int id, String nome, String cognome, String username, String domicilio, boolean b) throws Exception {
        setId(id);
        setNome(nome);
        setCognome(cognome);
        setUsername(username);
        setDomicilio(domicilio);
        //Gestione unicità dell'id
        if (this.id >= idCounter) {
            idCounter = this.id + 1;
        }
    }

    /**
     * Costruttore senza data di nascita della classe Utente
     *
     * @param id        id associato all'utente
     * @param password  password associata all'utente
     * @param nome      nome associato all'utente
     * @param cognome   cognome associato all'utente
     * @param username  username associato all'utente
     * @param domicilio domicilio associato all'utente
     * @throws Exception eccezione lanciata quando non si inseriscono dati validi
     */
    public Utente(int id, String password, String nome, String cognome, String username, String domicilio) throws Exception {
        setId(id);
        setPasswordCifrata(password);
        setNome(nome);
        setCognome(cognome);
        setUsername(username);
        setDomicilio(domicilio);
        //Gestione unicità dell'id
        if (this.id >= idCounter) {
            idCounter = this.id + 1;
        }
    }

    /**
     * Costruttore senza id della classe Utente
     *
     * @param password    password associata all'utente
     * @param nome        nome associato all'utente
     * @param cognome     cognome associato all'utente
     * @param username    username associato all'utente
     * @param dataNascita data di nascita associata all'utente
     * @param domicilio   domicilio associato all'utente
     * @throws Exception eccezione lanciata quando non si inseriscono dati validi
     */

    public Utente(String password, String nome, String cognome, String username, String dataNascita, String domicilio) throws Exception {
        setPasswordCifrata(password);
        setNome(nome);
        setCognome(cognome);
        setUsername(username);
        setDomicilio(domicilio);
        setDataNascita(dataNascita);
        this.id = idCounter++;
    }

    /**
     * Costruttore senza id e data di nascita della classe utente
     *
     * @param password  password associata all'utente
     * @param nome      nome associato all'utente
     * @param cognome   cognome associato all'utente
     * @param username  username associato all'utente
     * @param domicilio domicilio associato all'utente
     * @throws Exception eccezione lanciata quando non si inseriscono dati validi
     */

    public Utente(String password, String nome, String cognome, String username, String domicilio) throws Exception {
        setPasswordCifrata(password);
        setNome(nome);
        setCognome(cognome);
        setUsername(username);
        setDomicilio(domicilio);
        this.id = idCounter++;
    }

    /**
     * Metodo che restituisce l'id associato all'utente
     *
     * @return restituisce l'id utente
     */
    public int getId() {
        return id;
    }

    /**
     * Metodo che associa l'id dell'utente
     *
     * @param id associa l'id tramite il metodo
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Metodo che restituisce la data di nascita dell'utente
     *
     * @return restituisce la data di nascita associata all'utente
     */
    public String getDataNascita() {
        return dataNascita;
    }

    /**
     * Metodo che associa la data di nascita all'utente
     *
     * @param dataNascita associa la data di nascita tramite il metodo
     */
    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    /**
     * Metodo che restituisce il domicilio associato all'utente
     *
     * @return restituisce il domicilio associato all'utente
     */
    public String getDomicilio() {
        return domicilio;
    }

    /**
     * Metodo che associa il domicilio dell'utente
     *
     * @param domicilio associa il domicilio tramite il metodo
     */
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    /**
     * Metodo che restituisce l'username dell'utente
     *
     * @return restituisce l'username associato all'utente
     */
    public String getUsername() {
        return username;
    }

    /**
     * Metodo che associa l'username dell'utente
     *
     * @param username associa l'username tramite il metodo
     */
    public void setUsername(String username) throws InvalidUsernameException {
        if (username.isEmpty() || username.length() > 18) {
            throw new InvalidUsernameException();
        } else {
            this.username = username;
        }
    }

    /**
     * Metodo che restituisce il cognome dell'utente
     *
     * @return restituisce il cognome associato all'utente
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Metodo che associa il cognome dell'utente
     *
     * @param cognome associa il cognome tramite il metodo
     */
    public void setCognome(String cognome) throws InvalidNomeException {
        if (cognome.isEmpty()) {
            throw new InvalidNomeException();
        } else {
            this.cognome = cognome;
        }
    }

    /**
     * Metodo che restituisce il nome dell'utente
     *
     * @return restituisce il nome associato all'utente
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo che associa il nome dell'utente
     *
     * @param nome asssocia il nome all'utente
     */
    public void setNome(String nome) throws InvalidNomeException {
        if (nome.isEmpty()) {
            throw new InvalidNomeException();
        } else {
            this.nome = nome;
        }
    }

    /**
     * Metodo che restituisce la password cifrata dell'utente
     *
     * @return restituisce la password cifrata associata all'utente
     */
    public String getPasswordCifrata() {
        return passwordCifrata;
    }

    /**
     * Metodo che associa la password all'utente
     *
     * @param password associa la password tramite il metodo
     * @throws InvalidPasswordException eccezione lanciata quando la password ha meno di 6 caratteri
     */
    public void setPasswordCifrata(String password) throws InvalidPasswordException {
        if (password.length() < 6) {
            throw new InvalidPasswordException();
        } else {
            this.passwordCifrata = PasswordUtil.hashPassword(password);
        }
    }

    /**
     * Metodo che associa la password all'utente senza cifrarla, utilizzata per il recupero da file
     *
     * @param password password associata all'utente
     * @throws InvalidPasswordException eccezione lanciata quando la password ha meno di 6 caratteri
     */
    public void setPassword(String password) throws InvalidPasswordException {
        if (password.length() < 6) {
            throw new InvalidPasswordException();
        } else {
            this.passwordCifrata = password;
        }
    }

    /**
     * Metodo per verificare le credenziali di username e password dopo aver fatto l'hashing
     *
     * @param username username associato all'utente
     * @param password password associata all'utente
     * @return riporta vero se entrambe le credenziali sono verificate, falso se anche solo una delle due non viene verificata
     */
    public boolean verificaCredenziali(String username, String password) {
        String passwordHash = PasswordUtil.hashPassword(password);
        return this.username.equals(username) && this.passwordCifrata.equals(passwordHash);
    }

    /**
     * Metodo per verificare se due utenti sono uguali basandosi sull'ID
     *
     * @param obj oggetto da confrontare
     * @return true se gli utenti hanno lo stesso ID, false altrimenti
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Utente utente = (Utente) obj;
        return id == utente.id || username.equals(utente.username);
    }

    /**
     * Metodo per generare l'hashcode dell'utente basato sull'ID
     *
     * @return hashcode dell'utente
     */
    @Override
    public int hashCode() {
        return 31 * id + (username != null ? username.hashCode() : 0);
    }

    /**
     * Metodo astratto per ottenere il ruolo dell'utente
     *
     * @return ruolo
     */
    abstract public String getRuolo();

}