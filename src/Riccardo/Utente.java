package src.Riccardo;
public abstract class Utente {
    private int id;
    private String password;
    private String passwordCifrata;
    private String nome;
    private String cognome;
    private String username;
    private String domicilio;
    private String dataNascita;
    private String ruolo;

    public Utente(int id, String password, String nome, String cognome, String username, String dataNascita, String domicilio, String ruolo){

        this.id=id;
        this.passwordCifrata = PasswordUtil.hashPassword(password);
        this.nome=nome;
        this.cognome=cognome;
        this.username=username;
        this.domicilio= domicilio;
        this.dataNascita=dataNascita;
        this.ruolo=ruolo;

    }
    public Utente(int id, String password, String nome,String cognome, String username, String domicilio, String ruolo){

        this.id=id;
        this.passwordCifrata = PasswordUtil.hashPassword(password);
        this.nome=nome;
        this.cognome=cognome;
        this.username=username;
        this.domicilio= domicilio;
        this.ruolo=ruolo;

    }

    public Utente(String password, String nome,String cognome, String username,String dataNascita, String domicilio, String ruolo){

        this.passwordCifrata = PasswordUtil.hashPassword(password);
        this.nome=nome;
        this.cognome=cognome;
        this.username=username;
        this.domicilio= domicilio;
        this.dataNascita=dataNascita;
        this.ruolo=ruolo;

    }

    public Utente(String password, String nome,String cognome, String username, String domicilio, String ruolo){

        this.passwordCifrata = PasswordUtil.hashPassword(password);
        this.nome=nome;
        this.cognome=cognome;
        this.username=username;
        this.domicilio= domicilio;
        this.ruolo=ruolo;

    }
//getter e setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
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

    public void setPasswordCifrata(String passwordCifrata) {
        this.passwordCifrata = passwordCifrata;
    }


}
