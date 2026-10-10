package com.example.stepdefinitions;

import com.example.factory.DriverFactory;
import com.example.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert; // Importing TestNG Assertions to match your pom.xml

public class LoginSteps {

    private WebDriver driver;
    private LoginPage loginPage;

    // Zero-argument constructor cleanly pulls the running driver from DriverFactory
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
        // FIX: Updated expected URL string to match the real post-login dashboard path
        String expectedUrl = "https://demo.guru99.com/V4/manager/Managerhomepage.php";

        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "User was not redirected to the dashboard!");
    }

    @Then("an error message {string} should be displayed")
    public void an_error_message_should_be_displayed(String expectedErrorMessage) {
        String actualMessage = loginPage.getErrorMessage();
        Assert.assertTrue(actualMessage.contains(expectedErrorMessage),
                String.format("Expected error message '%s' but got '%s'", expectedErrorMessage, actualMessage));
    }
}