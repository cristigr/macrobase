package msolver.aria;

import msolver.MomentSolverBuilder;
import msolver.aria.rta.RtaDataRow;
import msolver.data.MomentData;
import msolver.struct.MomentStruct;
import org.apache.commons.math3.exception.NoBracketingException;

import java.util.*;

public class RtaAndKustoMomentsSolver {

    private static double[] EstimatedPercentiles = new double[] {0.001, 0.01, 0.05, 0.5, 0.95, 0.99, 0.999};

    private List<? extends SolvedMomentData> dataRows;

    public RtaAndKustoMomentsSolver(List<? extends SolvedMomentData> dataRows) {
        this.dataRows = dataRows;
    }

    public SortedMap<OperationType, List<PercentileDataPoint>> solve(SolverStats solverStats) {

        SortedMap<OperationType, List<PercentileDataPoint>> result = new TreeMap<OperationType, List<PercentileDataPoint>>() {
            {
                put(OperationType.Percentile001, new ArrayList<>());
            }
            {
                put(OperationType.Percentile01, new ArrayList<>());
            }
            {
                put(OperationType.Percentile05, new ArrayList<>());
            }
            {
                put(OperationType.Percentile50, new ArrayList<>());
            }
            {
                put(OperationType.Percentile95, new ArrayList<>());
            }
            {
                put(OperationType.Percentile99, new ArrayList<>());
            }
            {
                put(OperationType.Percentile999, new ArrayList<>());
            }
        };

        for (SolvedMomentData dataRow : dataRows) {
            if (dataRow.getKustoCount() != dataRow.getRtaCount()) {
                solverStats.addAriaDataMismatchError();
                continue;
            } else {
                solverStats.addRawEventsCount(dataRow.getRtaCount());
            }

            MomentStruct m = new MomentStruct();
            m.min = dataRow.getMin();
            m.max = dataRow.getMax();
            m.powerSums = dataRow.getPowerSums(10);

            MomentSolverBuilder builder = new MomentSolverBuilder(m);
            builder.setVerbose(false);
            builder.initialize();

            long startTime = System.currentTimeMillis();

            boolean flag = builder.checkThreshold(2, .01) && builder.checkThreshold(4, .01);
            if (!flag) {
                solverStats.addTime(System.currentTimeMillis() - startTime);
                solverStats.addSolverAssertionError();
                continue;
            }

            try {
                double[] qs = builder.getQuantiles(EstimatedPercentiles);
                solverStats.addTime(System.currentTimeMillis() - startTime);
                double[] ariaRtaPercentiles = dataRow.getRtaPercentiles();
                double[] ariaKustoPercentiles = dataRow.getKustoPercentiles();

                result.get(OperationType.Percentile001).add(new PercentileDataPoint(ariaRtaPercentiles[0], ariaKustoPercentiles[0], qs[0]));
                result.get(OperationType.Percentile01).add(new PercentileDataPoint(ariaRtaPercentiles[1], ariaKustoPercentiles[1], qs[1]));
                result.get(OperationType.Percentile05).add(new PercentileDataPoint(ariaRtaPercentiles[2], ariaKustoPercentiles[2], qs[2]));
                result.get(OperationType.Percentile50).add(new PercentileDataPoint(ariaRtaPercentiles[3], ariaKustoPercentiles[3], qs[3]));
                result.get(OperationType.Percentile95).add(new PercentileDataPoint(ariaRtaPercentiles[4], ariaKustoPercentiles[4], qs[4]));
                result.get(OperationType.Percentile99).add(new PercentileDataPoint(ariaRtaPercentiles[5], ariaKustoPercentiles[5], qs[5]));
                result.get(OperationType.Percentile999).add(new PercentileDataPoint(ariaRtaPercentiles[6], ariaKustoPercentiles[6], qs[6]));
            } catch (NoBracketingException e) {
                solverStats.addTime(System.currentTimeMillis() - startTime);
                solverStats.addSolverAlgorithmException();
            }
        }

        return result;
    }

}
