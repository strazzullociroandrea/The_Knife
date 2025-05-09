package src.controller;


import src.view.ViewBase;




public class Main {


    public static void main(String[] args) {
        try{
            ViewBase.view();//Visualizzo l'interfaccia "grafica" di base
        }catch(Exception e){
            System.err.println("Errore durante il caricamento degli utenti: " + e.getMessage());
            System.out.println("Progetto terminato con errore!");
            return;
        }
        System.out.println("Stop del progetto....");
        System.out.println("Progetto terminato con successo!");
    }
}