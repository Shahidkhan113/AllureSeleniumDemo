package stepdefs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import PagesObjectModel.tplogin;
import TestPackage.TestBasetp;
import io.cucumber.java.en.*;

public class LoginSteps extends TestBasetp {

    tplogin loginPage;
    String caseType;

    @Given("User is on the login page")
    public void user_is_on_login_page() {
        initializeBrowser();             // ✅ Open browser
        loginPage = new tplogin(driver); // ✅ Page Object create
    }

    @When("User enters {string} and {string}")
    public void user_enters_credentials(String email, String password) {
        loginPage.enterEmail(email);        // ✅ Enter email
        loginPage.enterPassword(password);  // ✅ Enter password
    }

    @Then("User should see the result {string}")
    public void user_should_see_result(String caseType) throws InterruptedException {
        this.caseType = caseType;

        // ✅ Eye icon click for valid login only
        if (caseType.equals("Valid Login")) {
            try {
                WebElement eyeIcon = driver.findElement(By.xpath("//mat-icon[contains(text(),'visibility')]"));
                eyeIcon.click();
            } catch (Exception e) {
                System.out.println("Eye icon not found: " + e.getMessage());
            }
        }

        loginPage.clickLoginButton();
        Thread.sleep(2000); // wait for page response

        String currentURL = driver.getCurrentUrl();

        // ❌ Invalid login check
        if (!caseType.equals("Valid Login")) {
            if (loginPage.getErrorMessage().equals("You have entered invalid credentials")) {
                System.out.println(caseType + " passed");
            } else {
                System.out.println(caseType + " failed");
            }
            loginPage.clickOkButton(); // close alert
        } 
        // ✅ Valid login + OTP
        else {
            if (currentURL.contains("auth/login")) {
            	String[] arrtOTP_CoDash = {"0", "1", "0", "1", "0", "1"};

            	for (int j = 1; j <= 6; j++) {
            	    loginPage.enterOTP(j, arrtOTP_CoDash[j - 1]);
            	}
                Thread.sleep(3000);
                if (!driver.getCurrentUrl().contains("auth/login")) {
                    System.out.println("Valid Login passed and redirected to dashboard");
                } else {
                    System.out.println("OTP accepted but not redirected");
                }
            } else {
                System.out.println("Login failed to reach OTP screen");
            }
        }
    }
}
