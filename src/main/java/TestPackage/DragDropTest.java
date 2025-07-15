package TestPackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import org.testng.annotations.*;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DragDropTest {

    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeTest
    public void setup() {
        // Launch browser
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Setup report
        extent = new ExtentReports();
        extent.attachReporter(new ExtentSparkReporter("DragDropReport.html"));
        test = extent.createTest("Drag & Drop + Advanced Actions Test");

        // Navigate to a demo page that supports drag-and-drop
        driver.get("https://jqueryui.com/droppable/");
        test.info("Opened droppable demo page");
    }

    @Test
    public void testAdvancedActions() throws InterruptedException {
        Actions actions = new Actions(driver);

        // Switch into frame that contains drag able elements
        driver.switchTo().frame(driver.findElement(By.className("demo-frame")));

        // Locate drag able and drop able
        WebElement src = driver.findElement(By.id("draggable"));
        WebElement tgt = driver.findElement(By.id("droppable"));

        // 1. Drag and drop
        actions.dragAndDrop(src, tgt).perform();
        test.pass("Performed drag and drop");

        Thread.sleep(1000);

        // 2. Double-click
        driver.switchTo().defaultContent();
        driver.get("https://api.jquery.com/dblclick/");
        test.info("Opened double-click demo page");

        driver.switchTo().frame(0);
        WebElement box = driver.findElement(By.tagName("div"));
        actions.doubleClick(box).perform();
        test.pass("Performed double-click");

        Thread.sleep(1000);

        // 3. Right-click (context click)
        driver.switchTo().defaultContent();
        driver.get("https://swisnl.github.io/jQuery-contextMenu/demo.html");
        test.info("Opened right-click demo");

        WebElement rightClickBtn = driver.findElement(By.cssSelector(".context-menu-one"));
        actions.contextClick(rightClickBtn).perform();
        test.pass("Performed right-click");

        Thread.sleep(1000);

        // 4. Hover + drop down
        driver.get("https://demoqa.com/menu");
        test.info("Opened hover menu demo");

        WebElement mainItem = driver.findElement(By.xpath("//a[text()='Main Item 2']"));
        actions.moveToElement(mainItem).perform();
        test.pass("Hovered over Main Item 2");

        WebElement subItem = driver.findElement(By.xpath("//a[text()='SUB SUB LIST »']"));
        actions.moveToElement(subItem).perform();
        test.pass("Hovered over sub menu");

        WebElement deepItem = driver.findElement(By.xpath("//a[text()='Sub Sub Item 1']"));
        deepItem.click();
        test.pass("Clicked on deep sub menu item");
        

        extent.flush();
    }

    @AfterTest
    public void teardown() {
        if (driver != null); 
        	//driver.quit();
    }
}
