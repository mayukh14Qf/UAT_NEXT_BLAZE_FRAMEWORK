package testBase;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
    public Logger logger = LogManager.getLogger(this.getClass());
    public Properties p;

    // Getter for WebDriver
    public static WebDriver getDriver() {
        return driver.get();
    }

    @BeforeClass
    @Parameters({"browser"})
    public void setUp(String br) throws IOException, InterruptedException {
        FileReader config = new FileReader(".//src//test//resources//config.properties");
        p = new Properties();
        p.load(config);


        WebDriver wd = null;
        switch (br.toLowerCase()) {
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--incognito");
                wd = new ChromeDriver(options);
                break;
            case "firefox":
                wd = new FirefoxDriver();
                break;
            case "edge":
                System.setProperty("webdriver.edge.driver", "C:\\msedgedriver.exe");
                wd = new EdgeDriver();
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

    }

    @AfterClass
    public void tearDown() throws InterruptedException {
        if (getDriver() != null) {
            logout();
           getDriver().quit();
            driver.remove();
        }
    }

    public void logout() throws InterruptedException {
        Thread.sleep(2000);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();",getDriver().findElement(By.xpath("//img[@id='chevron-logout']")));
        WebElement logOut=getDriver().findElement(By.xpath("//a[normalize-space()='Logout']"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();",logOut);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();",getDriver().findElement(By.xpath("//div[@data-bind='text: ((session.isSignedIn || session.isSamsungSso) && session.unsafe_fullName) || session.unsafe_displayName']")));

    }
    //log in to site
    public void login() throws InterruptedException{

        getDriver().findElement(By.xpath("//input[@type='email']")).sendKeys(p.getProperty("email"));
        getDriver().findElement(By.xpath("//input[@type='submit']")).click();


        Thread.sleep(3000);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='password']")));
        getDriver().findElement(By.xpath("//input[@type='password']")).sendKeys(p.getProperty("password"));


        WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='submit']")));
        submitBtn.click();


        WebElement clickToYes = wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
        clickToYes.click();
        Thread.sleep(6000);
        getDriver().navigate().refresh();
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

    // Wait for page to fully load (useful for Blazor apps after navigation)
    public void waitForPageLoad() {
        new WebDriverWait(getDriver(), Duration.ofSeconds(20)).until(
                webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete")
        );
    }
}