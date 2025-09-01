package testBase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class BaseClass {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public Logger logger= LogManager.getLogger(this.getClass());;
    public Properties p;

    // Getter for WebDriver
    public static WebDriver getDriver() {
        return driver.get();
    }

    @BeforeClass
    @Parameters({"browser"})
    public void setUp(String br) throws IOException, InterruptedException {
        FileReader config=new FileReader(".//src//test//resources//config.properties");
        p=new Properties();
        p.load(config);


        WebDriver wd = null;
        switch (br.toLowerCase()){
            case "chrome" :
                wd=new ChromeDriver();
                break;
            case "firefox" :
                wd=new FirefoxDriver();
                break;
            case "edge" :
                System.setProperty("webdriver.edge.driver", "C:\\msedgedriver.exe");
                wd=new EdgeDriver();
                break;
            default:
                System.out.println("Invalid browser...");
                return;
        }
        driver.set(wd);
        getDriver().manage().deleteAllCookies();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        getDriver().get(p.getProperty("appURL"));
        getDriver().manage().window().maximize();

        // Enter email
        getDriver().findElement(By.xpath("//input[@type='email']")).sendKeys(p.getProperty("email"));
        getDriver().findElement(By.xpath("//input[@type='submit']")).click();

        // Enter password
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='password']")));
        getDriver().findElement(By.xpath("//input[@type='password']")).sendKeys(p.getProperty("password"));

        // Re-locate submit button after password field loads (avoid stale element)
        WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='submit']")));
        submitBtn.click();

        // Click "Yes" (Stay signed in button)
        WebElement clickToYes = wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
        clickToYes.click();


    }

    @AfterClass
    public void tearDown(){
        if (getDriver() != null) {
//            getDriver().quit();
            driver.remove();
        }
    }

    // Capture screenshot (Thread-safe)
    public String captureScreen(String tname) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        String targetPath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
        File target = new File(targetPath);

        FileUtils.copyFile(source, target);
        return targetPath;
    }
}
