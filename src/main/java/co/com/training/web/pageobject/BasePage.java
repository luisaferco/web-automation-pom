package co.com.training.web.pageobject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.PageFactory.initElements;

public abstract class BasePage <T extends WebDriver> {

    private static final int TIMEOUT = 10;
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        initElements(driver, this);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void dispose() {
        if(driver != null){
            driver.close();
        }
    }

    /**
     *  custom click
     * @param element : WebElement
     * @author luisaferco
     */
    public void click(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.click();
    }

    public void type(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(text);
    }

    public void scrollTo(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void switchToFrame(WebElement webElement) {
        driver.switchTo().frame(webElement);
    }

    public void switchToFrame(String name) {
        driver.switchTo().frame(name);
    }
}
