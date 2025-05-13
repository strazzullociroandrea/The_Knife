package src.view;

import src.dao.GestoreFile;
import src.model.Ristorante;

import java.io.IOException;
import java.util.Scanner;

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


    /**
     * metodo per accedere alla view del cliente
     *
     * @throws Exception
     */
    public static void view() throws Exception {
        try (Scanner s = new Scanner(System.in)) {

            System.out.println("\n--- Menu Cliente ---");
            System.out.println("1. Visualizza tutti i ristoranti");
            System.out.println("2. Filtra ristoranti per parametri");
            System.out.println("3. Prenota un tavolo");
            System.out.println("4. Gestisci prenotazioni");
            System.out.println("5. Scrivi una recensione");
            System.out.println("6. Visualizza/modifica dati personali");
            System.out.println("7. Logout");


            int scelta = ViewBase.convertiScannerIntero("Scegli un'opzione:", s);

            switch (scelta) {
                case 1:
                    try {
                        for (Ristorante r : GestoreFile.caricaRistoranti(PATHRISTORANTI)) {
                            System.out.println(r);
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    break;

                case 2:
                    System.out.println("\n--- Scegliere i criteri del filtro ---");
                    System.out.println("1. mostra solo disponibili");
                    System.out.println("2. mostra solo ristoranti che fanno delivery");
                    System.out.println("3. ricerca combinata");
                    System.out.println("4. ");
                    System.out.println("5. ");
                    System.out.println("6. ");
                    System.out.println("7. ");

                    int sceltaFiltro = ViewBase.convertiScannerIntero("Scegli un'opzione:", s);
                    switch (sceltaFiltro) {
                        case 1:
                            System.out.println("inserire la città dove effettuare la ricerca");
                            try (Scanner sc = new Scanner(System.in)) {
                                String location = sc.nextLine();
                                Ristorante.perPrenotazioneOnline(GestoreFile.caricaRistoranti(PATHRISTORANTI), true, location);
                            } catch (Exception e) {
                                System.out.println("Cità non trovata");
                            }
                            break;

                        case 2:
                            Ristorante.perDelivery();
                            break;


                        case 3:
                            try(Scanner sc = new Scanner(System.in)) {
                                System.out.println("inserire una location");
                                String location = sc.nextLine();
                                System.out.println("inserire il tipo di cucina desiderata tra: /n" + Ristorante.);


                                Ristorante.combinata();
                            }



                    }


                case 3:
                    prenotaTavolo(cliente, scanner, ristoranti);
                    break;

                case 4:
                    gestisciPrenotazioni(cliente, scanner);
                    break;

                case 5:
                    scriviRecensione(cliente, scanner, ristoranti);
                    break;

                case 6:
                    modificaProfilo(cliente, scanner);
                    break;

            }

        } catch (
                Exception e) {
            throw new Exception();
        }

    }
}
