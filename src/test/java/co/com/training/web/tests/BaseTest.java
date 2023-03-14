package co.com.training.web.tests;

import co.com.training.web.config.driver.DriverManager;
import co.com.training.web.pageobject.NavigationPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;


public abstract class BaseTest {

    private static DriverManager driverManager;
    public NavigationPage navigationPage;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser","url"})
    public void beforeMethod(String browser, String url) {
        driverManager = DriverManager.newDriver(browser);
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
