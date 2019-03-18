package fr.uniamu.ibdm.featuresRunner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "features", glue ={"stepDefinition"})
public class FeatureRunner {
}
