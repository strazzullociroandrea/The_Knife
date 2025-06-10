package src.view;

import src.dao.GestoreFile;
import src.model.*;
import src.model.util.PasswordUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static src.dao.GestoreFile.caricaUtenti;
import static src.view.ViewBase.convertiScannerIntero;

public class ViewRistoratore {

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
     * Metodo per svuotare la console dai log di configurazione
     *
     * @throws IOException          eccezione di input/output
     * @throws InterruptedException eccezione di interruzione
     */
    private static void svuotaConsole() throws IOException, InterruptedException {
        try {
            String operatingSystem = System.getProperty("os.name"); // recupero del sistema operativo corrente
            if (operatingSystem.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++)
                System.out.println();
        }

    }

    /**
     * metodo privato per ottenere una stringa risposta, possibile attributo di di oggetti di tipo Recensione, in maniera corretta (<250 caratteri)
     *
     * @param s lo scanner utilizzato per leggere l'input dell'utente
     * @return la stringa da massimo 250 caratteri che può rappresentare il testo della recensione
     */

    private static String leggiRispostaValida(Scanner s) {
        while (true) {
            String input = gestisciInput("Scrivi la risposta (max 250 caratteri):", s, true);
            if (input.length() <= 250) {
                return input;
            } else {
                System.out.println("Errore: la risposta non può superare 250 caratteri.");
            }
        }
    }

    /**
     * metodo privato per impedire che l'utente inserisca, quando richiesto, dati che non siano numeri
     *
     * @param s         l'oggetto di tipo Scanner usato dal metodo
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
     * metodo per visualizzare, interagire e scorrere dinamicamente le recensioni
     *
     * @param u               utente (ristoratore) che può interagire con le recensioni
     * @param r
     * @param listaRecensioni lista delle recensioni che si vuole scorrere
     */
    private static void navigazioneRecensioni(Ristoratore u, Ristorante r, List<Recensione> listaRecensioni) {
        try (Scanner s = new Scanner(System.in)) {
            if (u.getRistorantiGestiti() == null || !u.getRistorantiGestiti().contains(r)) {
                System.out.println("Non gestisci questo ristorante.");
                return;
            }
            if (listaRecensioni == null || listaRecensioni.isEmpty()) {
                System.out.println("Nessuna recensione trovata per questo ristorante.");
                return;
            }
            int indiceR = 0;
            boolean visualizzaR = true;
            while (visualizzaR) {
                svuotaConsole();

                Recensione recensioneCorrente = listaRecensioni.get(indiceR);
                if (r.getRecensioni() == null || !r.getRecensioni().contains(recensioneCorrente)) {
                    System.out.println("Recensione non appartenente al ristorante selezionato.");
                    return;
                }
                System.out.println("\n--- Recensione " + (indiceR + 1) + " di " + listaRecensioni.size() + " ---");
                System.out.println("Testo: " + recensioneCorrente.getDescrizione());
                System.out.println("Stelle: " + recensioneCorrente.getStelle());
                System.out.println("""
                            Scegli un'opzione:
                            1. Vai alla recensione successiva
                            2. Torna alla recensione precedente
                            3. Rispondi alla recensione
                            4. Esci dalla visualizzazione
                        """);

                int sceltaInterna = ViewBase.convertiScannerIntero("Scelta:", s);

                switch (sceltaInterna) {
                    case 1 -> {
                        if (indiceR < listaRecensioni.size() - 1) {
                            indiceR++;
                        } else {
                            System.out.println("Hai raggiunto l'ultima recensione.");
                        }
                    }
                    case 2 -> {
                        if (indiceR > 0) {
                            indiceR--;
                        } else {
                            System.out.println("Sei già alla prima recensione.");
                        }
                    }
                    case 3 -> {
                        System.out.println("Inserisci la tua risposta, attenzione la risposta verrà sovrascritta:");
                        String risposta = leggiRispostaValida(s);
                        u.rispondiRecensione(recensioneCorrente, risposta);
                        System.out.println("Risposta aggiunta/modificata con successo.");
                    }
                    case 4 -> visualizzaR = false;
                    default -> System.out.println("Scelta non valida.");
                }
            }
        } catch (Exception e) {
            System.err.println("Errore nella navigazione delle recensioni: " + e.getMessage());
        }
    }


    /**
     * metodo per visualizzare, interagire e scorrere dinamicamente i ristoranti
     *
     * @param u               l'utente che interagisce col ristorante e che può lasciare una recensione
     * @param s               lo scanner che permette all'utente di lasciare una recensione se lo richiede (stelle e testo)
     * @param listaRistoranti la lista di ristoranti che si vuole scorrere
     */

    private static void navigazioneRistoranti(Ristoratore u, Scanner s, List<Ristorante> listaRistoranti, String pathUtenti, String pathRistoranti) throws IOException, InterruptedException {
        if (listaRistoranti == null || listaRistoranti.isEmpty()) {
            System.out.println("Nessun ristorante trovato.");
            return;
        }
        int indice = 0;
        boolean visualizza = true;
        while (visualizza) {
            svuotaConsole();

            if (listaRistoranti.isEmpty()) {
                System.out.println("Non ci sono più ristoranti da visualizzare.");
                return;
            }
            if (indice >= listaRistoranti.size()) {
                indice = listaRistoranti.size() - 1;
            }
            Ristorante ristoranteCorrente = listaRistoranti.get(indice);
            if (u.getRistorantiGestiti() == null || !u.getRistorantiGestiti().contains(ristoranteCorrente)) {
                System.out.println("Non gestisci questo ristorante, rimosso dalla lista.");
                listaRistoranti.remove(ristoranteCorrente);
                continue; // passa al prossimo ciclo senza incrementare l'indice
            }
            System.out.println("\n--- Ristorante " + (indice + 1) + " di " + listaRistoranti.size() + " ---");
            System.out.println(ristoranteCorrente.visualizzaRistorante());
            System.out.println("""
                    Scegli un'opzione:
                    1. Vai al ristorante successivo
                    2. Torna al ristorante precedente
                    3. Rimuovi il ristorante
                    4. Visualizza recensioni ristorante
                    5. Visualizza riepilogo ristorante
                    6. Modifica il ristorante 
                    7. Esci dalla visualizzazione
                    """);
            int sceltaInterna = ViewBase.convertiScannerIntero("Scelta:", s);
            switch (sceltaInterna) {
                case 1 -> {
                    if (indice < listaRistoranti.size() - 1) {
                        indice++;
                    } else {
                        System.out.println("Hai raggiunto l'ultimo ristorante.");
                    }
                }
                case 2 -> {
                    if (indice > 0) {
                        indice--;
                    } else {
                        System.out.println("Sei già al primo ristorante.");
                    }
                }
                case 3 -> {
                    u.eliminaRistorante(ristoranteCorrente);
                    listaRistoranti.remove(ristoranteCorrente);
                    System.out.println("Ristorante rimosso con successo.");
                }
                case 4 -> {
                    List<Recensione> listaRecensioni = ristoranteCorrente.getRecensioni();
                    navigazioneRecensioni(u, ristoranteCorrente, listaRecensioni);
                }
                case 5 -> u.visualizzaRiepilogo(ristoranteCorrente);

                case 6 -> {
                    String modNome = gestisciInput("inserisci il nuovo nome del ristorante. Premi invio per lasciarlo invariato", s, false);
                    if (modNome.isBlank()) {
                        System.out.println("dato non modificato");
                    } else
                        ristoranteCorrente.setNome(modNome);

                    String modNazione = gestisciInput("inserisci la nuova nazione del ristorante. Premi invio per lasciarla invariata", s, false);
                    if (modNazione.isBlank()) {
                        System.out.println("dato non modificato");
                    } else
                        ristoranteCorrente.setNazione(modNazione);

                    String modCitta = gestisciInput("inserisci la nuova città del ristorante. Premi invio per lasciarla invariata", s, false);
                    if (modCitta.isBlank()) {
                        System.out.println("dato non modificato");
                    } else
                        ristoranteCorrente.setCitta(modCitta);


                    while (true) {
                        int sceltaDelivery = ViewBase.convertiScannerIntero(
                                "\nScegli un'opzione delivery:\n0. non modificare\n1. sì\n2. no\n", s);
                        if (sceltaDelivery == 1) {
                            ristoranteCorrente.setDelivery(true);
                            break;
                        } else if (sceltaDelivery == 2) {
                            ristoranteCorrente.setDelivery(false);
                            break;
                        } else if (sceltaDelivery == 0) {
                            System.out.println("dato non modificato");
                            break;
                        } else {
                            System.out.println("Opzione non valida, riprova.");
                        }
                    }

                    while (true) {
                        int sceltaPrenotazione = ViewBase.convertiScannerIntero(
                                "\nScegli un'opzione prenotazione:\n0. non modificare \n1. sì\n2. no\n", s);
                        if (sceltaPrenotazione == 1) {
                            ristoranteCorrente.setPrenotazioneOnline(true);
                            break;
                        } else if (sceltaPrenotazione == 2) {
                            ristoranteCorrente.setPrenotazioneOnline(false);
                            break;
                        } else if (sceltaPrenotazione == 0) {
                            System.out.println("dato non modificato");
                            break;
                        } else {
                            System.out.println("Opzione non valida, riprova.");
                        }
                    }

                    while (true) {
                        int sceltaPrenOnline = ViewBase.convertiScannerIntero(
                                "\nScegli un'opzione prenotazione online:\n0. non modificare\n1. sì\n2. no\n", s);
                        if (sceltaPrenOnline == 1) {
                            ristoranteCorrente.setPrenotazioneOnline(true);
                            break;
                        } else if (sceltaPrenOnline == 2) {
                            ristoranteCorrente.setPrenotazioneOnline(false);
                            break;
                        } else if (sceltaPrenOnline == 0) {
                            System.out.println("dato non modificato");
                            break;
                        } else {
                            System.out.println("Opzione non valida, riprova.");
                        }
                    }

                    System.out.println("Inserire il prezzo minimo per il tuo ristorante (oppure premi INVIO per non modificarlo):");
                    String inputMin = s.nextLine().trim();
                    if (!inputMin.isEmpty()) {
                        try {
                            double minPrezzo = Double.parseDouble(inputMin);
                            if (minPrezzo < 0) {
                                System.out.println("Il prezzo minimo non può essere negativo. Valore non modificato.");
                            } else {
                                ristoranteCorrente.setMinPrezzo(minPrezzo);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Formato non valido. Prezzo minimo non modificato.");
                        }
                    } else {
                        System.out.println("Prezzo minimo non modificato.");
                    }


                    System.out.println("Inserire il prezzo massimo per il tuo ristorante (oppure premi INVIO per non modificarlo):");
                    String inputMax = s.nextLine().trim();
                    if (!inputMax.isEmpty()) {
                        try {
                            double maxPrezzo = Double.parseDouble(inputMax);
                            if (maxPrezzo < ristoranteCorrente.getMinPrezzo()) {
                                System.out.println("Il prezzo massimo deve essere maggiore o uguale al minimo. Valore non modificato.");
                            } else {
                                ristoranteCorrente.setMaxPrezzo(maxPrezzo);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Formato non valido. Prezzo massimo non modificato.");
                        }
                    } else {
                        System.out.println("Prezzo massimo non modificato.");
                    }

                    //salvataggio dei dati modificati
                    List<Ristorante> listaRistorantiSalvataggio = GestoreFile.caricaRistoranti(pathRistoranti);
                    
                    // Verifico se il ristorante esiste già per ID
                    boolean ristoranteEsistente = false;
                    for (int i = 0; i < listaRistorantiSalvataggio.size(); i++) {
                        if (listaRistorantiSalvataggio.get(i).getId() == ristoranteCorrente.getId()) {
                            // Sostituisco il ristorante esistente
                            listaRistorantiSalvataggio.set(i, ristoranteCorrente);
                            ristoranteEsistente = true;
                            break;
                        }
                    }
                    
                    // Se il ristorante non esiste, lo aggiungo
                    if (!ristoranteEsistente) {
                        listaRistorantiSalvataggio.add(ristoranteCorrente);
                    }
                    
                    GestoreFile.salvaRistoranti(listaRistorantiSalvataggio, pathRistoranti);

                    List<Utente> listaUtenti = GestoreFile.caricaUtenti(pathUtenti, pathRistoranti);
                    
                    // Rimuovo l'utente attuale dalla lista (usando equals)
                    listaUtenti.removeIf(utente -> utente.equals(u));
                    
                    // Aggiungo l'utente aggiornato
                    listaUtenti.add(u);
                    
                    GestoreFile.salvaUtenti(listaUtenti, pathUtenti);

                    System.out.println("Ristorante modificato con successo.");


                }

                case 7 -> visualizza = false;
                default -> System.out.println("Scelta non valida.");
            }
        }
    }


    /**
     * metodo per accedere alla view del risotrante
     *
     * @param u              utente in grado di interagire con la view
     * @param pathUtenti     path del file JSON contenente gli utenti
     * @param pathRistoranti path del file JSON contenente i ristoranti
     * @throws Exception
     */
    public static void view(Ristoratore u, String pathUtenti, String pathRistoranti) throws Exception {
        boolean continua = true;

        try (Scanner s = new Scanner(System.in)) {
            while (continua) {
                System.out.println("\n--- Menù Ristoratore ---");
                System.out.println("1. Visualizza tutti i ristoranti gestiti");
                System.out.println("2. Crea ristorante");
                System.out.println("3. Visualizza dati personali");
                System.out.println("4. Modifica dati personali");
                System.out.println("5. Logout");

                int scelta = ViewBase.convertiScannerIntero("Scegli un'opzione", s);

                switch (scelta) {
                    case 1 -> {
                        List<Ristorante> listaRistoranti = u.getRistorantiGestiti();// ✅ solo quelli del ristoratore loggato

                        if (listaRistoranti == null || listaRistoranti.isEmpty()) {
                            System.out.println("Non gestisci ancora alcun ristorante.");
                        } else {
                            navigazioneRistoranti(u, s, listaRistoranti, pathUtenti, pathRistoranti);
                        }
                    }


                    case 2 -> {
                        try {
                            System.out.println("Creazione del ristorante...");

                            String nome = gestisciInput("Inserire il nome del ristorante: ", s, true);
                            String nazione = gestisciInput("Inserire la nazione del ristorante: ", s, true);
                            String citta = gestisciInput("Inserire la città del ristorante: ", s, true);
                            String indirizzo = gestisciInput("Inserire l'indirizzo del ristorante: ", s, true);
                            String tipoCucina = gestisciInput("Inserire il tipo di cucina: ", s, true);

                            boolean delivery;
                            while (true) {
                                int sceltaDelivery = ViewBase.convertiScannerIntero(
                                        "\nScegli un'opzione delivery:\n1. sì\n2. no\n", s);
                                if (sceltaDelivery == 1) {
                                    delivery = true;
                                    break;
                                } else if (sceltaDelivery == 2) {
                                    delivery = false;
                                    break;
                                } else {
                                    System.out.println("Opzione non valida, riprova.");
                                }
                            }

                            boolean prenotazione;
                            while (true) {
                                int sceltaPrenotazione = ViewBase.convertiScannerIntero(
                                        "\nScegli un'opzione prenotazione:\n1. sì\n2. no\n", s);
                                if (sceltaPrenotazione == 1) {
                                    prenotazione = true;
                                    break;
                                } else if (sceltaPrenotazione == 2) {
                                    prenotazione = false;
                                    break;
                                } else {
                                    System.out.println("Opzione non valida, riprova.");
                                }
                            }

                            boolean prenotazioneOnline;
                            while (true) {
                                int sceltaPrenOnline = ViewBase.convertiScannerIntero(
                                        "\nScegli un'opzione prenotazione online:\n1. sì\n2. no\n", s);
                                if (sceltaPrenOnline == 1) {
                                    prenotazioneOnline = true;
                                    break;
                                } else if (sceltaPrenOnline == 2) {
                                    prenotazioneOnline = false;
                                    break;
                                } else {
                                    System.out.println("Opzione non valida, riprova.");
                                }
                            }

                            double minPrezzo;
                            do {
                                minPrezzo = leggiDouble(s, "Inserire il prezzo minimo per il tuo ristorante: ");
                                if (minPrezzo < 0) {
                                    System.out.println("Il prezzo non può essere negativo.");
                                }
                            } while (minPrezzo < 0);

                            double maxPrezzo;
                            do {
                                maxPrezzo = leggiDouble(s, "Inserire il prezzo massimo per il tuo ristorante: ");
                                if (maxPrezzo < minPrezzo) {
                                    System.out.println("Il prezzo massimo deve essere maggiore o uguale al minimo.");
                                }
                            } while (maxPrezzo < minPrezzo);

                            // creazione e salvataggio
                            Ristorante nuovoRistorante = u.creaRistorante(nome, nazione, citta, indirizzo,
                                    delivery, prenotazione, tipoCucina, prenotazioneOnline, minPrezzo, maxPrezzo);

                            // Carico tutti i ristoranti attuali
                            List<Ristorante> listaRistorantiSalvataggio = GestoreFile.caricaRistoranti(pathRistoranti);
                            
                            // Verifico se il ristorante esiste già per ID
                            boolean ristoranteEsistente = false;
                            for (int i = 0; i < listaRistorantiSalvataggio.size(); i++) {
                                if (listaRistorantiSalvataggio.get(i).getId() == nuovoRistorante.getId()) {
                                    // Sostituisco il ristorante esistente
                                    listaRistorantiSalvataggio.set(i, nuovoRistorante);
                                    ristoranteEsistente = true;
                                    break;
                                }
                            }
                            
                            // Se il ristorante non esiste, lo aggiungo
                            if (!ristoranteEsistente) {
                                listaRistorantiSalvataggio.add(nuovoRistorante);
                            }
                            
                            // Salvo la lista aggiornata dei ristoranti
                            GestoreFile.salvaRistoranti(listaRistorantiSalvataggio, pathRistoranti);

                            // Aggiorno anche l'utente
                            List<Utente> listaUtenti = GestoreFile.caricaUtenti(pathUtenti, pathRistoranti);
                            
                            // Rimuovo l'utente attuale dalla lista (usando equals)
                            listaUtenti.removeIf(utente -> utente.equals(u));
                            
                            // Aggiungo l'utente aggiornato
                            listaUtenti.add(u);
                            
                            // Salvo la lista aggiornata degli utenti
                            GestoreFile.salvaUtenti(listaUtenti, pathUtenti);

                            System.out.println("Ristorante creato con successo.");

                        } catch (Exception e) {
                            System.out.println("Errore durante la creazione del ristorante:");
                            e.printStackTrace(); // stampa completa per debugging
                        }
                    }


                    case 3 -> {
                        GestoreFile.caricaUtenti(pathUtenti, pathRistoranti);
                        System.out.println("\n--- Dati utente ---");
                        System.out.println("Nome: " + (u.getNome() != null ? u.getNome() : "Non specificato"));
                        System.out.println("Cognome: " + (u.getCognome() != null ? u.getCognome() : "Non specificato"));
                        System.out.println("Username: " + (u.getUsername() != null ? u.getUsername() : "Non specificato"));
                        System.out.println("Data di nascita: " + (u.getDataNascita() != null ? u.getDataNascita() : "Non specificata"));
                        System.out.println("Domicilio: " + (u.getDomicilio() != null ? u.getDomicilio() : "Non specificato"));
                        System.out.println("Tipo utente: " + (u instanceof Ristoratore ? "Ristoratore" : "Cliente"));
                    }


                    case 4 -> {
                        String modNome = gestisciInput("inserisci il tuo nuovo nome. Premi invio per lasciarlo invariato", s, false);
                        if (modNome.isBlank()) {
                            System.out.println("dato non modificato");
                        } else
                            u.setNome(modNome);

                        String modCognome = gestisciInput("inserisci il tuo nuovo cognome. Premi invio per lasciarlo invariato", s, false);
                        if (modCognome.isBlank()) {
                            System.out.println("dato non modificato");
                        } else
                            u.setCognome(modCognome);

                        String modUserName = gestisciInput("inserisci il tuo nuovo username. Premi invio per lasciarlo invariato", s, false);
                        if (modUserName.isBlank()) {
                            System.out.println("dato non modificato");
                        } else {
                            boolean usernameEsistente = false;
                            for (Utente u1 : GestoreFile.caricaUtenti(pathUtenti, pathRistoranti)) {
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

                        while (!passwordValida) {
                            String modPassword = gestisciInput("Inserisci la tua nuova password. Premi invio per lasciarla invariata: ", s, false);

                            if (modPassword.isBlank()) {
                                System.out.println("dato non modificato");
                                break;
                            }

                            if (modPassword.length() < 7) {
                                System.out.println("La password deve contenere almeno 7 caratteri.");
                                continue;
                            }

                            String pwCifrata = PasswordUtil.hashPassword(modPassword);

                            if (pwCifrata.equals(u.getPasswordCifrata())) {
                                System.out.println("La nuova password è uguale a quella attuale. Inserisci una password diversa.");
                            } else {
                                u.setPasswordCifrata(pwCifrata);
                                System.out.println("Password modificata con successo.");
                                passwordValida = true;
                            }
                        }

                        String modDomicilio = gestisciInput("inserisci il tuo nuovo domicilio. Premi invio per lasciarlo invariato", s, false);

                        if (modDomicilio.isBlank()) {
                            System.out.println("dato non modificato");
                        } else {
                            u.setDomicilio(modDomicilio);
                        }
                        //salvataggio dei dati modificati
                        List<Utente> listaUtentiTBS = GestoreFile.caricaUtenti(pathUtenti, pathRistoranti);
                        if (listaUtentiTBS == null) {
                            System.out.println("Impossibile salvare i dati: lista utenti non disponibile");
                        } else {
                            //salvataggio dati
                            listaUtentiTBS.remove(u);
                            listaUtentiTBS.add(u);
                            GestoreFile.salvaUtenti(listaUtentiTBS, pathUtenti);
                        }
                    }

                    case 5 -> {
                        System.out.println("Verrai reinderizzato al menù iniziale!");
                        ViewBase.view(pathUtenti, pathRistoranti);
                        return;
                    }

                    default -> System.out.println("Inserire una scelta valida.");
                }
            }
        }
    }

}
