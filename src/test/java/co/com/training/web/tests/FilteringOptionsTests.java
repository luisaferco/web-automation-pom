package co.com.training.web.tests;

import co.com.training.web.pageobject.NavigationPage;
import co.com.training.web.utils.NavigationOptions;
import io.qameta.allure.AllureId;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

@Feature("Filtering options page")
public class FilteringOptionsTests extends BaseTest{

    @BeforeTest
    public void before() {
        System.out.println("Before test");
    }

    @AfterTest
    public void after() {
        System.out.println("After test");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Before method");
    }

    @DataProvider(name = "dataFilteringOptions")
    public static Object[] dataProvider() {
        return Arrays.stream(NavigationOptions.values())
                .toArray();
    }

    @DataProvider (name = "dataFilterTable")
    public Object[][] dpMethod(){
        return new Object[][] {{"Cash","EXPENDITURE"}};
    }

    @Description("Validate navigation to multiple options")
    @AllureId("TMS-123")
    @Test(dataProvider = "dataFilteringOptions", groups = {"mainGroup", "filteringGroup"})
    public void filterBy(NavigationOptions option) throws IOException {
        NavigationPage navigationPage = getNavigationPage();
        navigationPage.navigateTo(option.getOption());
        Assert.assertEquals(navigationPage.getTitle(),option.getTitlePage());
    }

    @Test(dataProvider = "dataFilterTable", groups = {"filteringGroup"})
    public void navigateToTableAndSearchBy(String account, String type) {
        NavigationPage navigationPage = getNavigationPage();
        List<Map<String, String>> searchResults = navigationPage
                .navigateToSearchFilter()
                      .searchByAccount(account)
                      .searchByType(type)
                      .getSearchResults();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(searchResults.stream().allMatch(row -> row.get("Type").equals(type)),
                format("Expected all search results filtered by type \"%s\" ",type));

        softAssert.assertTrue(searchResults.stream().allMatch(row -> row.get("Account").equals(account)),
                format("Expected all search results filtered by type \"%s\" ", account));

        softAssert.assertAll();
    }
}
