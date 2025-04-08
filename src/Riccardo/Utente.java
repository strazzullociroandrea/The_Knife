package src.Riccardo;
public abstract class Utente {
    private int id;
    private String password;
    private String nome;
    private String cognome;
    private String username;
    private String domicilio;
    private String dataNascita;
    private String ruolo;
    
    public Utente(int id, String password, String nome,String cognome, String username, String domicilio, String ruolo){

        this.id=id;
        this.password=password;
        this.nome=nome;
        this.cognome=cognome;
        this.username=username;
        this.domicilio= domicilio;
        this.ruolo=ruolo;

    }


}
