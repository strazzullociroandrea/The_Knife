package src.dao;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import src.model.Ristorante;
import src.model.Cliente;
import src.model.Ristoratore;
import src.model.Utente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestoreFile {

    /**
     * Metodo per adattare i path in base al sistema operativo
     * @param pathParti array di stringhe che rappresentano i vari componenti del path
     * @return stringa che rappresenta il path completo
     */
    public static String adattaPath(String[] pathParti) {
        return String.join(File.separator, pathParti);
    }

    /**
     * Metodo per salvare la lista di ristoranti in un file JSON
     * @param ristoranti lista di ristoranti da salvare
     * @param path path del file in cui salvare i ristoranti
     * @throws IOException in caso di errore durante la scrittura del file
     */
    public static void salvaRistoranti(List<Ristorante> ristoranti, String path) throws IOException {
        try (Writer writer = new FileWriter(path)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(ristoranti, writer);
        }catch(Exception e){
            throw new IOException("Errore durante la scrittura dei ristoranti");
        }
    }

    /**
     * Metodo per caricare la lista di ristoranti da un file JSON
     * @param path path del file da cui caricare i ristoranti
     * @return lista di ristoranti caricati
     * @throws IOException in caso di errore durante la lettura del file
     */
    public static List<Ristorante> caricaRistoranti(String path) throws IOException {
        try (Reader reader = new FileReader(path)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(reader, new TypeToken<List<Ristorante>>() {}.getType());
        }catch(Exception e){
            throw new IOException("Errore durante il caricamento dei ristoranti");
        }
    }

    /**
     * Metodo per salvare la lista di utenti in un file JSON
     * @param utenti lista di utenti da salvare
     * @param path path del file in cui salvare gli utenti
     * @throws IOException in caso di errore durante la scrittura del file
     */
    public static void salvaUtenti(List<Utente> utenti, String path) throws IOException {
        JsonArray array = new JsonArray();
        for (Utente u : utenti) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
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

        try (Writer writer = new FileWriter(path)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(array, writer);
        }catch(Exception e){
            throw new IOException("Errore durante il salvataggio degli utenti");
        }
    }


    /**
     * Metodo per caricare la lista di utenti da un file JSON
     * @param path path del file da cui caricare gli utenti
     * @return lista di utenti caricati
     * @throws IOException in caso di errore durante la lettura del file
     */
    public static List<Utente> caricaUtenti(String path) throws IOException {
        List<Utente> utenti = new ArrayList<>();

        try (Reader reader = new FileReader(path)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement elem : array) {
                JsonObject obj = elem.getAsJsonObject();
                String ruolo = obj.get("ruolo").getAsString().toLowerCase();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                if (ruolo.equals("cliente")) {
                    utenti.add(gson.fromJson(obj, Cliente.class));
                } else if (ruolo.equals("ristoratore")) {
                    utenti.add(gson.fromJson(obj, Ristoratore.class));
                } else {
                    System.err.println("Tipo di utente sconosciuto: " + ruolo);
                }
            }
        }catch(Exception e){
            throw new IOException("Errore durante il caricamento degli utenti");
        }

        return utenti;
    }
}