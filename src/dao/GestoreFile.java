package src.dao;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import src.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestoreFile {

    /**
     * Metodo per adattare i path in base al sistema operativo
     *
     * @param pathParti array di stringhe che rappresentano i vari componenti del path
     * @return stringa che rappresenta il path completo
     */
    public static String adattaPath(String[] pathParti) {
        return String.join(File.separator, pathParti);
    }

    /**
     * Metodo per creare un file con contenuto iniziale se non esiste
     *
     * @param path              path del file da creare
     * @param contenutoIniziale contenuto iniziale del file
     * @throws IOException in caso di errore durante la creazione del file
     */
    private static void creaFile(String path, String contenutoIniziale) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.getParentFile().mkdirs(); // crea le cartelle che contengono il file se non esistono
            try (Writer writer = new FileWriter(file)) {
                writer.write(contenutoIniziale);
            }
        }
    }

    /**
     * Metodo per salvare la lista di ristoranti in un file JSON
     *
     * @param ristoranti lista di ristoranti da salvare
     * @param path       path del file in cui salvare i ristoranti
     * @throws IOException in caso di errore durante la scrittura del file
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
     * Metodo per caricare la lista di ristoranti da un file JSON
     *
     * @param path path del file da cui caricare i ristoranti
     * @return lista di ristoranti caricati
     * @throws IOException in caso di errore durante la lettura del file
     */
    public static List<Ristorante> caricaRistoranti(String path) throws IOException {
        creaFile(path, "[]");
        try (Reader reader = new FileReader(path)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(reader, new TypeToken<List<Ristorante>>() {
            }.getType());
        } catch (Exception e) {
            throw new IOException("Errore durante il caricamento dei ristoranti", e);
        }
    }

    /**
     * Metodo per salvare la lista di utenti in un file JSON
     *
     * @param utenti lista di utenti da salvare
     * @param path   path del file in cui salvare gli utenti
     * @throws IOException in caso di errore durante la scrittura del file
     */
    public static void salvaUtenti(List<Utente> utenti, String path) throws IOException {
        creaFile(path, "[]");
        JsonArray array = new JsonArray();
        for (Utente u : utenti) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement jsonElement = gson.toJsonTree(u);
            if (jsonElement.isJsonObject()) {
                JsonObject obj = jsonElement.getAsJsonObject();
                if (u instanceof Cliente && !obj.has("ruolo")) {
                    obj.addProperty("ruolo", "cliente");
                } else if (u instanceof Ristoratore && !obj.has("ruolo")) {
                    obj.addProperty("ruolo", "ristoratore");
                }
                array.add(obj);
            }
        }
        try (Writer writer = new FileWriter(path, false)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(array, writer);
        } catch (Exception e) {
            throw new IOException("Errore durante il salvataggio degli utenti", e);
        }
    }

    /**
     * Metodo per caricare la lista di utenti da un file JSON, associando correttamente i ristoranti e le recensioni
     *
     * @param pathUtenti     path del file da cui caricare gli utenti
     * @param pathRistoranti path del file da cui caricare i ristoranti
     * @return lista di utenti caricati
     * @throws IOException in caso di errore durante la lettura del file
     */
    public static List<Utente> caricaUtenti(String pathUtenti, String pathRistoranti) throws IOException {
        creaFile(pathUtenti, "[]");
        creaFile(pathRistoranti, "[]");
        List<Utente> utenti = new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Ristorante> ristorantiDisponibili = caricaRistoranti(pathRistoranti);
        try (Reader reader = new FileReader(pathUtenti)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            for (JsonElement elem : array) {
                JsonObject obj = elem.getAsJsonObject();
                if (!obj.has("ruolo")) {
                    System.err.println("Utente senza ruolo specificato: " + obj);
                    continue;
                }
                String ruolo = obj.get("ruolo").getAsString().toLowerCase();
                if (ruolo.equals("cliente")) {
                    Cliente cliente = gson.fromJson(obj, Cliente.class);
                    if (obj.has("preferiti")) {
                        JsonArray preferitiArray = obj.getAsJsonArray("preferiti");
                        List<Ristorante> preferiti = new ArrayList<>();
                        for (JsonElement elemento : preferitiArray) {
                            Ristorante r = gson.fromJson(elemento, Ristorante.class);
                            for (Ristorante disponibile : ristorantiDisponibili) {
                                if (disponibile.getId() == r.getId() ||
                                        (disponibile.getNome().equalsIgnoreCase(r.getNome())
                                                && disponibile.getIndirizzo().equalsIgnoreCase(r.getIndirizzo()))) {
                                    r = disponibile;
                                    break;
                                }
                            }
                            preferiti.add(r);
                        }
                        cliente.setPreferiti(preferiti);
                    }
                    if (obj.has("recensioniMesse")) {
                        JsonArray recensioniArray = obj.getAsJsonArray("recensioniMesse");
                        List<Recensione> recensioni = new ArrayList<>();
                        for (JsonElement elemento : recensioniArray) {
                            Recensione recensione = gson.fromJson(elemento, Recensione.class);
                            // Sincronizza la recensione con quella del ristorante
                            for (Ristorante ristorante : ristorantiDisponibili) {
                                List<Recensione> recensioniRistorante = ristorante.getRecensioni();
                                for (Recensione rec : recensioniRistorante) {
                                    if (rec.equals(recensione)) {
                                        recensione = rec;
                                        break;
                                    }
                                }
                            }
                            recensioni.add(recensione);
                        }
                        cliente.setRecensioniMesse(recensioni);
                    }
                    utenti.add(cliente);
                } else if (ruolo.equals("ristoratore")) {
                    Ristoratore ristoratore = gson.fromJson(obj, Ristoratore.class);
                    if (obj.has("ristorantiGestiti")) {
                        JsonArray gestitiArray = obj.getAsJsonArray("ristorantiGestiti");
                        List<Ristorante> gestiti = new ArrayList<>();
                        for (JsonElement elemento : gestitiArray) {
                            Ristorante r = gson.fromJson(elemento, Ristorante.class);
                            for (Ristorante disponibile : ristorantiDisponibili) {
                                if (disponibile.getId() == r.getId() ||
                                        (disponibile.getNome().equalsIgnoreCase(r.getNome())
                                                && disponibile.getIndirizzo().equalsIgnoreCase(r.getIndirizzo()))) {
                                    r = disponibile;
                                    break;
                                }
                            }
                            gestiti.add(r);
                        }
                        ristoratore.setRistorantiGestiti(gestiti);
                    }
                    utenti.add(ristoratore);
                }
            }
        } catch (Exception e) {
            throw new IOException("Errore durante il caricamento degli utenti", e);
        }
        return utenti;
    }
}
