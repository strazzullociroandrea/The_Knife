package src.theknife.controller;


import src.theknife.dao.GestoreFile;
import src.theknife.view.ViewBase;

import java.io.IOException;
/**
 * Classe Main per l'avvio del progetto "The Knife".
 *
 * @version 1.0
 * @Author Strazzullo Ciro Andrea, 763603, VA
 * @Author Riccardo Giovanni Rubini, 761126, VA
 * @Author Matteo Mongelli, 760960, VA 
 */

public class TheKnife {
    /**
     * Path del file JSON contenente gli utenti
     */
    private static final String PATHUTENTI = GestoreFile.adattaPath(new String[]{"data", "Utenti.json"});

    /**
     * Path del file JSON contenente i ristoranti
     */
    private static final String PATHRISTORANTI = GestoreFile.adattaPath(new String[]{"data", "Ristoranti.json"});

    /**
     * Metodo per svuotare la console dai log di configurazione
     *
     * @throws IOException          eccezione di input/output
     * @throws InterruptedException eccezione di interruzione
     */
    public static void svuotaConsole() throws IOException, InterruptedException {
        try {
            String operatingSystem = System.getProperty("os.name"); // recupero del sistema operativo corrente
            if (operatingSystem.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
            Thread.sleep(1000);
        } catch (Exception e) {
            for (int i = 0; i < 50; i++)
                System.out.println();
        }
    }

    /**
     * Metodo principale, dove inizia l'esecuzione del programma.
     * Inizializza l'interfaccia grafica e gestisce eventuali eccezioni.
     *
     * @param args Argomenti della riga di comando (non utilizzati in questo progetto).
     */
    public static void main(String[] args) {
        try {
            ViewBase.view(PATHUTENTI, PATHRISTORANTI);
        } catch (Exception e) {
            System.err.println("Errore durante il caricamento degli utenti: " + e.getMessage());
            System.out.println("Progetto terminato con errore!");
            return;
        }
        System.out.println("Stop del progetto....");
        System.out.println("Progetto terminato con successo!");
    }
}