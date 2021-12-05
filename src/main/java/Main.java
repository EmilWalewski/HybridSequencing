import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Mers mers = new Mers();
        List<String> merList = mers.generateRandomLMers(100, 3);
//        List<String> merList = Arrays.asList("ACG","CAC", "CGA", "TTC", "GTT", "AAT", "TCA", "GAA", "CTA");

        mers.createSpectrum(merList);

        System.out.println("Created spectrum:");
        String space = "";
        for (Map.Entry<Integer, List<String>> mersEntrySet : mers.getSpectrum().entrySet()){
            for (String mer : mersEntrySet.getValue()) {
                System.out.println(space + mer);
                space += " ";
            }
            System.out.println("Mer list size: "+mersEntrySet.getValue().size());
            System.out.println();
            space = "";
        }
    }
}
