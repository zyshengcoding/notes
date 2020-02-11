package ibm.cloud.order_server.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {


    private static final ObjectMapper obj = new ObjectMapper();


    public static JsonNode getJson(String str) {
        try {
            return obj.readTree(str);
        } catch (Exception e) {
            return null;
        }
    }

}
