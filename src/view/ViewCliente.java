package src.view;

import src.controller.Main;
import src.dao.GestoreFile;
import src.model.Cliente;
import src.model.Recensione;
import src.model.Ristorante;
import src.model.Utente;
import src.model.exception.RecensioneOutOfBoundException;
import src.model.exception.StelleOutOfBoundException;
import src.model.util.PasswordUtil;
import src.model.util.ReverseGeocoding;

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
     * @paaram PATHRISTORANTI il path del file JSON contenente i ristoranti
     * @return
     */

    public boolean verificaRistorante(Ristorante r, String PATHRISTORANTI) {
        boolean esiste = false;
        try {
            for (Ristorante tmp : GestoreFile.caricaRistoranti(PATHRISTORANTI))
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
     * metodo privato per ottenere una stringa descrizione, possibile attributo di un oggetto di tipo Recensione, in maniera corretta (<250 caratteri)
     * @param s lo scanner utilizzato per leggere l'input dell'utente
     * @return la stringa da massimo 250 caratteri che può rappresentare il testo della recensione
     */

    private static String leggiDescrizioneValida(Scanner s) {
        while (true) {
            String input = gestisciInput("Scrivi la descrizione (max 250 caratteri):", s, true);
            if (input.length() <= 250) {
                return input;
            } else {
                System.out.println("Errore: la descrizione non può superare 250 caratteri.");
            }
        }
    }

    /**
     * metodo privato per ottenere un int stelle, possibile attributo di un oggetto di tipo Recensione, in maniera corretta (tra 0 e 5)
     * @param s lo scanner utilizzato per leggere l'input dell'utente
     * @return l'int che può rappresentare le stelle di una recensione (da 0 a 5)
     */

    private static int leggiStelleValide(Scanner s) {
        while (true) {
            int stelle = ViewBase.convertiScannerIntero("Inserisci il numero di stelle (0-5):", s);
            if (stelle >= 0 && stelle <= 5) {
                return stelle;
            } else {
                System.out.println("Errore: inserisci un numero tra 0 e 5.");
            }
        }
    }



    /**
     * metodo per visualizzare, interagire e scorrere dinamicamente i ristoranti
     *
     * @param u               l'utente che interagisce col ristorante e che può lasciare una recensione
     * @param s               lo scanner che permette all'utente di lasciare una recensione se lo richiede (stelle e testo)
     * @param pathUtenti il path del file JSON contenente gli utenti
     * @param PATHRISTORANTI il path del file JSON contenente i ristoranti
     * @param listaRistoranti la lista di ristoranti che si vuole scorrere
     */

    private static void navigazioneRistoranti(Cliente u, Scanner s, List<Ristorante> listaRistoranti, String pathUtenti, String PATHRISTORANTI) throws IOException, InterruptedException {
        if (listaRistoranti == null || listaRistoranti.isEmpty()) {
            System.out.println("Nessun ristorante trovato");
            return;
        }

        //liste utenti e ristoranti aggiornate su cui vengono effettuate tutte le modifiche in esecuzione
        List<Utente> listaUtentiTBS = GestoreFile.caricaUtenti(pathUtenti, PATHRISTORANTI);
        List<Ristorante> listaristorantiTBS = GestoreFile.caricaRistoranti(PATHRISTORANTI);
        if (listaUtentiTBS == null || listaristorantiTBS == null) {
            System.out.println("Errore durante il caricamento dei dati");
            return;
        }


        int indice = 0;
        boolean visualizza = true;

        while (visualizza) {
            Main.svuotaConsole();
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
                        6. Visualizza e modifica le recensioni che hai lasciato al ristorante 
                        7. Visualizza tutte le recensioni lasciate al ristorante 
                        8. Esci dalla visualizzazione
                    """);

            int sceltaInterna = ViewBase.convertiScannerIntero("Scelta:", s);

            switch (sceltaInterna) {
                case 1:
                    System.out.println("Scrivi la tua recensione:");
                    String descrizione = leggiDescrizioneValida(s);
                    int stelle = leggiStelleValide(s);
                    u.aggiungiRecensione(ristoranteCorrente, stelle, descrizione);
                    System.out.println("Recensione aggiunta con successo!");
                    //salvataggio dati
                    listaUtentiTBS.remove(u);
                    listaUtentiTBS.add(u);
                    GestoreFile.salvaUtenti(listaUtentiTBS, pathUtenti);
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
                    System.out.println("Ristorante aggiunto ai preferiti");
                    u.aggiungiPreferito(ristoranteCorrente);
                    //salvataggio dati
                    listaUtentiTBS.remove(u);
                    listaUtentiTBS.add(u);
                    GestoreFile.salvaUtenti(listaUtentiTBS, pathUtenti);

                    break;

                case 5:
                    System.out.println("Ristorante rimosso dai preferiti");
                    u.rimuoviPreferito(ristoranteCorrente);
                    //salvataggio dati
                    listaUtentiTBS.remove(u);
                    listaUtentiTBS.add(u);
                    GestoreFile.salvaUtenti(listaUtentiTBS, pathUtenti);

                    break;

                case 6:
                    List<Recensione> recensioniUtente = new ArrayList<>();

                    // Filtra solo le recensioni dell'utente relative a ristoranteCorrente
                    for (Recensione r : ristoranteCorrente.getRecensioni()) {
                        if (u.getRecensioniMesse().contains(r)) {
                            recensioniUtente.add(r);
                        }
                    }

                    if (recensioniUtente.isEmpty()) {
                        System.out.println("Non hai lasciato recensioni per questo ristorante.");
                        break;
                    }

                    System.out.println("Le tue recensioni per questo ristorante:");
                    for (int i = 0; i < recensioniUtente.size(); i++) {
                        System.out.println((i + 1) + ". " + recensioniUtente.get(i));
                    }

                    int sceltaRec = ViewBase.convertiScannerIntero("Inserisci il numero della recensione da modificare:", s);

                    if (sceltaRec < 1 || sceltaRec > recensioniUtente.size()) {
                        System.out.println("Scelta non valida.");
                        break;
                    }

                    Recensione daModificare = recensioniUtente.get(sceltaRec - 1);

                    String nuovoTesto = leggiDescrizioneValida(s);

                    int nuoveStelle = leggiStelleValide(s);

                    u.modificaRecensione(daModificare, nuovoTesto, nuoveStelle);


                    // salva modifiche
                    listaUtentiTBS.remove(u);
                    listaUtentiTBS.add(u);
                    listaristorantiTBS.remove(ristoranteCorrente);
                    listaristorantiTBS.add(ristoranteCorrente);
                    GestoreFile.salvaUtenti(listaUtentiTBS, pathUtenti);
                    GestoreFile.salvaRistoranti(listaristorantiTBS, PATHRISTORANTI);
                    System.out.println("Recensione modificata con successo!");


                    break;

                case 7:
                    //recensioni del ristorante corrente
                    List<Recensione> recensioni = ristoranteCorrente.getRecensioni();
                    if (recensioni.isEmpty()) {
                        System.out.println("Nessuna recensione per questo ristorante.");
                    } else {
                        System.out.println("Recensioni per \"" + ristoranteCorrente.getNome() + "\":\n");

                        // Carichiamo tutti gli utenti per confrontare le loro recensioni
                        List<Utente> tuttiGliUtenti = GestoreFile.caricaUtenti(pathUtenti, PATHRISTORANTI);

                        for (Recensione r : recensioni) {
                            String autore = "Utente sconosciuto";

                            // Cerchiamo quale utente ha questa recensione tra le sue
                            for (Utente u1 : tuttiGliUtenti) {
                                if (u1 instanceof Cliente cliente) {
                                    for (Recensione rUtente : cliente.getRecensioniMesse()) {
                                        if (rUtente.getId() == r.getId()) {
                                            autore = cliente.getUsername();
                                            break;
                                        }
                                    }
                                }
                                if (!autore.equals("Utente sconosciuto")) break;
                            }

                            // Stampa dettagli della recensione
                            System.out.println("Utente: " + autore);
                            System.out.println("Stelle: " + r.getStelle());
                            System.out.println("Descrizione: " + r.getDescrizione());
                            System.out.println("-----");
                        }
                    }
                    break;


                case 8:
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
     * @param pathUtenti il path del file JSON contenente gli utenti
     * @param PATHRISTORANTI il path del file JSON contenente i ristoranti
     */

    public static void view(Cliente u, String pathUtenti, String PATHRISTORANTI) throws IOException {
        try (Scanner s = new Scanner(System.in)) {
            boolean continua = true;

            while (continua) {
                Main.svuotaConsole();
                //caricamento lista aggiornata dei ristoranti
                List<Ristorante> listaRistoranti = GestoreFile.caricaRistoranti(PATHRISTORANTI);
                //menù di navigazione iniziale
                System.out.println("\n--- Menù Cliente ---");
                System.out.println("1. Visualizza tutti i ristoranti");
                System.out.println("2. Cerca ristoranti filtrando per parametri");
                System.out.println("3. Visualizza i tuoi dati personali");
                System.out.println("4. Modifica dati personali");
                System.out.println("5. Visualizza le recensioni lasciate");
                System.out.println("6. Logout");


                int scelta = ViewBase.convertiScannerIntero("Scegli un'opzione:", s);

                switch (scelta) {
                    case 1:
                        if (listaRistoranti.isEmpty()) {
                            System.out.println("Nessun ristorante trovato.");
                        }

                        navigazioneRistoranti(u, s, listaRistoranti, pathUtenti, PATHRISTORANTI);

                        break;


                    case 2:
                        List<Ristorante> Ristoranti = GestoreFile.caricaRistoranti(PATHRISTORANTI);
                        System.out.println("Inserisci i parametri di ricerca:");
                        String locazione;
                        do {
                            locazione = gestisciInput("Inserisci la locazione. Parametro obbligatorio:", s, false);
                            if (locazione.isEmpty()) {
                                System.err.println("La locazione è obbligatoria!");
                            } else {
                                double[] latLon = ReverseGeocoding.getLatitudineLongitudine(locazione);
                                if (latLon[0] == -1 && latLon[1] == -1) {
                                    locazione = "";
                                    System.out.println("Locazione non valida, riprova! Assicurati di inserire un luogo esistente.");
                                }
                            }
                        } while (locazione.isEmpty());
                        System.out.println("Inserisci il tipo di cucina (premi invio per saltare):");
                        String tipoCucina = s.nextLine();
                        double minPrezzo = leggiDouble(s, "Prezzo minimo (0 per ignorare):");
                        double maxPrezzo = leggiDouble(s, "Prezzo massimo (0 per ignorare):");
                        if (maxPrezzo > 0 && minPrezzo > maxPrezzo) {
                            System.err.println("Attenzione: il prezzo minimo supera il massimo. Imposto entrambi a 0.");
                            minPrezzo = 0;
                            maxPrezzo = 0;
                        }
                        System.out.println("Vuoi includere il filtro delivery? (si/no - predefinito: no):");
                        boolean conDelivery = s.nextLine().equalsIgnoreCase("si");
                        System.out.println("Vuoi includere solo ristoranti con servizio delivery? (si/no - predefinito: no):");
                        boolean filtroDelivery = s.nextLine().equalsIgnoreCase("si");
                        System.out.println("Vuoi includere il filtro prenotazione? (si/no - predefinito: no):");
                        boolean conPrenotazione = s.nextLine().equalsIgnoreCase("si");
                        System.out.println("Vuoi includere solo ristoranti con servizio prenotazione? (si/no - predefinito: no):");
                        boolean filtroPrenotazione = s.nextLine().equalsIgnoreCase("si");
                        int minStelle = ViewBase.convertiScannerIntero("Numero minimo di stelle (0-5, default: 0):", s);
                        if (minStelle < 0 || minStelle > 5) {
                            System.out.println("Valore non valido per le stelle. Impostato a 0.");
                            minStelle = 0;
                        }
                        List<Ristorante> filtrati = Ristorante.combinata(
                                listaRistoranti, locazione, tipoCucina, minPrezzo, maxPrezzo,
                                conDelivery, filtroDelivery, conPrenotazione, filtroPrenotazione, minStelle
                        );

                        navigazioneRistoranti(u, s, filtrati, pathUtenti, PATHRISTORANTI);

                        break;



                    case 3:
                        GestoreFile.caricaUtenti(pathUtenti, PATHRISTORANTI);
                        System.out.println("\n--- Dati utente ---");
                        System.out.println("Nome: " + (u.getNome() != null ? u.getNome() : "Non specificato"));
                        System.out.println("Cognome: " + (u.getCognome() != null ? u.getCognome() : "Non specificato"));
                        System.out.println("Username: " + (u.getUsername() != null ? u.getUsername() : "Non specificato"));
                        System.out.println("Data di nascita: " + (u.getDataNascita() != null ? u.getDataNascita() : "Non specificata"));
                        System.out.println("Domicilio: " + (u.getDomicilio() != null ? u.getDomicilio() : "Non specificato"));
                        System.out.println("Tipo utente: " + (u instanceof Cliente ? "Cliente" : "Ristoratore"));
                        break;

                    case 4:
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
                            for (Utente u1 : GestoreFile.caricaUtenti(pathUtenti, PATHRISTORANTI)) {
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
                        List<Utente> listaUtentiTBS = GestoreFile.caricaUtenti(pathUtenti, PATHRISTORANTI);
                        if (listaUtentiTBS == null) {
                            System.out.println("Impossibile salvare i dati: lista utenti non disponibile");
                        } else {
                            //salvataggio dati
                            listaUtentiTBS.remove(u);
                            listaUtentiTBS.add(u);
                            GestoreFile.salvaUtenti(listaUtentiTBS, pathUtenti);
                        }
                        break;

                    case 5:
                        GestoreFile.caricaUtenti(pathUtenti, PATHRISTORANTI);
                        GestoreFile.caricaRistoranti(PATHRISTORANTI);
                        List<Recensione> tutteRecensioni = u.getRecensioniMesse();

                        if (tutteRecensioni.isEmpty()) {
                            System.out.println("Non hai ancora lasciato recensioni.");
                            break;
                        }

                        System.out.println("\n--- Le tue recensioni ---");

                        List<Ristorante> tuttiRistoranti = GestoreFile.caricaRistoranti(PATHRISTORANTI);

                        for (int i = 0; i < tutteRecensioni.size(); i++) {
                            Recensione rec = tutteRecensioni.get(i);
                            String nomeRistorante = "Ristorante sconosciuto";

                            // For annidati per cercare la recensione nei ristoranti
                            for (Ristorante r : tuttiRistoranti) {
                                for (Recensione rRec : r.getRecensioni()) {
                                    if (rRec.getId() == rec.getId()) {
                                        nomeRistorante = r.getNome();
                                        break;
                                    }
                                }
                            }

                            System.out.println((i + 1) + ". [" + nomeRistorante + "] " + rec);
                        }


                        int sceltaMod = ViewBase.convertiScannerIntero("Inserisci il numero della recensione da modificare (0 per annullare):", s);

                        if (sceltaMod == 0) {
                            System.out.println("Modifica annullata.");
                            break;
                        }

                        if (sceltaMod < 1 || sceltaMod > tutteRecensioni.size()) {
                            System.out.println("Scelta non valida.");
                            break;
                        }

                        Recensione recDaModificare = tutteRecensioni.get(sceltaMod - 1);

                        String nuovoTesto = leggiDescrizioneValida(s);
                        int nuoveStelle = leggiStelleValide(s);

                        u.modificaRecensione(recDaModificare, nuovoTesto, nuoveStelle);
                        System.out.println("Recensione modificata con successo.");

                        // Salvataggio
                        List<Utente> listaUtentiTBS2 = GestoreFile.caricaUtenti(pathUtenti, PATHRISTORANTI);
                        List<Ristorante> listaRistorantiTBS2 = GestoreFile.caricaRistoranti(PATHRISTORANTI);
                        listaUtentiTBS2.remove(u);
                        listaUtentiTBS2.add(u);

                        for (Ristorante r : listaRistorantiTBS2) {
                            if (r.getRecensioni().contains(recDaModificare)) {
                                r.modificaRecensione(recDaModificare, nuovoTesto, nuoveStelle); // supponendo esista
                                break;
                            }
                        }

                        GestoreFile.salvaUtenti(listaUtentiTBS2, pathUtenti);
                        GestoreFile.salvaRistoranti(listaRistorantiTBS2, PATHRISTORANTI);

                        break;


                    case 6:
                        System.out.println("Verrai reinderizzato al menù iniziale!");
                        ViewBase.view(pathUtenti, PATHRISTORANTI);
                        return;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
