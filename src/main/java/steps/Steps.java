package steps;

import io.cucumber.java.After;
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
    Properties props;
    static WebDriver driver;

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

    @And("navigate to tested website")
    public void navigateToTestedWebsite() throws InterruptedException {
        String baseUrl = props.getProperty("url");
        driver.get(baseUrl);
        Thread.sleep(1000);
    }

    @Given("press on {string} button")
    public void pressOnLogInButton(String buttonName) throws InterruptedException {
        if (buttonName.equalsIgnoreCase("login"))
            driver.findElement(By.id("login2")).click();
     else if (buttonName.equalsIgnoreCase("Sign up"))
            driver.findElement(By.id("signin2")).click();

        Thread.sleep(1000);

    }

    @When("login with {string} and {string}")
    public void loginWithUsernameAndPassword(String username, String password) throws InterruptedException {
        if (username.equalsIgnoreCase("random"))
            username = "user_" + RandomStringUtils.random(5, true, true);
        if (password.equalsIgnoreCase("random"))
            password = "pass_" + RandomStringUtils.random(5, true, true);
        driver.findElement(By.id("loginusername")).sendKeys(username);
        driver.findElement(By.id("loginpassword")).sendKeys(password);
        driver.findElement(By.xpath("//button[text()='Log in']")).click();
        Thread.sleep(2000);
    }

    @Then("got an alert with right {string}")
    public void gotAnAlertMessage(String message) {

        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        System.out.println("Alert: " + alertText);
        Asserts.check(alertText.toLowerCase().equals(message.toLowerCase()),"the alert showing: '"+ alertText+"' but we expected: '"+message+"'");
        alert.accept();
        driver.quit();

    }

    @Then("got a welcome text for {string}")
    public void gotAWelcomeTextForUsername(String username) {
        String welcomeMessege = driver.findElement(By.id("nameofuser")).getText();
        Asserts.check(welcomeMessege.toLowerCase().contains("welcome " + username),"the welcome messege not what we ecpected");
    }

    @When("sign in a new user")
    public void signInANewUser() throws InterruptedException {
        String randomStr = RandomStringUtils.random(7, true, true);
        String username = "user" + randomStr;
        String password = "pass" + randomStr;
        driver.findElement(By.id("sign-username")).sendKeys(username);
        driver.findElement(By.id("sign-password")).sendKeys(password);
        driver.findElement(By.xpath("//button[text()='Sign up']")).click();
        Thread.sleep(1000);

    }
    @After
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }


}
