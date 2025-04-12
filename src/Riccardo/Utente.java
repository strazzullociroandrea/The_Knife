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
    
    public Utente(int id, String password, String nome,String cognome, String username,String dataNascita, String domicilio, String ruolo){

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


}
