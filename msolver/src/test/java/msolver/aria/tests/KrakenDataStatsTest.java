package msolver.aria.tests;

import msolver.aria.PercentilesSolverChecker;
import msolver.aria.kusto.KustoClient;
import msolver.aria.kusto.KustoCsvFileClient;
import msolver.aria.rta.*;
import org.junit.Test;

public class KrakenDataStatsTest {

    /*@Test
    public void cube1StarQueryTest() throws Exception {
        KrakenClient krakenClient = new KrakenJsonFileClient("2855918937124050952c9e4599465c51", "Event%20Id","cube1/star-query.json");
        PercentilesSolverChecker solverChecker = new PercentilesSolverChecker(krakenClient, null);
        solverChecker.comparePercentilesNoKusto();
    }*/

    /*@Test
    public void cube5StarQueryTest() throws Exception {
        KrakenClient krakenClient = new KrakenJsonFileClient("aa35bf3be2fa4a3e8676c1bd34dcfbed", "AvgBlaToBlsDelay","cube3.3/star-query.json");
        KustoClient kustoClient = new KustoCsvFileClient("cube3.3/star-query.csv");
        PercentilesSolverChecker solverChecker = new PercentilesSolverChecker(krakenClient, kustoClient);
        solverChecker.comparePercentiles();
    }*/

    @Test
    public void cube2StarQueryTest() throws Exception {
        KrakenClient krakenClient = new KrakenJsonFileClient("2f1e00e0dde1498abffe0c5049c86e65", "RequestDuration","cube2/star-query.json");
        KustoClient kustoClient = new KustoCsvFileClient("cube2/star-query.csv");
        PercentilesSolverChecker solverChecker = new PercentilesSolverChecker(krakenClient, kustoClient);
        solverChecker.comparePercentiles();
    }

    @Test
    public void cube3StarQueryTest() throws Exception {
        KrakenClient krakenClient = new KrakenJsonFileClient("aa35bf3be2fa4a3e8676c1bd34dcfbed", "AvgMuxToOrcaDelay","cube3.1/star-query.json");
        KustoClient kustoClient = new KustoCsvFileClient("cube3.1/star-query.csv");
        PercentilesSolverChecker solverChecker = new PercentilesSolverChecker(krakenClient, kustoClient);
        solverChecker.comparePercentiles();
    }

    @Test
    public void cube4StarQueryTest() throws Exception {
        KrakenClient krakenClient = new KrakenJsonFileClient("aa35bf3be2fa4a3e8676c1bd34dcfbed", "ShardsWritten","cube3.2/star-query.json");
        KustoClient kustoClient = new KustoCsvFileClient("cube3.2/star-query.csv");
        PercentilesSolverChecker solverChecker = new PercentilesSolverChecker(krakenClient, kustoClient);
        solverChecker.comparePercentiles();
    }

    @Test
    public void cube2NonStarQuery() throws Exception {
        KrakenClient krakenClient = new KrakenJsonFileClient("2f1e00e0dde1498abffe0c5049c86e65", "RequestDuration","cube2/non-star-query1.json");
        KustoClient kustoClient = new KustoCsvFileClient("cube2/non-star-query1.csv");
        PercentilesSolverChecker solverChecker = new PercentilesSolverChecker(krakenClient, kustoClient);
        solverChecker.comparePercentiles();
    }

    @Test
    public void cube3NonStarQueryTest() throws Exception {
        KrakenClient krakenClient = new KrakenJsonFileClient("aa35bf3be2fa4a3e8676c1bd34dcfbed", "AvgMuxToOrcaDelay","cube3.1/non-star-query.json");
        KustoClient kustoClient = new KustoCsvFileClient("cube3.1/non-star-query.csv");
        PercentilesSolverChecker solverChecker = new PercentilesSolverChecker(krakenClient, kustoClient);
        solverChecker.comparePercentiles();
    }

    @Test
    public void cube4NonStarQueryTest() throws Exception {
        KrakenClient krakenClient = new KrakenJsonFileClient("aa35bf3be2fa4a3e8676c1bd34dcfbed","ShardsWritten", "cube3.2/non-star-query.json");
        KustoClient kustoClient = new KustoCsvFileClient("cube3.2/non-star-query.csv");
        PercentilesSolverChecker solverChecker = new PercentilesSolverChecker(krakenClient, kustoClient);
        solverChecker.comparePercentiles();
    }
}