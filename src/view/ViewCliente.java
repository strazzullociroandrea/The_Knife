package src.view;

import src.dao.GestoreFile;
import src.model.Cliente;
import src.model.Recensione;
import src.model.Ristorante;
import src.model.Utente;
import src.model.util.PasswordUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static src.dao.GestoreFile.caricaUtenti;


/**
 * Classe ViewCliente che rappresenta l'interfaccia grafica presentata al cliente
 *
 * @version 1.0
 * @Author Strazzullo Ciro Andrea
 * @Author Riccardo Giovanni Rubini
 * @Author Matteo Mongelli
 */

/**
 * classe che rappresenta l'interfaccia grafica del cliente e contiene metodi utili ad essa
 */
public class ViewCliente {
    /**
     * Path del file JSON contenente gli utenti
     */
    private static final String PATHUTENTI = GestoreFile.adattaPath(new String[]{"data", "Utenti.json"});
    /**
     * Path del file JSON contenente i ristoranti
     */
    private static final String PATHRISTORANTI = GestoreFile.adattaPath(new String[]{"data", "Ristoranti.json"});

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

    /**
     * metodo per verificare che un ristorante esista all'interno di una lista
     *
     * @param r il ristorante da verificare
     * @return
     */

    public boolean verificaRistorante(Ristorante r) {
        boolean esiste = false;
        try {
            for (Ristorante tmp : GestoreFile.caricaRistoranti(PATHUTENTI))
                if (tmp.equals(r))
                    esiste = true;
        } catch (IOException e) {
            throw new RuntimeException("Ristorante non trovato");
        }
        return esiste;
    }

    /**
     * Metodo privato per gestire l'input da parte dell'utente, richiedendo di inserire un informazione fino a quando non è diversa da stringa vuota
     *
     * @param msg     messaggio da visualizzare
     * @param scanner scanner da utilizzare per l'input
     * @param blank   boolean per decidere se richiedere nuovamente l'inserimento dei dati tramite scanner se l'input è vuoto (true per richiederlo, false per non richiederlo)
     * @return stringa inserita dall'utente
     */

    private static String gestisciInput(String msg, Scanner scanner, boolean blank) {
        String input = "";
        do {
            System.out.println(msg);
            input = scanner.nextLine();
        } while (input.isEmpty() && blank);
        return input;
    }

    /**
     * metodo privato per impedire che l'utente inserisca, quando richiesto, dati che non siano numeri
     * @param s l'oggetto di tipo Scanner usato dal metodo
     * @param messaggio la stringa che contiene il messaggio da riferire all'utente per guidare l'inserimento dei dati
     * @return un double
     */
    private static double leggiDouble(Scanner s, String messaggio) {
        while (true) {
            System.out.println(messaggio);
            try {
                return Double.parseDouble(s.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido (usa il punto come separatore decimale)");
            }
        }
    }



    /**
     * metodo per visualizzare, interagire e scorrere dinamicamente i ristoranti
     *
     * @param u               l'utente che interagisce col ristorante e che può lasciare una recensione
     * @param s               lo scanner che permette all'utente di lasciare una recensione se lo richiede (stelle e testo)
     * @param listaRistoranti la lista di ristoranti che si vuole scorrere
     */

    private static void navigazioneRistoranti(Cliente u, Scanner s, List<Ristorante> listaRistoranti) throws IOException {
        if (listaRistoranti == null || listaRistoranti.isEmpty()) {
            System.out.println("Nessun ristorante trovato");
            return;
        }

        //liste utenti e ristoranti aggiornate su cui vengono effettuate tutte le modifiche in esecuzione
        List<Utente> listaUtentiTBS = GestoreFile.caricaUtenti(PATHUTENTI);
        List<Ristorante> listaristorantiTBS = GestoreFile.caricaRistoranti(PATHRISTORANTI);
        if (listaUtentiTBS == null || listaristorantiTBS == null) {
            System.out.println("Errore durante il caricamento dei dati");
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
                        5. Rimuovi il ristorante dai preferiti
                        6. Modifica la recensione lasciata al ristorante 
                        7. Esci dalla visualizzazione
                    """);

            int sceltaInterna = ViewBase.convertiScannerIntero("Scelta:", s);

            switch (sceltaInterna) {
                case 1:
                    System.out.println("Scrivi la tua recensione:");
                    String descrizione = s.nextLine();
                    int stelle = ViewBase.convertiScannerIntero("Inserisci il numero di stelle (0-5):", s);
                    u.aggiungiRecensione(ristoranteCorrente, stelle, descrizione);
                    System.out.println("Recensione aggiunta con successo!");
                    //salvataggio dati
                    listaUtentiTBS.remove(u);
                    listaUtentiTBS.add(u);
                    GestoreFile.salvaUtenti(listaUtentiTBS, PATHUTENTI);
                    listaristorantiTBS.remove(ristoranteCorrente);
                    listaristorantiTBS.add(ristoranteCorrente);
                    GestoreFile.salvaRistoranti(listaristorantiTBS, PATHRISTORANTI);
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
                    //salvataggio dati
                    listaUtentiTBS.remove(u);
                    listaUtentiTBS.add(u);
                    GestoreFile.salvaUtenti(listaUtentiTBS, PATHUTENTI);

                    break;

                case 5:
                    u.rimuoviPreferito(ristoranteCorrente);
                    //salvataggio dati
                    listaUtentiTBS.remove(u);
                    listaUtentiTBS.add(u);
                    GestoreFile.salvaUtenti(listaUtentiTBS, PATHRISTORANTI);

                    break;

                case 6:
                    for (Recensione r : ristoranteCorrente.getRecensioni())
                        for (Recensione r1 : u.getRecensioniMesse())
                            if (r.getId() == r1.getId()) {
                                String txt = gestisciInput("inserire il testo della recensione", s, true);
                                int stelleMod = ViewBase.convertiScannerIntero(gestisciInput("inserire il numero di stelle", s, true), s);
                                u.modificaRecensione(r, txt, stelleMod);
                                //salvataggio dati
                                listaUtentiTBS.remove(u);
                                listaUtentiTBS.add(u);
                                listaristorantiTBS.remove(ristoranteCorrente);
                                listaristorantiTBS.add(ristoranteCorrente);
                                GestoreFile.salvaUtenti(listaUtentiTBS, PATHUTENTI);
                                GestoreFile.salvaRistoranti(listaristorantiTBS, PATHRISTORANTI);
                            }

                    break;


                case 7:
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

    public static void view(Cliente u) throws IOException {
        try (Scanner s = new Scanner(System.in)) {
            boolean continua = true;

            while (continua) {
                System.out.println("\n--- Menu Cliente ---");
                System.out.println("1. Visualizza tutti i ristoranti");
                System.out.println("2. Cerca ristoranti filtrando per parametri");
                System.out.println("3. Visualizza i tuoi dati personali");
                System.out.println("4. Modifica dati personali");
                System.out.println("5. Logout");


                int scelta = ViewBase.convertiScannerIntero("Scegli un'opzione:", s);

                switch (scelta) {
                    case 1:
                        List<Ristorante> listaRistoranti = GestoreFile.caricaRistoranti(PATHRISTORANTI);
                        if (listaRistoranti.isEmpty()) {
                            System.out.println("Nessun ristorante trovato.");
                        }
                        navigazioneRistoranti(u, s, listaRistoranti);

                        break;


                    case 2:
                        System.out.println("\n--- Scegliere i criteri del filtro ---");

                        System.out.println("inserire una location");
                        String location = s.nextLine();
                        System.out.println("inserire il tipo di cucina desiderata tra: /n");
                        String tipoCucina = s.nextLine();
                        double prezzoMinimo = leggiDouble(s, "Inserire il prezzo minimo richiesto:");
                        double prezzoMassimo = leggiDouble(s, "Inserire il prezzo massimo richiesto:");
                        System.out.println("Digitare 1 per ricercare solo ristoranti con delivery, altrimenti premere invio");
                        boolean delivery = false;
                        String deliveryText = s.nextLine();
                        if (deliveryText.equals("1")) {
                            delivery = true;
                        }
                        System.out.println("Digitare 1 per ricercare solo ristoranti con prenotazione disponibile, altrimenti premere invio");
                        boolean prenotazione = false;
                        String prenotazioneText = s.nextLine();
                        if (prenotazioneText.equals("1")) {
                            prenotazione = true;
                        }
                        System.out.println("inserire il minimo di stelle richiesto");
                        int stelleMin = ViewBase.convertiScannerIntero(s.nextLine(), s);


                        List<Ristorante> filtrati = Ristorante.combinata(GestoreFile.caricaRistoranti(PATHRISTORANTI), location, tipoCucina, prezzoMinimo, prezzoMassimo, true, delivery, true, prenotazione, stelleMin);

                        navigazioneRistoranti(u, s, filtrati);

                        break;


                    case 3:
                        GestoreFile.caricaUtenti(PATHUTENTI);
                        System.out.println("\n--- Dati utente ---");
                        System.out.println("Nome: " + u.getNome());
                        System.out.println("Cognome: " + u.getCognome());
                        System.out.println("Username: " + u.getUsername());
                        System.out.println("Data di nascita: " + u.getDataNascita());
                        System.out.println("Domicilio: " + u.getDomicilio());
                        System.out.println("Tipo utente: " + (u instanceof Cliente ? "Cliente" : "Ristoratore"));

                        break;

                    case 4:
                        String modNome = gestisciInput("inserisci il tuo nuovo nome", s, false);
                        if (modNome.isBlank()) {
                            System.out.println("dato non modificato");
                        } else
                            u.setNome(modNome);

                        String modCognome = gestisciInput("inserisci il tuo nuovo cognome", s, false);
                        if (modCognome.isBlank()) {
                            System.out.println("dato non modificato");
                        } else
                            u.setCognome(modCognome);

                        String modUserName = gestisciInput("inserisci il tuo nuovo username", s, false);
                        if (modUserName.isBlank()) {
                            System.out.println("dato non modificato");
                        } else {
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
                        }


                        boolean passwordValida = false;

                        String modPassword = gestisciInput("Inserisci la tua nuova password:", s, false);
                        if (modPassword.isBlank())
                            System.out.println("dato non modificato");
                        else {

                            while (!passwordValida) {
                                modPassword = gestisciInput("Inserisci la tua nuova password:", s, false);

                                if (modPassword.isBlank()) {
                                    System.out.println("dato non modificato");
                                } else {
                                    String pwCifrata = PasswordUtil.hashPassword(modPassword);

                                    if (pwCifrata.equals(u.getPasswordCifrata())) {
                                        System.out.println("La nuova password è uguale a quella attuale. Inserisci una password diversa.");
                                    } else {
                                        u.setPasswordCifrata(pwCifrata);
                                        System.out.println("Password modificata con successo.");
                                        passwordValida = true;
                                    }
                                }
                            }
                        }

                        String modDomicilio = gestisciInput("inserisci il tuo nuovo domicilio", s, false);

                        if (modDomicilio.isBlank()) {
                            System.out.println("dato non modificato");
                        } else {
                            u.setDomicilio(modDomicilio);
                        }
                        //salvataggio dei dati modificati
                        List<Utente> listaUtentiTBS = GestoreFile.caricaUtenti(PATHUTENTI);
                        if (listaUtentiTBS == null) {
                            System.out.println("Impossibile salvare i dati: lista utenti non disponibile");
                        }
                        else {
                            //salvataggio dati
                            listaUtentiTBS.remove(u);
                            listaUtentiTBS.add(u);
                            GestoreFile.salvaUtenti(listaUtentiTBS, PATHUTENTI);
                        }
                        break;
                    case 5:
                        System.out.println("Verrai reinderizzato al menù iniziale!");
                        ViewBase.view();

                        return;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
