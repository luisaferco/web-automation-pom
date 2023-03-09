package co.com.training.web.pageobject;

import org.openqa.selenium.WebDriver;

public class TablePage {


    private WebDriver driver;
    public TablePage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTitle() {
        return driver.getCurrentUrl();
    }
}
