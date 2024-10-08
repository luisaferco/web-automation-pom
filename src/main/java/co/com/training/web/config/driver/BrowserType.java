package co.com.training.web.config.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public enum BrowserType {

    CHROME {
        @Override
        public WebDriver createDriver(Capabilities capabilities) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            chromeOptions.merge(capabilities);
            return new ChromeDriver(chromeOptions);

        }
    },
    EDGE {
        @Override
        public WebDriver createDriver(Capabilities capabilities) {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.merge(capabilities);
            WebDriverManager.edgedriver().setup();
            return new EdgeDriver(options);
        }
    },
    FIREFOX {
        @Override
        public WebDriver createDriver(Capabilities capabilities) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.merge(capabilities);
            return new FirefoxDriver(options);
        }
    },
    REMOTE {
        @Override
        public WebDriver createDriver(Capabilities capabilities) {

            return new RemoteWebDriver(capabilities);
        }

    };



    public abstract WebDriver createDriver(Capabilities capabilities);
}
