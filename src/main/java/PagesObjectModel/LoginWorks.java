package PagesObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginWorks {
    WebDriver driver;
    WebDriverWait wait;

    public LoginWorks(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Step 1: Enter password on the demo screen
    public WebElement demoPasswordField() {
        return driver.findElement(By.id("input-10"));
    }

    // Step 2: Click Visit Demo button
    public WebElement visitDemoButton() {
        return driver.findElement(By.xpath("//button[.//span[text()='Visit Demo']]"));
    }

    // Step 3: Login page email field
    public WebElement emailField() {
        return driver.findElement(By.id("input-22"));
    }

    // Step 4: Login page password field
    public WebElement passwordField() {
        return driver.findElement(By.id("input-23"));
    }

    // Step 5: Login button
    public WebElement loginButton() {
        return driver.findElement(By.cssSelector("button[type='submit']"));
    }
}
