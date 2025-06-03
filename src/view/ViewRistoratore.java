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
        String input;
        do {
            System.out.println(msg);
            input = scanner.nextLine();
        } while (input.isEmpty());
        return input;
    }

    /**
     * metodo per visualizzare, interagire e scorrere dinamicamente le recensioni
     * @param u utente (ristoratore) che può interagire con le recensioni
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
                        String risposta = s.nextLine();
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

    private static void navigazioneRistoranti(Ristoratore u, Scanner s, List<Ristorante> listaRistoranti) {
        if (listaRistoranti == null || listaRistoranti.isEmpty()) {
            System.out.println("Nessun ristorante trovato.");
            return;
        }
        int indice = 0;
        boolean visualizza = true;
        while (visualizza) {
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
                6. Esci dalla visualizzazione
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
                case 6 -> visualizza = false;
                default -> System.out.println("Scelta non valida.");
            }
        }
    }


    /**
     * metodo per accedere alla view del risotrante
     * @param u utente in grado di interagire con la view
     * @throws Exception
     */
    public static void view(Ristoratore u) throws Exception {
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
                        List<Ristorante> listaRistoranti = GestoreFile.caricaRistoranti(PATHRISTORANTI);
                        if (listaRistoranti.isEmpty()) {
                            System.out.println("Nessun ristorante trovato.");
                        } else {
                            navigazioneRistoranti(u, s, listaRistoranti);
                        }
                    }

                    case 2 -> {
                        System.out.println("Creazione del ristorante...");

                        String nome = gestisciInput("Inserire il nome del ristorante: ", s);
                        String nazione = gestisciInput("Inserire la nazione del ristorante: ", s);
                        String citta = gestisciInput("Inserire la città del ristorante: ", s);
                        String indirizzo = gestisciInput("Inserire l'indirizzo del ristorante: ", s);

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
                                System.out.println("Opzione inserita in modo errato, riprova.");
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
                                System.out.println("Opzione inserita in modo errato, riprova.");
                            }
                        }

                        String tipoCucina = gestisciInput("Inserire il tipo di cucina: ", s);

                        boolean prenotazioneOnline;
                        while (true) {
                            int sceltaPrenotazioneOnline = ViewBase.convertiScannerIntero(
                                    "\nScegli un'opzione prenotazione online:\n1. sì\n2. no\n", s);
                            if (sceltaPrenotazioneOnline == 1) {
                                prenotazioneOnline = true;
                                break;
                            } else if (sceltaPrenotazioneOnline == 2) {
                                prenotazioneOnline = false;
                                break;
                            } else {
                                System.out.println("Opzione inserita in modo errato, riprova.");
                            }
                        }

                        System.out.print("Inserire il prezzo minimo per il tuo ristorante: ");
                        double minPrezzo = s.nextDouble();
                        s.nextLine(); // Consuma newline

                        System.out.print("Inserire il prezzo massimo per il tuo ristorante: ");
                        double maxPrezzo = s.nextDouble();
                        s.nextLine(); // Consuma newline

                        Ristorante nuovoRistorante = u.creaRistorante(nome, nazione, citta, indirizzo,
                                delivery, prenotazione, tipoCucina, prenotazioneOnline, minPrezzo, maxPrezzo);

                        List<Ristorante> listaRistoranti = GestoreFile.caricaRistoranti(PATHRISTORANTI);
                        listaRistoranti.add(nuovoRistorante);
                        GestoreFile.salvaRistoranti(listaRistoranti, PATHRISTORANTI);

                        System.out.println("Ristorante creato con successo.");
                    }

                    case 3 -> {
                        System.out.println("\n--- Dati utente ---");
                        System.out.println("Nome: " + u.getNome());
                        System.out.println("Cognome: " + u.getCognome());
                        System.out.println("Username: " + u.getUsername());
                        System.out.println("Data di nascita: " + u.getDataNascita());
                        System.out.println("Domicilio: " + u.getDomicilio());
                        System.out.println("Tipo utente: " + (u instanceof Ristoratore ? "Ristoratore" : "Cliente"));
                    }

                    case 4 -> {
                        String modNome = gestisciInput("Inserisci il tuo nuovo nome: ", s);
                        u.setNome(modNome);

                        String modCognome = gestisciInput("Inserisci il tuo nuovo cognome: ", s);
                        u.setCognome(modCognome);

                        String modUserName = gestisciInput("Inserisci il tuo nuovo username: ", s);

                        boolean usernameEsistente = false;
                        for (Utente u1 : GestoreFile.caricaUtenti(PATHUTENTI, PATHRISTORANTI)) {
                            if (modUserName.equals(u1.getUsername())) {
                                System.out.println("Username già esistente: modifica annullata");
                                usernameEsistente = true;
                                break;
                            }
                        }

                        if (!usernameEsistente) {
                            u.setUsername(modUserName);
                            System.out.println("Username aggiornato.");
                        }

                        boolean passwordValida = false;
                        while (!passwordValida) {
                            String modPassword = gestisciInput("Inserisci la tua nuova password: ", s);
                            String pwCifrata = PasswordUtil.hashPassword(modPassword);

                            if (pwCifrata.equals(u.getPasswordCifrata())) {
                                System.out.println("La nuova password è uguale a quella attuale. Inserisci una password diversa.");
                            } else {
                                u.setPasswordCifrata(pwCifrata);
                                System.out.println("Password modificata con successo.");
                                passwordValida = true;
                            }
                        }

                        String modDomicilio = gestisciInput("Inserisci il tuo nuovo domicilio: ", s);
                        u.setDomicilio(modDomicilio);
                    }

                    case 5 -> {
                        System.out.println("Logout effettuato.");
                        continua = false;
                    }

                    default -> System.out.println("Inserire una scelta valida.");
                }
            }
        }
    }

}