package com.saucedemo.base;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.saucedemo.config.ConfigReader;
import com.saucedemo.utils.DriverFactory;
import com.saucedemo.utils.ExtentReportManager;
import com.saucedemo.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;

/**
 * BaseTest — parent of all test classes.
 *
 * Responsibilities:
 *   - Initialize and quit WebDriver
 *   - Create Extent Report test nodes
 *   - Capture screenshots on failure
 *   - Flush the report after suite
 */
public class BaseTest {

    @BeforeSuite
    public void setUpSuite() {
        // Initialize report (creates the HTML file)
        ExtentReportManager.getInstance();
    }

    @BeforeMethod
    public void setUp(Method method) {
        // Start browser
        DriverFactory.initDriver(ConfigReader.getBrowser());

        // Create test node in Extent Report
        ExtentTest test = ExtentReportManager.getInstance()
                .createTest(method.getName());
        ExtentReportManager.setTest(test);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        WebDriver driver = DriverFactory.getDriver();
        ExtentTest test = ExtentReportManager.getTest();

        if (result.getStatus() == ITestResult.FAILURE) {
            // Capture screenshot and attach to report
            String screenshotPath = ScreenshotUtils.captureScreenshot(driver, result.getName());
            test.fail("Test Failed: " + result.getThrowable().getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test Passed");
        } else {
            test.log(Status.SKIP, "Test Skipped: " + result.getThrowable().getMessage());
        }

        // Quit driver
        DriverFactory.quitDriver();
    }

    @AfterSuite
    public void tearDownSuite() {
        // Write everything to the HTML report
        ExtentReportManager.getInstance().flush();
    }

    // Convenience getter for tests
    public WebDriver getDriver() {
        return DriverFactory.getDriver();
    }
}
