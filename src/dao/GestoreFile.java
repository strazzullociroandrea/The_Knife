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

public class GestoreFile {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Metodo per adattare i path in base al sistema operativo
     * @param pathParti array di stringhe che rappresentano i vari componenti del path
     * @return stringa che rappresenta il path completo
     */
    public static String adattaPath(String[] pathParti) {
        return String.join(File.separator, pathParti);
    }

    public static void salvaRistoranti(List<Ristorante> ristoranti, String path) throws IOException {
        try (Writer writer = new FileWriter(path)) {
            gson.toJson(ristoranti, writer);
        }
    }

    public static List<Ristorante> caricaRistoranti(String path) throws IOException {
        try (Reader reader = new FileReader(path)) {
            return gson.fromJson(reader, new TypeToken<List<Ristorante>>() {}.getType());
        }
    }

    public static void salvaUtenti(List<Utente> utenti, String path) throws IOException {
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

        try (Writer writer = new FileWriter(path)) {
            gson.toJson(array, writer);
        }
    }


    public static List<Utente> caricaUtenti(String path) throws IOException {
        List<Utente> utenti = new ArrayList<>();

        try (Reader reader = new FileReader(path)) {
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
        }

        return utenti;
    }
}