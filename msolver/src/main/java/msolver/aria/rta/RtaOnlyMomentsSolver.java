package msolver.aria.rta;

import msolver.MomentSolverBuilder;
import msolver.aria.OperationType;
import msolver.aria.PercentileDataPoint;
import msolver.aria.SolvedMomentData;
import msolver.aria.SolverStats;
import msolver.struct.MomentStruct;
import org.apache.commons.math3.exception.NoBracketingException;

import java.util.*;

public class RtaOnlyMomentsSolver {

    private static double[] EstimatedPercentiles = new double[] {0.001, 0.01, 0.05, 0.5, 0.95, 0.99, 0.999};

    private List<? extends SolvedMomentData> dataRows;

    public RtaOnlyMomentsSolver(List<? extends SolvedMomentData> dataRows) {
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
            MomentStruct m = new MomentStruct();
            m.min = dataRow.getMin();
            m.max = dataRow.getMax();
            m.powerSums = dataRow.getPowerSums(10);

            MomentSolverBuilder builder = new MomentSolverBuilder(m);
            builder.setVerbose(false);
            builder.initialize();

            /*boolean flag;
            flag = builder.checkThreshold(2, .01);
            assertTrue(flag);
            flag = builder.checkThreshold(4, .01);
            assertTrue(flag);*/

            long startTime = System.currentTimeMillis();
            try {
                double[] qs = builder.getQuantiles(EstimatedPercentiles);
                solverStats.addTime(System.currentTimeMillis() - startTime);
                double[] ariaRtaPercentiles = dataRow.getRtaPercentiles();

                result.get(OperationType.Percentile001).add(new PercentileDataPoint(ariaRtaPercentiles[0], 0, qs[0]));
                result.get(OperationType.Percentile01).add(new PercentileDataPoint(ariaRtaPercentiles[1], 0, qs[1]));
                result.get(OperationType.Percentile05).add(new PercentileDataPoint(ariaRtaPercentiles[2], 0, qs[2]));
                result.get(OperationType.Percentile50).add(new PercentileDataPoint(ariaRtaPercentiles[3], 0, qs[3]));
                result.get(OperationType.Percentile95).add(new PercentileDataPoint(ariaRtaPercentiles[4], 0, qs[4]));
                result.get(OperationType.Percentile99).add(new PercentileDataPoint(ariaRtaPercentiles[5], 0, qs[5]));
                result.get(OperationType.Percentile999).add(new PercentileDataPoint(ariaRtaPercentiles[6], 0, qs[6]));
            } catch (NoBracketingException e) {
                solverStats.addTime(System.currentTimeMillis() - startTime);
                solverStats.addError();
            }
        }

        return result;
    }

}
