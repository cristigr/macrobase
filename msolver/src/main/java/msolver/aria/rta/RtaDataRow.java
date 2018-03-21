package msolver.aria.rta;

import msolver.aria.SolvedMomentData;

import java.util.Arrays;

public class RtaDataRow extends SolvedMomentData {
    double min;
    double max;
    double[] powers;
    double[] percentiles;

    public RtaDataRow(double min, double max, double[] powers, double[] percentiles) {
        this.min = min;
        this.max = max;
        this.powers = powers;
        this.percentiles = percentiles;
    }

    public boolean isValid() {
        if (Double.isNaN(min) || Double.isNaN(max)) {
            return false;
        }

        for (double power : powers) {
            if (Double.isNaN(power)) {
                return false;
            }
        }

        for (double percentile : percentiles) {
            if (Double.isNaN(percentile)) {
                return false;
            }
        }

        return true;
    }

    public String toString() {
        return "Min: " + min +
                " Max: " + max +
                " Powers: " + Arrays.toString(powers) +
                " Percentiles: " + Arrays.toString(percentiles);
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double[] getPowerSums() {
        return powers;
    }

    public long getRtaCount() { return (long)powers[0]; }

    public double[] getRtaPercentiles() {
        return percentiles;
    }

    public long getKustoCount() { throw new UnsupportedOperationException("Not supported on RTA rows"); }

    public double[] getKustoPercentiles() { throw new UnsupportedOperationException("Not supported on RTA rows"); }
}