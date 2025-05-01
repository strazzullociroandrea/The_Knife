package src.model;

import src.model.util.PasswordUtil;

public abstract class Utente {


    private int id;
    private String passwordCifrata;
    private String nome;
    private String cognome;
    private String username;
    private String domicilio;
    private String dataNascita;
    private static int idCounter=0; //ID univoco per ogni utente

    public Utente(int id, String password, String nome, String cognome, String username, String dataNascita, String domicilio){
        this.id=id;
        this.passwordCifrata = PasswordUtil.hashPassword(password);
        this.nome=nome;
        this.cognome=cognome;
        this.username=username;
        this.domicilio= domicilio;
        this.dataNascita=dataNascita;
        idCounter++;
    }
    public Utente(int id, String password, String nome,String cognome, String username, String domicilio){
        this.id=id;
        this.passwordCifrata = PasswordUtil.hashPassword(password);
        this.nome=nome;
        this.cognome=cognome;
        this.username=username;
        this.domicilio= domicilio;
        idCounter++;
    }

    public Utente(String password, String nome,String cognome, String username,String dataNascita, String domicilio){
        this.passwordCifrata = PasswordUtil.hashPassword(password);
        this.nome=nome;
        this.cognome=cognome;
        this.username=username;
        this.domicilio= domicilio;
        this.dataNascita=dataNascita;
        idCounter++;
    }

    public Utente(String password, String nome,String cognome, String username, String domicilio){
        this.passwordCifrata = PasswordUtil.hashPassword(password);
        this.nome=nome;
        this.cognome=cognome;
        this.username=username;
        this.domicilio= domicilio;
        idCounter++;
    }
//getter e setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPasswordCifrata() {
        return passwordCifrata;
    }

    public void setPasswordCifrata(String password)throws RuntimeException {
        this.passwordCifrata = password;
    }

    /**
     * metodo per richiamare il ruolo
     * @return
     */
    abstract public String getRuolo();
}
