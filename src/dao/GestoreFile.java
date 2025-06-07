package src.dao;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import src.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestoreFile {

    public static String adattaPath(String[] pathParti) {
        return String.join(File.separator, pathParti);
    }

    private static void creaFile(String path, String contenutoIniziale) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try (Writer writer = new FileWriter(file)) {
                writer.write(contenutoIniziale);
                System.out.println("[INFO] Creato file nuovo: " + path);
            }
        }
    }

    public static void salvaRistoranti(List<Ristorante> ristoranti, String path) throws IOException {
        System.out.println("[INFO] Salvataggio ristoranti nel file: " + path);
        creaFile(path, "[]");
        try (Writer writer = new FileWriter(path, false)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(ristoranti, writer);
            System.out.println("[INFO] Salvataggio ristoranti completato con successo.");
        } catch (Exception e) {
            System.err.println("[ERRORE] Errore durante la scrittura dei ristoranti: " + e.getMessage());
            throw new IOException("Errore durante la scrittura dei ristoranti", e);
        }
    }

    public static List<Ristorante> caricaRistoranti(String path) throws IOException {
        System.out.println("[INFO] Caricamento ristoranti dal file: " + path);
        creaFile(path, "[]");
        try (Reader reader = new FileReader(path)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<Ristorante> lista = gson.fromJson(reader, new TypeToken<List<Ristorante>>(){}.getType());
            if (lista == null) {
                System.out.println("[WARN] Lista ristoranti caricata null, ritorno lista vuota.");
                return new ArrayList<>();
            }
            System.out.println("[INFO] Caricamento ristoranti completato. Numero ristoranti: " + lista.size());
            return lista;
        } catch (Exception e) {
            System.err.println("[ERRORE] Errore caricamento ristoranti: " + e.getMessage());
            throw new IOException("Errore caricamento ristoranti", e);
        }
    }

    public static void salvaUtenti(List<Utente> utenti, String path) throws IOException {
        System.out.println("[INFO] Salvataggio utenti nel file: " + path);
        creaFile(path, "[]");
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
                } else {
                    System.out.println("[WARN] Utente con tipo sconosciuto durante il salvataggio: " + u.getClass().getName());
                }
                array.add(obj);
            }
        }

        try (Writer writer = new FileWriter(path, false)) {
            gson.toJson(array, writer);
            System.out.println("[INFO] Salvataggio utenti completato con successo.");
        } catch (Exception e) {
            System.err.println("[ERRORE] Errore durante il salvataggio degli utenti: " + e.getMessage());
            throw new IOException("Errore durante il salvataggio degli utenti", e);
        }
    }

    public static List<Utente> caricaUtenti(String pathUtenti, String pathRistoranti) throws IOException {
        System.out.println("[INFO] Caricamento utenti dal file: " + pathUtenti);
        System.out.println("[INFO] Caricamento ristoranti da associare dal file: " + pathRistoranti);
        creaFile(pathUtenti, "[]");
        creaFile(pathRistoranti, "[]");

        List<Ristorante> ristorantiDisponibili = caricaRistoranti(pathRistoranti);
        List<Utente> utenti = new ArrayList<>();

        try (Reader reader = new FileReader(pathUtenti)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            for (JsonElement elem : array) {
                JsonObject obj = elem.getAsJsonObject();

                if (!obj.has("ruolo")) {
                    System.err.println("[ERRORE] Utente senza campo ruolo: " + obj);
                    continue;
                }
                String ruolo = obj.get("ruolo").getAsString().toLowerCase();

                if (ruolo.equals("cliente")) {
                    System.out.println("[INFO] Caricamento utente Cliente: " + obj.get("nome") + " " + obj.get("cognome"));
                    Cliente cliente = gson.fromJson(obj, Cliente.class);

                    // Preferiti
                    if (obj.has("preferiti")) {
                        List<Ristorante> preferiti = new ArrayList<>();
                        JsonArray preferitiArray = obj.getAsJsonArray("preferiti");
                        for (JsonElement pElem : preferitiArray) {
                            Ristorante r = gson.fromJson(pElem, Ristorante.class);
                            boolean trovato = false;
                            for (Ristorante disp : ristorantiDisponibili) {
                                if (disp.getId() == r.getId() ||
                                        (disp.getNome().equalsIgnoreCase(r.getNome()) && disp.getIndirizzo().equalsIgnoreCase(r.getIndirizzo()))) {
                                    r = disp;
                                    trovato = true;
                                    break;
                                }
                            }
                            if (!trovato) {
                                System.out.println("[WARN] Preferito non trovato tra i ristoranti disponibili: " + r.getNome());
                            }
                            preferiti.add(r);
                        }
                        cliente.setPreferiti(preferiti);
                    }

                    // Recensioni messe
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

                    utenti.add(cliente);

                } else if (ruolo.equals("ristoratore")) {
                    System.out.println("[INFO] Caricamento utente Ristoratore: " + obj.get("nome") + " " + obj.get("cognome"));
                    Ristoratore ristoratore = gson.fromJson(obj, Ristoratore.class);

                    if (obj.has("ristorantiGestiti")) {
                        List<Ristorante> gestiti = new ArrayList<>();
                        JsonArray gestitiArray = obj.getAsJsonArray("ristorantiGestiti");
                        for (JsonElement gElem : gestitiArray) {
                            Ristorante r = gson.fromJson(gElem, Ristorante.class);
                            boolean trovato = false;
                            for (Ristorante disp : ristorantiDisponibili) {
                                if (disp.getId() == r.getId() ||
                                        (disp.getNome().equalsIgnoreCase(r.getNome()) && disp.getIndirizzo().equalsIgnoreCase(r.getIndirizzo()))) {
                                    r = disp;
                                    trovato = true;
                                    break;
                                }
                            }
                            if (!trovato) {
                                System.out.println("[WARN] Ristorante gestito non trovato tra i ristoranti disponibili: " + r.getNome());
                            }
                            gestiti.add(r);
                        }
                        ristoratore.setRistorantiGestiti(gestiti);
                    }

                    utenti.add(ristoratore);

                } else {
                    System.err.println("[ERRORE] Ruolo utente sconosciuto: " + ruolo);
                }
            }
        } catch (Exception e) {
            System.err.println("[ERRORE] Eccezione durante caricamento utenti: " + e.getMessage());
            throw new IOException("Errore durante il caricamento degli utenti", e);
        }

        System.out.println("[INFO] Caricamento utenti completato. Numero utenti: " + utenti.size());
        return utenti;
    }
}