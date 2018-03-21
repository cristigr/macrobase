package msolver.aria.kusto;

import java.util.Arrays;

public class KustoDataRow {

    private double[] percentiles;
    private long rawDataPointsCount;

    public KustoDataRow(double[] percentiles, long rawDataPointsCount) {
        this.percentiles = percentiles;
        this.rawDataPointsCount = rawDataPointsCount;
    }

    public double[] getPercentiles() {
        return percentiles;
    }

    public long getCount() { return rawDataPointsCount; }

    public boolean isValid() {
        for (double percentile : percentiles) {
            if (Double.isNaN(percentile)) {
                return false;
            }
        }

        return true;
    }

    public String toString() {
        return Arrays.toString(percentiles);
    }

}
