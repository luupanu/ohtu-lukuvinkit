package ohtu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Stepdefs {

    WebDriver driver;

    public Stepdefs() {
        File file;
        if (System.getProperty("os.name").matches("Mac OS X")) {
            file = new File("lib/chromedriver-mac");
        } else if (System.getProperty("os.name").matches("Linux")) {
            file = new File("lib/chromedriver");
        } else { // assume Windows 32-bit
            file = new File("lib/chromedriver.exe");
        }
        String absolutePath = file.getAbsolutePath();
        System.setProperty("webdriver.chrome.driver", absolutePath);

        this.driver = new ChromeDriver();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Given("^user is at the main page$")
    public void userIsAtTheMainPage() throws Throwable {
        driver.get("http://localhost:" + 8080 + "/");
    }

    @When("^form is filled with title \"([^\"]*)\" description \"([^\"]*)\" url \"([^\"]*)\" and is submitted$")
    public void formIsFilledAndSubmitted(String title, String description, String url) throws Throwable {
        submitNewTip(title, description, url);
    }

    @When("^form is filled with title \"([^\"]*)\" description \"([^\"]*)\" url \"([^\"]*)\" tags \"([^\"]*)\" and is submitted$")
    public void formIsFilledAndSubmittedWithTags(String title, String description, String url, String tags)
            throws Throwable {
        submitNewTipWithTags(title, description, url, tags);
    }

    @When("^form is filled with tags \"([^\"]*)\" and is submitted$")
    public void formIsFilledAndSubmittedWithTags(String tags)
            throws Throwable {
        submitNewTipWithTags("", "", "", tags);
    }

    @When("^form is not filled and is submitted$")
    public void formIsNotFilledAndSubmitted() throws Throwable {
        submitNewTip("", "", "");
    }

    @When("^^\"([^\"]*)\" is clicked$")
    public void anElementIsClicked(String arg1) throws Throwable {
        clickElementByName(arg1);
    }

    @Then("^\"([^\"]*)\" is shown$")
    public void isShown(String arg1) throws Throwable {
        assertTrue(driver.findElement(By.tagName("body")).getText().contains(arg1));
    }

    @Then("^\"([^\"]*)\" is not shown$")
    public void isNotShown(String arg1) throws Throwable {
        assertFalse(driver.findElement(By.tagName("body")).getText().contains(arg1));
    }

    @Then("^error \"([^\"]*)\" is shown$")
    public void errorIsShown(String arg1) throws Throwable {
        assertTrue(driver.findElement(By.tagName("body")).getText().contains(arg1));
    }

    private void submitNewTip(String title, String description, String url) {
        assertTrue(driver.getPageSource().contains("Add a new reading tip"));
        WebElement element = driver.findElement(By.name("title"));
        element.sendKeys(title);
        element = driver.findElement(By.name("description"));
        element.sendKeys(description);
        element = driver.findElement(By.name("url"));
        element.sendKeys(url);
        element = driver.findElement(By.name("create-readingtip"));
        element.submit();
    }

    private void submitNewTipWithTags(String title, String description, String url, String tags) {
        assertTrue(driver.getPageSource().contains("Add a new reading tip"));
        WebElement element = driver.findElement(By.name("title"));
        element.sendKeys(title);
        element = driver.findElement(By.name("description"));
        element.sendKeys(description);
        element = driver.findElement(By.name("url"));
        element.sendKeys(url);
        element = driver.findElement(By.name("tagDescription"));
        element.sendKeys(tags);
        element = driver.findElement(By.name("create-readingtip"));
        element.submit();
    }

    private void clickElementByName(String text) {
        try {
            WebElement element = driver.findElement(By.name(text));
            element.click();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

    }

}
