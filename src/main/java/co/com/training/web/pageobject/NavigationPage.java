package co.com.training.web.pageobject;

import co.com.training.web.config.custom.CustomConditions;
import co.com.training.web.utils.NavigationOptions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class NavigationPage extends BasePage{

    @FindBy(className = "h20")
    private WebElement headerNavOptions;

    @FindBy(css = "div.row.price_table_holder.col_3 a")
    private List<WebElement> navigationOptions;

    @FindBy(id = "dismiss-button")
    private WebElement closeButton;

    @FindBy(tagName = "iframe")
    private List<WebElement> frames;

    private String titlePage;

    public NavigationPage(WebDriver driver) {
        super(driver);
    }


    public void navigateTo(String option) {
        scrollTo(headerNavOptions);
        this.titlePage = getTitlePage();
        WebElement navOption = wait.until(CustomConditions.itemIsIncludedIn(navigationOptions, option));
        this.click(navOption);
        try {
            closeVignetteWindow();
        }catch (Exception e){
            getDriver().navigate().refresh();
            navigateTo(option);
        }
    }

    public LoginPage navigateToRegistration(){
        navigateTo(NavigationOptions.REGISTRATION.getOption());
        return new LoginPage(getDriver());
    }

    public TablePage navigateToSearchFilter(){
        navigateTo(NavigationOptions.SEARCH_FILTER.getOption());
        return new TablePage(getDriver());
    }

    public void closeVignetteWindow() {
        if (isVignettePresent()){
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.tagName("iframe"),1));
            switchToFrame("aswift_1");
            switchToFrame("ad_iframe");
            Actions actions = new Actions(getDriver()); actions.moveToElement(closeButton);
            this.click(closeButton);
        }
    }

    private boolean isVignettePresent() {
        boolean isWindowPresent = false;
        try {
           wait.until(ExpectedConditions.urlContains("#google_vignette"));
           isWindowPresent = true;
        }catch (TimeoutException ignored){
        }
        return isWindowPresent;
    }

    public String getTitle() {
        wait.until(ExpectedConditions.not(ExpectedConditions.titleContains(titlePage)));
        return getTitlePage();
    }
}
