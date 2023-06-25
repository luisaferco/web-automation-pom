package co.com.training.web.config.env;

import co.com.training.web.exceptions.FailedUrlConnectionException;

import java.net.MalformedURLException;
import java.net.URL;

import static co.com.training.web.exceptions.FailedUrlConnectionException.FAILED_URL_CALLING;

public class ServerAddress {

    private final String username;
    private final String password;
    private final String server;

    public ServerAddress(String username, String password, String server) {
        this.username = username;
        this.password = password;
        this.server = server;
    }

    public URL getUrlConnection() {
        URL remoteAddress;
        try {
            remoteAddress = new URL(String.format("https://%s:%s@%s/wd/hub",username,password,server));
        } catch (MalformedURLException e) {
            throw new FailedUrlConnectionException(String.format(FAILED_URL_CALLING, e.getMessage()));
        }
        return remoteAddress;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
