package src.Matteoo;

import java.util.ArrayList;
import java.util.List;

public class GestoreRecensioni {
    //attributi
    private static List<Recensione> recensioni = new ArrayList<>();
    //metodi
    public static void aggiungiRecensione(Recensione r) {
        recensioni.add(r);
    }

    public static List<Recensione> getRecensioni() {
        return recensioni;
    }

    public static double mediaStelle() {
        if (recensioni.isEmpty()) return 0;
        int somma = 0;
        for (Recensione r : recensioni) {
            somma += r.getStelle();
        }
        return (double) somma / recensioni.size();
    }

    public int numeroRecensioni() {
        return recensioni.size();
    }
}

