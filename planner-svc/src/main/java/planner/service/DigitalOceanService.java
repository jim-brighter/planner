package planner.service;

import java.io.InputStream;
import java.util.Map;

public interface DigitalOceanService {

    public String retrieve(String key);

    public void delete(String key);

    public String store(InputStream is, Map<String, Object> metaData);
}
