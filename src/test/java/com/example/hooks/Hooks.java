package com.example.hooks;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import com.example.factory.DriverFactory;

public class Hooks {
    @Before("not @db and not @api")
    public void setUp() {
        DriverFactory.getDriver();
    }

    @After("not @db and not @api")
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}