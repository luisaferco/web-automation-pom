package co.com.training.web.config.driver;

import co.com.training.web.config.env.ServerAddress;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverManager extends ConfigCapabilities {

    private WebDriver webDriver;
    private String browserName;
    private DesiredCapabilities capabilities;
    private ServerAddress address;

    private DriverManager(String browserName, ServerAddress address) {
        super();
        this.browserName = browserName;
        this.address = address;
        this.capabilities = super.loadCapabilities();
        this.webDriver = BrowserType.valueOf(browserName).createDriver(capabilities, address);

    }

    private DriverManager(String browserName, DesiredCapabilities capabilities,ServerAddress address) {
        super();
        this.browserName = browserName;
        this.address = address;
        this.capabilities = super.loadCapabilities().merge(capabilities);
        this.webDriver = BrowserType.valueOf(browserName).createDriver(capabilities, address);

    }

    public static DriverManager newDriver(String browserName, ServerAddress serverAddress){
        return new DriverManager(browserName, serverAddress);
    }

    public static DriverManager newDriver(String browserName,
                                          DesiredCapabilities capabilities,
                                          ServerAddress serverAddress){
        return new DriverManager(browserName, capabilities, serverAddress);
    }

    public WebDriver getWebDriver(String url) {
        webDriver.manage().window().maximize();
        webDriver.get(url);
        return webDriver;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }
}
