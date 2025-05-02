package src.Riccardo;
public abstract class Utente {


    private int id;
    private String passwordCifrata;
    private String nome;
    private String cognome;
    private String username;
    private String domicilio;
    private String dataNascita;
    private static int idCounter=0; //ID univoco per ogni utente

    /**
     *
     * @param id id associato all'utente
     * @param password password associata all'utente
     * @param nome nome associato all'utente
     * @param cognome cognome associato all'utente
     * @param username username associato all'utente
     * @param dataNascita da di nascita associata all'utente
     * @param domicilio domicilio associato all'utente
     */
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

    /**
     *
     * @param id id associato all'utente
     * @param password password associata all'utente
     * @param nome nome associato all'utente
     * @param cognome cognome associato all'utente
     * @param username username associato all'utente
     * @param domicilio domicilio associato all'utente
     */
    public Utente(int id, String password, String nome,String cognome, String username, String domicilio){
        this.id=id;
        this.passwordCifrata = PasswordUtil.hashPassword(password);
        this.nome=nome;
        this.cognome=cognome;
        this.username=username;
        this.domicilio= domicilio;
        idCounter++;
    }

    /**
     *
     * @param password password associata all'utente
     * @param nome nome associato all'utente
     * @param cognome cognome associato all'utente
     * @param username username associato all'utente
     * @param dataNascita da di nascita associata all'utente
     * @param domicilio domicilio associato all'utente
     */

    public Utente(String password, String nome,String cognome, String username,String dataNascita, String domicilio){
        this.passwordCifrata = PasswordUtil.hashPassword(password);
        this.nome=nome;
        this.cognome=cognome;
        this.username=username;
        this.domicilio= domicilio;
        this.dataNascita=dataNascita;
        idCounter++;
    }

    /**
     *
     * @param password password associata all'utente
     * @param nome nome associato all'utente
     * @param cognome cognome associato all'utente
     * @param username username associato all'utente
     * @param domicilio domicilio associato all'utente
     */

    public Utente(String password, String nome,String cognome, String username, String domicilio){
        this.passwordCifrata = PasswordUtil.hashPassword(password);
        this.nome=nome;
        this.cognome=cognome;
        this.username=username;
        this.domicilio= domicilio;
        idCounter++;
    }
//getter e setter

    /**
     *
     * @return restituisce l'id associato all'utente
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id associa l'id tramite il metodo
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return restituisce la data di nascita associata all'utente
     */
    public String getDataNascita() {
        return dataNascita;
    }

    /**
     *
     * @param dataNascita associa la data di nascita tramite il metodo
     */
    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    /**
     *
     * @return restituisce il domicilio associato all'utente
     */
    public String getDomicilio() {
        return domicilio;
    }

    /**
     *
     * @param domicilio associa il domicilio tramite il metodo
     */
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    /**
     *
     * @return restituisce l'username associato all'utente
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username associa l'username tramite il metodo
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return restituisce il cognome associato all'utente
     */
    public String getCognome() {
        return cognome;
    }

    /**
     *
     * @param cognome associa il cognome tramite il metodo
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     *
     * @return restituisce il nome associato all'utente
     */
    public String getNome() {
        return nome;
    }

    /**
     * associa il nome tramite il metodo
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     *
     * @return restituisce la password cifrata associata all'utente
     */
    public String getPasswordCifrata() {
        return passwordCifrata;
    }

    /**
     *
     * @param password associa la password tramite il metodo
     * @throws RuntimeException eccezione lanciata
     */
    public void setPasswordCifrata(String password)throws RuntimeException {
        this.passwordCifrata = password;
    }

    /**
     * metodo per richiamare il ruolo
     * @return
     */
    abstract public String getRuolo();
}
