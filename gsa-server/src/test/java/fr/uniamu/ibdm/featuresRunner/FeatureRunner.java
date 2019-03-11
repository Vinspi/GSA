package fr.uniamu.ibdm.featuresRunner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber"},
        features = {"features"},
        glue={"fr.uniamu.ibdm.stepDefinition"},
        tags = {"@smoke"}
        )

public class FeatureRunner {
}
