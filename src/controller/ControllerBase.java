package src.controller;

import src.dao.GestoreFile;
import src.model.Cliente;
import src.model.Ristoratore;
import src.model.Utente;
import src.model.Ristorante;
import src.model.util.PasswordUtil;
import src.model.util.ReverseGeocoding;
import src.view.ViewBase;
import src.view.ViewCliente;
import src.view.ViewRistoratore;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ControllerBase {
    private List<Utente> utenti;
    private List<Ristorante> ristoranti;
    private String pathUtenti, pathRistoranti;

    public ControllerBase(String pathUtenti, String pathRistoranti) throws Exception {
        this.pathUtenti = pathUtenti;
        this.pathRistoranti = pathRistoranti;
        this.mostraMenuIniziale();
    }

    private void caricaDati() throws Exception {
        this.utenti = GestoreFile.caricaUtenti(this.pathUtenti, this.pathRistoranti);
        this.ristoranti = GestoreFile.caricaRistoranti(pathRistoranti);
    }

    private void mostraMenuIniziale() throws Exception {
        while (true) {
            ViewBase.mostraMenuIniziale();
            int scelta = ViewBase.leggiIntero("Scelta: ");
            switch (scelta) {
                case 1 -> gestisciLogin();
                case 2 -> gestisciRegistrazione();
                case 3 -> gestisciVisualizzazioneFiltro();
                case 4 -> {
                    ViewBase.mostraMessaggio("Uscita dal programma...");
                    return;
                }
                default -> ViewBase.mostraMessaggio("Scelta non valida. Riprova.");
            }
        }
    }

    private Utente trovaUtente(String username, String password) throws Exception {
        caricaDati();
        for (Utente u : utenti) {
            if (u.getUsername().equalsIgnoreCase(username) &&
                    u.getPasswordCifrata().equals(PasswordUtil.hashPassword(password))) {
                return u;
            }
        }
        return null;
    }

    private void gestisciLogin() throws Exception {
        ViewBase.mostraMessaggio("Login utente:");
        String email = ViewBase.leggiStringa("Email: ", true);
        String password = ViewBase.leggiStringa("Password: ", true);

        Utente utente = trovaUtente(email, password);
        if (utente != null) {
            ViewBase.mostraMessaggio("Login riuscito. Benvenuto, " + utente.getNome() + "!");
            Thread.sleep(100);
            Main.svuotaConsole();
            if (utente instanceof Cliente) {
                ViewCliente cliente = new ViewCliente((Cliente) utente, this.pathUtenti, this.pathRistoranti);
            } else if (utente instanceof Ristoratore) {
                ViewCliente ristoratore = new ViewCliente((Ristoratore) utente, this.pathUtenti, this.pathRistoranti);
            }
        } else {
            ViewBase.mostraMessaggio("Credenziali errate. Riprova.");
        }
    }

    private boolean giaPresente(String username) throws Exception {
        caricaDati();
        if (username != null && !username.isEmpty()) {
            for (Utente u : utenti) {
                if (u.getUsername().equalsIgnoreCase(username)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void gestisciRegistrazione() throws Exception {
        ViewBase.mostraMessaggio("Registrazione utente");

        String nome = ViewBase.leggiStringa("Nome: ", true);
        String cognome = ViewBase.leggiStringa("Cognome: ", true);

        String username;
        do {
            username = ViewBase.leggiStringa("Username: ", true);
            if (username.isEmpty()) {
                ViewBase.mostraMessaggio("L'username non può essere vuoto.");
            } else if (giaPresente(username)) {
                ViewBase.mostraMessaggio("Username già in uso, riprova.");
                username = "";
            }
        } while (username.isEmpty());

        String password, conferma;
        do {
            password = ViewBase.leggiStringa("Password (min 7 caratteri): ", true);
            if (password.length() < 7) {
                ViewBase.mostraMessaggio("Password troppo corta.");
                password = "";
            }
        } while (password.isEmpty());

        do {
            conferma = ViewBase.leggiStringa("Conferma password: ", true);
            if (!conferma.equals(password)) {
                ViewBase.mostraMessaggio("Le password non coincidono.");
                conferma = "";
            }
        } while (conferma.isEmpty());

        String dataNascita;
        do {
            dataNascita = ViewBase.leggiStringa("Data di nascita (dd/MM/yyyy), premi invio per saltare: ", false);
            try {
                LocalDate.parse(dataNascita, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                break;
            } catch (DateTimeParseException e) {
                ViewBase.mostraMessaggio("Formato data non valido.");
            }
        } while (true);

        String domicilio;
        do {
            domicilio = ViewBase.leggiStringa("Domicilio (via, città, nazione): ", true);
            double[] coord = ReverseGeocoding.getLatitudineLongitudine(domicilio);
            if (coord[0] == -1 && coord[1] == -1) {
                ViewBase.mostraMessaggio("Domicilio non valido.");
                domicilio = "";
            }
        } while (domicilio.isEmpty());

        String tipologia;
        do {
            tipologia = ViewBase.leggiStringa("Sei un cliente o un ristoratore? (c/r): ", true);
            if (!tipologia.equalsIgnoreCase("c") && !tipologia.equalsIgnoreCase("r")) {
                ViewBase.mostraMessaggio("Tipologia non valida. Inserisci 'c' o 'r'.");
                tipologia = "";
            }
        } while (tipologia.isEmpty());

        ViewBase.mostraMessaggio("Registrazione avvenuta con successo.");

        Thread.sleep(100);
        Main.svuotaConsole();
        Utente utente;
        if (tipologia.equalsIgnoreCase("c")) {
            if (dataNascita.isEmpty()) {
                utente = new Cliente(password, nome, cognome, username, domicilio);
            } else {
                utente = new Cliente(password, nome, cognome, username, dataNascita, domicilio);
            }
            this.utenti.add(utente);
            GestoreFile.salvaUtenti(this.utenti, this.pathUtenti);
            new ViewCliente((Cliente) utente, this.pathUtenti, this.pathRistoranti);
        } else if (tipologia.equalsIgnoreCase("r")) {
            if (dataNascita.isEmpty()) {
                utente = new Ristoratore(password, nome, cognome, username, domicilio);
            } else {
                utente = new Ristoratore(password, nome, cognome, username, dataNascita, domicilio);
            }
            this.utenti.add(utente);
            GestoreFile.salvaUtenti(this.utenti, this.pathUtenti);
            new ViewRistoratore((Ristoratore) utente, this.pathUtenti, this.pathRistoranti);
        }
    }

    private void visualizzaRistorantiVicini(String luogo) throws Exception {
        caricaDati();
        if (luogo.isEmpty() || ristoranti.isEmpty()) {
            ViewBase.mostraMessaggio("Luogo o elenco ristoranti vuoto!");
            return;
        }

        List<Ristorante> vicini = new ArrayList<>();
        for (Ristorante r : ristoranti) {
            if (r.isVicino(luogo)) {
                vicini.add(r);
            }
        }

        if (vicini.isEmpty()) {
            ViewBase.mostraMessaggio("Nessun ristorante trovato vicino a " + luogo + ".");
            return;
        }

        for (Ristorante r : vicini) {
            ViewBase.mostraRistorante(r);
            String continua;
            while (true) {
                continua = ViewBase.leggiStringa("Vuoi vedere un altro ristorante vicino? (s/n): ", true);
                if (continua.equalsIgnoreCase("s")) {
                    Main.svuotaConsole();
                    break;
                } else if (continua.equalsIgnoreCase("n")) {
                    ViewBase.mostraMessaggio("Uscita dalla visualizzazione dei ristoranti vicini...");
                    Thread.sleep(100);
                    Main.svuotaConsole();
                    return;
                } else {
                    ViewBase.mostraMessaggio("Risposta non valida. Inserisci 's' o 'n'.");
                }
            }
        }
    }

    private void gestisciVisualizzazioneFiltro() throws Exception {
        String luogo;
        do {
            luogo = ViewBase.leggiStringa("Inserisci il luogo per filtrare i ristoranti (formato 'via, città, nazione' o solo città): ", true);
            double[] coord = ReverseGeocoding.getLatitudineLongitudine(luogo);
            if (coord[0] == -1 && coord[1] == -1) {
                luogo = "";
                ViewBase.mostraMessaggio("Luogo non valido.");
            }
        } while (luogo.isEmpty());

        while (true) {
            ViewBase.mostraMenuGuest();
            int scelta = ViewBase.leggiIntero("Scelta: ");
            switch (scelta) {
                case 1 -> visualizzaRistorantiVicini(luogo);
                case 2 -> applicaFiltriRicerca(luogo);
                case 3 -> {
                    luogo = "";
                    while (luogo.isEmpty()) {
                        luogo = ViewBase.leggiStringa("Inserisci un nuovo luogo: ", true);
                        double[] coord = ReverseGeocoding.getLatitudineLongitudine(luogo);
                        if (coord[0] == -1 && coord[1] == -1) {
                            luogo = "";
                            ViewBase.mostraMessaggio("Luogo non valido.");
                        }
                    }
                    ViewBase.mostraMessaggio("Luogo modificato con successo.");
                }

                case 4 -> {
                    ViewBase.mostraMessaggio("Uscita dalla modalità guest...");
                    return;
                }
                default -> ViewBase.mostraMessaggio("Scelta non valida.");
            }
        }
    }

    private void applicaFiltriRicerca(String luogo) throws Exception {
        String tipoCucina, deliveryPresente = "", prenotazionePresente = "";
        double maxPrezzo, minPrezzo;
        boolean conDelivery, conPrenotazione, prezzoOk = false;
        int minStelle;
        tipoCucina = ViewBase.leggiStringa("Inserisci il tipo di cucina (premi invio per saltare): ", false);
        do {
            maxPrezzo = ViewBase.leggiDouble("Inserisci il prezzo massimo (premi invio per saltare): ", false);
            minPrezzo = ViewBase.leggiDouble("Inserisci il prezzo minimo (premi invio per saltare): ", false);
            if (maxPrezzo > minPrezzo) {
                prezzoOk = true;
            } else {
                ViewBase.mostraMessaggio("Il prezzo minimo non può essere maggiore del massimo. Riprova.");
            }
        } while (!prezzoOk);
        conDelivery = ViewBase.leggiStringa("Vuoi includere il filtro delivery? (si/no): ", false).equalsIgnoreCase("si");
        conPrenotazione = ViewBase.leggiStringa("Vuoi includere il filtro delivery? (si/no): ", false).equalsIgnoreCase("si");
        if (conDelivery) {
            do {
                deliveryPresente = ViewBase.leggiStringa("Vuoi includere  ristoranti con o senza servizio delivery? (con/senza): ", false);
                if (!deliveryPresente.equalsIgnoreCase("con") && !deliveryPresente.equalsIgnoreCase("senza")) {
                    ViewBase.mostraMessaggio("Risposta non valida. Inserisci 'con' o 'senza'.");
                    deliveryPresente = "";
                }
            } while (deliveryPresente.isEmpty());
        }
        if (conPrenotazione) {
            do {
                prenotazionePresente = ViewBase.leggiStringa("Vuoi includere  ristoranti con o senza servizio di prenotazione? (con/senza): ", false);
                if (!prenotazionePresente.equalsIgnoreCase("con") && !prenotazionePresente.equalsIgnoreCase("senza")) {
                    ViewBase.mostraMessaggio("Risposta non valida. Inserisci 'con' o 'senza'.");
                    prenotazionePresente = "";
                }
            } while (prenotazionePresente.isEmpty());
        }
        do {
            minStelle = ViewBase.leggiIntero("Inserisci il numero minimo di stelle (0-5, inserisci -1 per saltare): ");
            if (minStelle <= 0 || minStelle > 5) {
                ViewBase.mostraMessaggio("Numero di stelle non valido. Deve essere tra 0 e 5 o inserisci -1 per saltare).");
                minStelle = -1;
            }
        } while (minStelle == -1);
        List<Ristorante> risultati = Ristorante.combinata(
                ristoranti, luogo, tipoCucina, minPrezzo, maxPrezzo,
                conDelivery, deliveryPresente.equalsIgnoreCase("con"), conPrenotazione, prenotazionePresente.equalsIgnoreCase("con"), minStelle
        );
        if (risultati.isEmpty()) {
            ViewBase.mostraMessaggio("Nessun ristorante trovato con i filtri specificati.");
            return;
        }
        for (Ristorante r : risultati) {
            ViewBase.mostraRistorante(r);
            String continua;
            while (true) {
                continua = ViewBase.leggiStringa("Vuoi vedere un altro ristorante? (s/n): ", true);
                if (continua.equalsIgnoreCase("s")) {
                    Main.svuotaConsole();
                    break;
                } else if (continua.equalsIgnoreCase("n")) {
                    ViewBase.mostraMessaggio("Uscita dalla visualizzazione dei ristoranti vicini con filtro...");
                    Thread.sleep(100);
                    Main.svuotaConsole();
                    return;
                } else {
                    ViewBase.mostraMessaggio("Risposta non valida. Inserisci 's' o 'n'.");
                }
            }
        }
    }
}
