package msolver.aria;

public class SolverStats {

    private long totalElapsedMillis;
    private int errorCount;

    private int ariaDataMismatchCount;
    private int solverAssertionFailureCount;
    private int solverAlgorithmExceptionCount;

    private long totalRawPoints;
    private int rawPointsSourceCount;

    public void addTime(long elapsedMillis) {
        totalElapsedMillis += elapsedMillis;
    }

    public void addError() {
        errorCount++;
    }

    public void addAriaDataMismatchError() {
        errorCount++;
        ariaDataMismatchCount++;
    }

    public void addSolverAssertionError() {
        errorCount++;
        solverAssertionFailureCount++;
    }

    public void addSolverAlgorithmException() {
        errorCount++;
        solverAlgorithmExceptionCount++;
    }

    public void addRawEventsCount(long count) {
        totalRawPoints += count;
        rawPointsSourceCount++;
    }

    public void PrintSimple(String cubeId, String measureName, int dataRowsCount) {
        System.out.println(String.format("Cube id: %s Measure name: %s on %d data rows with %d errors took %d millis", cubeId, measureName, dataRowsCount, errorCount, totalElapsedMillis));
    }

    public void PrintComplex(String cubeId, String measureName, int dataRowsCount) {
        System.out.println(String.format("Cube id: %s Measure name: %s\n" +
                                         "on %d data rows with %d avg raw events per row has\n" +
                                         "%d aria raw events count mismatch errors %d solver assertion failures %d solver algorithm failures.\n" +
                                         "It took %d millis to compute the points",
                cubeId,
                measureName,
                dataRowsCount,
                totalRawPoints/rawPointsSourceCount,
                ariaDataMismatchCount,
                solverAssertionFailureCount,
                solverAlgorithmExceptionCount,
                totalElapsedMillis));
    }

}
