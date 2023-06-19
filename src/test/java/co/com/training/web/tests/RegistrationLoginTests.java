package co.com.training.web.tests;

import co.com.training.web.pageobject.LoginPage;
import co.com.training.web.pageobject.NavigationPage;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

/**
 * Test class is to show how we can work with @DataProvider annotation using ITestContext
 */
public class RegistrationLoginTests extends BaseTest{

    @BeforeClass
    public void selectRegistrationLogin() {
        System.out.println("Start before class...");
    }

    @DataProvider(name = "login")
    public static Object[][] data(Method method, ITestContext context){
        String methodName = method.getName();
        switch(methodName) {
            case "failedUserNameRegistration":
                String userName = context.getCurrentXmlTest().getParameter("dataWrongUserName");
                String password = context.getCurrentXmlTest().getParameter("password");
                return new String[][] {{userName, password}};
            case "failedPasswordRegistration":
                String badPassword = context.getCurrentXmlTest().getParameter("dataWrongPassword");
                String userName1 = context.getCurrentXmlTest().getParameter("userName");
                return new String[][] {{userName1, badPassword}};
            default:
                return new String[][]{{"no data"}};
        }
    }

    @Test(dataProvider = "login", groups = {"loginGroup"})
    public void failedUserNameRegistration(String userName, String password) {
        NavigationPage navigationPage = getNavigationPage();
        LoginPage loginPage = navigationPage.navigateToRegistration();
        loginPage.loginWith(userName, password);
        Assert.assertEquals(loginPage.getAlertMessage(),"Username or password is incorrect");

    }

    @Test(dataProvider = "login", groups = {"loginGroup"})
    public void failedPasswordRegistration(String userName, String password) {
        NavigationPage navigationPage = getNavigationPage();
        LoginPage loginPage = navigationPage.navigateToRegistration();
        loginPage.loginWith(userName, password);
        Assert.assertEquals(loginPage.getAlertMessage(),"Username or password is incorrect");
    }


}
