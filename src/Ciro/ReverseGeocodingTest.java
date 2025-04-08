package src.Ciro;

public class ReverseGeocodingTest {
    public static void main(String[] args) throws Exception {
        for(double val: ReverseGeocoding.getLatitudineLongitudine("Via Gran Paradiso, 4 Brugherio, MB, Italia")){
            System.out.println(val);
        }
    }
}
