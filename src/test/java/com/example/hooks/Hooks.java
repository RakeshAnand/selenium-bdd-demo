package com.example.hooks;

import io.cucumber.java.Before;
import io.cucumber.java.After;

import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.example.factory.DriverFactory;

public class Hooks {
    @Before("not @db and not @api")
    public void setUp() {
        DriverFactory.getDriver();
    }

    @After("not @db and not @api")
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                WebDriver d = DriverFactory.getDriver();
                if (d != null) {
                    byte[] png = ((TakesScreenshot) d).getScreenshotAs(OutputType.BYTES);
                    scenario.attach(png, "image/png", "failure");
                }
            }
        } finally {
            DriverFactory.quitDriver();
        }
    }
}