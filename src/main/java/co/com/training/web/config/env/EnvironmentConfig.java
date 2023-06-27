package co.com.training.web.config.env;

import co.com.training.web.exceptions.NotFoundOptionException;
import co.com.training.web.utils.maps.Adapter;
import com.browserstack.local.Local;
import org.json.simple.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EnvironmentConfig {


    private JSONObject config;
    private ServerAddress serverAddress;
    private Map<String, Object> commonCapabilities;

    public EnvironmentConfig(JSONObject config) {
        this.config = config;

    }

    public static EnvironmentConfig with(JSONObject config) {
        return new EnvironmentConfig(config);
    }

    public void createServerConnection() {
        String username = System.getenv("BROWSERSTACK_USERNAME");
        String password = System.getenv("BROWSERSTACK_ACCESS_KEY");
        String server =  config.get("server").toString();
        username = username == null? config.get("user").toString() : username;
        password = password == null? config.get("key").toString() : password;
        this.serverAddress = new ServerAddress(username,password,server);
    }

    public void setUp(){
        createServerConnection();
        commonCapabilities = (Map<String, Object>) config.get("capabilities");
        HashMap bstackOptionsMap = (HashMap) commonCapabilities.get("bstack:options");
        if(bstackOptionsMap.get("local") != null &&
            bstackOptionsMap.get("local").toString().equalsIgnoreCase("true")) {
            Local local = new Local();
            try {
                local.start(Collections.singletonMap("key", serverAddress.getPassword()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public DesiredCapabilities setUp(String environment) {
        JSONObject envs = (JSONObject) config.get("environments");
        Map<String, Object> envCapabilities = (Map<String, Object>) envs.get(environment.toLowerCase());
        if(envCapabilities == null) {
            throw new NotFoundOptionException(String.format("environment config not found: %s",environment));
        }
        Adapter.mapJsonToMap(envs, envCapabilities,"");

        DesiredCapabilities commonCapabilities = new DesiredCapabilities();
        for (Map.Entry<String, Object> cap : envCapabilities.entrySet()) {
            commonCapabilities.setCapability(cap.getKey(),cap.getValue());
        }
        JSONObject caps = (JSONObject) config.get("capabilities");
        Map<String, Object> capabilities = (Map<String, Object>) caps.get("bstack:options");

        Adapter.mapJsonToMap(envs, capabilities,"");
        for (Map.Entry<String, Object> cap : capabilities.entrySet()) {
            commonCapabilities.setCapability(cap.getKey(),cap.getValue());
        }
        return commonCapabilities;
    }


    public ServerAddress getServerAddress() {
        return serverAddress;
    }
}
