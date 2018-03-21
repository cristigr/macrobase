package msolver.aria.kusto;

import java.nio.file.Files;
import java.nio.file.Paths;

public class KustoCsvFileClient implements KustoClient {

    private String fileName;

    public KustoCsvFileClient(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getData() throws Exception {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
}
