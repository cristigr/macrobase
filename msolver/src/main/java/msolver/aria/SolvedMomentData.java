package msolver.aria;

import msolver.data.MomentData;

public abstract class SolvedMomentData extends MomentData {

    public abstract double[] getRtaPercentiles();

    public abstract double[] getKustoPercentiles();

    public abstract long getRtaCount();

    public abstract long getKustoCount();

}
