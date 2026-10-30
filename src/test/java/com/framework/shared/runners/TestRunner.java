package com.framework.shared.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(features = "src/test/resources/features", glue = {
        "com.framework.ui.tests", // Where LoginSteps & AddCustomerSteps live
        "com.framework.api.tests", // Where UserSteps lives
        "com.framework.db.tests", // Where DatabaseSteps lives
        "com.framework.ui.drivers" // Where Hooks.java lives
}, plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
}, monochrome = true, tags = "not @ignore")
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
