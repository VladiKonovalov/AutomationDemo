package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.bouncycastle.oer.its.etsi102941.Url;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Steps {
    Properties props = new Properties();
    WebDriver driver ;
    @io.cucumber.java.en.Given("open the browser")
    public void OpenTheBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Could not load properties file");
        }



}


    @When("the user navigates to the {string} category page")
    public void theUserNavigatesToTheCategoryCategoryPage(String category) {
    }


    @And("navigate to tested website")
    public void navigateToTestedWebsite() throws InterruptedException {
        String baseUrl = props.getProperty("url");
        driver.get(baseUrl);
        Thread.sleep(1000);
    }

    @Given("press on {string} button")
    public void pressOnLogInButton(String buttonName) throws InterruptedException{
   if (buttonName.equalsIgnoreCase("login")) {
       WebElement loginBtn = driver.findElement(By.id("login2"));
       loginBtn.click();
       Thread.sleep(1000);
   }
    }

    @When("enter invalid username and password")
    public void enterInvalidUsernameAndPassword() {

    }
}
