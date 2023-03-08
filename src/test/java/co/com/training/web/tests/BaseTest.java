package co.com.training.web.tests;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {

    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {

    }
}
