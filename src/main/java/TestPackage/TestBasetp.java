package TestPackage;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBasetp {

    public static WebDriver driver;

    public void initializeBrowser() {
        WebDriverManager.chromedriver().setup(); // Auto-setup latest ChromeDriver
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://company-staging.thriftplan.com.sa/#/auth/login"); // Your site URL
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
}
