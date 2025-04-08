package src.Ciro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.Gson;

public class ReverseGeocoding {
    /**
     * Metodo per ottenere la latitudine e longitudine da un indirizzo
     * @param indirizzo Indirizzo da analizzare il formato obbligatorio è "via, città, nazione"
     * @return Array di double con latitudine e longitudine nel formato [latitudine, longitudine]
     * @throws Exception Viene lanciata un'eccezione generare nel caso di errore
     */
    public static double[] getLatitudineLongitudine(String indirizzo) throws Exception {
        try {
            //Richiesta http al servizio di reverse da indirizzo a latitudine e longitudine
            String encodedAddress = URLEncoder.encode(indirizzo, "UTF-8");
            String urlStr = "https://nominatim.openstreetmap.org/search?q=" + encodedAddress + "&format=json&limit=1";
            URL url = new URL(urlStr);
            HttpURLConnection httpconnection = (HttpURLConnection) url.openConnection();
            httpconnection.setRequestMethod("GET");
            httpconnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Java test)");
            //Lettura del buffer di risposta
            BufferedReader in = new BufferedReader(new InputStreamReader(httpconnection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null)
                responseBuilder.append(line);
            in.close();
            String response = responseBuilder.toString();
            Gson gson = new Gson();
            //Trasforma la risposta json in un oggetto Result
            Result[] results = gson.fromJson(response, Result[].class);
            if (results.length > 0) {
                double lat = Double.parseDouble(results[0].lat);
                double lon = Double.parseDouble(results[0].lon);
                return new double[]{lat, lon};
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception("Errore durante il geocoding");
        }
    }
}
