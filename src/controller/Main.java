package src.controller;


import src.view.ViewBase;




public class Main {
    /**
     * Metodo principale, dove inizia l'esecuzione del programma.
     * Inizializza l'interfaccia grafica e gestisce eventuali eccezioni.
     * @param args Argomenti della riga di comando (non utilizzati in questo progetto).
     */
    public static void main(String[] args) {
        try{
            ViewBase.view();
        }catch(Exception e){
            System.err.println("Errore durante il caricamento degli utenti: " + e.getMessage());
            System.out.println("Progetto terminato con errore!");
            return;
        }
        System.out.println("Stop del progetto....");
        System.out.println("Progetto terminato con successo!");
    }
}