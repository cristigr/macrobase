package msolver.aria.kusto;

import java.util.*;

public class KustoResponseParser {

    private static String StarCombinationId = "";

    public Map<String, List<KustoDataRow>> ParseCombinationsResponse(String response, boolean onStarRequest) {
        Map<String, List<KustoDataRow>> combinationsToDataRows = new HashMap<>();
        String[] rows = response.split("\n");

        // skips the header and assumes a certain data format: eventInfo_time, percentile001, percentile01, ... percentile999, count - for star queries
        //                                                and: eventInfo_time, percentile001, percentile01, ... percentile999, count, combination - for non-star queries
        for (int i = 1; i < rows.length; i++) {
            String[] columns = rows[i].split(",");

            double[] percentiles = new double[7];
            for (int j = 1; j < 8; j++) {
                percentiles[j-1] = Double.valueOf(columns[j]);
            }
            long count = Double.valueOf(columns[8]).longValue();

            String combinationId = StarCombinationId;
            if (columns.length > 9 && !onStarRequest) {
                combinationId = columns[9];
            }

            combinationsToDataRows.putIfAbsent(combinationId, new ArrayList<>());
            combinationsToDataRows.get(combinationId).add(new KustoDataRow(percentiles, count));
        }

        return combinationsToDataRows;
    }

}
