package co.com.training.web.tests;

import co.com.training.web.config.driver.DriverManager;
import co.com.training.web.config.env.EnvironmentConfig;
import co.com.training.web.config.env.ReadConfigFile;
import co.com.training.web.config.env.ServerAddress;
import co.com.training.web.pageobject.NavigationPage;
import org.json.simple.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;


public abstract class BaseTest {

    private static JSONObject configValues;
    private static DriverManager driverManager;
    private static EnvironmentConfig envConfig;
    public NavigationPage navigationPage;

    @BeforeSuite(alwaysRun = true)
    @Parameters({"conf"})
    public void beforeSuite(String config){
        ReadConfigFile configFile = ReadConfigFile.getInstance(config);
        configValues = configFile.getConfig();
        envConfig = EnvironmentConfig.with(configValues);
        envConfig.setUp();
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser","url"})
    public void beforeMethod(String browser, String url) {
        DesiredCapabilities capabilities = envConfig.setUp(browser);
        ServerAddress address = envConfig.getServerAddress();
        driverManager = DriverManager.newDriver(browser, capabilities, address);
        navigationPage = new NavigationPage(driverManager.getWebDriver(url));
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        navigationPage.dispose();
    }

    public NavigationPage getNavigationPage() {
        return navigationPage;
    }
}
