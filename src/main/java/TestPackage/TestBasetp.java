package TestPackage;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBasetp {

    public static WebDriver driver;

    public void initializeBrowser() {
        try {
            WebDriverManager.chromedriver().setup(); // ✅ Setup latest ChromeDriver
            driver = new ChromeDriver();              // ✅ Launch Chrome
            driver.manage().window().maximize();      // ✅ Maximize browser
            driver.get("http://company-staging.thriftplan.com.sa/#/auth/login"); // ✅ Your app URL
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));   // ✅ Implicit Wait
        } catch (Exception e) {
            e.printStackTrace(); // Show any error in console
        }
    }
}
