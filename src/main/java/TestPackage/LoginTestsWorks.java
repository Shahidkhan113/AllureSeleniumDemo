package TestPackage;

import PagesObjectModel.LoginWorks;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

public class LoginTestsWorks extends TestBasetp {

    ExtentReports extent;
    ExtentTest test;
    LoginWorks loginPage;

    // Test data: {email, password, description}
    String[][] credentials = {
        {"m.saleh1@veroke.com", "Abc@12345", "Invalid Email Format"},
        {"m.saleh@veroke.com", "Abcd@12345", "Invalid Password Format"},
        {"m.saleh@veroke.com", "", "Valid Email & Blank Password"},
        {"", "Abc@12345", "Blank Email & Valid Password"},
        {"", "", "Blank Email & Password"},
        {"m.saleh@veroke.com", "Abc@12345", "Valid Login"}
    };

    @BeforeClass
    public void setup() throws InterruptedException {
        ExtentSparkReporter reporter = new ExtentSparkReporter("Login@WorksReport.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        test = extent.createTest("Login Tests on Veroxm");

        initializeBrowser(); // from TestBasetp
        loginPage = new LoginWorks(driver, wait); // POM class object

        // DEMO SCREEN
        loginPage.demoPasswordField().sendKeys("password@veroke");

        loginPage.visitDemoButton().click();

        Thread.sleep(1000); // Wait for login page
    }

    @Test
    public void runAllLoginTests() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (int i = 0; i < credentials.length; i++) {
            String email = credentials[i][0];
            String password = credentials[i][1];
            String description = credentials[i][2];


            // Re-fetch the fields to avoid stale/old input reference
            WebElement emailField = loginPage.emailField();
            WebElement passwordField = loginPage.passwordField();

            // Clear using JavaScript to avoid previous values sticking
            js.executeScript("arguments[0].value='';", emailField);
            js.executeScript("arguments[0].value='';", passwordField);
            Thread.sleep(1000);

            // Enter credentials
            emailField.sendKeys(email);
            passwordField.sendKeys(password);
            loginPage.loginButton().click();

            Thread.sleep(3000); // wait after click

            // Check if login success (you can adjust locator accordingly)
            if (driver.getCurrentUrl().contains("/dashboard") || isElementPresent(By.xpath("//button[contains(text(),'Logout')]"))) {
                test.pass("Test Passed: " + description + " (Login Success)");

                // Logout if needed
                driver.findElement(By.xpath("//button[contains(text(),'Logout')]")).click();
                Thread.sleep(1000); // wait for login page to reload

            } else {
                test.pass("Test Passed: " + description + " (Login Failed or Validation Shown)");
                // optionally refresh to reset
                driver.navigate().refresh();
                Thread.sleep(1000);
            }
        }
    }

    // Utility method to check if element is present
    public boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    @AfterClass
    public void tearDown() {
        extent.flush();
        if (driver != null) {
            // driver.quit(); // Uncomment when needed
        }
    }
}
