package com.example.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List; // CORRECT IMPORT
import java.util.stream.Collectors;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By usernameField = By.name("uid");
    private By passwordField = By.name("password");
    private By loginButton = By.name("btnLogin");
    private By leftPaneLinks = By.xpath("//ul[@class='menusubnav']/li/a");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public String getErrorMessage() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            alert.accept();
            return alertText;
        } catch (Exception e) {
            return "No alert displayed";
        }
    }

    // Standard List return signature
    public List<String> getLeftPaneLinkNames() {
        List<WebElement> links = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(leftPaneLinks));
        return links.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}
