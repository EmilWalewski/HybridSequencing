import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Mers {

    private int largestSpectrumSize;
    private Map<Integer, List<String>> spectrum;

    public List<String> generateRandomLMers(int amount, int mersLength) {
        List<String> nukleotids = Stream.of("A", "C", "T", "G").collect(Collectors.toList());

        List<String> mers = new ArrayList<>();
        Random random = new Random();
        System.out.println("Init set:");
        for (int i = 0; i < amount; i++) {
            StringBuilder mer = new StringBuilder();
            for (int j = 0; j < mersLength; j++) {
                mer.append(nukleotids.get(random.nextInt(4)));
            }
            mers.add(mer.toString());
            System.out.println(mer.toString());
        }
        return mers;
    }

    public void createSpectrum(List<String> merList) {

        spectrum = new HashMap<>();
        List<String> copiedMerList;
        for (int i = 0; i < merList.size(); i++) {
            copiedMerList = new ArrayList<>(merList);
            findSpectrum(copiedMerList, i, spectrum);
        }
    }

    private void findSpectrum(List<String> merList, int startMerIndex, Map<Integer, List<String>> spectrum) {
        String startMer = merList.get(startMerIndex);
        merList.remove(startMer);
        List<String> expectedSpectrum = iterateThroughSpace(merList, startMer);
        List<List<String>> sameLengthSpectrum = new ArrayList<>();
        if (spectrum.size() == 0) {
            spectrum.put(0, expectedSpectrum);
        } else {
            for (Map.Entry<Integer, List<String>> spectrumEntrySet : spectrum.entrySet()) {
                List<String> spectrumList = spectrumEntrySet.getValue();
                if (spectrumList.size() < expectedSpectrum.size()) {
                    spectrumEntrySet.setValue(expectedSpectrum);
                    largestSpectrumSize = expectedSpectrum.size();
                    break;
                } else if (spectrumList.size() == expectedSpectrum.size()) {
                    sameLengthSpectrum.add(expectedSpectrum);
                }
            }
            sameLengthSpectrum.forEach(spec -> spectrum.put(spectrum.size(), spec));
            findLargestSpectrum(spectrum);
        }
    }

    private List<String> iterateThroughSpace(List<String> merList, String startMer) {
        String currentMer, hybridMer, startMerTail;
        int currentMerListSize = merList.size(), merListSize = merList.size();
        List<String> spectrumSpace = new ArrayList<>(merList.size());
        spectrumSpace.add(startMer);
        while (merListSize > 0) {
            for (Iterator<String> merListIterator = merList.iterator(); merListIterator.hasNext(); ) {
                currentMer = merListIterator.next();
                startMerTail = startMer.substring(1);
                hybridMer = currentMer.substring(0, startMer.length() - 1);
                if (startMerTail.equals(hybridMer)) {
                    startMer = currentMer;
                    spectrumSpace.add(currentMer);
                    merListIterator.remove();
                    merListSize--;
                }
            }
            if (currentMerListSize == merListSize) {
                return spectrumSpace;
            }
            currentMerListSize = currentMerListSize - (currentMerListSize - merListSize);
        }
        return spectrumSpace;
    }

    private void findLargestSpectrum(Map<Integer, List<String>> spectrum) {
        List<List<String>> largestSpectrum = spectrum.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .filter(merList -> merList.size() == largestSpectrumSize)
                .distinct()
                .collect(Collectors.toList());
        spectrum.clear();
        int counter = 0;
        for (List<String> spec : largestSpectrum){
            spectrum.put(counter++, spec);
        }


//        spectrum = spectrum.entrySet()
//                .stream()
//                .map(Map.Entry::getValue)
//                .filter(merList -> merList.size() == largestSpectrumSize)
//                .distinct()
//                .flatMap(Collection::stream)
//                .collect(Collectors.groupingBy(merList -> (merList.length() / 10) * 10,
//                        Collectors.mapping(p -> p, Collectors.toList())));
    }

    public Map<Integer, List<String>> getSpectrum() {
        return spectrum;
    }
}
