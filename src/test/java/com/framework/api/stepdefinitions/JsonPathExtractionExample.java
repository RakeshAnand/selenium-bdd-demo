package com.framework.api.stepdefinitions;

import java.util.List;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JsonPathExtractionExample {

    public static void main(String[] args) {
        // 1. Send the API request and capture the response
        Response response = RestAssured
                .given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .contentType("application/json")
                .get("/users");

        // 2. Obtain the JsonPath view of the response body
        JsonPath jsonPath = response.jsonPath();

        // --- EXAMPLES OF TARGETED EXTRACTION ---
        System.out.println("--- EXAMPLES OF TARGETED EXTRACTION ---");
        System.out.println("Response Body: " + response.getBody().asString());

        System.out.println("--- EXAMPLE OF LOOP ---");
        // 2. Loop using a standard counter index
        for (int i = 0; i < jsonPath.getInt("size()"); i++) {
            // Dynamically inject the index string into the path
            String name = jsonPath.getString("[" + i + "].name");
            String companyName = jsonPath.getString("[" + i + "].company.name");
            String city = jsonPath.getString("[" + i + "].address.city");
            System.out.println("Index " + i + " -> " + name + " works at " + companyName + " in " + city);

            System.out.println("---------------------------------------------------");
        }

        // Example A: Extract a primitive value from the first item (Index 0)
        String firstUserName = jsonPath.getString("[0].name");
        System.out.println("First User Name: " + firstUserName);

        // Example B: Extract a deep-nested value using dot notation
        String firstUserCity = jsonPath.getString("[0].address.city");
        System.out.println("First User City: " + firstUserCity);

        // Example C: Extract a whole list of specific fields across all items
        List<String> allEmails = jsonPath.getList("email");
        System.out.println("All User Emails: " + allEmails);

        // Example D: Extract an entire nested structure as a Map
        Map<String, Object> companyDetails = jsonPath.getMap("[0].company");
        System.out.println("First User Company Map: " + companyDetails);
        System.out.println("Company Catchphrase: " + companyDetails.get("catchPhrase"));

        // Example E: Advanced Filtering using GPath (Find user by name and get phone)
        String targetName = "Ervin Howell";
        String query = String.format("find { it.name == '%s' }.phone", targetName);
        String phoneNumber = jsonPath.getString(query);
        System.out.println(targetName + "'s Phone Number: " + phoneNumber);
    }
}
