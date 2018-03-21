package msolver.aria;

import msolver.aria.kusto.KustoDataRow;
import msolver.aria.rta.RtaDataRow;

import java.util.Objects;

public class AriaDataRow extends SolvedMomentData {

    private RtaDataRow rtaDataRow;
    private KustoDataRow kustoDataRow;

    public AriaDataRow(RtaDataRow rtaDataRow, KustoDataRow kustoDataRow) {
        this.rtaDataRow = rtaDataRow;
        this.kustoDataRow = kustoDataRow;
    }

    public boolean isValid() {
        return rtaDataRow.isValid() && kustoDataRow.isValid();
    }

    public double getMin() {
        return rtaDataRow.getMin();
    }

    public double getMax() {
        return rtaDataRow.getMax();
    }

    public double[] getPowerSums() {
        return rtaDataRow.getPowerSums();
    }

    public double[] getRtaPercentiles() {
        return rtaDataRow.getRtaPercentiles();
    }

    public double[] getKustoPercentiles() {
        return kustoDataRow.getPercentiles();
    }

    public long getRtaCount() { return rtaDataRow.getRtaCount(); }

    public long getKustoCount() { return kustoDataRow.getCount(); }

    public String toString() {
        return "RTA: " + Objects.toString(rtaDataRow) + " KUSTO: " + Objects.toString(kustoDataRow);
    }
}