package runners; // ✅ match with folder: src/test/java/runners/

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
	    features = "src/test/resources/features",
	    glue = "stepdefs",
	    plugin = {
	        "pretty",
	        "html:target/cucumber-reports.html"
	    },
	    monochrome = true,
	    tags = "@LoginTest"
	)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
	