package com.capitole.inditex;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.capitole.inditex.stepdefinitions",
    plugin = {"pretty", "html:target/cucumber-reports.html"},
    monochrome = true
)
public class CucumberTest {
}