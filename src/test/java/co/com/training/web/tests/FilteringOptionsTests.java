package co.com.training.web.tests;

import co.com.training.web.pageobject.NavigationPage;
import co.com.training.web.pageobject.TablePage;
import co.com.training.web.utils.NavigationOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

@Listeners({TestReportListener.class})
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

    @Test( description = "navigate through modal component should redirect to intended page",
            dataProvider = "dataFilteringOptions",
            groups = {"mainGroup", "filteringGroup"})
    public void filterBy(NavigationOptions option) {
        NavigationPage navigationPage = getNavigationPage();
        navigationPage.navigateTo(option.getOption());
       Assert.assertEquals(navigationPage.getTitle(),option.getTitlePage());
    }

    @Test( description = "search by type should return filtered results",
            dataProvider = "dataFilterTable", groups = {"filteringGroup"})
    public void navigateToTableAndSearchBy(String account, String type) {
        NavigationPage navigationPage = getNavigationPage();
        TablePage tablePage = navigationPage.navigateToSearchFilter();
        List<Map<String, String>> searchResults = tablePage.searchByAccount(account)
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
