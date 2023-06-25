package co.com.training.web.config.env;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class ReadConfigFile {


    private String nameFile;
    private JSONObject config;
    private static final Logger LOGGER = LogManager.getLogger(ReadConfigFile.class.getName());
    private ReadConfigFile(String nameFile) {
        this.nameFile = nameFile;
        getProperties();
    }

    public static ReadConfigFile getInstance(String nameFile){
        return new ReadConfigFile(nameFile);
    }

    public JSONObject getConfig() {
        return config;
    }

    protected void getProperties() {
        JSONParser parser = new JSONParser();
        try {
            this.config = (JSONObject) parser.parse(new FileReader(String.format("src/test/resources/conf/%s", nameFile)));
        } catch (IOException | ParseException e) {
            LOGGER.error(e);
        }
    }

}
