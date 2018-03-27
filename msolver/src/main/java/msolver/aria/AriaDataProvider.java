package msolver.aria;

import msolver.aria.kusto.KustoDataRow;
import msolver.aria.rta.RtaDataRow;

import java.util.*;

public class AriaDataProvider {
    private Map<String, List<RtaDataRow>> rtaData;
    private Map<String, List<KustoDataRow>> kustoData;

    public AriaDataProvider(Map<String, List<RtaDataRow>> rtaData, Map<String, List<KustoDataRow>> kustoData) {
        Set<String> rtaCombinations = new TreeSet(rtaData.keySet());
        Set<String> kustoCombinations = new TreeSet(kustoData.keySet());

        System.out.println("RTA");
        for (String s: rtaCombinations) {
            System.out.println(s);
        }
        System.out.println("KUSTO");
        for (String s: kustoCombinations) {
            System.out.println(s);
        }
        System.out.println("----");

        if (!rtaCombinations.equals(kustoCombinations)) {
            throw new IllegalArgumentException("Rta and Kusto data don't match");
        }
        this.rtaData = rtaData;
        this.kustoData = kustoData;
    }

    public List<AriaDataRow> getDataRows() {
        List<AriaDataRow> ariaDataRows = new ArrayList<>();
        rtaData.forEach((combination, rtaDataRows) -> {
            List<KustoDataRow> kustoDataRows = kustoData.get(combination);
            if (kustoDataRows.size() != rtaDataRows.size()) {
                throw new IllegalArgumentException(String.format("RTA %d and Kusto %d data don't match for combination: "
                        , rtaDataRows.size(), kustoDataRows.size(), combination));
            }
            for (int i = 0; i < rtaDataRows.size(); i++) {
                ariaDataRows.add(new AriaDataRow(rtaDataRows.get(i), kustoDataRows.get(i)));
            }
        });

        return ariaDataRows;
    }

}
