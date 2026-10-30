package com.framework.db.tests;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class DatabaseSteps {

    // DB_CLOSE_DELAY=-1 keeps the in-memory DB alive for the whole JVM
    private static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private Connection connection;

    @Given("an H2 in-memory database with an employees table")
    public void create_database_and_table() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
        try (Statement st = connection.createStatement()) {
            st.execute("DROP TABLE IF EXISTS employees");
            st.execute("CREATE TABLE employees ("
                    + "id INT PRIMARY KEY, "
                    + "name VARCHAR(100) NOT NULL, "
                    + "salary INT NOT NULL)");
        }
    }

    @Given("I insert an employee with id {int}, name {string} and salary {int}")
    public void insert_employee(int id, String name, int salary) throws SQLException {
        insert(id, name, salary);
    }

    @Given("I insert the following employees:")
    public void insert_employees(DataTable table) throws SQLException {
        insertTable(table);
    }

    @When("I update the salary of employee {int} to {int}")
    public void update_salary(int id, int salary) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "UPDATE employees SET salary = ? WHERE id = ?")) {
            ps.setInt(1, salary);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            Assert.assertEquals(rows, 1, "Expected exactly one row to be updated");
        }
    }

    @When("I delete the employee with id {int}")
    public void delete_employee(int id) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM employees WHERE id = ?")) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            Assert.assertEquals(rows, 1, "Expected exactly one row to be deleted");
        }
    }

    @Then("the employee with id {int} should have name {string}")
    public void verify_name(int id, String expectedName) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT name FROM employees WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                Assert.assertTrue(rs.next(), "No employee found with id " + id);
                Assert.assertEquals(rs.getString("name"), expectedName);
            }
        }
    }

    @Then("the employee with id {int} should have salary {int}")
    public void verify_salary(int id, int expectedSalary) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT salary FROM employees WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                Assert.assertTrue(rs.next(), "No employee found with id " + id);
                Assert.assertEquals(rs.getInt("salary"), expectedSalary);
            }
        }
    }

    @Then("the total number of employees should be {int}")
    public void verify_count(int expectedCount) throws SQLException {
        Assert.assertEquals(count("SELECT COUNT(*) FROM employees"), expectedCount,
                "Unexpected number of employees");
    }

    @Then("{int} employees should earn more than {int}")
    public void verify_salary_threshold(int expectedCount, int threshold) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT COUNT(*) FROM employees WHERE salary > ?")) {
            ps.setInt(1, threshold);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                Assert.assertEquals(rs.getInt(1), expectedCount);
            }
        }
    }

    @After("@db")
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    // ---------- helpers ----------

    private void insert(int id, String name, int salary) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO employees (id, name, salary) VALUES (?, ?, ?)")) {
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setInt(3, salary);
            ps.executeUpdate();
        }
    }

    private void insertTable(DataTable table) throws SQLException {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            insert(Integer.parseInt(row.get("id")),
                    row.get("name"),
                    Integer.parseInt(row.get("salary")));
        }
    }

    private int count(String sql) throws SQLException {
        try (Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            return rs.getInt(1);
        }
    }
}