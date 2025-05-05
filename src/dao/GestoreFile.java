package src.dao;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import src.model.Ristorante;
import src.model.Cliente;
import src.model.Ristoratore;
import src.model.Utente;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe per la gestione del salvataggio e caricamento di ristoranti e utenti in formato JSON
 *
 * @version 1.0
 * @Author Strazzullo Ciro Andrea
 * @Author Riccardo Giovanni Rubini
 * @Author Matteo Mongelli
 * @Author Nicolò Valter Girardello
 */
public class GestoreFile {

    /**
     * Metodo statico per il salvataggio dei ristoranti in un file JSON
     * @param ristoranti Lista di ristoranti da salvare
     * @param path Percorso del file in cui salvare i ristoranti
     * @throws IOException  Eccezione lanciata in caso di errore durante il salvataggio
     */
    public static void salvaRistoranti(List<Ristorante> ristoranti, String path) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Path filePath = Paths.get(path);
        try (Writer writer = Files.newBufferedWriter(filePath)) {
            gson.toJson(ristoranti, writer);
        } catch (IOException e) {
            throw new IOException("Errore durante il salvataggio dei ristoranti: " + e.getMessage(), e);
        }
    }

    /**
     * Metodo statico per il caricamento dei ristoranti da un file JSON
     * @param path Percorso del file in cui sono salvati i ristoranti
     * @return Lista di ristoranti caricati
     * @throws IOException Eccezione lanciata in caso di errore durante il caricamento
     */
    public static List<Ristorante> caricaRistoranti(String path) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Path filePath = Paths.get(path);
        try (Reader reader = Files.newBufferedReader(filePath)) {
            return gson.fromJson(reader, new TypeToken<List<Ristorante>>() {}.getType());
        } catch (IOException e) {
            throw new IOException("Errore durante il caricamento dei ristoranti: " + e.getMessage(), e);
        }
    }

    /**
     * Metodo statico per il salvataggio degli utenti in un file JSON
     * @param utenti Lista di utenti da salvare
     * @param path Percorso del file in cui salvare gli utenti
     * @throws IOException Eccezione lanciata in caso di errore durante il salvataggio
     */
    public static void salvaUtenti(List<Utente> utenti, String path) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray array = new JsonArray();
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
        Path filePath = Paths.get(path);
        try (Writer writer = Files.newBufferedWriter(filePath)) {
            gson.toJson(array, writer);
        } catch (IOException e) {
            throw new IOException("Errore durante il salvataggio degli utenti: " + e.getMessage(), e);
        }
    }

    /**
     * Metodo statico per il caricamento degli utenti da un file JSON
     * @param path Percorso del file in cui sono salvati gli utenti
     * @return Lista di utenti caricati
     * @throws IOException Eccezione lanciata in caso di errore durante il caricamento
     */
    public static List<Utente> caricaUtenti(String path) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Utente> utenti = new ArrayList<>();
        Path filePath = Paths.get(path);
        try (Reader reader = Files.newBufferedReader(filePath)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            for (JsonElement elem : array) {
                JsonObject obj = elem.getAsJsonObject();
                String ruolo = obj.get("ruolo").getAsString().toLowerCase();
                if (ruolo.equals("cliente")) {
                    utenti.add(gson.fromJson(obj, Cliente.class));
                } else if (ruolo.equals("ristoratore")) {
                    utenti.add(gson.fromJson(obj, Ristoratore.class));
                } else {
                    System.err.println("Tipo di utente sconosciuto: " + ruolo);
                }
            }
        } catch (IOException e) {
            throw new IOException("Errore durante il caricamento degli utenti: " + e.getMessage(), e);
        }
        return utenti;
    }
}
