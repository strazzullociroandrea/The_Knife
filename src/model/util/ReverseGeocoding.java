package src.model.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Classe per la gestione del reverse geocoding, da longitudine e latitudine a indirizzo e viceversa
 *
 * @version 1.0
 * @Author Strazzullo Ciro Andrea
 * @Author Riccardo Giovanni Rubini
 * @Author Matteo Mongelli
 */
public class ReverseGeocoding {
    /**
     * Metodo per ottenere la latitudine e longitudine da un indirizzo
     * @param indirizzo Indirizzo da analizzare il formato obbligatorio è "via, città, nazione"
     * @return Array di double con latitudine e longitudine nel formato [latitudine, longitudine], restituisce un array di -1 nel caso in cui non viene trovato l'indirizzo
     * @throws Exception Viene lanciata un'eccezione generare nel caso di errore
     */
    public static double[] getLatitudineLongitudine(String indirizzo) throws Exception {
        if (indirizzo == null || indirizzo.trim().isEmpty()) {
            throw new IllegalArgumentException("Indirizzo non valido.");
        }

        try {
            String encodedAddress = URLEncoder.encode(indirizzo, "UTF-8");
            String urlStr = "https://nominatim.openstreetmap.org/search?q=" + encodedAddress + "&format=json&limit=1";
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Java test)");
            connection.setConnectTimeout(5000); // 5 secondi
            connection.setReadTimeout(5000);    // 5 secondi
            int status = connection.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                throw new IOException("Errore HTTP: " + status);
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                responseBuilder.append(line);
            }
            in.close();
            String response = responseBuilder.toString();
            Gson gson = new Gson();
            JsonObject[] results = gson.fromJson(response, JsonObject[].class);
            if (results.length == 0) {
                return new double[]{-1, -1}; // Nessun risultato
            }
            JsonObject location = results[0];
            if (!location.has("lat") || !location.has("lon")) {
                return new double[]{-1, -1};
            }

            double lat = location.get("lat").getAsDouble();
            double lon = location.get("lon").getAsDouble();
            return new double[]{lat, lon};

        } catch (Exception e) {
            throw new Exception("Errore durante il geocoding: " + e.getMessage(), e);
        }
    }


    /**
     * Metodo per ottenere l'indirizzo da latitudine e longitudine
     * @param latitudine latitudine dell'indirizzo, il formato obbligatorio è "xx.xxxxx"
     * @param longitudine longitudine dell'indirizzo, il formato obbligatorio è "xx.xxxxx"
     * @return  String con l'indirizzo trovato, restituisce una stringa di errore nel caso in cui non viene trovato l'indirizzo
     * @throws Exception Viene lanciata un'eccezione generare nel caso di errore
     */
    public static String getIndirizzoDaCoordinate(double latitudine, double longitudine) throws Exception {
        try {
            String urlStr = "https://nominatim.openstreetmap.org/reverse?lat=" + latitudine + "&lon=" + longitudine + "&format=json&addressdetails=1";
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Java test)");
            connection.setConnectTimeout(5000); // 5 secondi
            connection.setReadTimeout(5000);    // 5 secondi
            int status = connection.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                throw new IOException("Errore HTTP: " + status);
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                responseBuilder.append(line);
            }
            in.close();
            String response = responseBuilder.toString();
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
            if (jsonObject == null || !jsonObject.has("display_name")) {
                return "Nessun indirizzo trovato per latitudine: " + latitudine + ", longitudine: " + longitudine;
            }
            String displayName = jsonObject.get("display_name").getAsString();
            if (displayName == null || displayName.trim().isEmpty()) {
                return "Nessun indirizzo trovato per latitudine: " + latitudine + ", longitudine: " + longitudine;
            }
            return displayName;
        } catch (Exception e) {
            throw new Exception("Errore durante il reverse geocoding da coordinate a indirizzo: " + e.getMessage(), e);
        }
    }

}
