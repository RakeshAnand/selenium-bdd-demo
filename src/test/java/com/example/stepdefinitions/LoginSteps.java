package com.example.stepdefinitions;

import com.example.factory.DriverFactory;
import com.example.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List; // CORRECT IMPORT

public class LoginSteps {

    private WebDriver driver;
    private LoginPage loginPage;

    public LoginSteps() {
        this.driver = DriverFactory.getDriver();
        this.loginPage = new LoginPage(this.driver);
    }

    @Given("the user is on the login page")
    public void the_user_is_on_the_login_page() {
        driver.get("https://demo.guru99.com/V4/");
    }

    @When("the user enters username {string} and password {string}")
    public void the_user_enters_username_and_password(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("clicks the login button")
    public void clicks_the_login_button() {
        loginPage.clickLogin();
    }

    @Then("the user should see the dashboard page")
    public void the_user_should_see_the_dashboard_page() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // If login fails, the site shows a JS alert. Surface its text.
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            String msg = alert.getText();
            alert.accept();
            Assert.fail("Login failed with alert: " + msg);
        } catch (TimeoutException e) {
            // No alert appeared, which is what we want
        }

        wait.until(ExpectedConditions.urlContains("Managerhomepage.php"));
        Assert.assertEquals(driver.getCurrentUrl(),
                "https://demo.guru99.com/V4/manager/Managerhomepage.php",
                "User was not redirected to the dashboard!");
    }

    @Then("an error message {string} should be displayed")
    public void an_error_message_should_be_displayed(String expectedErrorMessage) {
        String actualMessage = loginPage.getErrorMessage();
        Assert.assertTrue(actualMessage.contains(expectedErrorMessage),
                String.format("Expected error message '%s' but got '%s'", expectedErrorMessage, actualMessage));
    }

    @Then("print all left pane link names")
    public void print_all_left_pane_link_names() {
        List<String> linkNames = loginPage.getLeftPaneLinkNames();
        for (String name : linkNames) {
            System.out.println("Left Pane Link: " + name);
        }
    }
}
