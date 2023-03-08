package co.com.training.web.tests;

import org.testng.annotations.*;

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
        return new Object[]{"option1","option2"};
    }

    @Test(dataProvider = "dataFilteringOptions", groups = {"mainGroup", "filteringGroup"})
    public void filterBy(String option) {
        System.out.println(option + " filteringGroup");
    }

    @Test(groups = {"filteringGroup"})
    public void navigateToTable() {
        System.out.println(" filteringGroup");
    }
}
