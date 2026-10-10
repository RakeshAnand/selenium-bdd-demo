package com.example.hooks;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import com.example.factory.DriverFactory;

public class Hooks {
    @Before
    public void setUp() {
        DriverFactory.getDriver();
    }

    @After
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}