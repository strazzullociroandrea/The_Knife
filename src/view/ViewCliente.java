package src.view;

import src.controller.Main;
import src.dao.GestoreFile;
import src.model.Cliente;
import src.model.Recensione;
import src.model.Ristorante;
import src.model.Utente;
import src.model.util.ReverseGeocoding;

import java.io.IOException;
import java.util.ArrayList;
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
public class ViewCliente {

    /**
     * metodo per verificare che un ristorante esista all'interno di una lista
     */
    public boolean verificaRistorante(Ristorante r, String PATHRISTORANTI) {
        boolean esiste = false;
        try {
            List<Ristorante> ristoranti = GestoreFile.caricaRistoranti(PATHRISTORANTI);
            for (int i = 0; i < ristoranti.size(); i++) {
                Ristorante tmp = ristoranti.get(i);
                if (tmp.equals(r))
                    esiste = true;
            }
        } catch (IOException e) {
            throw new RuntimeException("Ristorante non trovato");
        }
        return esiste;
    }

    /**
     * Metodo privato per gestire l'input da parte dell'utente, richiedendo di inserire un informazione fino a quando non è diversa da stringa vuota
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
     */
    private static String leggiDescrizioneValida(Scanner s) {
        while (true) {
            String input = gestisciInput("Scrivi la descrizione (max 250 caratteri):", s, true);
            if (input.length() <= 250) {
                return input;
            } else if (input.isEmpty()) {
                return "";
            } else {
                System.out.println("Errore: la descrizione deve essere lunga al massimo 250 caratteri.");
            }
        }
    }

    /**
     * metodo privato per ottenere un int stelle, possibile attributo di un oggetto di tipo Recensione, in maniera corretta (tra 0 e 5)
     */
    private static int leggiStelleValide(Scanner s) {
        while (true) {
            int stelle = ViewBase.convertiScannerIntero("Inserisci il numero di stelle (0-5)", s);
            if (stelle >= 0 && stelle <= 5) {
                return stelle;
            } else {
                System.out.println("Errore: inserisci un numero tra 0 e 5.");
            }
        }
    }

    /**
     * metodo per visualizzare, interagire e scorrere dinamicamente i ristoranti
     */
    private static void navigazioneRistoranti(Cliente u, Scanner s, List<Ristorante> listaRistoranti, String pathUtenti, String PATHRISTORANTI) throws IOException, InterruptedException {
        if (listaRistoranti == null || listaRistoranti.isEmpty()) {
            System.out.println("Nessun ristorante trovato");
            return;
        }

        List<Utente> listaUtentiTBS = caricaUtenti(pathUtenti, PATHRISTORANTI);
        List<Ristorante> listaristorantiTBS = GestoreFile.caricaRistoranti(PATHRISTORANTI);
        if (listaUtentiTBS == null || listaristorantiTBS == null) {
            System.out.println("Errore durante il caricamento dei dati");
            return;
        }

        int indice = 0;
        boolean visualizza = true;

        while (visualizza) {
            if (listaRistoranti == null || listaRistoranti.isEmpty()) {
                System.out.println("Non ci sono più ristoranti da visualizzare.");
                break;
            }

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
                    // AGGIUNTA RECENSIONE: aggiorna anche in tutti i riferimenti
                    System.out.println("Scrivi la tua recensione:");
                    String descrizione = leggiDescrizioneValida(s);
                    int stelle = leggiStelleValide(s);
                    Recensione nuovaRecensione = null;
                    try {
                        u.aggiungiRecensione(ristoranteCorrente, stelle, descrizione);
                        List<Recensione> recMesse = u.getRecensioniMesse();
                        if (recMesse.size() > 0) {
                            nuovaRecensione = recMesse.get(recMesse.size() - 1);
                        }
                    } catch (Exception e) {
                        System.out.println("Errore durante l'aggiunta della recensione: " + e.getMessage());
                        break;
                    }
                    if (nuovaRecensione != null) {
                        // Aggiorna nel ristorante effettivo (ogni copia reale)
                        for (int i = 0; i < listaristorantiTBS.size(); i++) {
                            Ristorante r = listaristorantiTBS.get(i);
                            if (r.getId() == ristoranteCorrente.getId()) {
                                List<Recensione> recs = r.getRecensioni();
                                boolean giaPresente = false;
                                for (int j = 0; j < recs.size(); j++) {
                                    if (recs.get(j).getId() == nuovaRecensione.getId()) {
                                        giaPresente = true;
                                    }
                                }
                                if (!giaPresente) {
                                    recs.add(nuovaRecensione);
                                }
                            }
                        }
                        // Aggiorna in tutti i preferiti dei clienti
                        for (int i = 0; i < listaUtentiTBS.size(); i++) {
                            Utente ut = listaUtentiTBS.get(i);
                            if (ut instanceof Cliente) {
                                Cliente cliente = (Cliente) ut;
                                List<Ristorante> preferiti = cliente.visualizzaPreferiti();
                                for (int j = 0; j < preferiti.size(); j++) {
                                    Ristorante pref = preferiti.get(j);
                                    if (pref.getId() == ristoranteCorrente.getId()) {
                                        List<Recensione> recsPref = pref.getRecensioni();
                                        boolean giaPresente = false;
                                        for (int k = 0; k < recsPref.size(); k++) {
                                            if (recsPref.get(k).getId() == nuovaRecensione.getId()) {
                                                giaPresente = true;
                                            }
                                        }
                                        if (!giaPresente) {
                                            recsPref.add(nuovaRecensione);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // Salvataggio immediato
                    for (int i = 0; i < listaUtentiTBS.size(); i++) {
                        if (listaUtentiTBS.get(i).getUsername().equals(u.getUsername())) {
                            listaUtentiTBS.remove(i);
                            break;
                        }
                    }
                    listaUtentiTBS.add(u);
                    GestoreFile.salvaUtenti(listaUtentiTBS, pathUtenti);
                    for (int i = 0; i < listaristorantiTBS.size(); i++) {
                        if (listaristorantiTBS.get(i).getId() == ristoranteCorrente.getId()) {
                            listaristorantiTBS.remove(i);
                            break;
                        }
                    }
                    listaristorantiTBS.add(ristoranteCorrente);
                    GestoreFile.salvaRistoranti(listaristorantiTBS, PATHRISTORANTI);
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
                    if (u.aggiungiPreferito(ristoranteCorrente)) {
                        System.out.println("Ristorante aggiunto ai preferiti.");
                        // Salvataggio immediato
                        for (int i = 0; i < listaUtentiTBS.size(); i++) {
                            if (listaUtentiTBS.get(i).getUsername().equals(u.getUsername())) {
                                listaUtentiTBS.remove(i);
                                break;
                            }
                        }
                        listaUtentiTBS.add(u);
                        GestoreFile.salvaUtenti(listaUtentiTBS, pathUtenti);
                    } else {
                        System.out.println("Il ristorante è già nei preferiti.");
                    }
                    break;

                case 5:
                    if (u.rimuoviPreferito(ristoranteCorrente)) {
                        System.out.println("Ristorante rimosso dai preferiti.");
                        for (int i = 0; i < listaUtentiTBS.size(); i++) {
                            if (listaUtentiTBS.get(i).getUsername().equals(u.getUsername())) {
                                listaUtentiTBS.remove(i);
                                break;
                            }
                        }
                        listaUtentiTBS.add(u);
                        GestoreFile.salvaUtenti(listaUtentiTBS, pathUtenti);
                    } else {
                        System.out.println("Il ristorante non era nei preferiti.");
                    }
                    break;

                case 6:
                    // Modifica recensione, aggiorna anche nel ristorante e nei preferiti di tutti i clienti
                    List<Recensione> recensioniUtente = new ArrayList<Recensione>();
                    List<Recensione> recRistorante = ristoranteCorrente.getRecensioni();
                    for (int i = 0; i < recRistorante.size(); i++) {
                        Recensione r = recRistorante.get(i);
                        if (u.getRecensioniMesse().contains(r)) {
                            recensioniUtente.add(r);
                        }
                    }

                    if (recensioniUtente.isEmpty()) {
                        System.out.println("Non hai lasciato recensioni per questo ristorante.");
                        break;
                    }

                    while (true) {
                        System.out.println("\n--- Le tue recensioni per questo ristorante ---");
                        for (int i = 0; i < recensioniUtente.size(); i++) {
                            System.out.println((i + 1) + ". " + recensioniUtente.get(i));
                        }
                        System.out.println("0. Torna indietro");

                        int sceltaRec = ViewBase.convertiScannerIntero("Inserisci il numero della recensione da modificare (0 per uscire):", s);
                        if (sceltaRec == 0) break;
                        if (sceltaRec < 1 || sceltaRec > recensioniUtente.size()) {
                            System.out.println("Scelta non valida. Riprova.");
                            continue;
                        }

                        Recensione daModificare = recensioniUtente.get(sceltaRec - 1);

                        System.out.println("Lascia vuoto per mantenere la descrizione attuale:");
                        s.nextLine();
                        String nuovoTesto = s.nextLine().trim();
                        if (nuovoTesto.isEmpty()) nuovoTesto = daModificare.getDescrizione();

                        System.out.println("Inserisci le nuove stelle (0 per mantenere quelle attuali):");
                        int nuoveStelle = ViewBase.convertiScannerIntero("Stelle:", s);
                        if (nuoveStelle <= 0 || nuoveStelle > 5) nuoveStelle = daModificare.getStelle();

                        // Modifica in recensioniMesse
                        u.modificaRecensione(daModificare, nuovoTesto, nuoveStelle);

                        // Modifica nel ristorante effettivo
                        for (int i = 0; i < listaristorantiTBS.size(); i++) {
                            Ristorante r = listaristorantiTBS.get(i);
                            if (r.getId() == ristoranteCorrente.getId()) {
                                List<Recensione> recRist = r.getRecensioni();
                                for (int j = 0; j < recRist.size(); j++) {
                                    if (recRist.get(j).getId() == daModificare.getId()) {
                                        r.modificaRecensione(recRist.get(j), nuovoTesto, nuoveStelle);
                                        break;
                                    }
                                }
                            }
                        }
                        // Modifica in tutti i preferiti di tutti i clienti
                        for (int i = 0; i < listaUtentiTBS.size(); i++) {
                            Utente ut = listaUtentiTBS.get(i);
                            if (ut instanceof Cliente) {
                                Cliente cliente = (Cliente) ut;
                                List<Ristorante> preferiti = cliente.visualizzaPreferiti();
                                for (int j = 0; j < preferiti.size(); j++) {
                                    Ristorante pref = preferiti.get(j);
                                    if (pref.getId() == ristoranteCorrente.getId()) {
                                        List<Recensione> recsPref = pref.getRecensioni();
                                        for (int k = 0; k < recsPref.size(); k++) {
                                            if (recsPref.get(k).getId() == daModificare.getId()) {
                                                pref.modificaRecensione(recsPref.get(k), nuovoTesto, nuoveStelle);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        // Salvataggio immediato
                        for (int i = 0; i < listaUtentiTBS.size(); i++) {
                            if (listaUtentiTBS.get(i).getUsername().equals(u.getUsername())) {
                                listaUtentiTBS.remove(i);
                                break;
                            }
                        }
                        listaUtentiTBS.add(u);
                        GestoreFile.salvaUtenti(listaUtentiTBS, pathUtenti);
                        for (int i = 0; i < listaristorantiTBS.size(); i++) {
                            if (listaristorantiTBS.get(i).getId() == ristoranteCorrente.getId()) {
                                listaristorantiTBS.remove(i);
                                break;
                            }
                        }
                        listaristorantiTBS.add(ristoranteCorrente);
                        GestoreFile.salvaRistoranti(listaristorantiTBS, PATHRISTORANTI);

                        System.out.println("Recensione modificata con successo!");
                    }
                    break;

                case 7:
                    List<Recensione> recensioni = ristoranteCorrente.getRecensioni();
                    if (recensioni.isEmpty()) {
                        System.out.println("Nessuna recensione per questo ristorante.");
                    } else {
                        System.out.println("Recensioni per \"" + ristoranteCorrente.getNome() + "\":\n");
                        List<Utente> tuttiGliUtenti = caricaUtenti(pathUtenti, PATHRISTORANTI);
                        for (int i = 0; i < recensioni.size(); i++) {
                            Recensione r = recensioni.get(i);
                            String autore = "Utente sconosciuto";
                            for (int j = 0; j < tuttiGliUtenti.size(); j++) {
                                Utente u1 = tuttiGliUtenti.get(j);
                                if (u1 instanceof Cliente) {
                                    Cliente cliente = (Cliente) u1;
                                    List<Recensione> recUtente = cliente.getRecensioniMesse();
                                    for (int k = 0; k < recUtente.size(); k++) {
                                        Recensione rUtente = recUtente.get(k);
                                        if (rUtente.getId() == r.getId()) {
                                            autore = cliente.getUsername();
                                            break;
                                        }
                                    }
                                }
                                if (!autore.equals("Utente sconosciuto")) break;
                            }
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
     */
    public static void view(Cliente u, String pathUtenti, String PATHRISTORANTI) throws IOException {
        try (Scanner s = new Scanner(System.in)) {
            boolean continua = true;

            while (continua) {
                Main.svuotaConsole();
                List<Ristorante> listaRistoranti = GestoreFile.caricaRistoranti(PATHRISTORANTI);

                System.out.println("\n--- Menù Cliente ---");
                System.out.println("1. Visualizza tutti i ristoranti");
                System.out.println("2. Cerca ristoranti filtrando per parametri");
                System.out.println("3. Visualizza i tuoi dati personali");
                System.out.println("4. Modifica dati personali");
                System.out.println("5. Visualizza le recensioni lasciate");
                System.out.println("6. Visualizza i ristoranti preferiti");
                System.out.println("7. Logout");

                int scelta = ViewBase.convertiScannerIntero("Scegli un'opzione:", s);

                switch (scelta) {
                    case 1:
                        if (listaRistoranti == null || listaRistoranti.isEmpty()) {
                            System.out.println("Nessun ristorante trovato.");
                            break;
                        }
                        navigazioneRistoranti(u, s, listaRistoranti, pathUtenti, PATHRISTORANTI);
                        break;
                    case 2:
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
                                    System.out.println("Locazione non valida, riprova!");
                                }
                            }
                        } while (locazione.isEmpty());

                        System.out.println("Inserisci il tipo di cucina (premi invio per saltare):");
                        String tipoCucina = s.nextLine();

                        double minPrezzo = leggiDouble(s, "Prezzo minimo (0 per ignorare):");
                        double maxPrezzo = leggiDouble(s, "Prezzo massimo (0 per ignorare):");
                        if (maxPrezzo > 0 && minPrezzo > maxPrezzo) {
                            System.err.println("Prezzo minimo superiore al massimo. Entrambi azzerati.");
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

                        int minStelle = ViewBase.convertiScannerIntero("Numero minimo di stelle (0-5):", s);
                        if (minStelle < 0 || minStelle > 5) {
                            System.out.println("Valore stelle non valido. Impostato a 0.");
                            minStelle = 0;
                        }

                        List<Ristorante> filtrati = Ristorante.combinata(
                                listaRistoranti, locazione, tipoCucina, minPrezzo, maxPrezzo,
                                conDelivery, filtroDelivery, conPrenotazione, filtroPrenotazione, minStelle
                        );

                        navigazioneRistoranti(u, s, filtrati, pathUtenti, PATHRISTORANTI);
                        break;
                    case 3:
                        System.out.println("\n--- Dati utente ---");
                        System.out.println("Nome: " + (u.getNome() != null ? u.getNome() : "Non specificato"));
                        System.out.println("Cognome: " + (u.getCognome() != null ? u.getCognome() : "Non specificato"));
                        System.out.println("Username: " + (u.getUsername() != null ? u.getUsername() : "Non specificato"));
                        System.out.println("Data di nascita: " + (u.getDataNascita() != null ? u.getDataNascita() : "Non specificata"));
                        System.out.println("Domicilio: " + (u.getDomicilio() != null ? u.getDomicilio() : "Non specificato"));
                        System.out.println("Tipo utente: Cliente");
                        break;
                    case 4:
                        String vecchioUsername = u.getUsername();

                        String modNome = gestisciInput("inserisci il tuo nuovo nome. Premi invio per lasciarlo invariato", s, false);
                        if (!modNome.isBlank()) u.setNome(modNome);

                        String modCognome = gestisciInput("inserisci il tuo nuovo cognome. Premi invio per lasciarlo invariato", s, false);
                        if (!modCognome.isBlank()) u.setCognome(modCognome);

                        String modUserName = gestisciInput("inserisci il tuo nuovo username. Premi invio per lasciarlo invariato", s, false);
                        if (!modUserName.isBlank()) {
                            boolean usernameEsistente = false;
                            List<Utente> utenti = caricaUtenti(pathUtenti, PATHRISTORANTI);
                            for (int i = 0; i < utenti.size(); i++) {
                                Utente u1 = utenti.get(i);
                                if (modUserName.equals(u1.getUsername())) {
                                    System.out.println("Username già esistente.");
                                    usernameEsistente = true;
                                    break;
                                }
                            }
                            if (!usernameEsistente) u.setUsername(modUserName);
                        }

                        boolean passwordValida = false;
                        while (!passwordValida) {
                            String modPassword = gestisciInput("Inserisci la tua nuova password. Premi invio per lasciarla invariata: ", s, false);
                            if (modPassword.isBlank()) break;
                            if (modPassword.length() < 7) {
                                System.out.println("Password troppo corta.");
                                continue;
                            }
                            u.setPasswordCifrata(modPassword);
                            System.out.println("Password aggiornata.");
                            passwordValida = true;
                        }

                        String modDomicilio = gestisciInput("inserisci il tuo nuovo domicilio. Premi invio per lasciarlo invariato", s, false);
                        if (!modDomicilio.isBlank()) u.setDomicilio(modDomicilio);

                        List<Utente> listaUtentiTBS = caricaUtenti(pathUtenti, PATHRISTORANTI);
                        for (int i = 0; i < listaUtentiTBS.size(); i++) {
                            if (listaUtentiTBS.get(i).getUsername().equals(vecchioUsername)) {
                                listaUtentiTBS.remove(i);
                                break;
                            }
                        }
                        listaUtentiTBS.add(u);
                        GestoreFile.salvaUtenti(listaUtentiTBS, pathUtenti);
                        break;
                    case 5:
                        List<Recensione> tutteRecensioni = u.getRecensioniMesse();
                        if (tutteRecensioni == null || tutteRecensioni.isEmpty()) {
                            System.out.println("Non hai lasciato recensioni.");
                            break;
                        }

                        while (true) {
                            System.out.println("\n--- Le tue recensioni ---");
                            for (int i = 0; i < tutteRecensioni.size(); i++) {
                                Recensione rec = tutteRecensioni.get(i);
                                System.out.println((i + 1) + ". " + rec);
                            }
                            System.out.println("0. Torna al menu");

                            int sceltaMod = ViewBase.convertiScannerIntero("Numero recensione da modificare (0 per annullare):", s);
                            if (sceltaMod == 0) break;
                            if (sceltaMod < 1 || sceltaMod > tutteRecensioni.size()) {
                                System.out.println("Scelta non valida.");
                                continue;
                            }

                            Recensione recDaModificare = tutteRecensioni.get(sceltaMod - 1);
                            String nuovoTesto = leggiDescrizioneValida(s);
                            if (nuovoTesto.isEmpty()) nuovoTesto = recDaModificare.getDescrizione();

                            int nuoveStelle = leggiStelleValide(s);
                            u.modificaRecensione(recDaModificare, nuovoTesto, nuoveStelle);

                            List<Utente> listaUtentiTBS2 = caricaUtenti(pathUtenti, PATHRISTORANTI);
                            List<Ristorante> listaRistorantiTBS2 = GestoreFile.caricaRistoranti(PATHRISTORANTI);

                            for (int i = 0; i < listaUtentiTBS2.size(); i++) {
                                if (listaUtentiTBS2.get(i).getUsername().equals(u.getUsername())) {
                                    listaUtentiTBS2.remove(i);
                                    break;
                                }
                            }
                            listaUtentiTBS2.add(u);

                            for (int i = 0; i < listaRistorantiTBS2.size(); i++) {
                                Ristorante r = listaRistorantiTBS2.get(i);
                                List<Recensione> recRist = r.getRecensioni();
                                for (int j = 0; j < recRist.size(); j++) {
                                    if (recRist.get(j).equals(recDaModificare)) {
                                        r.modificaRecensione(recRist.get(j), nuovoTesto, nuoveStelle);
                                        break;
                                    }
                                }
                            }
                            // Salvataggio immediato
                            GestoreFile.salvaUtenti(listaUtentiTBS2, pathUtenti);
                            GestoreFile.salvaRistoranti(listaRistorantiTBS2, PATHRISTORANTI);
                            System.out.println("Recensione aggiornata.");
                        }
                        break;
                    case 6:
                        List<Ristorante> preferiti = u.visualizzaPreferiti();
                        if (preferiti == null || preferiti.isEmpty()) {
                            System.out.println("Nessun ristorante preferito.");
                            break;
                        }
                        navigazioneRistoranti(u, s, preferiti, pathUtenti, PATHRISTORANTI);
                        break;
                    case 7:
                        System.out.println("Verrai reindirizzato al menù iniziale.");
                        ViewBase.view(pathUtenti, PATHRISTORANTI);
                        return;
                    default:
                        System.out.println("Scelta non valida.");
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }
}