package cucumberRunner;


import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


	@RunWith(Cucumber.class)
	@CucumberOptions(
			features = "src/main/java/features/login.feature", //the path of the feature files
			glue={"stepDefinitions"}, //the path of the step definition files
			dryRun = false,
			//format = {"pretty"}
			monochrome = true //display the console output in a proper readable format
			//strict = true,
			//tags = {"~@SmokeTest" , "~@RegressionTest", "~@End2End"}			
			)
	 
	public class TestRunner {
	}
	