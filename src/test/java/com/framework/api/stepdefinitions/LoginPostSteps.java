package com.framework.api.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import java.util.HashMap;
import java.util.Map;

public class LoginPostSteps {

    private RequestSpecification request;
    private Response response;
    private String accessToken; // Class variable to store the token for subsequent requests

    @Given("I hit the endpoint {string}")
    public void i_hit_the_endpoint(String endpointUrl) {
        RestAssured.baseURI = endpointUrl;
        request = RestAssured.given().contentType(ContentType.JSON);
    }

    @When("I send a POST request to {string} with email {string} and password {string}")
    public void i_send_a_post_request_to_with_email_and_password(String resourcePath, String email, String password) {
        // 1. Create request payload map matching the API specifications
        Map<String, Object> loginPayload = new HashMap<>();
        loginPayload.put("email", email);
        loginPayload.put("password", password);

        // 2. Execute the POST call
        response = request.body(loginPayload).post(resourcePath);
    }

    @And("the response should contain a valid accessToken")
    public void the_response_should_contain_a_valid_access_token() {
        // 3. Extract the token value using RestAssured JsonPath
        JsonPath jsonPath = response.jsonPath();

        // ReqRes.in returns the key as "token" in the response: { "token":
        // "QpwL5tke4Pnpja7X4" }
        accessToken = jsonPath.getString("token");

        // 4. Assert token properties
        Assert.assertNotNull(accessToken, "Access token was null!");
        Assert.assertFalse(accessToken.isEmpty(), "Access token was empty!");

        // Output confirmation to console
        System.out.println("==================================================");
        System.out.println("Login Successful!");
        System.out.println("Extracted Access Token: " + accessToken);
        System.out.println("==================================================");
    }
}
