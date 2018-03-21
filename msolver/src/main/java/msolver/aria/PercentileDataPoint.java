package msolver.aria;

import java.util.List;
import java.util.stream.Collectors;

public class PercentileDataPoint {

    private double rtaValue;
    private double kustoValue;
    private double momentsSolverValue;

    public PercentileDataPoint(double rtaValue, double kustoValue, double momentsSolverValue) {
        this.rtaValue = rtaValue;
        this.momentsSolverValue = momentsSolverValue;
        this.kustoValue = kustoValue;
    }

    // Average
    public static double getAvgRtaToSolverRelativeError(List<PercentileDataPoint> points) {
        return getAvgRelativeError(points.stream().map(point -> point.rtaValue).collect(Collectors.toList()), points.stream().map(point -> point.momentsSolverValue).collect(Collectors.toList()));
    }

    public static double getAvgRtaToKustoRelativeError(List<PercentileDataPoint> points) {
        return getAvgRelativeError(points.stream().map(point -> point.rtaValue).collect(Collectors.toList()), points.stream().map(point -> point.kustoValue).collect(Collectors.toList()));
    }

    public static double getAvgKustoToSolverRelativeError(List<PercentileDataPoint> points) {
        return getAvgRelativeError(points.stream().map(point -> point.kustoValue).collect(Collectors.toList()), points.stream().map(point -> point.momentsSolverValue).collect(Collectors.toList()));
    }

    public static double getAvgKustoToRtaRelativeError(List<PercentileDataPoint> points) {
        return getAvgRelativeError(points.stream().map(point -> point.kustoValue).collect(Collectors.toList()), points.stream().map(point -> point.rtaValue).collect(Collectors.toList()));
    }

    public static double getAvgSolverToRtaRelativeError(List<PercentileDataPoint> points) {
        return getAvgRelativeError(points.stream().map(point -> point.momentsSolverValue).collect(Collectors.toList()), points.stream().map(point -> point.rtaValue).collect(Collectors.toList()));
    }

    public static double getAvgSolverToKustoRelativeError(List<PercentileDataPoint> points) {
        return getAvgRelativeError(points.stream().map(point -> point.momentsSolverValue).collect(Collectors.toList()), points.stream().map(point -> point.kustoValue).collect(Collectors.toList()));
    }

    public static double getAvgSolverToKustoAbsoluteError(List<PercentileDataPoint> points) {
        return getAvgAbsoluteError(points.stream().map(point -> point.momentsSolverValue).collect(Collectors.toList()), points.stream().map(point -> point.kustoValue).collect(Collectors.toList()));
    }

    public static double getAvgRtaToKustoAbsoluteError(List<PercentileDataPoint> points) {
        return getAvgAbsoluteError(points.stream().map(point -> point.rtaValue).collect(Collectors.toList()), points.stream().map(point -> point.kustoValue).collect(Collectors.toList()));
    }

    // Max
    public static double getMaxRtaToSolverRelativeError(List<PercentileDataPoint> points) {
        return getMaxRelativeError(points.stream().map(point -> point.rtaValue).collect(Collectors.toList()), points.stream().map(point -> point.momentsSolverValue).collect(Collectors.toList()));
    }

    public static double getMaxRtaToKustoRelativeError(List<PercentileDataPoint> points) {
        return getMaxRelativeError(points.stream().map(point -> point.rtaValue).collect(Collectors.toList()), points.stream().map(point -> point.kustoValue).collect(Collectors.toList()));
    }

    public static double getMaxKustoToSolverRelativeError(List<PercentileDataPoint> points) {
        return getMaxRelativeError(points.stream().map(point -> point.kustoValue).collect(Collectors.toList()), points.stream().map(point -> point.momentsSolverValue).collect(Collectors.toList()));
    }

    public static double getMaxKustoToRtaRelativeError(List<PercentileDataPoint> points) {
        return getMaxRelativeError(points.stream().map(point -> point.kustoValue).collect(Collectors.toList()), points.stream().map(point -> point.rtaValue).collect(Collectors.toList()));
    }

    public static double getMaxSolverToRtaRelativeError(List<PercentileDataPoint> points) {
        return getMaxRelativeError(points.stream().map(point -> point.momentsSolverValue).collect(Collectors.toList()), points.stream().map(point -> point.rtaValue).collect(Collectors.toList()));
    }

    public static double getMaxSolverToKustoRelativeError(List<PercentileDataPoint> points) {
        return getMaxRelativeError(points.stream().map(point -> point.momentsSolverValue).collect(Collectors.toList()), points.stream().map(point -> point.kustoValue).collect(Collectors.toList()));
    }

    public static double getMaxSolverToKustoAbsoluteError(List<PercentileDataPoint> points) {
        return getMaxAbsoluteError(points.stream().map(point -> point.momentsSolverValue).collect(Collectors.toList()), points.stream().map(point -> point.kustoValue).collect(Collectors.toList()));
    }

    public static double getMaxRtaToKustoAbsoluteError(List<PercentileDataPoint> points) {
        return getMaxAbsoluteError(points.stream().map(point -> point.rtaValue).collect(Collectors.toList()), points.stream().map(point -> point.kustoValue).collect(Collectors.toList()));
    }

    private static double getAvgRelativeError(List<Double> estimatedPoints, List<Double> referencePoints) {
        if (referencePoints.size() == 0) {
            throw new IllegalArgumentException("Can't compute avg if no points are provided");
        }

        if (estimatedPoints.size() != referencePoints.size()) {
            throw new IllegalArgumentException("Estimated and reference points don't have same size");
        }

        double sumOfRelativeDiffs = 0;
        for (int i = 0; i < estimatedPoints.size(); i++) {
            sumOfRelativeDiffs += Math.abs(estimatedPoints.get(i)/referencePoints.get(i) - 1);
        }

        return sumOfRelativeDiffs/referencePoints.size();
    }

    private static double getMaxRelativeError(List<Double> estimatedPoints, List<Double> referencePoints) {
        if (referencePoints.size() == 0) {
            throw new IllegalArgumentException("Can't compute avg if no points are provided");
        }

        if (estimatedPoints.size() != referencePoints.size()) {
            throw new IllegalArgumentException("Estimated and reference points don't have same size");
        }

        double maxError = 0;
        for (int i = 0; i < estimatedPoints.size(); i++) {
            double error = Math.abs(estimatedPoints.get(i)/referencePoints.get(i) - 1);
            if (error > maxError) {
                maxError = error;
            }
        }

        return maxError;
    }

    private static double getAvgAbsoluteError(List<Double> estimatedPoints, List<Double> referencePoints) {
        if (referencePoints.size() == 0) {
            throw new IllegalArgumentException("Can't compute avg if no points are provided");
        }

        if (estimatedPoints.size() != referencePoints.size()) {
            throw new IllegalArgumentException("Estimated and reference points don't have same size");
        }

        double sumOfRelativeDiffs = 0;
        for (int i = 0; i < estimatedPoints.size(); i++) {
            sumOfRelativeDiffs += Math.abs(estimatedPoints.get(i) - referencePoints.get(i));
        }

        return sumOfRelativeDiffs/referencePoints.size();
    }

    private static double getMaxAbsoluteError(List<Double> estimatedPoints, List<Double> referencePoints) {
        if (referencePoints.size() == 0) {
            throw new IllegalArgumentException("Can't compute avg if no points are provided");
        }

        if (estimatedPoints.size() != referencePoints.size()) {
            throw new IllegalArgumentException("Estimated and reference points don't have same size");
        }

        double maxError = 0;
        for (int i = 0; i < estimatedPoints.size(); i++) {
            double error = Math.abs(estimatedPoints.get(i) - referencePoints.get(i));
            if (error > maxError) {
                maxError = error;
            }
        }

        return maxError;
    }

}
