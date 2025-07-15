package PagesObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class tplogin {

    WebDriver driver;

    // Constructor
    public tplogin(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Locators
    @FindBy(id = "mat-input-0")
    WebElement emailInput;

    @FindBy(id = "mat-input-1")
    WebElement passwordInput;

    @FindBy(xpath = "//button[@class='mat-focus-indicator btnLogin mat-raised-button mat-button-base']//span")
    WebElement loginBtn;

    @FindBy(xpath = "//div[@class='ng-star-inserted']")
    WebElement errorMsg;

    @FindBy(xpath = "//button[@type='button']")
    WebElement okButton;
    
    // OTP boxes
    public void enterOTP(int index, String digit) {
        WebElement otpBox = driver.findElement(By.xpath("//ng-otp-input/div/input[" + index + "]"));
        otpBox.sendKeys(digit);
    }

    // Actions
    public void enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickLoginButton() {
        loginBtn.click();
    }

    public String getErrorMessage() {
        return errorMsg.getText();
    }

    public void clickOkButton() {
        okButton.click();
    }
    
}
