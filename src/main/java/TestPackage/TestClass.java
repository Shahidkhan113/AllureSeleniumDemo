package TestPackage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class TestClass {

    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeTest
    public void setup() {
        // Setup Chrome
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Setup Extent Report
        ExtentSparkReporter spark = new ExtentSparkReporter("SauceDemoLoginReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        test = extent.createTest("SauceDemo Login Test");
    }

    @Test
    public void loginWithExplicitWaitAndReport() {
        try {
            // Step 1: Open SauceDemo site
            driver.get("https://www.saucedemo.com/");
            test.pass("Opened SauceDemo website");

            // Step 2: Setup Explicit Wait
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Step 3: Wait and enter username
            WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
            username.sendKeys("standard_user");
            test.pass("Entered Username");

            // Step 4: Wait and enter password
            WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
            password.sendKeys("secret_sauce");
            test.pass("Entered Password");

            // Step 5: Wait and click Login
            WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));
            loginBtn.click();
            test.pass("Clicked Login Button");

            // Step 6: Wait for next page title or element
            wait.until(ExpectedConditions.urlContains("inventory"));
            test.pass("Successfully navigated to Products page");

            // Optional Assertion
            String title = driver.getTitle();
            Assert.assertTrue(title.toLowerCase().contains("swag"));
            test.pass("Assertion Passed: Title contains 'Swag'");

        } catch (Exception e) {
            test.fail("Test Failed: " + e.getMessage());
        } finally {
            //driver.quit();
            extent.flush();
        }
    }
}
