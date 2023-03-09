package co.com.training.web.pageobject;

import co.com.training.web.exceptions.NotFoundOptionException;
import co.com.training.web.utils.NavigationOptions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class NavigationPage extends BasePage<WebDriver> {

    @FindBy(css = "div.row.price_table_holder.col_3 a")
    private List<WebElement> navigationOptions;

    @FindBy(id = "dismiss-button")
    private WebElement closeButton;

    @FindBy(css = "iframe[title=Advertisement]")
    private List<WebElement> window;

    public NavigationPage(WebDriver driver) {
        super(driver);
       // driver.manage().window().maximize();
        driver.get("https://www.globalsqa.com/angularjs-protractor-practice-site/");
    }

    public NavigationPage refresh() {
        driver.get("https://www.globalsqa.com/angularjs-protractor-practice-site/");
        return this;
    }

    public void navigateTo(String option) {
        WebElement navOption = navigationOptions.stream().filter(item -> item.getText().equals(option))
                .findFirst()
                .orElseThrow(() -> new NotFoundOptionException(option));
        this.click(navOption);
        closeAlertWindow();
    }

    public LoginPage navigateToRegistration(){
        navigateTo(NavigationOptions.REGISTRATION.getOption());
        return new LoginPage(getDriver());
    }

    public TablePage navigateToSearchFilter(){
        navigateTo(NavigationOptions.FILTERING.getOption());
        return new TablePage(getDriver());
    }

    public void slightScroll() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,350)", "");
    }

    public void closeAlertWindow() {
        for(WebElement webElement : window) {
           if (webElement.isDisplayed()) {
               this.getIFrame(webElement);
               this.click(closeButton);
               break;
           }
        }
    }
}
