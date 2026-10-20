package com.example.stepdefinitions;

import com.example.factory.DriverFactory;
import com.example.pages.AddCustomerPage;
import com.example.pages.CustomerRegistrationPage;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.Map;

public class AddCustomerSteps {

    private final AddCustomerPage addCustomerPage;
    private final CustomerRegistrationPage registrationPage;
    private String customerId;

    public AddCustomerSteps() {
        WebDriver driver = DriverFactory.getDriver();
        this.addCustomerPage = new AddCustomerPage(driver);
        this.registrationPage = new CustomerRegistrationPage(driver);
    }

    @Given("the user opens the add customer page")
    public void the_user_opens_the_add_customer_page() {
        addCustomerPage.open();
    }

    @When("the user enters the following customer details:")
    public void the_user_enters_the_following_customer_details(DataTable table) {
        Map<String, String> d = table.asMaps(String.class, String.class).get(0);

        String email = "random".equalsIgnoreCase(d.get("email"))
                ? "user" + System.currentTimeMillis() + "@example.com"
                : d.get("email");

        addCustomerPage.enterName(d.get("name"));
        addCustomerPage.selectGender(d.get("gender"));
        addCustomerPage.enterDob(d.get("dob"));
        addCustomerPage.enterAddress(d.get("address"));
        addCustomerPage.enterCity(d.get("city"));
        addCustomerPage.enterState(d.get("state"));
        addCustomerPage.enterPin(d.get("pin"));
        addCustomerPage.enterMobile(d.get("mobile"));
        addCustomerPage.enterEmail(email);
        addCustomerPage.enterPassword(d.get("password"));
    }

    @When("the user submits the customer form")
    public void the_user_submits_the_customer_form() {
        addCustomerPage.clickSubmit();
    }

    @Then("the customer should be registered successfully")
    public void the_customer_should_be_registered_successfully() {
        try {
            String message = addCustomerPage.getSuccessMessage();
            Assert.assertEquals(message, "Customer Registered Successfully!!!");
        } catch (org.openqa.selenium.TimeoutException | org.openqa.selenium.UnhandledAlertException e) {
            org.openqa.selenium.WebDriver d = DriverFactory.getDriver();
            try {
                Alert alert = d.switchTo().alert();
                String text = alert.getText();
                alert.accept();
                Assert.fail("Customer was not registered. Alert: " + text);
            } catch (NoAlertPresentException ex) {
                String body = d.findElement(org.openqa.selenium.By.tagName("body")).getText();
                System.out.println("URL: " + d.getCurrentUrl());
                System.out.println("TITLE: " + d.getTitle());
                System.out.println("BODY: " + body.substring(0, Math.min(600, body.length())));
                Assert.fail("Success message not shown. See URL/BODY printed above.", e);
            }
        }
    }

    @Then("the generated customer id should be displayed")
    public void the_generated_customer_id_should_be_displayed() {
        customerId = registrationPage.getCustomerId();
        Assert.assertFalse(customerId.isEmpty(), "Customer ID was empty");
        System.out.println("Created customer ID: " + customerId);
    }
}