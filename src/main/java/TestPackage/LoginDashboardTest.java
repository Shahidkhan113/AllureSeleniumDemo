package TestPackage;

import org.testng.annotations.*;

import PagesObjectModel.LoginPage;
import PagesObjectModel.DashboardPage;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class LoginDashboardTest extends TestBasetp {

    LoginPage loginPage;
    DashboardPage dashboardPage;

    ExtentReports extent;
    ExtentTest test;

    @BeforeTest
    public void setupReport() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("OrangeHRM_LoginDashboard_Report.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        test = extent.createTest("Login and Dashboard Test");
    }

    @BeforeMethod
    public void setupBrowser() throws InterruptedException {
        initializeBrowser(); // ✅ method from TestBasetp
        test.info("Browser launched and navigated to login page");
        Thread.sleep(3000);
    }

    @Test
    public void testLoginAndDashboardAndLogout() throws InterruptedException {
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);

        loginPage.enterUsername("Admin");
        loginPage.enterPassword("admin123");
        loginPage.clickLoginButton();
        test.info("Entered login details");

        Thread.sleep(3000); // wait for dashboard

        if (dashboardPage.isDashboardVisible()) {
            test.pass("Dashboard loaded successfully after login");
        } else {
            test.fail("Dashboard not visible after login");
        }

        dashboardPage.clickProfileIcon();
        Thread.sleep(1000);
        dashboardPage.clickLogout();
        Thread.sleep(1000);

        if (driver.getCurrentUrl().contains("login")) {
            test.pass("Successfully logged out and redirected to login page");
        } else {
            test.fail("Logout failed");
        }
    }

    @AfterMethod
    public void closeBrowser() {
        driver.quit();
        test.info("Browser closed");
    }

    @AfterTest
    public void generateReport() {
        extent.flush();
    }
}
