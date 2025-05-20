package src.view;
import src.dao.GestoreFile;
import src.model.*;
import src.model.util.PasswordUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static src.view.ViewBase.convertiScannerIntero;

public class ViewRistoratore {
    /**
     * Path del file JSON contenente gli utenti
     */
    private static final String PATHUTENTI = GestoreFile.adattaPath(new String[]{ "data", "Utenti.json"});
    /**
     * Path del file JSON contenente i ristoranti
     */
    private static final String PATHRISTORANTI = GestoreFile.adattaPath(new String[]{ "data", "Ristoranti.json"});

    /**
     * Metodo privato per gestire l'input da parte dell'utente, richiedendo di inserire un informazione fino a quando non è diversa da stringa vuota
     *
     * @param msg     messaggio da visualizzare
     * @param scanner scanner da utilizzare per l'input
     * @return stringa inserita dall'utente
     */

    private static String gestisciInput(String msg, Scanner scanner) {
        String input = "";
        do {
            System.out.println(msg);
            input = scanner.nextLine();
        } while (input.isEmpty());
        return input;
    }

    private static void navigazioneRecensioni(Ristoratore u, Ristorante r, List<Recensione> listaRecensioni) {
        Scanner s= new Scanner(System.in);
        if (listaRecensioni == null || listaRecensioni.isEmpty()) {
            System.out.println("Nessuna recensione trovata.");
        }
        int indiceR = 0;
        boolean visualizzaR = true;
        while (visualizzaR) {
            Recensione recensioneCorrente = listaRecensioni.get(indiceR);
            System.out.println("\n--- Recensione " + (indiceR + 1) + " di " + listaRecensioni.size() + " ---");
            System.out.println(recensioneCorrente.getDescrizione());
            System.out.println(recensioneCorrente.getStelle());
            System.out.println("""
                        Scegli un'opzione:
                        1. Vai alla recensione successiva
                        2. Torna al recensione precedente
                        3. Rispondi a recensione
                        4. Esci dalla visualizzazione
                    """);
            int sceltaInterna = ViewBase.convertiScannerIntero("Scelta:", s);
            switch (sceltaInterna) {

                case 1:
                    if (indiceR< listaRecensioni.size() - 1) {
                        indiceR++;
                    } else {
                        System.out.println("Hai raggiunto l'ultima recensione.");
                    }

                    break;

                case 2:
                    if (indiceR > 0) {
                        indiceR--;
                    } else {
                        System.out.println("Sei già alla prima recensione");
                    }

                    break;
                case 3:
                    String risposta=recensioneCorrente.getRisposta();
                    u.rispondiRecensione(recensioneCorrente,risposta);
                    break;
                case 4:
                    visualizzaR= false;
                    break;

                default:
                    System.out.println("Scelta non valida.");
            }
        }

    }

    private static void navigazioneRistoranti(Ristoratore u, Scanner s, List<Ristorante> listaRistoranti) {
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
                        1. Vai al ristorante successivo
                        2. Torna al ristorante precedente
                        3. Rimuovi il ristorante 
                        4. Visualizza recensioni ristorante
                        5. Visualizza riepilogo ristorante
                        6. Esci dalla visualizzazione
                    """);

            int sceltaInterna = ViewBase.convertiScannerIntero("Scelta:", s);

            switch (sceltaInterna) {

                case 1:
                    if (indice < listaRistoranti.size() - 1) {
                        indice++;
                    } else {
                        System.out.println("Hai raggiunto l'ultimo ristorante.");
                    }

                    break;

                case 2:
                    if (indice > 0) {
                        indice--;
                    } else {
                        System.out.println("Sei già al primo ristorante.");
                    }

                    break;

                case 3:
                    u.eliminaRistorante(ristoranteCorrente);

                    break;

                case 4:
                    List<Recensione> listaRecensioni;
                    listaRecensioni=ristoranteCorrente.getRecensioni();
                    navigazioneRecensioni(u,ristoranteCorrente,listaRecensioni);
                    break;

                case 5:
                    u.visualizzaRiepilogo(ristoranteCorrente);
                    break;

                case 6:
                    visualizza = false;

                    break;

                default:
                    System.out.println("Scelta non valida.");
            }
        }
    }

    public static void view(Ristoratore u) throws Exception {
        /*
         Menù ristoratore:
         1. Lista ristoranti gestiti (crea,modifica, elimina, aggiungi ristorante ai gestiti)
         2. Gestisci recensioni ristoranti gestiti (visualizza recensioni, rispondi a recensioni)
                """,
         */
        boolean continuaDelivery = true;
        boolean continua = true;
        boolean delivery = false;
        boolean continuaPrenotazione = true;
        boolean prenotazione = false;
        boolean continuaPrenotazioneOnline = true;
        boolean prenotazioneOnline = false;

        while (continua) {

            System.out.println("\n---Menù Ristoratore:---");
            System.out.println("1.Visualizza tutti i ristoranti gestiti");
            System.out.println("2.Crea ristorante");
            System.out.println("3.visualizza dati personali");
            System.out.println("4.modifica dati personali");
            System.out.println("5. Logout");


            try (Scanner s = new Scanner(System.in)) {

                int scelta = ViewBase.convertiScannerIntero("Scegli un'opzione", s);

                switch (scelta) {
                    case 1:
                        List<Ristorante> listaRistoranti = GestoreFile.caricaRistoranti(PATHRISTORANTI);
                        if (listaRistoranti.isEmpty()) {
                            System.out.println("Nessun ristorante trovato.");
                        }
                        navigazioneRistoranti(u, s, listaRistoranti);
                        break;
                    case 2:

                        System.out.println("Creazione del ristorante...");
                        System.out.println("inserire nome ristorante");
                        String nome= s.nextLine();
                        System.out.println("inserire nazione ristorante");
                        String nazione= s.nextLine();
                        System.out.println("inserire città ristorante");
                        String citta= s.nextLine();
                        System.out.println("inserire indirizzo ristorante");
                        String indirizzo= s.nextLine();
                        System.out.println("inserire se presente delivery del ristorante");
                        while (continuaDelivery) {
                            int sceltaDelivery = ViewBase.convertiScannerIntero("""
                                            \n\n
                                            Scegli un'opzione delivery:
                                            1.si
                                            2.no""",s);
                            switch (sceltaDelivery) {
                                case 1:
                                    delivery= true;
                                    continuaDelivery=false;
                                    break;
                                case 2:
                                    delivery=false;
                                    continuaDelivery=false;
                                    break;
                                default:
                                    System.out.println("Inserire un'opzione delivery");;
                                    break;
                            }
                        }
                        while (continuaPrenotazione) {
                            int sceltaPrenotazione = ViewBase.convertiScannerIntero("""
                                            \n\n
                                            Scegli un'opzione prenotazione:
                                            1.si
                                            2.no""",s);
                            switch (sceltaPrenotazione) {
                                case 1:
                                    prenotazione= true;
                                    continuaPrenotazione=false;
                                    break;
                                case 2:
                                    prenotazione=false;
                                    continuaPrenotazione=false;
                                    break;
                                default:
                                    System.out.println("opzione inserita in modo errato!");;
                                    break;
                            }

                        }

                        System.out.println("inserire il tipo cucina del ristorante");
                        String tipoCucina= s.nextLine();

                        while (continuaPrenotazioneOnline) {
                            int sceltaPrenotazioneOnline = ViewBase.convertiScannerIntero("""
                                            \n\n
                                            Scegli un'opzione prenotazione:
                                            1.si
                                            2.no""",s);
                            switch (sceltaPrenotazioneOnline) {
                                case 1:
                                    prenotazioneOnline= true;
                                    continuaPrenotazioneOnline=false;
                                    break;
                                case 2:
                                    prenotazioneOnline=false;
                                    continuaPrenotazioneOnline=false;
                                    break;
                                default:
                                    System.out.println("opzione inserita in modo errato!");
                                    break;
                            }

                        }

                        System.out.println("inserire il prezzo minimo per il tuo ristorante");
                        double minPrezzo=  s.nextDouble();

                        System.out.println("inserire il prezzo massimo per il tuo ristorante");
                        double maxPrezzo=  s.nextDouble();

                        Ristorante nuovoRistorante=u.creaRistorante(nome,nazione,citta,indirizzo,delivery,prenotazione,tipoCucina,prenotazioneOnline,minPrezzo,maxPrezzo);
                        List<Ristorante>listaRistoranti2=GestoreFile.caricaRistoranti(PATHRISTORANTI);
                        listaRistoranti2.add(nuovoRistorante);
                        GestoreFile.salvaRistoranti(listaRistoranti2,PATHRISTORANTI);
                        break;

                    case 3:
                        System.out.println("\n--- Dati utente ---");
                        System.out.println("Nome: " + u.getNome());
                        System.out.println("Cognome: " + u.getCognome());
                        System.out.println("Username: " + u.getUsername());
                        System.out.println("Data di nascita: " + u.getDataNascita());
                        System.out.println("Domicilio: " + u.getDomicilio());
                        System.out.println("Tipo utente: " + (u instanceof Ristoratore ? "Ristoratore" : "Cliente"));

                        break;
                    case 4:
                        String modNome = gestisciInput("inserisci il tuo nuovo nome", s);

                        u.setNome(modNome);

                        String modCognome = gestisciInput("inserisci il tuo nuovo cognome", s);

                        u.setCognome(modCognome);

                        String modUserName = gestisciInput("inserisci il tuo nuovo username", s);

                        boolean usernameEsistente = false;
                        for (Utente u1 : GestoreFile.caricaUtenti(PATHUTENTI)) {
                            if (modUserName.equals(u1.getUsername())) {
                                System.out.println("Username già esistente: modifica annullata");
                                usernameEsistente = true;
                                break;
                            }
                        }
                        if (!usernameEsistente) {
                            u.setUsername(modUserName);
                            System.out.println("Username aggiornato");
                        }


                        boolean passwordValida = false;

                        while (!passwordValida) {
                            String modPassword = gestisciInput("Inserisci la tua nuova password:", s);
                            String pwCifrata = PasswordUtil.hashPassword(modPassword);

                            if (pwCifrata.equals(u.getPasswordCifrata())) {
                                System.out.println("La nuova password è uguale a quella attuale. Inserisci una password diversa.");
                            } else {
                                u.setPasswordCifrata(pwCifrata);
                                System.out.println("Password modificata con successo.");
                                passwordValida = true;
                            }
                        }

                        String modDomicilio = gestisciInput("inserisci il tuo nuovo domicilio", s);
                        u.setDomicilio(modDomicilio);

                        break;


                }
            }
        }
    }
}