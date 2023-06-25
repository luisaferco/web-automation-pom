package co.com.training.web.config.driver;

import co.com.training.web.config.env.ServerAddress;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;

import java.time.Duration;

public enum BrowserType {

    CHROME {
        @Override
        public WebDriver createDriver(Capabilities capabilities, ServerAddress serverAddress) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            chromeOptions.merge(capabilities);
            WebDriverManager.chromedriver().setup();
            ClientConfig customConfig = ClientConfig.defaultConfig().readTimeout(Duration.ofMinutes(15))
                    .connectionTimeout(Duration.ofMinutes(15));
            return RemoteWebDriver.builder()
                    .address(serverAddress.getUrlConnection())
                    .config(customConfig)
                    .oneOf(chromeOptions)
                    .build();

        }
    },
    EDGE {
        @Override
        public WebDriver createDriver(Capabilities capabilities, ServerAddress serverAddress) {
            EdgeOptions options = new EdgeOptions();
            options.merge(capabilities);
            WebDriverManager.edgedriver().setup();
            ClientConfig customConfig = ClientConfig.defaultConfig().readTimeout(Duration.ofMinutes(15))
                    .connectionTimeout(Duration.ofMinutes(15));
            return RemoteWebDriver.builder()
                    .address(serverAddress.getUrlConnection())
                    .config(customConfig)
                    .oneOf(options)
                    .build();
        }
    },
    REMOTE {
        @Override
        public WebDriver createDriver(Capabilities capabilities,ServerAddress serverAddress) {
            ClientConfig customConfig = ClientConfig.defaultConfig().readTimeout(Duration.ofMinutes(15))
                    .connectionTimeout(Duration.ofMinutes(15));
            return RemoteWebDriver.builder()
                    .address(serverAddress.getUrlConnection())
                    .config(customConfig)
                    .oneOf(capabilities)
                    .build();
        }

    };



    public abstract WebDriver createDriver(Capabilities capabilities, ServerAddress serverAddress);
}
