package msolver.aria.rta;

import java.nio.file.Files;
import java.nio.file.Paths;

public class KrakenJsonFileClient implements KrakenClient {
    private String krakenDataFile;
    private String cubeId;
    private String measureName;

    public KrakenJsonFileClient(String cubeId, String measureName, String file) {
        this.krakenDataFile = file;
        this.cubeId = cubeId;
        this.measureName = measureName;
    }

    @Override
    public String getData() throws Exception {
        return new String(Files.readAllBytes(Paths.get(krakenDataFile)));
    }

    public String getCubeId() {
        return cubeId;
    }

    public String getMeasureName() {
        return measureName;
    }
}
