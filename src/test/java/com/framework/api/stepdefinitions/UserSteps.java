package com.framework.api.stepdefinitions;

import com.framework.api.models.User;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

public class UserSteps {

    // These share the same object state across the entire scenario context
    private RequestSpecification request;
    private Response response;

    @Given("I use the endpoint {string}")
    public void i_use_the_endpoint(String endpointUrl) {
        RestAssured.baseURI = endpointUrl;
        request = RestAssured.given().contentType(ContentType.JSON);
    }

    @When("I send a POST request to {string} with name {string} and job {string}")
    public void i_send_a_post_request_to_with_name_and_job(String resourcePath, String name, String job) {
        User userPayload = new User(name, job);
        response = request.body(userPayload).post(resourcePath);
    }

    @When("I send a GET request to {string}")
    public void i_send_a_get_request_to(String resourcePath) {
        response = request.get(resourcePath);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer statusCode) {
        // This will now correctly evaluate because response was assigned in the When
        // step above
        Assert.assertEquals(response.getStatusCode(), (int) statusCode);
    }

    @And("the response should contain the created user details")
    public void the_response_should_contain_the_created_user_details() {
        User createdUser = response.as(User.class);
        Assert.assertNotNull(createdUser.getName(), "Response name verification failed");
        Assert.assertTrue(createdUser.getId() > 0, "Generated ID should be greater than 0");
    }

    @And("the response should contain valid user profile information")
    public void the_response_should_contain_valid_user_profile_information() {
        User user = response.as(User.class);
        Assert.assertEquals(user.getId(), 1, "User ID mismatch!");
        Assert.assertNotNull(user.getName(), "User name should not be null");
        Assert.assertNotNull(user.getUsername(), "Username field should not be null");
        Assert.assertNotNull(user.getEmail(), "User email address should not be null");
    }

    @And("I extract and print the detail {string} for the user named {string}")
    public void i_extract_and_print_the_detail_for_the_user_named(String detailPath, String targetName) {
        // Convert 'company.catchPhrase' dot notation to robust map styling:
        // Resulting query: find { it.name == 'Clementina Bauch'
        // }['company']['catchPhrase']
        String formattedPath = detailPath.replace(".", "']['");
        String gPathQuery = String.format("find { it.name == '%s' }['%s']", targetName, formattedPath);

        // Extract the value dynamically from the JSON payload
        Object extractedValue = response.jsonPath().get(gPathQuery);

        // Verify the user profile and requested key actually exist
        Assert.assertNotNull(extractedValue,
                String.format("Could not find property '%s' for user '%s'", detailPath, targetName));

        // Print clean console report
        System.out.println("==================================================");
        System.out.println("User: " + targetName);
        System.out.println("Extracted Detail (" + detailPath + "): " + extractedValue.toString());
        System.out.println("==================================================");
    }

    @And("I extract and print the detail using POJO for the user named {string}")
    public void i_extract_and_print_the_detail_using_pojo(String targetName) {
        // 1. Deserialize the full array payload directly into the User POJO Array
        User[] users = response.as(User[].class);
        User matchedUser = null;

        // 2. Find the user by name using a simple loop
        for (User u : users) {
            if (u.getName().equalsIgnoreCase(targetName)) {
                matchedUser = u;
                break;
            }
        }

        // Verify user was found
        Assert.assertNotNull(matchedUser, "User with name " + targetName + " was not found in response!");

        // 3. Print out whatever complex nested information you want dynamically using
        // Java code!
        System.out.println("=================== POJO EXTRACTION ===================");
        System.out.println("User Name:    " + matchedUser.getName());
        System.out.println("Email:        " + matchedUser.getEmail());
        System.out.println("City:         " + matchedUser.getAddress().getCity());
        System.out.println("Catchphrase:  " + matchedUser.getCompany().getCatchPhrase());
        System.out.println("Lat/Long:     " + matchedUser.getAddress().getGeo().getLat() + " , "
                + matchedUser.getAddress().getGeo().getLng());
        System.out.println("=======================================================");
    }

    @And("I extract and print the address and company details for the user named {string}")
    public void i_extract_and_print_the_address_and_company_details_for_the_user_named(String targetName) {
        // 1. Deserialize the full JSON array payload into the User POJO Array
        User[] users = response.as(User[].class);
        // Print the name of the very first user in the array
        System.out.println("This is test " + users[0].getName());
        User matchedUser = null;

        // 2. Loop through the array to find the target user by name
        for (User u : users) {
            if (u.getName().equalsIgnoreCase(targetName)) {
                matchedUser = u;
                break;
            }
        }

        // Assert that the user was actually found in the response payload
        Assert.assertNotNull(matchedUser, "User with name '" + targetName + "' was not found in the response!");

        // 3. Print out the structured nested details safely using your POJO getters
        System.out.println("==================================================");
        System.out.println("          USER ACCOUNT PROFILE INFO               ");
        System.out.println("==================================================");
        System.out.println("User Name:       " + matchedUser.getName());
        System.out.println("Username:        " + matchedUser.getUsername());
        System.out.println("Email:           " + matchedUser.getEmail());
        System.out.println("Phone:           " + matchedUser.getPhone());
        System.out.println("Website:         " + matchedUser.getWebsite());

        System.out.println("\n--- ADDRESS DETAILS ---");
        System.out.println("Street:          " + matchedUser.getAddress().getStreet());
        System.out.println("Suite:           " + matchedUser.getAddress().getSuite());
        System.out.println("City:            " + matchedUser.getAddress().getCity());
        System.out.println("Zipcode:         " + matchedUser.getAddress().getZipcode());
        System.out.println("Geo Location:    Lat: " + matchedUser.getAddress().getGeo().getLat() +
                " | Lng: " + matchedUser.getAddress().getGeo().getLng());

        System.out.println("\n--- COMPANY DETAILS ---");
        System.out.println("Company Name:    " + matchedUser.getCompany().getName());
        System.out.println("Catchphrase:     " + matchedUser.getCompany().getCatchPhrase());
        System.out.println("Business Spec:   " + matchedUser.getCompany().getBs());
        System.out.println("==================================================");
    }

    @And("print the detail of user named {string}")
    public void i_extract_and_print_the_detail_using_pojo_ignore(String targetName) {
        // 1. Deserialize the full JSON array payload into the User POJO Array
        User[] users = response.as(User[].class);
        // Print the name of the very first user in the array
        System.out.println("This is test " + users[0].getName());

        System.out.println("--- Printing All User Names ---");

        for (int i = 0; i < users.length; i++) {
            if (users[i].getName().equalsIgnoreCase(targetName)) {
                System.out.println("Found " + targetName + " with email: " + users[i].getEmail());
            }
        }
    }

}
