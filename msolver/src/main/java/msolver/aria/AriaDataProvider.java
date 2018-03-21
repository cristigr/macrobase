package msolver.aria;

import msolver.aria.kusto.KustoDataRow;
import msolver.aria.rta.RtaDataRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AriaDataProvider {
    private Map<String, List<RtaDataRow>> rtaData;
    private Map<String, List<KustoDataRow>> kustoData;

    public AriaDataProvider(Map<String, List<RtaDataRow>> rtaData, Map<String, List<KustoDataRow>> kustoData) {
        Set<String> rtaCombinations = rtaData.keySet();
        Set<String> kustoCombinations = kustoData.keySet();
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
                throw new IllegalArgumentException("RTA and Kusto data don't match for combination: " + combination);
            }
            for (int i = 0; i < rtaDataRows.size(); i++) {
                ariaDataRows.add(new AriaDataRow(rtaDataRows.get(i), kustoDataRows.get(i)));
            }
        });

        return ariaDataRows;
    }

}
