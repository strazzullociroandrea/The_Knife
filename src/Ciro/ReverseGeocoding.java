package src.Ciro;

import java.io.BufferedReader;
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
 * @Author Nicolò Valter Girardello
 */
public class ReverseGeocoding {
    /**
     * Metodo per ottenere la latitudine e longitudine da un indirizzo
     * @param indirizzo Indirizzo da analizzare il formato obbligatorio è "via, città, nazione"
     * @return Array di double con latitudine e longitudine nel formato [latitudine, longitudine], restituisce un array di -1 nel caso in cui non viene trovato l'indirizzo
     * @throws Exception Viene lanciata un'eccezione generare nel caso di errore
     */
    public static double[] getLatitudineLongitudine(String indirizzo) throws Exception {
        try {
            //Richiesta http al servizio di reverse da indirizzo a latitudine e longitudine
            String encodedAddress = URLEncoder.encode(indirizzo, "UTF-8");
            String urlStr = "https://nominatim.openstreetmap.org/search?q=" + encodedAddress + "&format=json&limit=1";
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Java test)");
            //Lettura del buffer di risposta
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null)
                responseBuilder.append(line);
            in.close();
            String response = responseBuilder.toString();
            Gson gson = new Gson();
            //Trasformo una stringa array di json  in un array di json
            JsonObject[] jsonObject = gson.fromJson(response, JsonObject[].class);
            if (jsonObject.length > 0)
                return new double[]{jsonObject[0].get("lat").getAsDouble(),jsonObject[0].get("lon").getAsDouble()};
            else
                return new double[]{-1,-1};
        } catch (Exception e) {
            throw new Exception("Errore durante il geocoding");
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
        try{
            //Richiesta http al servizio di reverse da latitudine e longitudine a indirizzo
            String urlStr = "https://nominatim.openstreetmap.org/reverse?lat=" + latitudine + "&lon=" + longitudine + "&format=json&addressdetails=1";
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Java test)");
            // Lettura del buffer di risposta
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null)
                responseBuilder.append(line);
            in.close();
            String response = responseBuilder.toString();
            //trasformo una stringa json in un json
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
            if(!(jsonObject.get("display_name").getAsString().isEmpty() || jsonObject.get("display_name").getAsString() == null))
                return jsonObject.get("display_name").getAsString();
            return "Nessun indirizzo trovato dalle coordinate inserite: latitudine: " + latitudine + " longitudine: " + longitudine;
        }catch(Exception e){
            throw new Exception("Errore durante il reverse geocoding da coordinate a indirizzo " + e.getMessage());
        }
    }
}
