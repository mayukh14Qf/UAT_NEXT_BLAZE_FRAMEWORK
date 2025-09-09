package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class AddTestcasePage extends BasePage{
    public AddTestcasePage(WebDriver driver){
        super(driver);
    }

    //locators

    @FindBy(xpath = "(//input[@type='text'])[1]")
    WebElement textName;

    @FindBy(xpath = "(//input[@type='text'])[2]")
    WebElement textDescription;

    @FindBy(xpath = "(//select)[3]")
    WebElement dropDownPriority;

    @FindBy(xpath = "(//select)[4]")
    WebElement dropDownType;

    @FindBy(xpath = "(//select)[5]")
    WebElement dropDownQAUser;

    @FindBy(xpath = "(//input[@type='text'])[3]")
    WebElement textPrecondition;

    @FindBy(xpath = "//button[normalize-space()='SAVE']")
    WebElement buttonSave;

    public WebElement linkTestCaseIdFromId(String id){
        return driver.findElement(By.xpath("//div[@class='testlistcell']/a[text()='"+id+"']"));
    }

    public WebElement linkTestCaseIdFromName(String name){
        return driver.findElement(By.xpath("//p[text()='"+name+"']/ancestor::div[@class='testlistrow']//a"));
    }

    //Actions

    public void setTestCaseName(String testCaseName){
        textName.sendKeys(testCaseName);
    }

    public void setDescription(String description){
        textDescription.sendKeys(description);
    }

    public void selectPriority(String priority){
        Select s= new Select(dropDownPriority);
        s.selectByVisibleText(priority);
    }

    public void selectType(String type){
        Select s= new Select(dropDownType);
        s.selectByVisibleText(type);
    }

    public void selectQaUser(String qaUser){
        Select s= new Select(dropDownQAUser);
        s.selectByVisibleText(qaUser);
    }
    public void setPrecondition(String precondition){
        textPrecondition.sendKeys(precondition);
    }
    public void clickSave(){
        buttonSave.click();
    }

    public String getTestcaseId(String name){
        return linkTestCaseIdFromName(name).getText();
    }

}
