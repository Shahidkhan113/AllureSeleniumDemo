package PagesObjectModel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class AdvancedActions {

    WebDriver driver;
    Actions actions;

    public AdvancedActions(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
    }

    public void mouseHover(WebElement element) {
        actions.moveToElement(element).perform();
    }

    public void rightClick(WebElement element) {
        actions.contextClick(element).perform();
    }

    public void doubleClick(WebElement element) {
        actions.doubleClick(element).perform();
    }

    public void dragAndDrop(WebElement source, WebElement target) {
        actions.dragAndDrop(source, target).perform();
    }
}
