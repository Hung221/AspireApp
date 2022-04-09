package pageObjects;

import commons.AbstractPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import pageUIs.CommonUI;
import pageUIs.LoginPageUI;
import utils.ASPIREConstants;

public class LoginPage extends AbstractPage {
    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Login Aspire page ")
    public void loginAspireOdooPage() {
        waitToElementVisible(driver, LoginPageUI.EMAIL_TEXTBOX);
        sendKeyElement(driver, LoginPageUI.EMAIL_TEXTBOX, ASPIREConstants.USERNAME);
        sendKeyElement(driver, LoginPageUI.PASSWORD_TEXTBOX, ASPIREConstants.PASSWORD);
        clickToElement(driver, CommonUI.COMMON_BUTTON, "Log in");
    }

}
