package msolver.aria.rta;

import msolver.aria.QueryType;

import java.util.*;

public class KrakenQueryParams {
    private String cubeId;
    private String measureName;
    private String interval;
    private QueryType queryType;

    private Set<String> dimensions;
    private Map<String, String[]> dimensionNamesToValues;

    private KrakenQueryParams(String cubeId, String measureName, String interval, QueryType queryType, Set<String> dimensions, Map<String, String[]> dimensionNamesToValues) {
        this.cubeId = cubeId;
        this.measureName = measureName;
        this.interval = interval;
        this.queryType = queryType;
        this.dimensions = new HashSet<>(dimensions);
        this.dimensionNamesToValues = dimensionNamesToValues;
    }

    public String getCubeId() {
        return cubeId;
    }

    public String getMeasureName() {
        return measureName;
    }

    public String getInterval() {
        return interval;
    }

    public QueryType getQueryType() {
        return queryType;
    }

    public Set<String> getDimensions() {
        return dimensions;
    }

    public Map<String, String[]> getDimensionNamesToValues() {
        return dimensionNamesToValues;
    }

    public static class KrakenQueryParamsBuilder {
        private String cubeId;
        private String measureName;
        private String interval;
        private QueryType queryType;

        private Set<String> dimensions = new HashSet<>();
        private Map<String, String[]> dimensionNamesToValues = new HashMap<>();

        public KrakenQueryParamsBuilder withCubeId(String cubeId) {
            this.cubeId = cubeId;
            return this;
        }

        public KrakenQueryParamsBuilder withMeasureName(String measureName) {
            this.measureName = measureName;
            return this;
        }

        public KrakenQueryParamsBuilder withInterval(String interval) {
            this.interval = interval;
            return this;
        }

        public KrakenQueryParamsBuilder withQueryType(QueryType queryType) {
            this.queryType = queryType;
            return this;
        }

        public KrakenQueryParamsBuilder clearDimensions() {
            dimensions.clear();
            return this;
        }

        public KrakenQueryParamsBuilder withDimension(String dimension) {
            dimensions.add(dimension);
            return this;
        }

        public KrakenQueryParamsBuilder withDimensionAndValues(String dimension, String[] dimensionValues) {
            dimensionNamesToValues.put(dimension, dimensionValues);
            return this;
        }

        public KrakenQueryParams build() {
            return new KrakenQueryParams(cubeId, measureName, interval, queryType, dimensions, dimensionNamesToValues);
        }
    }
}
