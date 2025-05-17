package src.view;

import src.dao.GestoreFile;
import src.model.Cliente;
import src.model.Recensione;
import src.model.Ristorante;
import src.model.Utente;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static src.dao.GestoreFile.caricaUtenti;


/**
 * Classe ViewCliente che rappresenta l'interfaccia grafica presentata al cliente
 * <p>
 * * @Author Strazzullo Ciro Andrea
 * * @Author Riccardo Giovanni Rubini
 * * @Author Matteo Mongelli
 */


public class ViewCliente {
    /**
     * Path del file JSON contenente gli utenti
     */
    private static final String PATHUTENTI = GestoreFile.adattaPath(new String[]{"..", "data", "Utenti.json"});
    /**
     * Path del file JSON contenente i ristoranti
     */
    private static final String PATHRISTORANTI = GestoreFile.adattaPath(new String[]{"..", "data", "Ristoranti.json"});

    //metodi

    /*
        public void prenotaTavolo(String ristorante) {
            try {
                for (Ristorante r : GestoreFile.caricaRistoranti(PATHRISTORANTI)) {
                    if (ristorante.equals(r.getNome())) {
                        r.;
                    }
                }


            }
        } catch(
        IOException e;)

        {
            throw new RuntimeException(e);
        }
    */

    private static String gestisciInput(String msg, Scanner scanner) {
        String input = "";
        do {
            System.out.println(msg);
            input = scanner.nextLine();
        } while (input.isEmpty());
        return input;
    }


    /**
     * metodo per visualizzare, interagire e scorrere dinamicamente i ristoranti
     *
     * @param u               l'utente che interagisce col ristorante e che può lasciare una recensione
     * @param s               lo scanner che permette all'utente di lasciare una recensione se lo richiede (stelle e testo)
     * @param listaRistoranti la lista di ristoranti che si vuole scorrere
     */

    private static void navigazioneRistoranti(Cliente u, Scanner s, List<Ristorante> listaRistoranti) {
        if (listaRistoranti == null || listaRistoranti.isEmpty()) {
            System.out.println("Nessun ristorante trovato.");
            return;
        }

        int indice = 0;
        boolean visualizza = true;

        while (visualizza) {
            Ristorante ristoranteCorrente = listaRistoranti.get(indice);
            System.out.println("\n--- Ristorante " + (indice + 1) + " di " + listaRistoranti.size() + " ---");
            System.out.println(ristoranteCorrente.visualizzaRistorante());

            System.out.println("""
                        Scegli un'opzione:
                        1. Lascia una recensione
                        2. Vai al ristorante successivo
                        3. Torna al ristorante precedente
                        4. Inserisci il ristorante tra i preferiti
                        5. Modifica la recensione lasciata al ristorante 
                        6. Esci dalla visualizzazione
                    """);

            int sceltaInterna = ViewBase.convertiScannerIntero("Scelta:", s);

            switch (sceltaInterna) {
                case 1:
                    System.out.println("Scrivi la tua recensione:");
                    String descrizione = s.nextLine();
                    int stelle = ViewBase.convertiScannerIntero("Inserisci il numero di stelle (0-5):", s);
                    u.aggiungiRecensione(ristoranteCorrente, stelle, descrizione);
                    System.out.println("Recensione aggiunta con successo!");
                    break;

                case 2:
                    if (indice < listaRistoranti.size() - 1) {
                        indice++;
                    } else {
                        System.out.println("Hai raggiunto l'ultimo ristorante.");
                    }
                    break;

                case 3:
                    if (indice > 0) {
                        indice--;
                    } else {
                        System.out.println("Sei già al primo ristorante.");
                    }
                    break;

                case 4:
                    u.aggiungiPreferito(ristoranteCorrente);

                case 5:
                    for (Recensione r : ristoranteCorrente.getRecensioni())
                        if (r.getId().)
                            if (u.getRecensioniMesse().contains(ristoranteCorrente.)) {
                            }
                    u.modificaRecensione()

                case 6:
                    visualizza = false;
                    break;

                default:
                    System.out.println("Scelta non valida.");
            }
        }
    }


    /**
     * metodo per acccedere alla view del cliente
     *
     * @param u l'utente di tipo Cliente che interagisce con la view
     */

    public static void view(Cliente u) {
        try (Scanner s = new Scanner(System.in)) {


            System.out.println("\n--- Menu Cliente ---");
            System.out.println("1. Visualizza tutti i ristoranti");
            System.out.println("2. Cerca ristoranti filtrando per parametri");
            System.out.println("3. Scrivi una recensione");
            System.out.println("4. Visualizza/modifica dati personali");
            System.out.println("5. Logout");


            int scelta = ViewBase.convertiScannerIntero("Scegli un'opzione:", s);

            switch (scelta) {
                case 1:
                    List<Ristorante> listaRistoranti = GestoreFile.caricaRistoranti(PATHRISTORANTI);
                    if (listaRistoranti.isEmpty()) {
                        System.out.println("Nessun ristorante trovato.");
                        break;
                    }
                    navigazioneRistoranti(u, s, listaRistoranti);
                    break;


                case 2:
                    System.out.println("\n--- Scegliere i criteri del filtro ---");

                    try (Scanner sc = new Scanner(System.in)) {
                        System.out.println("inserire una location");
                        String location = sc.nextLine();
                        System.out.println("inserire il tipo di cucina desiderata tra: /n");
                        String tipoCucina = sc.nextLine();
                        System.out.println("inserire il prezzo minimo richiesto");
                        double prezzoMinimo = sc.nextDouble();
                        System.out.println("inserire il prezzo massimo richiesto");
                        double prezzoMassimo = sc.nextDouble();
                        System.out.println("Digitare 1 per ricercare solo ristoranti con delivery, altrimenti premere invio");
                        boolean delivery = false;
                        String deliveryText = sc.nextLine();
                        if (deliveryText.equals("1")) {
                            delivery = true;
                        }
                        System.out.println("Digitare 1 per ricercare solo ristoranti con prenotazione disponibile, altrimenti premere invio");
                        boolean prenotazione = false;
                        String prenotazioneText = sc.nextLine();
                        if (deliveryText.equals("1")) {
                            prenotazione = true;
                        }
                        System.out.println("inserire il minimo di stelle richiesto");
                        int stelle = ViewBase.convertiScannerIntero(sc.nextLine(), s);


                        List<Ristorante> filtrati = Ristorante.combinata(GestoreFile.caricaRistoranti(PATHRISTORANTI), location, tipoCucina, prezzoMinimo, prezzoMassimo, true, delivery, true, prenotazione, stelle);

                        navigazioneRistoranti(u, s, filtrati);

                    }


                case 3:
                    try (Scanner sc = new Scanner(System.in)) {
                        System.out.println("inserire il nome del ristorante da recensire");
                        String nome = sc.nextLine();
                        for (Ristorante r : GestoreFile.caricaRistoranti(PATHUTENTI)) {
                            if (nome.equals(r.getNome())) {
                                System.out.println("scrivere la recensione");
                                String descrizione = sc.nextLine();
                                System.out.println("inserire il numero di stelle");
                                int stelle = ViewBase.convertiScannerIntero(sc.nextLine(), s);
                                u.aggiungiRecensione(r, stelle, descrizione);
                            }

                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:

                    break;

                case 5:
                    scriviRecensione(cliente, scanner, ristoranti);
                    break;

                case 6:
                    modificaProfilo(cliente, scanner);
                    break;

            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
