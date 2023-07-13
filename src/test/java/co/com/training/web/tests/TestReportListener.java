package co.com.training.web.tests;

import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestReportListener extends BaseTest implements ITestListener {

    private static final Logger LOGGER = LogManager.getLogger(TestReportListener.class.getName());

    private static String getTestName(ITestResult iTestResult){
        return iTestResult.getTestName();
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] takeScreenshot(WebDriver driver){
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        LOGGER.info("Test case passed: {}", iTestResult.getName());
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        LOGGER.info("Test case failed: {}" , getTestName(iTestResult));
        iTestResult.getInstance();
        WebDriver driver = ((BaseTest)  iTestResult.getInstance()).getDriver();
        if(driver != null) {
            takeScreenshot(driver);
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }
}
