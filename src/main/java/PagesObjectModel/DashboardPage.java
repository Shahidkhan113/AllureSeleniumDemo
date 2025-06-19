package PagesObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage {

    WebDriver driver;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isDashboardVisible() {
        return driver.getCurrentUrl().contains("/dashboard");
    }

    public void clickProfileIcon() {
        driver.findElement(By.className("oxd-userdropdown-tab")).click();
    }

    public void clickLogout() {
        driver.findElement(By.xpath("//a[text()='Logout']")).click();
    }
}
