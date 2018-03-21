package msolver.aria.rta;

import msolver.aria.OperationType;

import java.util.ArrayList;
import java.util.List;

public class SeriesQueryResult {

    private List<Double> dataPoints;
    private OperationType operationType;

    SeriesQueryResult(List<Double> dataPoints, OperationType operationType) {
        this.dataPoints = dataPoints;
        this.operationType = operationType;
    }

    static List<RtaDataRow> toDataRows(List<SeriesQueryResult> seriesQueryResults) {
        List<RtaDataRow> dataRows = new ArrayList<>();
        for (int i = 0; i < seriesQueryResults.get(0).dataPoints.size(); i++) {
            double min = 0;
            double max = 0;
            double[] powers = new double[11];
            double[] percentiles = new double[7];
            for (SeriesQueryResult seriesQueryResult : seriesQueryResults) {
                double value = seriesQueryResult.dataPoints.get(i);
                switch (seriesQueryResult.operationType) {
                    case Min:
                        min = value;
                        break;
                    case Max:
                        max = value;
                        break;
                    case Count:
                        powers[0] = value;
                        break;
                    case Sum:
                        powers[1] = value;
                        break;
                    case SecondPower:
                        powers[2] = value;
                        break;
                    case ThirdPower:
                        powers[3] = value;
                        break;
                    case FourthPower:
                        powers[4] = value;
                        break;
                    case FifthPower:
                        powers[5] = value;
                        break;
                    case SixthPower:
                        powers[6] = value;
                        break;
                    case SeventhPower:
                        powers[7] = value;
                        break;
                    case EightPower:
                        powers[8] = value;
                        break;
                    case NinthPower:
                        powers[9] = value;
                        break;
                    case TenthPower:
                        powers[10] = value;
                        break;
                    case Percentile001:
                        percentiles[0] = value;
                        break;
                    case Percentile01:
                        percentiles[1] = value;
                        break;
                    case Percentile05:
                        percentiles[2] = value;
                        break;
                    case Percentile50:
                        percentiles[3] = value;
                        break;
                    case Percentile95:
                        percentiles[4] = value;
                        break;
                    case Percentile99:
                        percentiles[5] = value;
                        break;
                    case Percentile999:
                        percentiles[6] = value;
                        break;
                }
            }

            RtaDataRow dataRow = new RtaDataRow(min, max, powers, percentiles);

            if (dataRow.isValid()) {
                dataRows.add(dataRow);
            }
        }

        return dataRows;
    }
}
