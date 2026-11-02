package com.framework.shared.hooks;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.framework.ui.drivers.DriverFactory;

public class Hooks {

    // Defines clear execution filtering tags
    private static final String UI_TEST_TAGS = "not @db and not @api";

    @Before(UI_TEST_TAGS)
    public void setUp() {
        DriverFactory.getDriver();
    }

    @After(UI_TEST_TAGS)
    public void tearDown(Scenario scenario) {
        try {
            // Safety check ensures we only talk to existing drivers without waking up a
            // ghost browser
            if (DriverFactory.isDriverExisting()) {
                WebDriver activeDriver = DriverFactory.getDriver();

                if (scenario.isFailed() && activeDriver != null) {
                    byte[] screenshot = ((TakesScreenshot) activeDriver).getScreenshotAs(OutputType.BYTES);
                    scenario.attach(screenshot, "image/png", "Failed_Scenario_Screenshot");
                }
            }
        } catch (Exception e) {
            System.err.println("⚠️ Critical error capturing automation screenshot: " + e.getMessage());
        } finally {
            // Safely terminates session and wipes the thread allocation clean
            DriverFactory.quitDriver();
        }
    }
}
