package src.view;

import src.model.Recensione;
import src.model.Ristorante;

import java.util.Scanner;

public class ViewBase {
    private static final Scanner scanner = new Scanner(System.in);

    public static void mostraMessaggio(String messaggio) {
        System.out.println(messaggio);
    }

    public static String leggiStringa(String prompt, boolean obbligatorio) {
        while (true) {
            mostraMessaggio(prompt);
            if (!obbligatorio) {
                return scanner.nextLine();
            }
            if (!scanner.hasNextLine() || scanner.nextLine().trim().isEmpty()) {
                mostraMessaggio("Input non valido. Riprova.");
            } else {
                return scanner.nextLine();
            }
        }
    }

    public static int leggiIntero(String prompt) {
        while (true) {
            try {
                mostraMessaggio(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                mostraMessaggio("Input non valido. Inserisci un numero intero.");
            }
        }
    }

    public static double leggiDouble(String prompt, boolean obbligatorio) {
        while (true) {
            try {
                mostraMessaggio(prompt);
                if (!obbligatorio) {
                    return -1;
                } else {
                    if (!scanner.hasNextLine() || scanner.nextLine().trim().isEmpty()) {
                        mostraMessaggio("Input non valido. Riprova.");
                    } else {
                        return Double.parseDouble(scanner.nextLine());
                    }
                }
            } catch (NumberFormatException e) {
                mostraMessaggio("Input non valido. Inserisci un numero decimale.");
            }
        }
    }

    public static void mostraMenuIniziale() {
        mostraMessaggio("""
                       Benvenuto in TheKnife
                        1. Login
                        2. Registrati
                        3. Visualizza ristoranti in modalità guest
                        4. Esci
                """);
    }

    public static void mostraMenuGuest() {
        mostraMessaggio("""
                        1. Visualizza ristoranti vicini al luogo specificato
                        2. Applica filtri di ricerca ai ristoranti vicini
                        3. Modifica luogo
                        4. Esci dalla modalità guest
                """);
    }

    public static void mostraRistorante(Ristorante ristorante) {
        ristorante.visualizzaRistorante();
        mostraMessaggio("---------------Recensioni--------------");
        if (ristorante.getRecensioni().isEmpty())
            mostraMessaggio("Nessuna recensione disponibile per questo ristorante.");
        else
            for (Recensione recensione : ristorante.getRecensioni())
                mostraMessaggio(String.valueOf(recensione));

    }
}
