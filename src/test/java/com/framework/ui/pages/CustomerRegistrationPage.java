package com.framework.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CustomerRegistrationPage {

    private final WebDriverWait wait;

    private final By successHeading = By.xpath("//*[contains(normalize-space(),'Customer Registered Successfully')]");
    private final By customerId = By.xpath("//td[normalize-space()='Customer ID']/following-sibling::td");
    private final By customerName = By.xpath("//td[normalize-space()='Customer Name']/following-sibling::td");
    private final By email = By.xpath("//td[normalize-space()='Email']/following-sibling::td");

    public CustomerRegistrationPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successHeading)).getText().trim();
    }

    public String getCustomerId() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(customerId)).getText().trim();
    }

    public String getCustomerName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(customerName)).getText().trim();
    }

    public String getEmail() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(email)).getText().trim();
    }
}