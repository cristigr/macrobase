package msolver.aria;

import msolver.aria.kusto.KustoClient;
import msolver.aria.kusto.KustoDataRow;
import msolver.aria.kusto.KustoResponseParser;
import msolver.aria.rta.KrakenClient;
import msolver.aria.rta.KrakenResponseParser;
import msolver.aria.rta.RtaDataRow;
import msolver.aria.rta.RtaOnlyMomentsSolver;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class PercentilesSolverChecker {

    private KrakenClient krakenClient;
    private KustoClient kustoClient;

    public PercentilesSolverChecker(KrakenClient krakenClient, KustoClient kustoClient) {
        this.krakenClient = krakenClient;
        this.kustoClient = kustoClient;
    }

    public void comparePercentilesNoKusto() throws Exception {
        String response = krakenClient.getData();
        Map<String, List<RtaDataRow>> combinationsToDataRows = new KrakenResponseParser().ParseCombinationsResponse(response);
        List<RtaDataRow> flatListOfDataRows = combinationsToDataRows.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        solveNoKusto(krakenClient.getCubeId(), krakenClient.getMeasureName(), flatListOfDataRows);
    }

    public void comparePercentiles(boolean onStarRequest) throws Exception {
        String krakenResponse = krakenClient.getData();
        Map<String, List<RtaDataRow>> krakenDataRows = new KrakenResponseParser().ParseCombinationsResponse(krakenResponse);

        String kustoResponse = kustoClient.getData();
        Map<String, List<KustoDataRow>> kustoDataRows = new KustoResponseParser().ParseCombinationsResponse(kustoResponse, onStarRequest);

        AriaDataProvider ariaDataProvider = new AriaDataProvider(krakenDataRows, kustoDataRows);
        List<AriaDataRow> flatListOfAriaDataRows = ariaDataProvider.getDataRows();

        solve(krakenClient.getCubeId(), krakenClient.getMeasureName(), flatListOfAriaDataRows);
    }

    private static void solveNoKusto(String cubeId, String measureName, List<? extends SolvedMomentData> dataRows) {
        RtaOnlyMomentsSolver solver = new RtaOnlyMomentsSolver(dataRows);
        SolverStats solverStats = new SolverStats();
        Map<OperationType, List<PercentileDataPoint>> percentilesWithPoints = solver.solve(solverStats);

        solverStats.PrintSimple(cubeId, measureName, dataRows.size());
        for (Map.Entry<OperationType, List<PercentileDataPoint>> percentileTypeWithPoints : percentilesWithPoints.entrySet()) {
            OperationType percentileType = percentileTypeWithPoints.getKey();
            List<PercentileDataPoint> values = percentileTypeWithPoints.getValue();
            double avgError = PercentileDataPoint.getAvgSolverToRtaRelativeError(values);
            double maxError = PercentileDataPoint.getMaxSolverToRtaRelativeError(values);
            PrintPercentileErrorResult(percentileType, avgError, maxError);
        }
    }

    private static void solve(String cubeId, String measureName, List<? extends SolvedMomentData> dataRows) {
        RtaAndKustoMomentsSolver solver = new RtaAndKustoMomentsSolver(dataRows);
        SolverStats solverStats = new SolverStats();
        Map<OperationType, List<PercentileDataPoint>> percentilesWithPoints = solver.solve(solverStats);

        Map<OperationType, double[]> opTypeToErrors = new TreeMap<>();

        for (Map.Entry<OperationType, List<PercentileDataPoint>> percentileTypeWithPoints : percentilesWithPoints.entrySet()) {
            OperationType percentileType = percentileTypeWithPoints.getKey();
            List<PercentileDataPoint> values = percentileTypeWithPoints.getValue();

            double avgSolverError = PercentileDataPoint.getAvgSolverToKustoRelativeError(values);
            double maxSolverError = PercentileDataPoint.getMaxSolverToKustoRelativeError(values);
            double avgRtaError = PercentileDataPoint.getAvgRtaToKustoRelativeError(values);
            double maxRtaError = PercentileDataPoint.getMaxRtaToKustoRelativeError(values);

            double avgAbsoluteSolverError = PercentileDataPoint.getAvgSolverToKustoAbsoluteError(values);
            double maxAbsoluteSolverError = PercentileDataPoint.getMaxSolverToKustoAbsoluteError(values);
            double avgAbsoluteRtaError = PercentileDataPoint.getAvgRtaToKustoAbsoluteError(values);
            double maxAbsoluteRtaError = PercentileDataPoint.getMaxRtaToKustoAbsoluteError(values);

            opTypeToErrors.put(percentileType, new double[] { avgSolverError, maxSolverError, avgRtaError, maxRtaError, avgAbsoluteSolverError, maxAbsoluteSolverError, avgAbsoluteRtaError, maxAbsoluteRtaError}); }

        // Relative diffs
        solverStats.PrintComplex(cubeId, measureName, dataRows.size());
        for (Map.Entry<OperationType, double[]> entry : opTypeToErrors.entrySet()) {
            OperationType opType = entry.getKey();
            double[] errors = entry.getValue();
            PrintPercentileRelativeIncorportaingKustoErrorResult(opType, errors[0], errors[1], errors[2], errors[3]);
        }
        //Absolute diffs
        solverStats.PrintComplex(cubeId, measureName, dataRows.size());
        for (Map.Entry<OperationType, double[]> entry : opTypeToErrors.entrySet()) {
            OperationType opType = entry.getKey();
            double[] errors = entry.getValue();
            PrintPercentileAbsoluteIncorportaingKustoErrorResult(opType, errors[4], errors[5], errors[6], errors[7]);
        }
        System.out.println();
    }

    private static void PrintPercentileErrorResult(OperationType percentileType, double avgError, double maxError) {
        System.out.println(String.format("Percentile %s  MAX relative error: %f%% \tAVG relative error: %f%%", percentileType, maxError * 100, avgError * 100));
    }

    private static void PrintPercentileRelativeIncorportaingKustoErrorResult(OperationType percentileType, double avgSolverToKusto, double maxSolverToKustoError, double avgRtaToKustoRrror, double maxRtaToKustoError) {
        System.out.printf("%-25s%15s%-25s%40s%15s%-30s%40s\n",
                String.format("Percentile %s", percentileType),
                "SolverToKusto:\t",
                String.format("MAX relative error: %15f%%", maxSolverToKustoError * 100),
                String.format("AVG relative error: %15f%%", avgSolverToKusto * 100),
                "RtaToKusto:\t",
                String.format("MAX relative error: %15f%%", maxRtaToKustoError * 100),
                String.format("AVG relative error: %15f%%", avgRtaToKustoRrror * 100));
    }

    private static void PrintPercentileAbsoluteIncorportaingKustoErrorResult(OperationType percentileType, double avgAbsoluteSolverError, double maxAbsoluteSolverError, double avgAbsoluteRTtaError, double maxAbsoluteRtaError) {
        System.out.printf("%-25s%15s%-25s%40s%15s%-30s%40s\n",
                String.format("Percentile %s", percentileType),
                "SolverToKusto:\t",
                String.format("MAX absolute error: %15f", maxAbsoluteSolverError),
                String.format("AVG absolute error: %15f", avgAbsoluteSolverError),
                "RtaToKusto:\t",
                String.format("MAX absolute error: %15f", maxAbsoluteRtaError),
                String.format("AVG absolute error: %15f", avgAbsoluteRTtaError));
    }

}
