package TestPackage;

import PagesObjectModel.BranchPage;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

public class EcommerceCheckFlow extends TestBasetp {

    ExtentReports extent;
    ExtentTest test;
    BranchPage branchPage;

    @BeforeClass
    public void setup() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("TestBranchCreationReport.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);

        test = extent.createTest(" Login + Branch Form Test on Wareed CMS");

        initializeBrowser(); // from TestBase
        branchPage = new BranchPage(driver, wait); // POM
    }

    @Test(priority = 1)
    public void testLoginScenarios() {
        String[][] loginCases = {
            {"admin.andpercent@gmail.com", "wrongpass", " Wrong Password"},
            {"wrongemail@gmail.com", "abc123", " Wrong Email"},
            {"", "abc123", " Blank Email"},   
            {"admin.andpercent@gmail.com", "abc123", "✅ Correct Credentials"}
        };

        for (String[] login : loginCases) {
            ExtentTest node = test.createNode("Login Test: " + login[2]);

            try {
                branchPage.emailField().clear();
                branchPage.passwordField().clear();
                Thread.sleep(2000);

                branchPage.emailField().sendKeys(login[0]);
                branchPage.passwordField().sendKeys(login[1]);
                Thread.sleep(2000);

                branchPage.loginButton().click();
                Thread.sleep(2000);

                if (login[2].contains("✅")) {
                    if (!driver.getCurrentUrl().contains("login")) {
                        node.pass(" Login successful");
                    } else {
                        node.fail(" Login should succeed but failed.");
                    }
                } else {
                    if (driver.getCurrentUrl().contains("login")) {
                        node.pass(" Login blocked as expected");
                    } else {
                        node.fail(" Login should fail but passed.");
                    }
                }

                Thread.sleep(2000);

            } catch (Exception e) {
                node.fail(" Exception during login: " + e.getMessage());
            }
        }
    }

    @Test(priority = 2)
    public void testBranchCreationScenarios() {
        ExtentTest branchNode = test.createNode(" Branch Form Test Cases");

        try {
            // Open Branches Section
            wait.until(ExpectedConditions.elementToBeClickable(branchPage.branchesLink())).click();
            Thread.sleep(2000);
            branchNode.pass(" Branches page loaded");

            // Open Add Branch Form ONCE
            wait.until(ExpectedConditions.elementToBeClickable(branchPage.addNewButton())).click();
            wait.until(ExpectedConditions.visibilityOf(branchPage.nameInputField()));
            //branchNode.info(" Branch form opened");

            // Test Case 1 - All Fields Empty
            clearBranchForm();
            branchPage.saveButton().click();
            Thread.sleep(2000);
            branchNode.pass(" Test 1: All fields empty submitted");
            
            // Test Case 2 - Empty Status
            clearBranchForm();
            branchPage.nameInputField().sendKeys("Test Branch");
            branchPage.locationInputField().sendKeys("Islamabad");
            Thread.sleep(1000);
            branchPage.saveButton().click();
            Thread.sleep(2000);
            branchNode.pass(" Test 2: Status empty submitted");

            // Test Case 3 - Valid Data
            clearBranchForm();
            branchPage.nameInputField().sendKeys("Final Branch");
            branchPage.locationInputField().sendKeys("I-11 Islamabad, Pakistan");
            new Select(branchPage.statusDropdown()).selectByValue("inactive");
            Thread.sleep(1000);
            branchPage.saveButton().click();
            Thread.sleep(2000);
            branchNode.pass(" Test 3: Valid form submitted");

        } catch (Exception e) {
            branchNode.fail(" Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    // Utility: Clear Form Safely
    public void clearBranchForm() {
        try {
            wait.until(ExpectedConditions.visibilityOf(branchPage.nameInputField())).clear();
            wait.until(ExpectedConditions.visibilityOf(branchPage.locationInputField())).clear();
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(" Failed to clear form: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        extent.flush();
        if (driver != null) {
            // driver.quit(); // Uncomment for actual run
        }
    }
}
