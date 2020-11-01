package com.testCanal.testCanal;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(plugin = {"pretty"},tags= "@adresse and @modification",features= "src/test/resources/features")
public class RunCucumberTest {

}
