package com.batch2.AutomationFrameworkFromScratch.runner;

import org.testng.annotations.AfterSuite;

import com.github.mkolisnyk.cucumber.reporting.CucumberDetailedResults;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;


 @ExtendedCucumberOptions(
    overviewReport =true,
    detailedAggregatedReport = true,
    coverageReport=true,
    jsonUsageReport ="target/cucumber_usage.json",
    toPDF= true,
    retryCount = 1,
    outputFolder="target")
 @CucumberOptions(
    plugin = {
      "pretty",
      "html:target/cucumber-reports/cucumber-html-report(sandwitch)",
      "json:target/cucumber-reports/cucumber.json",
      "rerun:target/cucumber-reports/rerun.txt"
},
    tags = { "@Etsy" }, 
    features= {"src/test/resources/features/"},
    glue="com/batch2/AutomationFrameworkFromScratch/step_definitions",
    monochrome = true, //display the console output in a proper readable format
    strict = true, //it will check if any step is not defined in step definition file
    dryRun=false
)

public class Regular_Runner extends AbstractTestNGCucumberTests {
	 
	 @AfterSuite
		public void generateDetailedReport() throws Exception{
			
			CucumberDetailedResults results = new CucumberDetailedResults();
			results.setOutputDirectory("target/");
			results.setOutputName("cucumber-reports");
			results.setSourceFile("./target/cucumber-reports/cucumber.json");
			results.setScreenShotLocation("../src/test/resources/gov/cms/ess/screenshots/");
	        results.execute(true);
	 }
	
}
	 
