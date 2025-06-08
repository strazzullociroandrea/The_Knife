package src.dao;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import src.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestoreFile {

    /**
     * Metodo per gestire l'adattamento del path in base al sistema operativo usato
     *
     * @param pathParti array contenente il path al file senza separatore
     * @return path per il sistema operativo usato
     */
    public static String adattaPath(String[] pathParti) {
        return String.join(File.separator, pathParti);
    }

    /**
     * Metodo per crere un file se non esiste nel path specificato
     *
     * @param path percorso al file
     * @param contenutoIniziale contenuto iniziale del file appena creato
     * @throws IOException eccezione lanciata in caso di errore
     */
    private static void creaFile(String path, String contenutoIniziale) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try (Writer writer = new FileWriter(file)) {
                writer.write(contenutoIniziale);
            }catch(IOException e){
                throw new IOException("Non è stato possibile creare il file da te richiesto");
            }
        }
    }

    /**
     * Metodo utilizzato per salvare i ristoranti in un file, vengono trattati come json
     *
     * @param ristoranti lista di ristoranti da salvare
     * @param path percorso al file
     * @throws IOException eccezione lanciata in caso di errore
     */
    public static void salvaRistoranti(List<Ristorante> ristoranti, String path) throws IOException {
        creaFile(path, "[]");
        try (Writer writer = new FileWriter(path, false)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(ristoranti, writer);
        } catch (Exception e) {
            throw new IOException("Errore durante la scrittura dei ristoranti", e);
        }
    }

    /**
     * Metodo per salvare i ristoranti in un file, vengono trattati come json
     *
     * @param utenti lista di utenti da salvare
     * @param path percorso al file
     * @throws IOException eccezione lanciata in caso di errore
     */
    public static void salvaUtenti(List<Utente> utenti, String path) throws IOException {
        try (Writer writer = new FileWriter(path, false)) {
            creaFile(path, "[]");
            JsonArray array = new JsonArray();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            //Aggiunta del ruolo in base al tipo di istanza dell'oggetto cliente
            for (Utente u : utenti) {
                JsonElement jsonElement = gson.toJsonTree(u);
                if (jsonElement.isJsonObject()) {
                    JsonObject obj = jsonElement.getAsJsonObject();
                    if (u instanceof Cliente) {
                        obj.addProperty("ruolo", "cliente");
                    } else if (u instanceof Ristoratore) {
                        obj.addProperty("ruolo", "ristoratore");
                    }
                    array.add(obj);
                }
            }
            gson.toJson(array, writer);
        } catch (Exception e) {
            throw new IOException("Errore durante il salvataggio degli utenti", e);
        }
    }

    /**
     * Metodo per ottenere la lista di ristoranti salvati su file
     *
     * @param path path al file contenente i ristoranti
     * @return lista di ristoranti
     * @throws IOException eccezione lanciata in caso di errore
     */
    public static List<Ristorante> caricaRistoranti(String path) throws IOException {
        try (Reader reader = new FileReader(path)) {
            //Creazione del file vuoto se non esistente
            creaFile(path, "[]");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();

            //Lista
            List<Ristorante> lista = new ArrayList<>();

            //Ciclo di salvataggio su variabile
            for (JsonElement elem : array) {
                JsonObject obj = elem.getAsJsonObject();

                //Recupero dei dati del ristorante
                int id = obj.get("id").getAsInt();
                String nome = obj.get("nome").getAsString();
                String nazione = obj.get("nazione").getAsString();
                String citta = obj.get("citta").getAsString();
                String indirizzo = obj.get("indirizzo").getAsString();
                String tipoCucina = obj.get("tipoCucina").getAsString();
                boolean delivery =  obj.get("delivery").getAsBoolean();
                boolean prenotazioneOnline =  obj.get("prenotazioneOnline").getAsBoolean();
                double minPrezzo = obj.get("minPrezzo").getAsDouble();
                double maxPrezzo = obj.get("maxPrezzo").getAsDouble();

                lista.add(new Ristorante(
                        id,
                        nome,
                        nazione,
                        citta,
                        indirizzo,
                        tipoCucina,
                        delivery,
                        prenotazioneOnline,
                        minPrezzo,
                        maxPrezzo
                ));
            }
            return lista;

        } catch (Exception e) {
            throw new IOException("Errore caricamento ristoranti", e);
        }
    }

    /**
     * Metodo per ottenere la lista di utenti salvati su file con recensioni/ristoranti preferiti/ristoranti gestiti associati
     *
     * @param pathUtenti path al file contenente gli utenti
     * @param pathRistoranti path al file contenente i ristoranti
     * @return lista di utenti
     * @throws IOException eccezione lanciata in caso di errore
     */
    public static List<Utente> caricaUtenti(String pathUtenti, String pathRistoranti) throws IOException {
        try (Reader reader = new FileReader(pathUtenti)){
            //Creazione dei file vuoti se non esistente
            creaFile(pathUtenti, "[]");

            //Liste
            List<Ristorante> ristorantiDisponibili = caricaRistoranti(pathRistoranti);
            List<Utente> utenti = new ArrayList<>();

            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            //Ciclo di salvataggio su variabile
            for (JsonElement elem : array) {
                JsonObject obj = elem.getAsJsonObject();

                //Se l'oggetto non possiede il parametro ruolo lo salto
                if (!obj.has("ruolo")) {
                    continue;
                }

                //Recupero dei dati dell'utente
                int id = obj.get("id").getAsInt();
                String passwordCifrata = obj.get("passwordCifrata").getAsString();
                String nome = obj.get("nome").getAsString();
                String cognome = obj.get("cognome").getAsString();
                String username = obj.get("username").getAsString();
                String dataNascita = obj.has("dataNascita") ? obj.get("dataNascita").getAsString() : "Non data";
                String domicilio = obj.get("domicilio").getAsString();
                String ruolo = obj.get("ruolo").getAsString();

                if(ruolo.equalsIgnoreCase("cliente")){
                    //Creazione dell'istanza cliente corretta
                    Cliente c;
                    if(dataNascita.equals("Non data")){
                        c = new Cliente(
                                id,
                                nome,
                                cognome,
                                username,
                                domicilio,
                                true
                        );
                    }else{
                        c = new Cliente(
                                id,
                                nome,
                                cognome,
                                username,
                                dataNascita,
                                domicilio,
                                true
                        );
                    }
                    c.setPassword(passwordCifrata);

                    //Recupero dei ristoranti preferiti
                    if (obj.has("preferiti")) {
                        List<Ristorante> preferiti = new ArrayList<>();
                        JsonArray preferitiArray = obj.getAsJsonArray("preferiti");
                        for (JsonElement pElem : preferitiArray) {
                            JsonObject object = elem.getAsJsonObject();

                            //Recupero dei dati del ristorante
                            int idTmp = object.get("id").getAsInt();
                            String nomeTmp = object.get("nome").getAsString();
                            String nazioneTmp = object.get("nazione").getAsString();
                            String cittaTmp = object.get("citta").getAsString();
                            String indirizzoTmp = object.get("indirizzo").getAsString();
                            String tipoCucinaTmp = object.get("tipoCucina").getAsString();
                            boolean deliveryTmp =  object.get("delivery").getAsBoolean();
                            boolean prenotazioneOnlineTmp =  object.get("prenotazioneOnline").getAsBoolean();
                            double minPrezzoTmp = object.get("minPrezzo").getAsDouble();
                            double maxPrezzoTmp = object.get("maxPrezzo").getAsDouble();
                            Ristorante r = new Ristorante(
                                    idTmp,
                                    nomeTmp,
                                    nazioneTmp,
                                    cittaTmp,
                                    indirizzoTmp,
                                    tipoCucinaTmp,
                                    deliveryTmp,
                                    prenotazioneOnlineTmp,
                                    minPrezzoTmp,
                                    maxPrezzoTmp
                            );
                            for (Ristorante disp : ristorantiDisponibili) {
                                if (disp.getId() == r.getId()) {
                                    r = disp;
                                    break;
                                }
                            }
                            preferiti.add(r);
                        }
                        c.setPreferiti(preferiti);
                    }

                    //Recupero delle recensioni messe ai ristoranti
                    /*
                    if (obj.has("recensioniMesse")) {
                        List<Recensione> recensioni = new ArrayList<>();
                        JsonArray recensioniArray = obj.getAsJsonArray("recensioniMesse");
                        for (JsonElement rElem : recensioniArray) {
                            Recensione rec = gson.fromJson(rElem, Recensione.class);
                            boolean trovato = false;
                            for (Ristorante ristorante : ristorantiDisponibili) {
                                for (Recensione recReal : ristorante.getRecensioni()) {
                                    if (recReal.equals(rec)) {
                                        rec = recReal;
                                        trovato = true;
                                        break;
                                    }
                                }
                                if (trovato) break;
                            }
                            if (!trovato) {
                                System.out.println("[WARN] Recensione non trovata associata ad alcun ristorante: " + rec);
                            }
                            recensioni.add(rec);
                        }
                        cliente.setRecensioniMesse(recensioni);
                    }
                    */

                    utenti.add(c);
                }else if(ruolo.equalsIgnoreCase("ristoratore")){
                    //Creazione dell'istanza ristoratore corretta
                    Ristoratore r;
                    if(dataNascita.equals("Non data")){
                        r = new Ristoratore(id, nome, cognome, username, domicilio, true);
                    }else{
                        r = new Ristoratore(id, nome, cognome, username, dataNascita, domicilio, true);
                    }
                    r.setPassword(passwordCifrata);

                    //Recupero dei ristoranti gestiti
                    if (obj.has("ristorantiGestiti")) {
                        List<Ristorante> gestiti = new ArrayList<>();
                        JsonArray gestitiArray = obj.getAsJsonArray("ristorantiGestiti");
                        for (JsonElement pElem : gestitiArray) {
                            JsonObject object = elem.getAsJsonObject();

                            //Recupero dei dati del ristorante
                            int idTmp = object.get("id").getAsInt();
                            String nomeTmp = object.get("nome").getAsString();
                            String nazioneTmp = object.get("nazione").getAsString();
                            String cittaTmp = object.get("citta").getAsString();
                            String indirizzoTmp = object.get("indirizzo").getAsString();
                            String tipoCucinaTmp = object.get("tipoCucina").getAsString();
                            boolean deliveryTmp =  object.get("delivery").getAsBoolean();
                            boolean prenotazioneOnlineTmp =  object.get("prenotazioneOnline").getAsBoolean();
                            double minPrezzoTmp = object.get("minPrezzo").getAsDouble();
                            double maxPrezzoTmp = object.get("maxPrezzo").getAsDouble();
                            Ristorante rist = new Ristorante(
                                    idTmp,
                                    nomeTmp,
                                    nazioneTmp,
                                    cittaTmp,
                                    indirizzoTmp,
                                    tipoCucinaTmp,
                                    deliveryTmp,
                                    prenotazioneOnlineTmp,
                                    minPrezzoTmp,
                                    maxPrezzoTmp
                            );
                            for (Ristorante disp : ristorantiDisponibili) {
                                if (disp.getId() == r.getId()) {
                                    rist = disp;
                                    break;
                                }
                            }
                            gestiti.add(rist);
                        }
                    }
                    utenti.add(r);
                }
            }

            return utenti;
        } catch (Exception e) {
            throw new IOException("Errore durante il caricamento degli utenti", e);
        }
    }
}