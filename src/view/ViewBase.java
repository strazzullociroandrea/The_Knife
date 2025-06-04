package src.view;

import src.dao.GestoreFile;
import src.model.*;
import src.model.Ristorante;
import src.model.util.ReverseGeocoding;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe ViewBase che rappresenta l'interfaccia grafica di base dell'applicazione.
 * @version 1.0
 *
 * @Author Strazzullo Ciro Andrea
 * @Author Riccardo Giovanni Rubini
 * @Author Matteo Mongelli
 */
public class ViewBase {
    /**
     * Path del file JSON contenente gli utenti
     */
    private static final String PATHUTENTI = GestoreFile.adattaPath(new String[]{"data", "Utenti.json"});
    /**
     * Path del file JSON contenente i ristoranti
     */
    private static final String PATHRISTORANTI = GestoreFile.adattaPath(new String[]{ "data", "Ristoranti.json"});

    /**
     * Metodo per svuotare la console dai log di configurazione
     * @throws IOException eccezione di input/output
     * @throws InterruptedException eccezione di interruzione
     */
    private static void svuotaConsole() throws IOException, InterruptedException {
        try{
            String operatingSystem = System.getProperty("os.name"); // recupero del sistema operativo corrente
            if (operatingSystem.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        }catch(Exception e){
            for(int i=0;i<50;i++)
                System.out.println();
        }

    }
    /**
     * Metodo privato per gestire l'input da parte dell'utente, richiedendo di inserire un informazione fino a quando non è diversa da stringa vuota
     * @param msg messaggio da visualizzare
     * @param scanner scanner da utilizzare per l'input
     * @return stringa inserita dall'utente
     */
    private static String gestisciInput(String msg, Scanner scanner){
        String input;
        do{
            System.out.println(msg);
            input = scanner.nextLine();
        }while(input.isEmpty());
        return input;
    }

    /**
     * Metodo privato per convertire la stringa in console in un intero
     * @param msg messaggio da visualizzare
     * @param scanner scanner da utilizzare per l'input
     * @return intero inserito dall'utente
     */
    static int convertiScannerIntero(String msg, Scanner scanner){
        int numero;
        while (true) {
            String input = gestisciInput(msg, scanner);
            try {
                numero = Integer.parseInt(input);
                return numero;
            } catch (NumberFormatException e) {
                System.err.println("Valore non valido. Inserisci un numero intero valido.");
            }
        }
    }

    /**
     * Metodo privato per convertire la stringa in console in un double
     * @param msg messaggio da visualizzare
     * @param scanner scanner da utilizzare per l'input
     * @return double inserito dall'utente
     */
    private static double convertiScannerDouble(String msg, Scanner scanner){
        double numero;
        while (true) {
            String input = gestisciInput(msg, scanner);
            try {
                numero = Double.parseDouble(input);
                return numero;
            } catch (NumberFormatException e) {
                System.err.println("Valore non valido. Inserisci un numero double valido.");
            }
        }
    }

    /**
     * Metodo privato per indicare se un utente è già presente nella lista o no per evitare duplicati
     * @param utenti lista di utenti da scorrere
     * @param u utente da testare
     * @return true se è già presente, false altrimenti
     */
    private static boolean giaPresente(List<Utente> utenti, Utente u){
        if(!utenti.isEmpty() && u != null){
            for(Utente user: utenti){
                if (user.getUsername().equalsIgnoreCase(u.getUsername())) {
                    System.err.println("Attenzione, username già in uso!");
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Metodo per registrare un nuovo utente
     * @return oggetto Utente registrato
     */
    private static Utente registrati(Scanner s) throws Exception {
        String nome = gestisciInput("Inserisci il tuo nome:", s);
        String cognome = gestisciInput("Inserisci il tuo cognome:", s);
        String username = gestisciInput("Inserisci il tuo username:", s);
        String dataNascita;
        boolean notValidData;
        do{
            System.out.println("Inserisci la tua data di nascita (dd/MM/yyyy), premi invio per lasciare il campo vuoto:");
            dataNascita = s.nextLine();
            if(dataNascita.isEmpty()){
                notValidData = false;
            }else{
                try {
                    LocalDate.parse(dataNascita, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    notValidData = false;
                } catch (DateTimeParseException e) {
                    System.err.println("Data non valida, riprova! Formato richiesto: dd/MM/yyyy");
                    notValidData = true;
                }
            }
        }while(notValidData);
        String domicilio;
        do{
            domicilio = gestisciInput("Inserisci il tuo domicilio:", s);
            double[] latLon = ReverseGeocoding.getLatitudineLongitudine(domicilio);
            if(latLon[0] == -1 && latLon[1] == -1) {
                domicilio = "";
                System.out.println("Domicilio non valido, riprova! Assicurati di inserire un indirizzo completo e valido nel formato 'via, città, nazione'.");
            }
        }while(domicilio.isEmpty());

        String password, secondaPassword;
        do {
            System.out.println("Inserisci la tua password (minimo 7 caratteri):");
            password = s.nextLine();
            if (password.length() < 7) {
                System.err.println("La password deve contenere almeno 7 caratteri.");
                password = "";
            }
        } while (password.isEmpty());

        do {
            System.out.println("Inserisci di nuovo la tua password:");
            secondaPassword =  s.nextLine();
            if (!secondaPassword.equals(password)) {
                System.err.println("Le password non coincidono, riprova.");
                secondaPassword = "";
            }
        } while (secondaPassword.isEmpty());

        String tipologia;
        do {
            tipologia = gestisciInput("Sei un cliente o un ristoratore? (c/r):", s);
            if (!tipologia.equalsIgnoreCase("c") && !tipologia.equalsIgnoreCase("r")) {
                System.err.println("Scelta non valida, inserisci 'c' per cliente o 'r' per ristoratore.");
                tipologia = "";
            }
        } while (tipologia.isEmpty());

        // Restituisco l'utente corretto
        if(tipologia.equalsIgnoreCase("c")) {
            if(dataNascita.isEmpty()){
                return new Cliente(password, nome, cognome, username, domicilio);
            }else{
                return new Cliente(password, nome, cognome, username, dataNascita, domicilio);
            }
        } else {
            if(dataNascita.isEmpty()){
                return new Ristoratore(password, nome, cognome, username, domicilio);
            }else{
                return new Ristoratore(password, nome, cognome, username, dataNascita, domicilio);
            }
        }
    }

    /**
     * Metodo per caricare gli utenti dal file json
     * @return lista di utenti caricati
     * @throws IOException eccezione di input/output
     */
    private static List<Utente> caricaUtenti() throws IOException {
        return GestoreFile.caricaUtenti(PATHUTENTI, PATHRISTORANTI);
    }

    /**
     * Metodo per loggare l'utente verificando le credenziali
     * @param s Scanner per l'input
     * @return oggetto Utente loggato di tipo Cliente o Ristoratore o null (Se non esiste)
     */
    private static Utente login(Scanner s){
        String username = gestisciInput("Inserisci il tuo username:", s);
        String password = gestisciInput("Inserisci la tua password:", s);
        List<Utente> utenti;
        try {
            utenti = caricaUtenti();
            for(Utente u: utenti){
                if(u.verificaCredenziali(username, password)){
                    return u;
                }
            }
            System.err.println("Credenziali non valide, riprova!");
            return null;
        } catch (IOException e) {
            System.err.println("Errore durante il caricamento degli utenti: " + e.getMessage());
        }
        System.err.println("Login non avvenuto con successo!");
        return null;
    }

    /**
     * Metodo per cercare i ristoranti vicini al luogo desiderato
     * @param luogo luogo di ricerca nel formato "indirizzo, città, nazione"
     * @return lista di ristoranti vicini a 10 km o null se c'è qualche errore
     * @throws Exception eccezione in caso di errore
     */
    private static List<Ristorante> ristorantiVicini(List<Ristorante> elenco, String luogo) throws Exception {
        if(luogo.isEmpty() || elenco.isEmpty()){
            System.err.println("Luogo o elenco non valido, riprova!");
            return null;
        }else{
            List <Ristorante> tmp = new ArrayList<>();
            for(Ristorante r: elenco){
                if(r.isVicino(luogo)){
                    tmp.add(r);
                }
            }
            return tmp;
        }
    }

    /**
     * Metodo per visualizzare l'interfaccia grafica di base
     * @throws Exception eccezione lanciata in qualsiasi caso di errore di esecuzione con messaggio
     */
    public static void view() throws Exception {
        try(Scanner s = new Scanner(System.in)){
            System.out.println("Avvio del progetto....");
            System.out.println("Progetto avviato con successo!");
            System.out.println("Carico i ristoranti..");
            List<Ristorante> ristoranti =  GestoreFile.caricaRistoranti(PATHRISTORANTI);
            System.out.println("Ristoranti caricati con successo!");
            System.out.println("Carico l'interfaccia grafica di base, inzio programma...");
            svuotaConsole();//Svuoto la console dai log di configurazione
            System.out.println("Benvenuto in TheKnife!");
            System.out.println("Ecco il menu principale:");
            boolean continua = true;
            Utente u;
            while(continua){
                int scelta = convertiScannerIntero( """
                    \n\n
                    Menù:
                    1. Visualizza ristoranti (luogo, fascia prezzo, servizi, recensioni) in modalità guest
                    2. Login come cliente o ristoratore 
                    3. Registrati come cliente o ristoratore 
                    4. Chiudi l'applicazione
                    La tua scelta: 
                """, s);
                switch(scelta){
                    case 1 ->{

                        String luogo;
                        do{
                            luogo = gestisciInput("Inserisci il luogo di ricerca:", s);
                            double[] latLon = ReverseGeocoding.getLatitudineLongitudine(luogo);
                            if(latLon[0] == -1 && latLon[1] == -1) {
                                luogo = "";
                                System.out.println("Luogo non valido, riprova! Assicurati di inserire un luogo esistente.");
                            }
                        }while(luogo.isEmpty());

                        boolean continuaInterno = true;
                        do{
                            int sceltaIn = convertiScannerIntero("""
                                \n\n
                                Menù Ristoranti guest:
                                1. Visualizza ristoranti vicini al luogo specificato con i relativi dettagli
                                2. Visualizza ristoranti secondo un filtro e i relativi dettagli
                                3. Torna al menù principale
                                4. Modifica luogo
                                La tua scelta:  
                        """, s);
                            switch(sceltaIn){
                                case 1 ->{
                                    List<Ristorante> tmp = ristorantiVicini(ristoranti, luogo);
                                    if(tmp != null && !tmp.isEmpty()) {
                                        for(Ristorante r: tmp){
                                            System.out.println(r.visualizzaRistorante());
                                            System.out.println("Recensioni:");
                                            for(Recensione rec: r.getRecensioni()){
                                                System.out.println(rec);
                                            }
                                            String continuaRicerca;
                                            //Aspetta fino a quando non è stato inserito un valore valido
                                            do {
                                                System.out.println("Digita 'c' per continuare la ricerca o 'q' per tornare al menù ");
                                                continuaRicerca = s.nextLine();
                                            }while((!continuaRicerca.equalsIgnoreCase("c") && !continuaRicerca.equalsIgnoreCase("q")));
                                            svuotaConsole();
                                            if(continuaRicerca.equalsIgnoreCase("q")) {
                                                System.out.println("Tornando al menù ...");
                                                break;
                                            }
                                        }
                                    }else{
                                        System.out.println("nessun ristorante trovato!");
                                    }

                                }
                                case 2 ->{
                                    System.out.println("Inserisci i parametri di ricerca:");
                                    String locazione;
                                    do {
                                        locazione = gestisciInput("Inserisci la locazione. Parametro obbligatorio:", s);
                                        if (locazione.isEmpty()) {
                                            System.err.println("La locazione è obbligatoria!");
                                        }else{
                                            double[] latLon = ReverseGeocoding.getLatitudineLongitudine(locazione);
                                            if(latLon[0] == -1 && latLon[1] == -1) {
                                                locazione = "";
                                                System.out.println("Locazione non valida, riprova! Assicurati di inserire un luogo esistente.");
                                            }
                                        }
                                    } while (locazione.isEmpty());
                                    System.out.println("Inserisci il tipo di cucina (premi invio per saltare):");
                                    String tipoCucina = s.nextLine();
                                    double minPrezzo = convertiScannerDouble("Prezzo minimo (0 per ignorare):", s);
                                    double maxPrezzo = convertiScannerDouble("Prezzo massimo (0 per ignorare):", s);
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
                                    int minStelle = convertiScannerIntero("Numero minimo di stelle (0-5, default: 0):", s);
                                    if (minStelle < 0 || minStelle > 5) {
                                        System.out.println("Valore non valido per le stelle. Impostato a 0.");
                                        minStelle = 0;
                                    }
                                    List<Ristorante> risultati = Ristorante.combinata(
                                            ristoranti, locazione, tipoCucina, minPrezzo, maxPrezzo,
                                            conDelivery, filtroDelivery, conPrenotazione, filtroPrenotazione, minStelle
                                    );

                                    if (risultati.isEmpty()) {
                                        System.out.println("Nessun ristorante trovato con i criteri specificati.");
                                    } else {
                                        for (Ristorante r : risultati) {
                                            System.out.println(r.visualizzaRistorante());
                                            System.out.println("Recensioni:");
                                            for (Recensione rec : r.getRecensioni()) {
                                                System.out.println(rec);
                                            }
                                            String sceltaInterna;
                                            do {
                                                System.out.println("Digita 'c' per continuare a vedere i risultati o 'q' per tornare al menu principale:");
                                                sceltaInterna = s.nextLine();
                                            } while (!sceltaInterna.equalsIgnoreCase("c") && !sceltaInterna.equalsIgnoreCase("q"));

                                            svuotaConsole();

                                            if (sceltaInterna.equalsIgnoreCase("q")) {
                                                System.out.println("Torno al menu principale...");
                                                break;
                                            }
                                        }
                                    }
                                }
                                case 3 ->{
                                    System.out.println("Tornando al menù principale...");
                                    continuaInterno = false;
                                    svuotaConsole();
                                }
                                case 4 ->{
                                    do{
                                        luogo = gestisciInput("Inserisci il luogo di ricerca:", s);
                                        double[] latLon = ReverseGeocoding.getLatitudineLongitudine(luogo);
                                        if(latLon[0] == -1 && latLon[1] == -1) {
                                            luogo = "";
                                            System.out.println("Luogo non valido, riprova! Assicurati di inserire un luogo esistente.");
                                        }
                                    }while(luogo.isEmpty());
                                    System.out.println("Luogo modificato con successo!");
                                }
                            }
                        }while(continuaInterno);
                    }
                    case 2 ->{
                        //login dell'utente
                        u = login(s);
                        //In base alla tipologia di u visualizzo la relativa interfaccia
                        if(u instanceof  Cliente){
                            continua = false;
                            ViewCliente.view((Cliente)u);
                        }else if(u instanceof Ristoratore){
                            continua = false;
                            ViewRistoratore.view((Ristoratore)u);
                        }else{
                            System.err.println("Login non avvenuto con successo!");
                        }
                    }
                    case 3 ->{
                        //Registro utente
                        u = registrati(s);
                        List<Utente> utenti = caricaUtenti();
                        if(u == null || giaPresente(utenti, u)){
                            System.err.println("Registrazione non avvenuta con successo!");
                        }else{
                            //salvo il file aggiornato di utenti
                            utenti.add(u);
                            GestoreFile.salvaUtenti(utenti, PATHUTENTI);
                            //In base alla tipologia di u visualizzo la relativa interfaccia
                            if(u instanceof  Cliente){
                                continua = false;
                                ViewCliente.view((Cliente)u);
                            }else if(u instanceof Ristoratore){
                                continua = false;
                                ViewRistoratore.view((Ristoratore)u);
                            }else{
                                System.err.println("Registrazione non avvenuta con successo!");
                            }
                        }
                    }
                    case 4 -> {//Se scelta == 4 allora interrompo l'esecuzione dell'applicazione
                        System.out.println("Grazie per aver utilizzato TheKnife!");
                        System.out.println("Arrivederci!");
                        continua = false;
                    }
                    default ->//qualsiasi numero inserito diverso da 1,2,3,4 richiede la scelta
                         System.err.println("Attenzione, scelta non valida, riprova!");
                }
            }
        } catch (IOException e) {
            System.err.println("Errore durante l'esecuzione del programma: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore imprevisto: " + e.getMessage());
        }
    }
}
