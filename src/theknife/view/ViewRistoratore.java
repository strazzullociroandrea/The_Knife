
package src.theknife.view;

import src.theknife.controller.TheKnife;
import src.theknife.model.*;
import src.theknife.model.util.ReverseGeocoding;
import src.theknife.dao.GestoreFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
 
/**
 * Classe ViewRistoratore che rappresenta l'interfaccia grafica per il gestore dei ristoranti
 *
 * @version 1.0
 * @Author Strazzullo Ciro Andrea, 763603, VA
 * @Author Riccardo Giovanni Rubini, 761126, VA
 * @Author Matteo Mongelli, 760960, VA 
 */
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
     * metodo per visualizzare, interagire e scorrere dinamicamente i ristoranti
     *
     * @param u               l'utente che interagisce col ristorante e che può lasciare una recensione
     * @param s               lo scanner che permette all'utente di lasciare una recensione se lo richiede (stelle e testo)
     * @param listaRistoranti la lista di ristoranti che si vuole scorrere
     * @param  pathUtenti     il path del file JSON contenente gli utenti
     * @param pathRistoranti  il path del file JSON contenente i ristoranti
     */
    private static void navigazioneRistoranti(Ristoratore u, Scanner s, List<Ristorante> listaRistoranti, String pathUtenti, String pathRistoranti) throws IOException, InterruptedException {
        if (listaRistoranti == null || listaRistoranti.isEmpty()) {
            System.out.println("Nessun ristorante trovato.");
            return;
        }
        int indice = 0;
        boolean visualizza = true;
        while (visualizza) {
            TheKnife.svuotaConsole();

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
                continue;
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
                    listaRistoranti.remove(ristoranteCorrente);
                    System.out.println("Ristorante rimosso con successo.");
                    GestoreFile.salvaRistoranti(listaRistoranti, pathRistoranti);
                    List<Utente> listaUtenti = GestoreFile.caricaUtenti(pathUtenti, pathRistoranti);
                    for (int i = 0; i < listaUtenti.size(); i++) {
                        Utente ut = listaUtenti.get(i);
                        if (ut instanceof Ristoratore) {
                            Ristoratore ristoratore = (Ristoratore) ut;
                            List<Ristorante> nuoviGestiti = new ArrayList<Ristorante>();
                            List<Ristorante> vecchiGestiti = ristoratore.getRistorantiGestiti();
                            for (int j = 0; j < vecchiGestiti.size(); j++) {
                                Ristorante rGestito = vecchiGestiti.get(j);
                                for (int k = 0; k < listaRistoranti.size(); k++) {
                                    Ristorante rSalvato = listaRistoranti.get(k);
                                    if (rGestito.getId() == rSalvato.getId()) {
                                        nuoviGestiti.add(rSalvato);
                                        break;
                                    }
                                }
                            }
                            ristoratore.setRistorantiGestiti(nuoviGestiti);
                        }
                    }
                    for (int i = 0; i < listaUtenti.size(); i++) {
                        if (listaUtenti.get(i).equals(u)) {
                            listaUtenti.remove(i);
                            break;
                        }
                    }
                    listaUtenti.add(u);
                    GestoreFile.salvaUtenti(listaUtenti, pathUtenti);
                    break;
                case 4:
                    List<Recensione> listaRecensioni = ristoranteCorrente.getRecensioni();
                    if (listaRecensioni.size() > 0) {
                        navigazioneRecensioni(u, ristoranteCorrente, listaRecensioni, pathUtenti, pathRistoranti, s);
                    } else {
                        System.out.println("non sono state inserite recensioni");
                    }
                    break;
                case 5:
                    u.visualizzaRiepilogo(ristoranteCorrente);
                    break;
                case 6:
                    boolean modificato = false;

                    String modNome = gestisciInput("inserisci il nuovo nome del ristorante. Premi invio per lasciarlo invariato", s, false);
                    if (!modNome.isBlank()) {
                        ristoranteCorrente.setNome(modNome);
                        modificato = true;
                    } else {
                        System.out.println("dato non modificato");
                    }

                    String modNazione = gestisciInput("inserisci la nuova nazione del ristorante. Premi invio per lasciarla invariata", s, false);
                    if (!modNazione.isBlank()) {
                        ristoranteCorrente.setNazione(modNazione);
                        modificato = true;
                    } else {
                        System.out.println("dato non modificato");
                    }

                    String modCitta = gestisciInput("inserisci la nuova città del ristorante. Premi invio per lasciarla invariata", s, false);
                    if (!modCitta.isBlank()) {
                        ristoranteCorrente.setCitta(modCitta);
                        modificato = true;
                    } else {
                        System.out.println("dato non modificato");
                    }

                    while (true) {
                        int sceltaDelivery = ViewBase.convertiScannerIntero(
                                "\nScegli un'opzione delivery:\n0. non modificare\n1. sì\n2. no\n", s);
                        if (sceltaDelivery == 1) {
                            if (!ristoranteCorrente.isDelivery()) {
                                ristoranteCorrente.setDelivery(true);
                                modificato = true;
                            }
                            break;
                        } else if (sceltaDelivery == 2) {
                            if (ristoranteCorrente.isDelivery()) {
                                ristoranteCorrente.setDelivery(false);
                                modificato = true;
                            }
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
                            if (!ristoranteCorrente.isPrenotazioneOnline()) {
                                ristoranteCorrente.setPrenotazioneOnline(true);
                                modificato = true;
                            }
                            break;
                        } else if (sceltaPrenotazione == 2) {
                            if (ristoranteCorrente.isPrenotazioneOnline()) {
                                ristoranteCorrente.setPrenotazioneOnline(false);
                                modificato = true;
                            }
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
                            if (!ristoranteCorrente.isPrenotazioneOnline()) {
                                ristoranteCorrente.setPrenotazioneOnline(true);
                                modificato = true;
                            }
                            break;
                        } else if (sceltaPrenOnline == 2) {
                            if (ristoranteCorrente.isPrenotazioneOnline()) {
                                ristoranteCorrente.setPrenotazioneOnline(false);
                                modificato = true;
                            }
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
                            } else if (ristoranteCorrente.getMinPrezzo() != minPrezzo) {
                                ristoranteCorrente.setMinPrezzo(minPrezzo);
                                modificato = true;
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
                            } else if (ristoranteCorrente.getMaxPrezzo() != maxPrezzo) {
                                ristoranteCorrente.setMaxPrezzo(maxPrezzo);
                                modificato = true;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Formato non valido. Prezzo massimo non modificato.");
                        }
                    } else {
                        System.out.println("Prezzo massimo non modificato.");
                    }

                    if (modificato) {
                        List<Ristorante> listaRistorantiSalvataggio = GestoreFile.caricaRistoranti(pathRistoranti);

                        boolean ristoranteEsistente = false;
                        for (int i = 0; i < listaRistorantiSalvataggio.size(); i++) {
                            if (listaRistorantiSalvataggio.get(i).getId() == ristoranteCorrente.getId()) {
                                listaRistorantiSalvataggio.set(i, ristoranteCorrente);
                                ristoranteEsistente = true;
                                break;
                            }
                        }

                        if (!ristoranteEsistente) {
                            listaRistorantiSalvataggio.add(ristoranteCorrente);
                        }

                        GestoreFile.salvaRistoranti(listaRistorantiSalvataggio, pathRistoranti);

                        List<Utente> utenti = GestoreFile.caricaUtenti(pathUtenti, pathRistoranti);

                        for (int i = 0; i < utenti.size(); i++) {
                            Utente ut = utenti.get(i);
                            if (ut instanceof Ristoratore) {
                                Ristoratore ristoratore = (Ristoratore) ut;
                                List<Ristorante> nuoviGestiti = new ArrayList<>();
                                List<Ristorante> vecchiGestiti = ristoratore.getRistorantiGestiti();
                                for (int j = 0; j < vecchiGestiti.size(); j++) {
                                    Ristorante rGestito = vecchiGestiti.get(j);
                                    for (int k = 0; k < listaRistorantiSalvataggio.size(); k++) {
                                        Ristorante rSalvato = listaRistorantiSalvataggio.get(k);
                                        if (rGestito.getId() == rSalvato.getId()) {
                                            nuoviGestiti.add(rSalvato);
                                            break;
                                        }
                                    }
                                }
                                ristoratore.setRistorantiGestiti(nuoviGestiti);
                            }
                        }
                        for (int i = 0; i < utenti.size(); i++) {
                            if (utenti.get(i).equals(u)) {
                                utenti.remove(i);
                                break;
                            }
                        }
                        utenti.add(u);
                        GestoreFile.salvaUtenti(utenti, pathUtenti);

                        System.out.println("Ristorante modificato con successo.");
                    } else {
                        System.out.println("Nessuna modifica effettuata, salvataggio saltato.");
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
     * metodo per visualizzare, interagire e scorrere dinamicamente le recensioni
     *
     * @param u               utente (ristoratore) che può interagire con le recensioni
     * @param r             ristorante su cui si vogliono visualizzare le recensioni
     * @param listaRecensioni lista delle recensioni che si vuole scorrere
     * @param pathUtenti     path del file JSON contenente gli utenti
     * @param PATHRISTORANTI path del file JSON contenente i ristoranti
     * @param s               scanner per l'input dell'utente
     */
    private static void navigazioneRecensioni(Ristoratore u, Ristorante r, List<Recensione> listaRecensioni,String pathUtenti,  String PATHRISTORANTI, Scanner s) {
        try{
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
                TheKnife.svuotaConsole();

                Recensione recensioneCorrente = listaRecensioni.get(indiceR);

                boolean appartieneAlRistorante = false;
                for (int i = 0; i < r.getRecensioni().size(); i++) {
                    Recensione rec = r.getRecensioni().get(i);
                    if (rec.getId() == recensioneCorrente.getId()) {
                        appartieneAlRistorante = true;
                        break;
                    }
                }

                if (!appartieneAlRistorante) {
                    System.out.println("Recensione non appartenente al ristorante selezionato.");
                    return;
                }

                System.out.println("\n--- Recensione " + (indiceR + 1) + " di " + listaRecensioni.size() + " ---");
                System.out.println("Testo: " + recensioneCorrente.getDescrizione());
                System.out.println("Stelle: " + recensioneCorrente.getStelle());

                if (recensioneCorrente.getRisposta() != null && !recensioneCorrente.getRisposta().isBlank()) {
                    System.out.println("Risposta: " + recensioneCorrente.getRisposta());
                } else {
                    System.out.println("Risposta: [Nessuna risposta]");
                }

                System.out.println("""
                    Scegli un'opzione:
                    1. Vai alla recensione successiva
                    2. Torna alla recensione precedente
                    3. Rispondi alla recensione
                    4. Esci dalla visualizzazione
                    """);

                int sceltaInterna = ViewBase.convertiScannerIntero("Scelta:", s);

                switch (sceltaInterna) {
                    case 1:
                        if (indiceR < listaRecensioni.size() - 1) {
                            indiceR++;
                        } else {
                            System.out.println("Hai raggiunto l'ultima recensione.");
                        }
                        break;
                    case 2:
                        if (indiceR > 0) {
                            indiceR--;
                        } else {
                            System.out.println("Sei già alla prima recensione.");
                        }
                        break;
                    case 3:
                        System.out.println("Inserisci la tua risposta. Attenzione: se già presente verrà sovrascritta:");
                        String risposta = leggiRispostaValida(s);
                        recensioneCorrente.setRisposta(risposta);

                        List<Ristorante> listaRistoranti = GestoreFile.caricaRistoranti(PATHRISTORANTI);

                        for (int i = 0; i < listaRistoranti.size(); i++) {
                            Ristorante rest = listaRistoranti.get(i);
                            if (rest.getId() == r.getId()) {
                                List<Recensione> recs = rest.getRecensioni();
                                for (int j = 0; j < recs.size(); j++) {
                                    Recensione rec = recs.get(j);
                                    if (rec.getId() == recensioneCorrente.getId()) {
                                        rec.setRisposta(risposta);
                                        break;
                                    }
                                }
                                break;
                            }
                        }

                        List<Utente> listaUtenti = GestoreFile.caricaUtenti(pathUtenti, PATHRISTORANTI);
                        for (int i = 0; i < listaUtenti.size(); i++) {
                            Utente ut = listaUtenti.get(i);
                            if (ut instanceof Cliente) {
                                Cliente cliente = (Cliente) ut;
                                List<Recensione> recCliente = cliente.getRecensioniMesse();
                                for (int j = 0; j < recCliente.size(); j++) {
                                    Recensione rec = recCliente.get(j);
                                    if (rec.getId() == recensioneCorrente.getId()) {
                                        rec.setRisposta(risposta);
                                    }
                                }
                                List<Ristorante> preferiti = cliente.visualizzaPreferiti();
                                for (int pi = 0; pi < preferiti.size(); pi++) {
                                    Ristorante pref = preferiti.get(pi);
                                    if (pref.getId() == r.getId()) {
                                        List<Recensione> recsPref = pref.getRecensioni();
                                        for (int ri = 0; ri < recsPref.size(); ri++) {
                                            Recensione rec = recsPref.get(ri);
                                            if (rec.getId() == recensioneCorrente.getId()) {
                                                rec.setRisposta(risposta);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        GestoreFile.salvaUtenti(listaUtenti, pathUtenti);
                        GestoreFile.salvaRistoranti(listaRistoranti, PATHRISTORANTI);
                        System.out.println("Risposta salvata correttamente.");
                        break;
                    case 4:
                        visualizzaR = false;
                        break;
                    default:
                        System.out.println("Scelta non valida.");
                }
            }
        } catch (Exception e) {
            System.err.println("Errore nella navigazione delle recensioni: " + e.getMessage());
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
                    case 1:
                        List<Ristorante> listaRistoranti = u.getRistorantiGestiti();
                        if (listaRistoranti == null || listaRistoranti.isEmpty()) {
                            System.out.println("Non gestisci ancora alcun ristorante.");
                        } else {
                            navigazioneRistoranti(u, s, listaRistoranti, pathUtenti, pathRistoranti);
                        }
                        break;

                    case 2:
                        try {
                            System.out.println("Creazione del ristorante...");

                            String nome = gestisciInput("Inserire il nome del ristorante: ", s, true);
                            String domicilio;
                            String nazione;
                            String citta;
                            String indirizzo;
                            do {
                                nazione = gestisciInput("Inserire la nazione del ristorante: ", s, true);
                                citta = gestisciInput("Inserire la città del ristorante: ", s, true);
                                indirizzo = gestisciInput("Inserire l'indirizzo del ristorante: ", s, true);
                                domicilio = indirizzo +","+ citta +","+nazione;
                                double[] latLon = ReverseGeocoding.getLatitudineLongitudine(domicilio);
                                if (latLon[0] == -1 && latLon[1] == -1) {
                                    domicilio = "";
                                    System.out.println("Domicilio non valido, riprova! Assicurati di inserire un indirizzo completo e valido nel formato 'via, città, nazione'.");
                                }
                            } while (domicilio.isEmpty());

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
                            Ristorante nuovoRistorante = u.creaRistorante(nome, nazione, citta, indirizzo,
                                    delivery, prenotazione, tipoCucina, prenotazioneOnline, minPrezzo, maxPrezzo);
                            List<Ristorante> listaRistorantiSalvataggio = GestoreFile.caricaRistoranti(pathRistoranti);
                            boolean ristoranteEsistente = false;
                            for (int i = 0; i < listaRistorantiSalvataggio.size(); i++) {
                                if (listaRistorantiSalvataggio.get(i).getId() == nuovoRistorante.getId()) {
                                    listaRistorantiSalvataggio.set(i, nuovoRistorante);
                                    ristoranteEsistente = true;
                                    break;
                                }
                            }
                            if (!ristoranteEsistente) {
                                listaRistorantiSalvataggio.add(nuovoRistorante);
                            }
                            GestoreFile.salvaRistoranti(listaRistorantiSalvataggio, pathRistoranti);

                            List<Utente> listaUtenti = GestoreFile.caricaUtenti(pathUtenti, pathRistoranti);
                            for (int i = 0; i < listaUtenti.size(); i++) {
                                Utente ut = listaUtenti.get(i);
                                if (ut instanceof Ristoratore) {
                                    Ristoratore ristoratore = (Ristoratore) ut;
                                    List<Ristorante> nuoviGestiti = new ArrayList<Ristorante>();
                                    List<Ristorante> vecchiGestiti = ristoratore.getRistorantiGestiti();
                                    for (int j = 0; j < vecchiGestiti.size(); j++) {
                                        Ristorante rGestito = vecchiGestiti.get(j);
                                        for (int k = 0; k < listaRistorantiSalvataggio.size(); k++) {
                                            Ristorante rSalvato = listaRistorantiSalvataggio.get(k);
                                            if (rGestito.getId() == rSalvato.getId()) {
                                                nuoviGestiti.add(rSalvato);
                                                break;
                                            }
                                        }
                                    }
                                    ristoratore.setRistorantiGestiti(nuoviGestiti);
                                }
                            }
                            for (int i = 0; i < listaUtenti.size(); i++) {
                                if (listaUtenti.get(i).equals(u)) {
                                    listaUtenti.remove(i);
                                    break;
                                }
                            }
                            listaUtenti.add(u);
                            GestoreFile.salvaUtenti(listaUtenti, pathUtenti);

                            System.out.println("Ristorante creato con successo.");

                        } catch (Exception e) {
                            System.out.println("Errore durante la creazione del ristorante:");
                        }
                        break;
                    case 3:
                        System.out.println("\n--- Dati utente ---");
                        System.out.println("Nome: " + (u.getNome() != null ? u.getNome() : "Non specificato"));
                        System.out.println("Cognome: " + (u.getCognome() != null ? u.getCognome() : "Non specificato"));
                        System.out.println("Username: " + (u.getUsername() != null ? u.getUsername() : "Non specificato"));
                        System.out.println("Data di nascita: " + (u.getDataNascita() != null ? u.getDataNascita() : "Non specificata"));
                        System.out.println("Domicilio: " + (u.getDomicilio() != null ? u.getDomicilio() : "Non specificato"));
                        System.out.println("Tipo utente: Ristoratore");
                        break;

                    case 4:
                        boolean modificheEffettuate = false;
                        String vecchioUsername = u.getUsername();

                        String modNome = gestisciInput("Inserisci il tuo nuovo nome. Premi invio per lasciarlo invariato", s, false);
                        if (!modNome.isBlank()) {
                            u.setNome(modNome);
                            modificheEffettuate = true;
                        } else {
                            System.out.println("Dato non modificato");
                        }

                        String modCognome = gestisciInput("Inserisci il tuo nuovo cognome. Premi invio per lasciarlo invariato", s, false);
                        if (!modCognome.isBlank()) {
                            u.setCognome(modCognome);
                            modificheEffettuate = true;
                        } else {
                            System.out.println("Dato non modificato");
                        }

                        String modUserName = gestisciInput("Inserisci il tuo nuovo username. Premi invio per lasciarlo invariato", s, false);
                        if (!modUserName.isBlank()) {
                            boolean usernameEsistente = false;
                            List<Utente> listaUtentiTmp = GestoreFile.caricaUtenti(pathUtenti, pathRistoranti);
                            for (int i = 0; i < listaUtentiTmp.size(); i++) {
                                Utente u1 = listaUtentiTmp.get(i);
                                if (modUserName.equals(u1.getUsername()) && !u1.equals(u)) {
                                    System.out.println("Username già esistente: modifica annullata");
                                    usernameEsistente = true;
                                    break;
                                }
                            }
                            if (!usernameEsistente) {
                                u.setUsername(modUserName);
                                System.out.println("Username aggiornato");
                                modificheEffettuate = true;
                            }
                        } else {
                            System.out.println("Dato non modificato");
                        }

                        boolean passwordValida = false;
                        while (!passwordValida) {
                            String modPassword = gestisciInput("Inserisci la tua nuova password. Premi invio per lasciarla invariata: ", s, false);

                            if (modPassword.isBlank()) {
                                System.out.println("Dato non modificato");
                                passwordValida = true;
                            } else if (modPassword.length() < 7) {
                                System.out.println("La password deve contenere almeno 7 caratteri.");
                            } else {
                                u.setPasswordCifrata(modPassword);
                                System.out.println("Password modificata con successo.");
                                modificheEffettuate = true;
                                passwordValida = true;
                            }
                        }

                        String modDomicilio = gestisciInput("Inserisci il tuo nuovo domicilio. Premi invio per lasciarlo invariato", s, false);
                        if (!modDomicilio.isBlank()) {
                            u.setDomicilio(modDomicilio);
                            modificheEffettuate = true;
                        } else {
                            System.out.println("Dato non modificato");
                        }

                        if (modificheEffettuate) {
                            List<Utente> listaUtentiTBS = GestoreFile.caricaUtenti(pathUtenti, pathRistoranti);
                            if (listaUtentiTBS == null) {
                                System.out.println("Impossibile salvare i dati: lista utenti non disponibile");
                            } else {
                                for (int i = 0; i < listaUtentiTBS.size(); i++) {
                                    if (listaUtentiTBS.get(i).getUsername().equals(vecchioUsername)) {
                                        listaUtentiTBS.remove(i);
                                        break;
                                    }
                                }
                                listaUtentiTBS.add(u);
                                GestoreFile.salvaUtenti(listaUtentiTBS, pathUtenti);
                                System.out.println("Dati personali aggiornati con successo.");
                            }
                        } else {
                            System.out.println("Nessuna modifica effettuata, salvataggio saltato.");
                        }
                        break;
                    case 5:
                        System.out.println("Verrai reindirizzato al menù iniziale!");
                        continua = false;
                        ViewBase.view(pathUtenti, pathRistoranti);
                        break;
                    default:
                        System.out.println("Inserire una scelta valida.");
                }
            }
        }
    }

}