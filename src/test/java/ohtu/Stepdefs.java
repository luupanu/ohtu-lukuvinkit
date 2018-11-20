package ohtu;

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
    public void user_is_at_the_main_page() throws Throwable {
        driver.get("http://localhost:" + 8080 + "/");
        Thread.sleep(1000);        
    }
    
    @When("^form is filled with title \"([^\"]*)\" description \"([^\"]*)\" url \"([^\"]*)\" and is submitted$")
    public void form_is_filled_and_sumbmitted(String title, String description, String url) throws Throwable {
        Thread.sleep(1000);  
        submitNewTip(title, description, url);
        Thread.sleep(1000);  
    } 

    @When("^a link is clicked$")
    public void a_link_is_clicked() throws Throwable {
        Thread.sleep(1000);  
        clickLinkWithText("linkki");
        Thread.sleep(1000);  
    } 
    
   
    @Then("^\"([^\"]*)\" is shown$")
    public void is_shown(String arg1) throws Throwable {
        assertTrue(driver.findElement(By.tagName("body"))
                .getText().contains(arg1));
    }
    
    private void submitNewTip(String title, String description, String url) {
        assertTrue(driver.getPageSource().contains("Lisää uusi lukuvinkki"));
        WebElement element = driver.findElement(By.name("title"));
        element.sendKeys(title);
        element = driver.findElement(By.name("description"));
        element.sendKeys(description);
        element = driver.findElement(By.name("url"));
        element.sendKeys(url);
        element = driver.findElement(By.name("submit_form"));
        element.submit();  
    }    

    private void clickLinkWithText(String text) {
        int trials = 0;
        while (trials++ < 5) {
            try {
                WebElement element = driver.findElement(By.linkText(text));
                element.click();
                break;           
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
        }
    }
    
}
