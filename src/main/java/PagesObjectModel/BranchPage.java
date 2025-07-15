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

    public WebElement emailField() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
    }

    public WebElement passwordField() {
        return driver.findElement(By.name("password"));
    }

    public WebElement loginButton() {
        return driver.findElement(By.cssSelector("button.btn.btn-primary"));
    }

    public WebElement branchesLink() {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href,'/super-admin/branches')]")));
    }

    public WebElement addNewButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Add New')]")));
    }

    public WebElement nameInputField() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@name='name']")));
    }

    public WebElement locationInputField() {
        return driver.findElement(By.id("search-box-add"));
    }

    public WebElement statusDropdown() {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//select[contains(@class,'form-control')]")));
    }

    public WebElement saveButton() {
        return driver.findElement(By.xpath("//button[text()='Save']"));
    }
}
