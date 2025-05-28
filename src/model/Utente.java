package src.model;

import src.model.exception.InvalidNomeException;
import src.model.exception.InvalidPasswordException;
import src.model.exception.InvalidUsernameException;
import src.model.util.PasswordUtil;

import javax.naming.InvalidNameException;

/**
 * Classe Utente
 * @version 1.0
 * @Author Strazzullo Ciro Andrea
 * @Author Riccardo Giovanni Rubini
 * @Author Matteo Mongelli
 */
public abstract class Utente {

    /**
     * id associato all'utente
     */
    private int id;
    /**
     * password cifrata dell'utente
     */
    private String passwordCifrata;
    /**
     * nome associato all'utente
     */
    private String nome;
    /**
     * cognome associato all'utente
     */
    private String cognome;
    /**
     * username associato all'utente
     */
    private String username;
    /**
     * domicilio associato all'utente
     */
    private String domicilio;
    /**
     * data di nascita dell'utente
     */
    private String dataNascita;
    /**
     * counter per ottenere un id unico
     */
    private static int idCounter=0;//ID univoco per ogni utente

    /**
     *costruttore base della classe Utente
     * @param id id associato all'utente
     * @param password password associata all'utente
     * @param nome nome associato all'utente
     * @param cognome cognome associato all'utente
     * @param username username associato all'utente
     * @param dataNascita da di nascita associata all'utente
     * @param domicilio domicilio associato all'utente
     */
    public Utente(int id, String password, String nome, String cognome, String username, String dataNascita, String domicilio)throws InvalidPasswordException{
        this.id=id;
        setPasswordCifrata(password);
        this.nome=nome;
        this.cognome=cognome;
        this.username=username;
        this.domicilio= domicilio;
        this.dataNascita=dataNascita;
        idCounter++;
    }

    /**
     *Costruttore senza data di nascita della classe Utente
     * @param id id associato all'utente
     * @param password password associata all'utente
     * @param nome nome associato all'utente
     * @param cognome cognome associato all'utente
     * @param username username associato all'utente
     * @param domicilio domicilio associato all'utente
     */
    public Utente(int id, String password, String nome,String cognome, String username, String domicilio){
        this.id=id;
        setPasswordCifrata(password);
        this.nome=nome;
        this.cognome=cognome;
        this.username=username;
        this.domicilio= domicilio;
        idCounter++;
    }

    /**
     *Costruttore senza id della classe Utente
     * @param password password associata all'utente
     * @param nome nome associato all'utente
     * @param cognome cognome associato all'utente
     * @param username username associato all'utente
     * @param dataNascita da di nascita associata all'utente
     * @param domicilio domicilio associato all'utente
     */

    public Utente(String password, String nome,String cognome, String username,String dataNascita, String domicilio){
        setPasswordCifrata(password);
        this.nome=nome;
        this.cognome=cognome;
        this.username=username;
        this.domicilio= domicilio;
        this.dataNascita=dataNascita;
        idCounter++;
    }

    /**
     *Costruttore senza id e data di nascita della classe utente
     * @param password password associata all'utente
     * @param nome nome associato all'utente
     * @param cognome cognome associato all'utente
     * @param username username associato all'utente
     * @param domicilio domicilio associato all'utente
     */

    public Utente(String password, String nome,String cognome, String username, String domicilio){
        setPasswordCifrata(password);
        this.nome=nome;
        this.cognome=cognome;
        this.username=username;
        this.domicilio= domicilio;
        idCounter++;
    }
//getter e setter

    /**
     *Metodo che restituisce l'id associato all'utente
     * @return restituisce l'id utente
     */
    public int getId() {
        return id;
    }

    /**
     *Metodo che associa l'id dell'utente
     * @param id associa l'id tramite il metodo
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *Metodo che restituisce la data di nascita dell'utente
     * @return restituisce la data di nascita associata all'utente
     */
    public String getDataNascita() {
        return dataNascita;
    }

    /**
     *Metodo che associa la data di nascita all'utente
     * @param dataNascita associa la data di nascita tramite il metodo
     */
    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    /**
     *Metodo che restituisce il domicilio associato all'utente
     * @return restituisce il domicilio associato all'utente
     */
    public String getDomicilio() {
        return domicilio;
    }

    /**
     *Metodo che associa il domicilio dell'utente
     * @param domicilio associa il domicilio tramite il metodo
     */
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    /**
     *Metodo che restituisce l'username dell'utente
     * @return restituisce l'username associato all'utente
     */
    public String getUsername() {
        return username;
    }

    /**
     *Metodo che associa l'username dell'utente
     * @param username associa l'username tramite il metodo
     */
    public void setUsername(String username)throws InvalidUsernameException {
        if (username.length()==0 || username.length() > 18){
            throw new InvalidUsernameException();
        }else{
            this.username = username;
        }
    }

    /**
     *Metodo che restituisce il cognome dell'utente
     * @return restituisce il cognome associato all'utente
     */
    public String getCognome() {
        return cognome;
    }

    /**
     *Metodo che associa il cognome dell'utente
     * @param cognome associa il cognome tramite il metodo
     */
    public void setCognome(String cognome)throws InvalidNomeException{
        if(cognome.length()==0) {
            throw new InvalidNomeException();
        }else{
            this.cognome = cognome;
        }
    }

    /**
     *Metodo che restituisce il nome dell'utente
     * @return restituisce il nome associato all'utente
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo che associa il nome dell'utente
     * @param nome asssocia il nome all'utente
     */
    public void setNome(String nome)throws InvalidNomeException{
        if(nome.length()==0) {
            throw new InvalidNomeException();
        }else {
            this.nome = nome;
        }
    }

    /**
     *Metodo che restituisce la password cifrata dell'utente
     * @return restituisce la password cifrata associata all'utente
     */
    public String getPasswordCifrata() {
        return passwordCifrata;
    }

    /**
     *Metodo che associa la password all'utente
     * @param password associa la password tramite il metodo
     * @throws InvalidPasswordException eccezione lanciata quando la password ha meno di 6 caratteri
     */
    public void setPasswordCifrata(String password)throws InvalidPasswordException {
        if (password.length() < 6) {
            throw new InvalidPasswordException();
        }else {
            this.passwordCifrata = PasswordUtil.hashPassword(password);
        }
    }

    /**
     * Metodo per verificare le credenziali di username e password dopo aver fatto l'hashing
     * @param username
     * @param password
     * @return riporta vero se entrambe le credenziali sono verificate, falso se anche solo una delle due non viene verificata
     */
    public boolean verificaCredenziali(String username, String password) {
        String passwordHash = PasswordUtil.hashPassword(password);
        return this.username.equals(username) && this.passwordCifrata.equals(passwordHash);
    }

    /**
     * Metodo astratto per ottenere il ruolo dell'utente
     * @return ruolo
     */
    abstract public String getRuolo();
}
