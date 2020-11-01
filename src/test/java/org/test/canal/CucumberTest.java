package org.test.canal;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
//import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;


@RunWith(CucumberReportRunner.class)
@CucumberOptions(features = "classpath:features",
                 plugin = {"json:target/cucumber-report.json",
                           "pretty:target/cucumber-pretty.txt"},
                 tags= "@adresse and @modification",
                 glue = {"org.test.canal.config",
                         "org.test.canal.stepDefinition"})

@TestPropertySource(locations = "classpath:application-bdd.properties")
public class CucumberTest {


    
}



