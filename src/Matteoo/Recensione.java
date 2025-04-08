package src.Matteoo;

public class Recensione {
    private String testo;
    private int stelle;

    public Recensione(String testo, int stelle) {
        this.testo = testo;
        this.stelle = stelle;
    }

    public void SetNumeroStelle(int nStelle){
        if(nStelle > 0 && nStelle <= 5 )
            this.stelle = nStelle;
    }
    int count;
    public void setDescrizione(String txt) {
        for(char tmp :txt.toCharArray()){
            count++;
    }
}
