package steps;

import io.cucumber.java.After;
import io.cucumber.java.an.E;
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
import java.util.List;
import java.util.NoSuchElementException;
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
        Thread.sleep(1500);
    }

    @Given("press on {string} button")
    public void pressOnLogInButton(String buttonName) throws InterruptedException {
        if (buttonName.equalsIgnoreCase("login"))
            driver.findElement(By.id("login2")).click();
        else if (buttonName.equalsIgnoreCase("Sign up"))
            driver.findElement(By.id("signin2")).click();
        else if (buttonName.equalsIgnoreCase("Add to card"))
            driver.findElement(By.cssSelector("a.btn.btn-success")).click();
        else {
            throw new IllegalArgumentException("Unknown button: " + buttonName);

        }
        Thread.sleep(2000);


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
        String alertText = alert.getText().toLowerCase();
        System.out.println("Alert: " + alertText);
        Asserts.check(alertText.equals(message.toLowerCase()), "the alert showing: '" + alertText + "' but we expected: '" + message + "'");
        alert.accept();
        driver.quit();

    }

    @Then("got a welcome text for {string}")
    public void gotAWelcomeTextForUsername(String username) {
        String welcomeMessege = driver.findElement(By.id("nameofuser")).getText();
        Asserts.check(welcomeMessege.toLowerCase().contains("welcome " + username), "the welcome messege not what we ecpected");
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


    //category or product
    @Given("press on {string}")
    public void pressOnOption(String label) throws InterruptedException {
        try {
            WebElement category = driver.findElement(By.linkText(label.toLowerCase()));
            category.click();
        } catch (NoSuchElementException e) {
            WebElement product = driver.findElement(By.xpath("//a[text()='" + label.toLowerCase() + "']"));
            product.click();
        }

    }

    @When("clicks on {string}")
    public void clickOnCategory(String category) throws InterruptedException {
        WebElement categoryLink = driver.findElement(By.linkText(category));
        categoryLink.click();
        Thread.sleep(2000);
    }

    @After
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    @And("press on product number {int}")
    public void theUserPressOnProductNumberProduct(int productIndex) throws InterruptedException {
        List<WebElement> productLinks = driver.findElements(By.cssSelector(".hrefch"));
        if (productIndex >= 1 && productIndex -1 < productLinks.size()) {
            productLinks.get(productIndex - 1).click();
        } else {
            throw new IllegalArgumentException("Product index " + productIndex + " is not exist.");
        }
        Thread.sleep(2000);
    }


}
