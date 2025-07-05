package src.dao;

import com.google.gson.*;
import src.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Classe GestoreFile per gestire la lettura e scrittura di dati su file
 *
 * @version 1.0
 * @Author Strazzullo Ciro Andrea
 * @Author Riccardo Giovanni Rubini
 * @Author Matteo Mongelli
 */
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
        creaFile(path, "[]");
        try (Writer writer = new FileWriter(path, false)) {
            JsonArray array = new JsonArray();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
        creaFile(path, "[]");
        try (Reader reader = new FileReader(path)) {

            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();

            List<Ristorante> lista = new ArrayList<>();

            int maxId = -1;

            for (JsonElement elem : array) {
                JsonObject obj = elem.getAsJsonObject();

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
        creaFile(pathUtenti, "[]");
        creaFile(pathRistoranti, "[]");

        try (Reader readerUtenti = new FileReader(pathUtenti)) {

            List<Ristorante> ristorantiDisponibili = caricaRistoranti(pathRistoranti);
            Map<String, Utente> mappaUtenti = new HashMap<>();
            Map<Integer, Ristorante> mappaRistoranti = new HashMap<>();
            Map<Integer, Recensione> mappaRecensioni = new HashMap<>();

            for (Ristorante r : ristorantiDisponibili) {
                mappaRistoranti.put(r.getId(), r);
                for (Recensione rec : r.getRecensioni()) {
                    mappaRecensioni.put(rec.getId(), rec);
                }
            }

            JsonArray arrayUtenti = JsonParser.parseReader(readerUtenti).getAsJsonArray();

            for (JsonElement elem : arrayUtenti) {
                JsonObject obj = elem.getAsJsonObject();

                if (!obj.has("ruolo")) continue;

                String username = obj.get("username").getAsString();
                String ruolo = obj.get("ruolo").getAsString();

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

            for (JsonElement elem : arrayUtenti) {
                JsonObject obj = elem.getAsJsonObject();

                if (!obj.has("ruolo")) continue;

                String username = obj.get("username").getAsString();
                String ruolo = obj.get("ruolo").getAsString();

                Utente utente = mappaUtenti.get(username);
                if (utente == null) continue;

                if (ruolo.equalsIgnoreCase("cliente") && utente instanceof Cliente) {
                    Cliente cliente = (Cliente) utente;

                    if (obj.has("recensioniMesse")) {
                        JsonArray recensioniArray = obj.getAsJsonArray("recensioniMesse");

                        for (JsonElement pElem : recensioniArray) {
                            JsonObject recensioneObj = pElem.getAsJsonObject();

                            int idRecensione = recensioneObj.has("id") ? recensioneObj.get("id").getAsInt() : -1;
                            String descrizione = recensioneObj.get("descrizione").getAsString();
                            int stelle = recensioneObj.get("stelle").getAsInt();

                            Recensione recensioneTrovata = null;
                            if (idRecensione != -1) {
                                recensioneTrovata = mappaRecensioni.get(idRecensione);
                            }

                            if (recensioneTrovata == null) {
                                for (Recensione r : mappaRecensioni.values()) {
                                    if (r.getDescrizione().equals(descrizione) && r.getStelle() == stelle) {
                                        recensioneTrovata = r;
                                        break;
                                    }
                                }
                            }

                            if (recensioneTrovata != null) {
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

                    if (obj.has("preferiti") && obj.getAsJsonArray("preferiti").size() > 0) {
                        JsonArray preferitiArray = obj.getAsJsonArray("preferiti");
                        for (JsonElement prefElem : preferitiArray) {
                            JsonObject prefObj = prefElem.getAsJsonObject();
                            int idRistorante = prefObj.get("id").getAsInt();

                            Ristorante ristorante = mappaRistoranti.get(idRistorante);
                            if (ristorante != null) {
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

                    if (obj.has("ristorantiGestiti") && obj.getAsJsonArray("ristorantiGestiti").size() > 0) {
                        JsonArray ristorantiArray = obj.getAsJsonArray("ristorantiGestiti");
                        for (JsonElement ristElem : ristorantiArray) {
                            JsonObject ristObj = ristElem.getAsJsonObject();
                            int idRistorante = ristObj.get("id").getAsInt();

                            Ristorante ristorante = mappaRistoranti.get(idRistorante);
                            if (ristorante != null) {
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