package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddCustomerPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By customerName = By.name("name");
    private final By maleRadio = By.xpath("//input[@name='rad1' and @value='m']");
    private final By femaleRadio = By.xpath("//input[@name='rad1' and @value='f']");
    private final By dob = By.name("dob");
    private final By address = By.name("addr");
    private final By city = By.name("city");
    private final By state = By.name("state");
    private final By pin = By.name("pinno");
    private final By mobile = By.name("telephoneno");
    private final By email = By.name("emailid");
    private final By password = By.name("password");
    private final By submit = By.name("sub");

    private final By successHeading = By.cssSelector("p.heading3");
    private final By customerId = By.xpath("//td[normalize-space()='Customer ID']/following-sibling::td");

    public AddCustomerPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void open() {
        driver.get("https://demo.guru99.com/V4/manager/addcustomerpage.php");
        wait.until(ExpectedConditions.visibilityOfElementLocated(customerName));
    }

    private void type(By locator, String value) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        el.sendKeys(value);
    }

    public void enterName(String value) {
        type(customerName, value);
    }

    public void selectGender(String gender) {
        By radio = "female".equalsIgnoreCase(gender) ? femaleRadio : maleRadio;
        wait.until(ExpectedConditions.elementToBeClickable(radio)).click();
    }

    /**
     * Date format yyyy-MM-dd. Set via JS because sendKeys on date inputs depends on
     * browser locale.
     */
    public void enterDob(String isoDate) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(dob));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1];" +
                        "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));" +
                        "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));" +
                        "arguments[0].dispatchEvent(new Event('blur', {bubbles:true}));",
                el, isoDate);
    }

    public void enterAddress(String value) {
        type(address, value);
    }

    public void enterCity(String value) {
        type(city, value);
    }

    public void enterState(String value) {
        type(state, value);
    }

    public void enterPin(String value) {
        type(pin, value);
    }

    public void enterMobile(String value) {
        type(mobile, value);
    }

    public void enterEmail(String value) {
        type(email, value);
    }

    public void enterPassword(String value) {
        type(password, value);
    }

    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(submit)).click();
    }

    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successHeading)).getText();
    }

    public String getCustomerId() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(customerId)).getText().trim();
    }
}
