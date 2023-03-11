package co.com.training.web.tests;

import co.com.training.web.config.DriverManager;
import co.com.training.web.pageobject.NavigationPage;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;


public abstract class BaseTest {

    public static DriverManager driverManager;
    public static NavigationPage navigationPage;

    @BeforeSuite(alwaysRun = true)
    @Parameters({"browser","url"})
    public void beforeSuite(String browser, String url) {
        driverManager = DriverManager.newDriver(browser);
        navigationPage = new NavigationPage(driverManager.getWebDriver(url));
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        navigationPage.dispose();
    }

    public NavigationPage getNavigationPage() {
        return navigationPage.refresh();
    }
}
