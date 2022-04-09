package pageObjects;

import commons.AbstractPage;
import commons.AbstractTest;
import org.openqa.selenium.WebDriver;
import pageUIs.HomePageUI;

public class HomePage extends AbstractPage {
    WebDriver driver;
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOnFeature(String feature) {
        waitToElementVisible(driver, HomePageUI.COMMON_FEATURE_LINK, feature);
        clickToElement(driver, HomePageUI.COMMON_FEATURE_LINK, feature);
    }
}
