package src.model.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Classe per la gestione dell'hashing della password
 *
 * @version 1.0
 * @Author Strazzullo Ciro Andrea
 * @Author Riccardo Giovanni Rubini
 * @Author Matteo Mongelli
 * @Author Nicolò Valter Girardello
 */
public class PasswordUtil {
    /**
     * Calcola l'hash di una password utilizzando l'algoritmo SHA-256 (Secure Hash Algorithm 256 ovvero, come output avremo una stringa di 64 caratteri).
     * Questo algoritmo trasforma la password in una stringa appartenente al dominio della funzione hash.
     * Non è possibile risalire alla password originale a partire dall'hash, questo perchè servirebbero
     * grandi capacità di calcolo, garantendo così un elevato livello di sicurezza.
     * @param password la password da convertire in hash
     * @return l'hash della password sotto forma di stringa
     */

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();

            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Errore nell'hash della password", e);
        }
    }
}
