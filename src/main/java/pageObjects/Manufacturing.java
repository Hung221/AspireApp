package pageObjects;

import commons.AbstractPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageUIs.CommonUI;
import pageUIs.ManufacturingPageUI;

import java.util.List;

public class Manufacturing extends AbstractPage {
    WebDriver driver;

    public Manufacturing(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Create new manufacturing for the product {0}")
    public void createNewManufacturing(String productName) {
        clickToElement(driver, CommonUI.COMMON_BUTTON, "Create");
        searchAndSelectItemInDropdown(driver, ManufacturingPageUI.PRODUCTNAME_DROPDOWN, ManufacturingPageUI.PRODUCTNAME_OPTION, productName);
        clickToElement(driver, CommonUI.COMMON_CHILD_BUTTON, "Confirm");
        waitToElementClickAble(driver, ManufacturingPageUI.MARK_AS_DONE_BUTTON);
        clickToElement(driver, ManufacturingPageUI.MARK_AS_DONE_BUTTON);
        waitToElementClickAble(driver, CommonUI.COMMON_CHILD_BUTTON, "Ok");
        clickToElement(driver, CommonUI.COMMON_CHILD_BUTTON, "Ok");
        waitToElementClickAble(driver, CommonUI.COMMON_CHILD_BUTTON, "Apply");
        clickToElement(driver, CommonUI.COMMON_CHILD_BUTTON, "Apply");
    }

    public String getManufacturingName() {
        return getElementText(driver, ManufacturingPageUI.MANUFACTORING_REFERENCE_NAME);
    }

    public boolean inState(String expectedState) {
        waitToElementVisible(driver, ManufacturingPageUI.CURRENT_STATE, expectedState);
        return isElementDisplayed(driver, ManufacturingPageUI.CURRENT_STATE, expectedState);
    }

    @Step("Search for manufacturing {0}")
    public void searchForManufacturing(String manufacturingName) {
        clickToElement(driver, CommonUI.COMMON_LINK, "Manufacturing");
        if (isElementDisplayed(driver, ManufacturingPageUI.REMOVE_MANUFACTURING_ORDER)) {
            clickToElement(driver, ManufacturingPageUI.REMOVE_MANUFACTURING_ORDER);
        }
        waitToElementVisible(driver, ManufacturingPageUI.SEARCH_TEXTBOX);
        sendKeyElement(driver, ManufacturingPageUI.SEARCH_TEXTBOX, manufacturingName);
        sendKeyBoardToElement(driver, ManufacturingPageUI.SEARCH_TEXTBOX, Keys.ENTER);
    }

    @Step("Verify Expected Manufacturing is displayed {0}")
    public boolean expectedManufacturingDisplayed(String manufacturingName, String productName, String quantity) {
        boolean expectedInfomation = true;
        waitToElementClickAble(driver, CommonUI.COMMON_TABLE_TD, manufacturingName);
        clickToElement(driver, CommonUI.COMMON_TABLE_TD, manufacturingName);
        if(!isElementDisplayed (driver,CommonUI.COMMON_SPAN,manufacturingName)
                || !isElementDisplayed(driver, CommonUI.COMMON_SPAN, productName)
                || !isElementDisplayed(driver, CommonUI.COMMON_SPAN, quantity)){
            expectedInfomation = false;
        }
        return expectedInfomation;
    }
}