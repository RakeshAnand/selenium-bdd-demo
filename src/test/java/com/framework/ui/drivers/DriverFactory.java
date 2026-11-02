package com.framework.ui.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    // Keep static setup for legacy systems, but note Selenium 4 handles binaries
    // natively
    static {
        WebDriverManager.chromedriver().setup();
    }

    public static WebDriver getDriver() {
        // Skip browser entirely for explicit API runs
        String testType = System.getProperty("testType", "ui");
        if ("api".equalsIgnoreCase(testType)) {
            return null;
        }

        if (driverThreadLocal.get() == null) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");

            // Modern headless execution targeting string
            if ("true".equalsIgnoreCase(System.getenv("CI"))
                    || "true".equalsIgnoreCase(System.getProperty("headless"))) {
                options.addArguments("--headless=new");
                options.addArguments("--disable-gpu");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
            } else {
                options.addArguments("--start-maximized");
            }

            driverThreadLocal.set(new ChromeDriver(options));
        }
        return driverThreadLocal.get();
    }

    /**
     * Checks whether a browser driver instance actively exists on the current
     * execution thread.
     */
    public static boolean isDriverExisting() {
        return driverThreadLocal.get() != null;
    }

    public static void quitDriver() {
        WebDriver currentDriver = driverThreadLocal.get();
        if (currentDriver != null) {
            try {
                currentDriver.quit();
            } catch (Exception e) {
                System.err.println("⚠️ Exception encountered while closing WebDriver session: " + e.getMessage());
            } finally {
                driverThreadLocal.remove(); // Prevents thread-local memory retention leaks
            }
        }
    }
}
