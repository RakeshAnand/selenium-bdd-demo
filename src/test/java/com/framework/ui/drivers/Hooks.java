package com.framework.ui.drivers;

import io.cucumber.java.Before;
import io.cucumber.java.After;

import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks {
    @Before("not @db and not @api")
    public void setUp() {
        DriverFactory.getDriver();
    }

    @After("not @db and not @api")
    public void tearDown(Scenario scenario) {
        try {
            // Fetch the active driver instance without forcing a new window initialization
            WebDriver activeDriver = DriverFactory.getDriver();

            if (scenario.isFailed() && activeDriver != null) {
                byte[] screenshot = ((TakesScreenshot) activeDriver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failed_Scenario_Screenshot");
            }
        } catch (Exception e) {
            System.err.println("⚠️ Critical error capturing screenshot: " + e.getMessage());
        } finally {
            // Always terminate the session to prevent orphaned browser processes
            DriverFactory.quitDriver();
        }
    }
}