package src.dao;

import com.google.gson.*;
import src.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                throw new IOException("Non è stato possibile creare il file da te richiesto: " + e.getMessage());
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
            throw new IOException("Errore durante la scrittura dei ristoranti: "  + e.getMessage());
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
        //Creazione del file vuoto se non esistente
        creaFile(path, "[]");
        try (Writer writer = new FileWriter(path, false)) {
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
            throw new IOException("Errore durante il salvataggio degli utenti: " + e.getMessage());
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
        //Creazione del file vuoto se non esistente
        creaFile(path, "[]");
        try (Reader reader = new FileReader(path)) {

            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();

            //Lista
            List<Ristorante> lista = new ArrayList<>();

            // Teniamo traccia dell'ID massimo per aggiornare il contatore
            int maxId = -1;

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

                // Aggiorniamo il massimo ID trovato
                if (id > maxId) {
                    maxId = id;
                }

                Ristorante ristorante  = new Ristorante(
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
                );

                // CARICAMENTO RECENSIONI DAL JSON
                if (obj.has("recensioni")) {
                    JsonArray recensioniArray = obj.getAsJsonArray("recensioni");
                    for (JsonElement recElem : recensioniArray) {
                        JsonObject recObj = recElem.getAsJsonObject();
                        String descrizione = recObj.get("descrizione").getAsString();
                        int stelle = recObj.get("stelle").getAsInt();
                        int idRec = recObj.get("id").getAsInt();
                        String risposta = recObj.has("risposta") ? recObj.get("risposta").getAsString() : "";

                        try {
                            Recensione recensione;
                            if (risposta.isEmpty()) {
                                recensione = new Recensione(descrizione, stelle, idRec);
                            } else {
                                recensione = new Recensione(descrizione, stelle, risposta, idRec);
                            }
                            ristorante.recensisciRistorante(recensione);
                        } catch (Exception e) {
                            System.err.println("Errore nel caricamento recensione: " + e.getMessage());
                        }
                    }
                }

                lista.add(ristorante);
            }

            // Aggiorniamo il contatore statico nella classe Ristorante per garantire ID univoci
            if (maxId >= 0) {
                Ristorante.aggiornaContatore(maxId);
            }

            return lista;

        } catch (Exception e) {
            throw new IOException("Errore caricamento ristoranti: "  + e.getMessage());
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
        // Creazione dei file vuoti se non esistenti
        creaFile(pathUtenti, "[]");
        creaFile(pathRistoranti, "[]");

        try (Reader readerUtenti = new FileReader(pathUtenti)) {

            // Carichiamo i ristoranti con le loro recensioni già caricate
            List<Ristorante> ristorantiDisponibili = caricaRistoranti(pathRistoranti);
            Map<String, Utente> mappaUtenti = new HashMap<>();
            Map<Integer, Ristorante> mappaRistoranti = new HashMap<>();
            Map<Integer, Recensione> mappaRecensioni = new HashMap<>();

            // Popoliamo la mappa dei ristoranti e delle recensioni per accesso rapido
            for (Ristorante r : ristorantiDisponibili) {
                mappaRistoranti.put(r.getId(), r);
                // Aggiungiamo tutte le recensioni del ristorante alla mappa globale
                for (Recensione rec : r.getRecensioni()) {
                    mappaRecensioni.put(rec.getId(), rec);
                }
            }

            JsonArray arrayUtenti = JsonParser.parseReader(readerUtenti).getAsJsonArray();

            // Prima passiamo e creiamo tutti gli utenti unici per username
            for (JsonElement elem : arrayUtenti) {
                JsonObject obj = elem.getAsJsonObject();

                if (!obj.has("ruolo")) continue;

                String username = obj.get("username").getAsString();
                String ruolo = obj.get("ruolo").getAsString();

                // Se l'utente esiste già nella mappa, passiamo al prossimo
                if (mappaUtenti.containsKey(username)) {
                    continue;
                }

                int id = obj.get("id").getAsInt();
                String passwordCifrata = obj.get("passwordCifrata").getAsString();
                String nome = obj.get("nome").getAsString();
                String cognome = obj.get("cognome").getAsString();
                String dataNascita = obj.has("dataNascita") ? obj.get("dataNascita").getAsString() : "Non data";
                String domicilio = obj.get("domicilio").getAsString();

                Utente utente;
                if (ruolo.equalsIgnoreCase("cliente")) {
                    Cliente cliente = dataNascita.equals("Non data")
                            ? new Cliente(id, nome, cognome, username, domicilio, true)
                            : new Cliente(id, nome, cognome, username, dataNascita, domicilio, true);
                    cliente.setPassword(passwordCifrata);
                    utente = cliente;
                    mappaUtenti.put(username, utente);
                } else if (ruolo.equalsIgnoreCase("ristoratore")) {
                    Ristoratore ristoratore = dataNascita.equals("Non data")
                            ? new Ristoratore(id, nome, cognome, username, domicilio, true)
                            : new Ristoratore(id, nome, cognome, username, dataNascita, domicilio, true);
                    ristoratore.setPassword(passwordCifrata);
                    utente = ristoratore;
                    mappaUtenti.put(username, utente);
                }
            }

            // Ora passiamo nuovamente sull'array per aggiungere i dati specifici (recensioni, ristoranti gestiti, ecc.)
            for (JsonElement elem : arrayUtenti) {
                JsonObject obj = elem.getAsJsonObject();

                if (!obj.has("ruolo")) continue;

                String username = obj.get("username").getAsString();
                String ruolo = obj.get("ruolo").getAsString();

                // Otteniamo l'utente dalla mappa
                Utente utente = mappaUtenti.get(username);
                if (utente == null) continue;

                if (ruolo.equalsIgnoreCase("cliente") && utente instanceof Cliente) {
                    Cliente cliente = (Cliente) utente;

                    // Aggiungiamo le recensioni se presenti
                    if (obj.has("recensioniMesse")) {
                        JsonArray recensioniArray = obj.getAsJsonArray("recensioniMesse");

                        for (JsonElement pElem : recensioniArray) {
                            JsonObject recensioneObj = pElem.getAsJsonObject();

                            // Nel JSON delle recensioni utente, cerchiamo di associarle a quelle già caricate dai ristoranti
                            int idRecensione = recensioneObj.has("id") ? recensioneObj.get("id").getAsInt() : -1;
                            String descrizione = recensioneObj.get("descrizione").getAsString();
                            int stelle = recensioneObj.get("stelle").getAsInt();

                            // Cerchiamo la recensione corrispondente tra quelle già caricate dai ristoranti
                            Recensione recensioneTrovata = null;
                            if (idRecensione != -1) {
                                recensioneTrovata = mappaRecensioni.get(idRecensione);
                            }

                            // Se non la troviamo per ID, cerchiamo per descrizione e stelle
                            if (recensioneTrovata == null) {
                                for (Recensione r : mappaRecensioni.values()) {
                                    if (r.getDescrizione().equals(descrizione) && r.getStelle() == stelle) {
                                        recensioneTrovata = r;
                                        break;
                                    }
                                }
                            }

                            // Se abbiamo trovato la recensione, la aggiungiamo al cliente
                            if (recensioneTrovata != null) {
                                // Verifichiamo che non sia già presente
                                boolean giaPresente = false;
                                for (Recensione r : cliente.getRecensioniMesse()) {
                                    if (r.getId() == recensioneTrovata.getId()) {
                                        giaPresente = true;
                                        break;
                                    }
                                }
                                if (!giaPresente) {
                                    cliente.getRecensioniMesse().add(recensioneTrovata);
                                }
                            }
                        }
                    }

                    // Aggiungiamo i preferiti se presenti
                    if (obj.has("preferiti") && obj.getAsJsonArray("preferiti").size() > 0) {
                        JsonArray preferitiArray = obj.getAsJsonArray("preferiti");
                        for (JsonElement prefElem : preferitiArray) {
                            JsonObject prefObj = prefElem.getAsJsonObject();
                            int idRistorante = prefObj.get("id").getAsInt();

                            // Cerchiamo il ristorante corrispondente usando la mappa
                            Ristorante ristorante = mappaRistoranti.get(idRistorante);
                            if (ristorante != null) {
                                // Verifichiamo se il ristorante è già nei preferiti
                                boolean giaPreferito = false;
                                for (Ristorante r : cliente.visualizzaPreferiti()) {
                                    if (r.getId() == ristorante.getId()) {
                                        giaPreferito = true;
                                        break;
                                    }
                                }
                                if (!giaPreferito) {
                                    cliente.aggiungiPreferito(ristorante);
                                }
                            }
                        }
                    }
                } else if (ruolo.equalsIgnoreCase("ristoratore") && utente instanceof Ristoratore) {
                    Ristoratore ristoratore = (Ristoratore) utente;

                    // Aggiungiamo i ristoranti gestiti se presenti
                    if (obj.has("ristorantiGestiti") && obj.getAsJsonArray("ristorantiGestiti").size() > 0) {
                        JsonArray ristorantiArray = obj.getAsJsonArray("ristorantiGestiti");
                        for (JsonElement ristElem : ristorantiArray) {
                            JsonObject ristObj = ristElem.getAsJsonObject();
                            int idRistorante = ristObj.get("id").getAsInt();

                            // Cerchiamo il ristorante corrispondente usando la mappa
                            Ristorante ristorante = mappaRistoranti.get(idRistorante);
                            if (ristorante != null) {
                                // Verifichiamo se il ristorante è già gestito
                                boolean giaGestito = false;
                                for (Ristorante r : ristoratore.getRistorantiGestiti()) {
                                    if (r.getId() == ristorante.getId()) {
                                        giaGestito = true;
                                        break;
                                    }
                                }

                                if (!giaGestito) {
                                    ristoratore.aggiungiRistorante(ristorante);
                                }
                            }
                        }
                    }
                }
            }

            return new ArrayList<>(mappaUtenti.values());

        } catch (Exception e) {
            throw new IOException("Errore durante il caricamento degli utenti: " + e.getMessage(), e);
        }
    }


}