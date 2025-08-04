package TestPackage;

import PagesObjectModel.BranchPage;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;

public class EcommerceCheckFlow extends TestBasetp {

    ExtentReports extent;
    ExtentTest test;
    BranchPage branchPage;

    @BeforeClass
    public void setup() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("WareedLoginReport.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);

        test = extent.createTest("Wareed CMS - Login & Internal User Scenarios");

        // Launch browser
        initializeBrowser(); // From TestBasetp
        branchPage = new BranchPage(driver, wait);
    }

    @Test(priority = 1)
    public void testLoginScenarios() {
        String[][] loginCases = {
            {"admin.andpercent@gmail.com", "abcd123", "Invalid Password Format"},
            {"admin.andpercent@gmail.com", "", "Valid Email & Blank Password"},
            {"admin.andpercent1@gmail.com", "abc123", "Invalid Email Format"},
            {"", "abc123", "Blank Email & Valid Password"},
            {"", "", "Blank Email & Password"},
            {"admin.andpercent@gmail.com", "abc123", "Correct Credentials"}
        };

        for (int i = 0; i < loginCases.length; i++) {
            String email = loginCases[i][0];
            String password = loginCases[i][1];
            String description = loginCases[i][2];

            ExtentTest node = test.createNode("Login Scenario: " + description);

            try {
                branchPage.emailField().clear();
                branchPage.passwordField().clear();
                Thread.sleep(1000);

                branchPage.emailField().sendKeys(email);
                branchPage.passwordField().sendKeys(password);
                Thread.sleep(1000);

                branchPage.loginButton().click();
                Thread.sleep(2000);

                if (description.equalsIgnoreCase("Correct Credentials")) {
                    if (!driver.getCurrentUrl().contains("login")) {
                        node.pass("Login successful");
                    } else {
                        node.fail("Login failed with correct credentials");
                    }
                } else {
                    if (driver.getCurrentUrl().contains("login")) {
                        node.pass("Login blocked as expected");
                    } else {
                        node.fail("Login passed unexpectedly for invalid input");
                    }
                }

            } catch (Exception e) {
                node.fail("Exception occurred: " + e.getMessage());
            }
        }
    }

    @Test(priority = 2, dependsOnMethods = "testLoginScenarios")
    public void testInternalUserScenarios() throws InterruptedException {
        ExtentTest node = test.createNode("Internal User Form Validations");

        branchPage.internalUsersLink().click();
        Thread.sleep(1000);
        branchPage.addNewButton().click();
        Thread.sleep(1000);

        // Common Select Objects
        Select genderSelect = new Select(branchPage.genderDropdown());
        Select roleSelect = new Select(branchPage.roleDropdown());

        // Array of form test scenarios
        Object[][] scenarios = {
            {"Submit without entering any data", "", "", "", "", false},
            {"Submit with blank name", "", "longemail@name.com", "", "test1234", false},
            {"Submit with blank email", "shahid", "", "", "123123", false},
            {"Enter blank phone number", "ali", "shahidkhan@gmail.com", "", "124124", false},
            {"Enter blank password", "khan", "alikhan@gmail.khan", "", "", false},          
            {"Enter short password",  "kapoor", "muskankhan@gmail.com", "", "12", false},
            {"Submit with all valid inputs", "Test User", "test.user@example.com", "03123456789", "test1234", true}
        };

        for (Object[] scenario : scenarios) {
            String title = (String) scenario[0];
            String name = (String) scenario[1];
            String email = (String) scenario[2];
            String phone = (String) scenario[3];
            String password = (String) scenario[4];
            boolean fillDropdowns = (boolean) scenario[5];

            ExtentTest tc = node.createNode(title);

            // Clear and fill inputs
            branchPage.nameInputField().clear();
            branchPage.emailInputField().clear();
            branchPage.phoneInputField().clear();
            branchPage.passwordInputField().clear();
            Thread.sleep(1000);

            branchPage.nameInputField().sendKeys(name);
            branchPage.emailInputField().sendKeys(email);
            branchPage.phoneInputField().sendKeys(phone);
            branchPage.passwordInputField().sendKeys(password);

            if (fillDropdowns) {
                genderSelect.selectByVisibleText("Male");
                roleSelect.selectByVisibleText("Admin");
            }

            branchPage.addUserSubmitButton().click();
            Thread.sleep(1000);

            tc.pass("Executed: " + title);
            
            branchPage.nameInputField().clear();
            branchPage.emailInputField().clear();
            branchPage.phoneInputField().clear();
            branchPage.passwordInputField().clear();
            Thread.sleep(1000);
        }}


    @AfterClass
    public void tearDown() {
        extent.flush();
        if (driver != null) {
            // driver.quit(); // Uncomment if needed
        }
    }
}