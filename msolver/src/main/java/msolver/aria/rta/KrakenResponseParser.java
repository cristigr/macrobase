package msolver.aria.rta;

import msolver.aria.OperationType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KrakenResponseParser {

    public Map<String, List<RtaDataRow>> ParseCombinationsResponse(String response) {
        Map<String, List<SeriesQueryResult>> rowsForCombination = new HashMap<>();

        JSONObject asJson = new JSONObject(response.toString());
        JSONArray jsonArray = (JSONArray)asJson.get("series");
        for (Object entry : jsonArray) {
            JSONObject asJSON = (JSONObject)entry;
            JSONArray values = (JSONArray)asJSON.get("values");
            List<Double> doubleValues = new ArrayList<>();
            for (Object value : values) {
                doubleValues.add(Double.valueOf(value.toString()));
            }
            OperationType operationType = OperationType.valueOf(asJSON.get("operation").toString());

            String combinationId = asJSON.get("combination").toString();

            List<SeriesQueryResult> seriesQueryResults =
                    rowsForCombination.containsKey(combinationId)
                            ? rowsForCombination.get(combinationId)
                            : new ArrayList<>();

            seriesQueryResults.add(new SeriesQueryResult(doubleValues, operationType));
            rowsForCombination.putIfAbsent(combinationId, seriesQueryResults);
        }

        Map<String, List<RtaDataRow>> combinationsToDataRows = new HashMap<>();
        for (Map.Entry<String, List<SeriesQueryResult>> entry : rowsForCombination.entrySet()) {
            String combinationId = entry.getKey();
            List<SeriesQueryResult> seriesQueryResults = entry.getValue();
            combinationsToDataRows.put(combinationId, SeriesQueryResult.toDataRows(seriesQueryResults));
        }

        return combinationsToDataRows;
    }
}
