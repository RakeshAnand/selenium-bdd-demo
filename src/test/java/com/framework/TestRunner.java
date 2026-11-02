package com.framework;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(features = "src/test/resources/features", glue = {
        "com.framework.ui.stepdefinitions", // Matches your UI stepdefinitions folder
        "com.framework.api.stepdefinitions", // Matches your API stepdefinitions folder
        "com.framework.db.stepdefinitions", // Matches your DB stepdefinitions folder
        "com.framework.shared.hooks" // Matches your shared hooks folder where Hooks.java lives
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
