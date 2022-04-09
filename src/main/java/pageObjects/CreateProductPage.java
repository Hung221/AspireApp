package pageObjects;

import commons.AbstractPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import pageUIs.CommonUI;
import pageUIs.CreateProductPageUI;

public class CreateProductPage extends AbstractPage {
    WebDriver driver;

    public CreateProductPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Create new product with product name {0}")
    public void createNewProduct(String productName) {
        waitToElementClickAble(driver, CommonUI.COMMON_BUTTON, "Create");
        clickToElement(driver, CommonUI.COMMON_BUTTON, "Create");
        waitToElementVisible(driver, CreateProductPageUI.PRODUCTNAME_TEXTBOX);
        sendKeyElement(driver, CreateProductPageUI.PRODUCTNAME_TEXTBOX, productName);
    }

    @Step("Update the quantity for the product {0} with value {1}")
    public void updateQuantityProduct(String productName, int value) {
        String quantityValue = String.valueOf(value);
        clickToElement(driver, CommonUI.COMMON_CHILD_BUTTON, "Update Quantity");
        waitToElementClickAble(driver, CommonUI.COMMON_BUTTON, "Create");
        clickToElement(driver, CommonUI.COMMON_BUTTON, "Create");
        sendKeyElement(driver, CreateProductPageUI.COUNTED_QUANTITY_TEXTBOX, quantityValue, productName);
        clickToElement(driver, CommonUI.COMMON_BUTTON, "Save");
    }

    @Step("Click on Home Page Icon")
    public void clickOnHomePageIcon() {
        clickToElement(driver, CommonUI.HOME_PAGE_ICON);
    }
}
