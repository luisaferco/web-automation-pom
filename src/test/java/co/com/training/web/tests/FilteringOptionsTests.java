package co.com.training.web.tests;

import co.com.training.web.pageobject.NavigationPage;
import co.com.training.web.pageobject.TablePage;
import co.com.training.web.utils.NavigationOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Arrays;

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
                .map(NavigationOptions::getOption)
                .toArray();
    }

    @Test(dataProvider = "dataFilteringOptions", groups = {"mainGroup", "filteringGroup"})
    public void filterBy(String option) {
        NavigationPage navigationPage = getNavigationPage();
        navigationPage.slightScroll();
        navigationPage.navigateTo(option);
    }

    @Test(groups = {"filteringGroup"})
    public void navigateToTable() {
        NavigationPage navigationPage = getNavigationPage();
        navigationPage.slightScroll();
        TablePage tablePage = navigationPage.navigateToSearchFilter();
        Assert.assertTrue(tablePage.getTitle().contains("SearchFilter"), "Expected navigate to Search filter page but is " + tablePage.getTitle());
    }
}
