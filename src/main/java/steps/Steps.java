package steps;

import dev.failsafe.internal.util.Assert;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.hc.core5.util.Asserts;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Steps {
    Properties props ;
    WebDriver driver ;
    Asserts asserts;
    @io.cucumber.java.en.Given("open the browser")
    public void OpenTheBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        props = new Properties();
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
    public void enterInvalidUsernameAndPassword() throws InterruptedException{
        String generatedString = RandomStringUtils.random(5, true, true);
        driver.findElement(By.id("loginusername")).sendKeys("user_"+generatedString);
        driver.findElement(By.id("loginpassword")).sendKeys("pass_"+generatedString);
        driver.findElement(By.xpath("//button[text()='Log in']")).click();
        Thread.sleep(1000);
    }

    @Then("got an error message")
    public void gotAnErrorMessage() {
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        System.out.println("Alert: " + alertText);
        asserts.equals( alertText.toLowerCase().contains("user does not exist") ||
                alertText.toLowerCase().contains("wrong password"));
        alert.accept();
        driver.quit();

    }

    @When("login with {string} and {string}")
    public void loginWithUsernameAndPassword(String username,String password)throws InterruptedException {
        driver.findElement(By.id("loginusername")).sendKeys(username);
        driver.findElement(By.id("loginpassword")).sendKeys(password);
        driver.findElement(By.xpath("//button[text()='Log in']")).click();
        Thread.sleep(1000);
    }

    @Then("got a welcome text for {string}")
    public void gotAWelcomeTextForUsername(String username) {
        String welcomeMessege =driver.findElement(By.id("nameofuser")).getText();
        asserts.equals( welcomeMessege.toLowerCase().contains("welcome "+username) );
    }
}
