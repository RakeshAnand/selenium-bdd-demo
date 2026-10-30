package com.framework.api.tests;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.framework.api.models.User;
import org.testng.Assert;

public class UserSteps {
    private RequestSpecification request;
    private Response response;

    @Given("the API base URI is {string}")
    public void setBaseUri(String baseUri) {
        RestAssured.baseURI = baseUri;
        request = RestAssured.given().header("Content-Type", "application/json");
    }

    @When("I send a POST request to {string} with name {string} and job {string}")
    public void sendPostRequest(String endpoint, String name, String job) {
        User userPayload = new User(name, job);
        response = request.body(userPayload).post(endpoint);
    }

    @Then("the response status code should be {int}")
    public void verifyStatusCode(int expectedStatus) {
        Assert.assertEquals(response.getStatusCode(), expectedStatus);
    }

    @Then("the response body should contain the generated row field {string}")
    public void verifyFieldExists(String fieldName) {
        Assert.assertNotNull(response.jsonPath().get(fieldName), "Database ID assignment failed!");
    }

    @Then("the database field {string} value should match {string}")
    public void verifyFieldValue(String fieldName, String expectedValue) {
        String actualValue = response.jsonPath().getString(fieldName);
        Assert.assertEquals(actualValue, expectedValue);
    }
}
