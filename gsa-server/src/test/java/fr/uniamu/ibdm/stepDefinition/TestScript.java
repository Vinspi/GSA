package fr.uniamu.ibdm.stepDefinition;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class TestScript {


    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\lenovo\\Downloads\\chromedriver.exe");
    }

    WebDriver driver = new ChromeDriver();

    @When("^I open browser and enter valid \"(.*?)\" and valid \"(.*?)\"$")
    public void iLogin(String login, String password) {

        driver.get("http://localhost:4200/");
        driver.findElement(By.id("email")).sendKeys(login);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.className("TS-loginButton")).click();
    }

    @Then("^user should be able to connect successfully$")
    public void iConnect() {
    }

    @And("^I add new aliquote with \"(.*?)\" \"(.*?)\" \"(.*?)\" \"(.*?)\" \"(.*?)\" and \"(.*?)\"$")
    public void iAddAliquote(String NumLot, String price, String providerName, String QtyVisibleStock, String QtyHiddenStock, String ProductName) {
    }

    @Then("^I logout$")
    public void iLogout() {
    }

}
