package com.example.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Download the driver binary once per JVM, not once per thread
    static {
        WebDriverManager.chromedriver().setup();
    }

    public static WebDriver getDriver() {
        // Skip the browser entirely for API-only runs
        String testType = System.getProperty("testType", "ui");
        if ("api".equalsIgnoreCase(testType)) {
            return null;
        }

        if (driver.get() == null) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");

            if ("true".equals(System.getenv("CI"))) {
                options.addArguments("--headless=new");
                options.addArguments("--disable-gpu");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
            } else {
                options.addArguments("--start-maximized");
            }

            driver.set(new ChromeDriver(options));
        }
        return driver.get();
    }

    public static void quitDriver() {
        WebDriver current = driver.get();
        if (current != null) {
            current.quit();
            driver.remove(); // important: prevents leaks in thread pools
        }
    }
}