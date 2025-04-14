package src.Nico;

import src.Ciro.ReverseGeocoding;

import java.util.Scanner;

import java.util.ArrayList;

public class testRistorante {
    public static void main(String[] args) throws Exception {
        String nome;
        String nazione;
        String citta;
        String indirizzo;
        double lat;
        double log;
        double min;
        double max;
        boolean delivery;
        boolean prenotazione;
        String tipoCucina;
        String tmps;
        boolean risp;
        boolean risp2;
        Scanner var1 = new Scanner(System.in);
        System.out.println("Dimmi in che nazione vorresti trovare il ristorante:");
        nazione = var1.nextLine();
        System.out.println("Dimmi in che citta vorresti trovare il ristorante:");
        citta = var1.nextLine();
        System.out.println("Vicino a quale via vorresti trovare questo ristorante");
        indirizzo = var1.nextLine();
        String posizione = indirizzo+", "+citta+", "+nazione+".";
        double[] coordinate = ReverseGeocoding.getLatitudineLongitudine(posizione);
        lat = coordinate[0];
        log = coordinate[1];
        System.out.println("Quale tipologia di cucina vorresti offrisse?");
        tipoCucina = var1.nextLine();
        System.out.println("Qual' è il minimo che vorresti spendere?");
        min = var1.nextDouble();
        System.out.println("Qual' è il massimo che vorresti spendere?");
        tmps = var1.nextLine();
        if (tmps.equalsIgnoreCase("si")){
            delivery = true;
            risp = true;
        } else if (tmps.equalsIgnoreCase("no")) {
            delivery = false;
            risp = true;
        }
        else if (tmps.equalsIgnoreCase("")) {
            delivery = false;
            risp = false;
        }
        System.out.println("vorresti che ci sia la possibilità di prenotare?");
        tipoCucina = var1.nextLine();if (tmps.equalsIgnoreCase("si")){
            prenotazione = true;
            risp2 = true;
        } else if (tmps.equalsIgnoreCase("no")) {
            prenotazione = false;
            risp2 = true;
        }
        else if (tmps.equalsIgnoreCase("")) {
            prenotazione = false;
            risp2 = false;
        }
    }
}
