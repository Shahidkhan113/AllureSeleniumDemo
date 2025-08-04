package PagesObjectModel;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class BranchPage {

    WebDriver driver;
    WebDriverWait wait;

    public BranchPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Login Elements
    public WebElement emailField() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
    }

    public WebElement passwordField() {
        return driver.findElement(By.name("password"));
    }

    public WebElement loginButton() {
        return driver.findElement(By.cssSelector("button.btn.btn-primary"));
    }

    // Navigation Link: Internal Users
    public WebElement internalUsersLink() {
        return wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.nav-trigger[routerlink='/super-admin/admins']")));
    }

    // Add New Button
    public WebElement addNewButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.btn.btn-primary.pull-right")));
    }

    // Form Fields
    public WebElement nameInputField() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));
    }

    public WebElement emailInputField() {
        return driver.findElement(By.name("email"));
    }

    public WebElement phoneInputField() {
        return driver.findElement(By.name("phone"));
    }

    public WebElement passwordInputField() {
        return driver.findElement(By.name("password"));
    }

    // Gender Dropdown (Male/Female)
    public WebElement genderDropdown() {
        return driver.findElements(By.cssSelector("select.form-control")).get(0); // first dropdown
    }

    // Role Dropdown (Admin/Coordinator)
    public WebElement roleDropdown() {
        return driver.findElements(By.cssSelector("select.form-control")).get(1); // second dropdown
    }

    // Submit Button
    public WebElement addUserSubmitButton() {
        return driver.findElement(By.cssSelector("button.btn.btn-primary[type='submit']"));
    }
}
