package com.batch2.AutomationFrameworkFromScratch.runner;

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
	    tags = "@smoke", 
	    features= {"src/test/resources/features/"},
	    glue="com/batch2/AutomationFrameworkFromScratch/step_definitions",
	    monochrome = true, //display the console output in a proper readable format
	    strict = true, //it will check if any step is not defined in step definition file
	    dryRun=false
	)


public class Smoke_Runner extends AbstractTestNGCucumberTests {

}
