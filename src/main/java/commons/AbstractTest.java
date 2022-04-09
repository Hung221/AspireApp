package commons;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.Reporter;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AbstractTest {
    //String projectLocation = System.getProperty("user.dir");
    private WebDriver driver;
    protected final Log log;

    public AbstractTest() {
        log = LogFactory.getLog(getClass());
    }

    private enum BrowserName {
        CHROME, FIREFOX, EDGE_CHROMIUM;
    }

    protected WebDriver getBrowserDriver(String browserName, String urlApp) {
        BrowserName browser = BrowserName.valueOf(browserName.toUpperCase());
        if (browser == BrowserName.CHROME) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser == BrowserName.FIREFOX) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser == BrowserName.EDGE_CHROMIUM) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else {
            throw new RuntimeException("Please enter correct browser name!");
        }
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(urlApp);
        return driver;
    }

    protected WebDriver getBrowserDriver(String browserName) {
        BrowserName browser = BrowserName.valueOf(browserName.toUpperCase());
        if (browser == BrowserName.CHROME) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser == BrowserName.FIREFOX) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser == BrowserName.EDGE_CHROMIUM) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else {
            throw new RuntimeException("Please enter correct browser name!");
        }
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }
    public WebDriver getDriver() {
        return driver;
    }

    @Step("Create random number")
    public String randomNumberAsString(int num) {
        String randomNumAsString;
        if (num == 3) {
            randomNumAsString = String.valueOf(getRandomNumberInRange(100, 999));
        } else {
            randomNumAsString = String.valueOf(getRandomNumberInRange(100000000, 999999999));
        }
        return randomNumAsString;
    }

    public static int getRandomNumberInRange(int minValue, int maxValue) {
        return new Random().nextInt(maxValue - minValue + 1) + minValue;
    }
}
