package co.com.training.web.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage<WebDriver> {

    @FindBy(id = "username")
    private WebElement usernameTxt;

    @FindBy(id = "password")
    private WebElement passwordTxt;

    @FindBy(css = "button[type=submit]")
    private WebElement loginBtn;

    @FindBy(linkText = "Register")
    private WebElement registerOpt;

    @FindBy(css = ".ng-binding.ng-scope.alert.alert-danger")
    private WebElement alertMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("user login with credentials username:{0} password:{1}")
    public void loginWith(String user, String password) {
        type(usernameTxt, user);
        type(passwordTxt, password);
        click(loginBtn);
    }

    @Step("user see alert message")
    public String getAlertMessage() {
        return wait.until(ExpectedConditions.visibilityOf(alertMessage)).getText();
    }
}
