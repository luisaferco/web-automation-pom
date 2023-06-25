package co.com.training.web.utils.maps;

import org.json.simple.JSONObject;

import java.util.Map;

public class Adapter {

    public static void mapJsonToMap(JSONObject jsonObject, Map<String, Object> dataMap, String parentKey) {
        for (Object key : jsonObject.keySet()) {
            String fullKey = key.toString();
            Object value = jsonObject.get(key);

            if (value instanceof JSONObject) {
                mapJsonToMap((JSONObject) value, dataMap, fullKey);
            } else {
                dataMap.put(fullKey, value);
            }
        }
    }
}
