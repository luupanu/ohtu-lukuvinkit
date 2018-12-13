package lukuvinkit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Stepdefs {

    private static final int PORT_NUMBER = 8080;
    private WebDriver driver;

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
        driver.get("http://localhost:" + PORT_NUMBER + "/");
    }

    @When("^\"([^\"]*)\" is typed in$")
    public void isTypedIn(String searchterm) throws Throwable {
        WebElement element = driver.findElement(By.id("search"));
        element.sendKeys(searchterm);
    }

    @When("^new tip is submitted$")
    public void formIsSubmitted() throws Throwable {
        WebElement element = driver.findElement(By.name("create-readingtip"));
        element.submit();
    }

    @When("^form is filled with title \"([^\"]*)\" description \"([^\"]*)\" url \"([^\"]*)\" tags \"([^\"]*)\"$")
    public void formIsFilledWithNewLinkTipWithTags(String title, String description, String url, String tags)
            throws Throwable {
        fillNewLinkTipWithTags(title, description, url, tags);
    }

    @When("^form is filled with tags \"([^\"]*)\"$")
    public void formIsFilledWithOnlyTags(String tags) throws Throwable {
        fillNewLinkTipWithTags("", "", "", tags);
    }

    @When("^^\"([^\"]*)\" is clicked$")
    public void anElementIsClicked(String element) throws Throwable {
        clickElementByName(element);
    }

    @When("^\"([^\"]*)\" inside \"([^\"]*)\" is clicked$")
    public void insideTipIsClicked(String name, String tipTitle) throws Throwable {
        String id = getTipIdByTitle(tipTitle);
        WebElement tip = driver.findElement(By.id(id));
        WebElement element = tip.findElement(By.name(name));
        element.click();
    }

    @When("^link \"([^\"]*)\" is clicked$")
    public void linkIsClicked(String arg1) throws Throwable {
        driver.findElement(By.linkText(arg1)).click();
    }

    @When("^page contains \"([^\"]*)\" field$")
    public void pageContainsField(String arg1) {
        assertTrue(driver.getPageSource().contains(arg1));
    }

    @When("^type \"([^\"]*)\" is selected in the form$")
    public void typeIsSelectedInTheForm(String type) throws Throwable {
        Select dropdown = new Select(driver.findElement(By.id("typeField")));
        dropdown.selectByVisibleText(type);
    }

    @When("^form is filled with title \"([^\"]*)\" description \"([^\"]*)\" author \"([^\"]*)\" tags \"([^\"]*)\"$")
    public void formIsFilledWithTitleDescriptionAuthorTagsAndIsSubmitted(String title, String description, String author, String tags) throws Throwable {
        fillNewArticleTipWithTags(title, description, author, tags);
    }

    @When("^form is filled with title \"([^\"]*)\" description \"([^\"]*)\" author \"([^\"]*)\" isbn \"([^\"]*)\" tags \"([^\"]*)\"$")
    public void formIsFilledWithTitleDescriptionAuthorIsbnTagsAndIsSubmitted(String title, String description, String author, String isbn, String tags) throws Throwable {
        fillNewBookTipWithTags(title, description, author, isbn, tags);
    }

    @When("^\"([^\"]*)\" is dragged and dropped to \"([^\"]*)\"$")
    public void tipIsDraggedAndDroppedTo(String tip1, String tip2) throws Throwable {
        String from = getTipIdByTitle(tip1);
        String to = getTipIdByTitle(tip2);

        // Note: Couldn't get Selenium's drag and drop working, using an external library from
        // https://github.com/Photonios/JS-DragAndDrop-Simulator
        String dragAndDropSimulator = new String(Files.readAllBytes(Paths.get("lib/dndsim.js")));
        JavascriptExecutor executor = (JavascriptExecutor) driver;

        String injectedString = "\nDndSimulator.simulate(" + from + ", " + to + ");";

        executor.executeScript(dragAndDropSimulator + injectedString);
    }

    @Then("^link \"([^\"]*)\" is shown$")
    public void linkIsShown(String linkText) {
        assertTrue(driver.findElement(By.linkText(linkText)).getText().contains(linkText));
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
        try {
            assertTrue(driver.findElement(By.tagName("body")).getText().contains(arg1));
        } catch (Exception e) {
            Thread.sleep(4000);
            System.out.println(e.getStackTrace());
        }
    }

    @Then("^comment \"([^\"]*)\" is submitted$")
    public void submitNewComment(String comment) throws Throwable {
        WebElement element = driver.findElement(By.name("commentDescription"));
        element.sendKeys(comment);
        element = driver.findElement(By.name("create-comment"));
        element.click();
    }

    @Then("^only one \"([^\"]*)\" is shown$")
    public void onlyOneIsShown(String arg1) throws Throwable {
        String body = driver.findElement(By.tagName("body")).getText();

        int counter = 0;
        int index = 0;

        while (index != -1) {
            index = body.indexOf(arg1, index);

            if (index != -1) {
                counter++;
                index++;
            }
        }
        assertEquals(1, counter);
    }

    @Then("^\"([^\"]*)\" has higher priority than \"([^\"]*)\"$")
    public void hasHigherPriorityThan(String tip1, String tip2) throws Throwable {
        String body = driver.findElement(By.tagName("body")).getText();

        assertTrue(body.indexOf(tip1) < body.indexOf(tip2));
    }

    // Call this method always with the right order of arguments
    //  -> title, description, url, author, isbn, tagDescription
    private void fillNewGenericTip(String... arguments) {
        assertTrue(driver.getPageSource().contains("Add a new reading tip"));

        int i = 0;
        for (String value : arguments) {
            if (value != null && value != "") {
                fillField(i, value);
            }
            i++;
        }
    }

    private void fillField(int fieldNumber, String value) {
        final String[] elements = {
            "title",
            "description",
            "url",
            "author",
            "isbn",
            "tagDescription"
        };

        WebElement element = driver.findElement(By.name(elements[fieldNumber]));
        element.sendKeys(value);
    }

    private void fillNewLinkTip(String title, String description, String url) {
        fillNewGenericTip(title, description, url);
    }

    private void fillNewLinkTipWithTags(String title, String description, String url, String tags) {
        fillNewGenericTip(title, description, url, null, null, tags);
    }

    private void fillNewArticleTipWithTags(String title, String description, String author, String tags) {
        fillNewGenericTip(title, description, null, author, null, tags);
    }

    private void fillNewBookTipWithTags(String title, String description, String author, String isbn, String tags) {
        fillNewGenericTip(title, description, null, author, isbn, tags);
    }

    private void clickElementByName(String text) {
        try {
            WebElement element = driver.findElement(By.name(text));
            element.click();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

    }

    private String getTipIdByTitle(String title) {
        return driver.findElement(
            By.xpath("//*[text() = '" + title + "']//ancestor::article"))
            .getAttribute("id");
    }

}
