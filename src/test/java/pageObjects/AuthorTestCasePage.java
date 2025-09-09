package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AuthorTestCasePage extends BasePage{
    public AuthorTestCasePage(WebDriver driver){
        super(driver);
    }
    // locators
    @FindBy(xpath = "(//select[@class='text select-dropdown'])[1]")
    WebElement dropdownEpic;

    @FindBy(xpath = "(//select[@class='text select-dropdown'])[2]")
    WebElement dropdownFeature;

    public WebElement linkRequirement(String reqId) {
        return driver.findElement(By.xpath("//div[text()='"+ reqId +"']"));
    }

    @FindBy(xpath = "//p[@class='supporting-text']")
    WebElement headingRequirement;

    @FindBy(xpath = "//button[@id='createTestCaseButton']/div")
    WebElement buttonAddTestCase;

    @FindBy(xpath = "//span[@id='author']")
    WebElement tabAuthorTestcase;

    //actions

    public void selectEpic(String epicName){
        Select s= new Select(dropdownEpic);
        s.selectByVisibleText(epicName);
    }

    public void selectFeature(String featureName){
        Select s= new Select(dropdownFeature);
        s.selectByVisibleText(featureName);
    }

    public void clickRequirement(String requirementId) throws InterruptedException {
        Thread.sleep(2000);
        linkRequirement(requirementId).click();
        Thread.sleep(2000);
    }

    public String showRequirementHeader(){
        return headingRequirement.getText();
    }

    public void clickAddTestcase(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", buttonAddTestCase);
    }
    public void clickAuthorTestcase(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tabAuthorTestcase);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tabAuthorTestcase);
    }

}
