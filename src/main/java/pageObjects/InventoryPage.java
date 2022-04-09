package pageObjects;

import commons.AbstractPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import pageUIs.CommonUI;

public class InventoryPage extends AbstractPage {
    WebDriver driver;
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step ("Click on Product menu and go to create product step")
    public void selectProductItemFromMenu() {
        String button = "Products";
        waitToElementClickAble(driver, CommonUI.COMMON_CHILD_BUTTON, button);
        clickToElement(driver, CommonUI.COMMON_CHILD_BUTTON, button);
        waitToElementClickAble(driver, CommonUI.COMMON_LINK, button);
        clickToElement(driver, CommonUI.COMMON_LINK, button);
    }
}
