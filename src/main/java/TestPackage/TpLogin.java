package TestPackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import PagesObjectModel.tplogin;
public class TpLogin extends TestBasetp {

	String[] arrtOTP_CoDash = {"0", "1", "0", "1", "0", "1"};
    ExtentReports extent;
    ExtentTest test;
    tplogin loginPage;

    @BeforeTest
    public void setup() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("CoDashLoginReport.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);

        test = extent.createTest("ThriftPlan Company Dashboard Login Test");

        initializeBrowser(); // Opens Chrome and URL
        loginPage = new tplogin(driver); // Page object created
    }

    @Test
    public void CoDash_LoginPage() throws InterruptedException {
        String incorrectEmail = "muhammad.shakeel2@veroke.sa";
        String correctEmail = "muhammad.shakeel@abc.com";
        String incorrectPassword = "123321";
        String correctPassword = "123123";

        // Test Case 1: Incorrect Email
        loginPage.enterEmail(incorrectEmail);
        loginPage.enterPassword(correctPassword);
        loginPage.clickLoginButton();
        Thread.sleep(1000);
        if (loginPage.getErrorMessage().equals("You have entered invalid credentials")) {
            test.pass("Invalid Email test passed");
        } else {
            test.fail("Invalid Email test failed");
        }
        loginPage.clickOkButton();

        // Test Case 2: Incorrect Password 
        loginPage.enterEmail(correctEmail);
        loginPage.enterPassword(incorrectPassword);
        loginPage.clickLoginButton();
        Thread.sleep(1000);
        if (loginPage.getErrorMessage().equals("You have entered invalid credentials")) {
            test.pass("Invalid Password test passed");
        } else {
            test.fail("Invalid Password test failed");
        }
        loginPage.clickOkButton();

        //  Test Case 3: Valid Login with Show Password & OTP
        loginPage.enterEmail(correctEmail);
        loginPage.enterPassword(correctPassword);

        // Click Eye Icon to Show Password
        try {
            WebElement eyeIcon = driver.findElement(By.xpath("//mat-icon[contains(text(),'visibility')]"));
            eyeIcon.click();
            //test.info("Clicked on eye icon to show password");

            WebElement passwordField = driver.findElement(By.xpath("//input[@type='password' or @type='text']"));
            String fieldType = passwordField.getAttribute("type");

            if (fieldType.equals("text")) {
                test.pass("Password is now visible (input type='text')");
            } else {
                test.fail("Password is not visible. Current type: " + fieldType);
            }
        } catch (Exception e) {
            test.warning("Eye icon not found or clickable: " + e.getMessage());
        }

        // Click Login Button
        loginPage.clickLoginButton();
        Thread.sleep(2000);

        //  OTP Handling Code
        String actualURLOTP_CoDash = "http://company-staging.thriftplan.com.sa/#/auth/login";
        String expectedURL_CoDash = driver.getCurrentUrl();

        if (actualURLOTP_CoDash.equals(expectedURL_CoDash)) {


            for (int i = 1; i <= 6; i++) {
                WebElement OTPBoxes_CoDash = driver.findElement(By.xpath(
                    "/html/body/app-root/div/app-root/div/app-login/div/div[2]/ng-otp-input/div/input[" + i + "]"));
                OTPBoxes_CoDash.sendKeys(arrtOTP_CoDash[i - 1]);
            }

            test.log(Status.PASS, "OTP entered successfully");
            System.out.println("OTP entered successfully");

        } else {
            test.log(Status.FAIL, "OTP Page not displayed or login failed");
            System.out.println("OTP Page not found");
        }

        //Final URL Check after OTP
        Thread.sleep(5000); // Wait for redirect
        String finalURL = driver.getCurrentUrl();

        if (!finalURL.contains("auth/login")) {
            test.pass("Valid login redirected to home page after OTP");
        } else {
            test.fail("Valid login failed even after OTP input");
        }

        extent.flush();
    }
}
