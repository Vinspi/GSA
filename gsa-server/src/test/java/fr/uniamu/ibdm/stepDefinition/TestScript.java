package fr.uniamu.ibdm.stepDefinition;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;


public class TestScript {

    @When("^I open browser and tap \"(.*?)\"$")
    public void iOpenBrowser(){
    }

    @Then("^i should see login page and login with \"(.*?)\" and \"(.*?)\"$")
    public void login(){
    }
}
