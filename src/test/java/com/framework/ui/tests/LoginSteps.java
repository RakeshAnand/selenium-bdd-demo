package com.framework.ui.tests;

import com.framework.ui.drivers.DriverFactory;
import com.framework.ui.pages.LoginPage;
import com.framework.shared.utils.ConfigReader;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
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
        driver.get(ConfigReader.get("app.url"));
    }

    @When("the user enters valid credentials")
    public void the_user_enters_valid_credentials() {
        loginPage.enterUsername(ConfigReader.get("app.username"));
        loginPage.enterPassword(ConfigReader.get("app.password"));
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

        // Returns as soon as EITHER an alert appears OR the dashboard URL loads
        wait.until(d -> {
            try {
                d.switchTo().alert();
                return true;
            } catch (NoAlertPresentException e) {
                return d.getCurrentUrl().contains("Managerhomepage.php");
            }
        });

        try {
            Alert alert = driver.switchTo().alert();
            String msg = alert.getText();
            alert.accept();
            Assert.fail("Login failed with alert: " + msg);
        } catch (NoAlertPresentException e) {
            // no alert, login succeeded
        }

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
