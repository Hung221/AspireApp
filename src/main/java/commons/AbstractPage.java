package commons;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ASPIREConstants;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AbstractPage {
    private WebDriverWait explicitWait;
    private JavascriptExecutor jsExecutor;
    private WebElement element;
    private List<WebElement> elements;
    private Select select;
    public Actions action;

    //For Web Browser
    public void openPageUrl(WebDriver driver, String url) {
        driver.get(url);
    }

    public String getCurrentUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    public String getCurrentPageTitle(WebDriver driver) {
        return driver.getTitle();
    }

    public String getCurrentPageSource(WebDriver driver) {
        return driver.getPageSource();
    }

    public void backToPage(WebDriver driver) {
        driver.navigate().back();
    }

    public void forwardToPage(WebDriver driver) {
        driver.navigate().forward();
    }

    public void refreshCurrentPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    public void acceptAlert(WebDriver driver) {
        driver.switchTo().alert().accept();
    }

    public void cancelAlert(WebDriver driver) {
        driver.switchTo().alert().dismiss();
    }

    public String getTextAlert(WebDriver driver) {
        return driver.switchTo().alert().getText();
    }

    public void setTextAlert(WebDriver driver, String value) {
        driver.switchTo().alert().sendKeys(value);
    }

    public void waitAlertPresent(WebDriver driver) {
        explicitWait = new WebDriverWait(driver, 30);
        explicitWait.until(ExpectedConditions.alertIsPresent());
    }

    public String getParentPageID(WebDriver driver, String urlParent) {
        driver.get(urlParent);
        return driver.getWindowHandle();
    }

    public void switchToWindowByID(WebDriver driver, String parentID) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String runWindow : allWindows) {
            if (!runWindow.equals(parentID)) {
                driver.switchTo().window(runWindow);
                break;
            }
        }
    }

    public void switchToWindowByTitle(WebDriver driver, String title) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String runWindows : allWindows) {
            driver.switchTo().window(runWindows);
            String currentPageTitle = driver.getTitle();
            if (currentPageTitle.equals(title)) {
                break;
            }
        }
    }

    public void closeAllWindowsWithoutParent(WebDriver driver, String parentID) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String runWindows : allWindows) {
            if (!runWindows.equals(parentID)) {
                driver.switchTo().window(runWindows);
                driver.close();
            }
        }
        driver.switchTo().window(parentID);
    }

    //For WebElement
    public By getByXpath(String locator) {
        return By.xpath(locator);
    }

    public WebElement getElement(WebDriver driver, String locator) {
        return driver.findElement(getByXpath(locator));
    }

    public List<WebElement> getElements(WebDriver driver, String locator) {
        return driver.findElements(getByXpath(locator));
    }

    public void clickToElement(WebDriver driver, String locator) {
        element = getElement(driver, locator);
        element.click();
    }
    public void clickToElement(WebDriver driver, String locator, String... params) {
        locator = getDynamicLocator(locator, params);
        element = getElement(driver, locator);
        element.click();
    }

    public void sendKeyElement(WebDriver driver, String locator, String value) {
        element = getElement(driver, locator);
        element.clear();
        element.sendKeys(value);
    }
    public void sendKeyElement(WebDriver driver, String locator, String value, String... params) {
        locator = getDynamicLocator(locator,params);
        element = getElement(driver, locator);
        element.clear();
        element.sendKeys(value);
    }

    public void selectItemInDropDown(WebDriver driver, String locator, String valueItem) {
        element = getElement(driver, locator);
        // element of Select is locator of select tag and below is option
        select = new Select(element);
        select.selectByVisibleText(valueItem);
    }

    public String getFistSelectedTextInDropDown(WebDriver driver, String locator) {
        element = getElement(driver, locator);
        // element of Select is locator of select tag and below is option
        select = new Select(element);
        return select.getFirstSelectedOption().getText();
    }

    public boolean isDropDownMultible(WebDriver driver, String locator) {
        element = getElement(driver, locator);
        select = new Select(element);
        return select.isMultiple();
    }

    public void selectItemInCustomDropdown(WebDriver driver, String parentLocator, String childItemLocator, String expectedItem) {
        // click on parent loactor to view all child locator
        getElement(driver, parentLocator).click();
        sleepInSecond(1);
        explicitWait = new WebDriverWait(driver, 30);
        // Waiting until all locator of child elment is showed
        elements = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(childItemLocator)));
        for (WebElement item : elements) {
            if (item.getText().trim().equals(expectedItem)) {
                JavascriptExecutor jsExecutor;
                jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
                sleepInSecond(1);
                item.click();
                sleepInSecond(1);
                break;
            }
        }
    }
    protected void searchAndSelectItemInDropdown(WebDriver driver, String parentLocator, String childLocator, String expectedItem) {
        waitToElementVisible(driver, parentLocator);
        sendKeyElement(driver, parentLocator, expectedItem);
        sleepInSecond(1);
        waitToElementVisible(driver, childLocator);
        List<WebElement> options = getElements(driver, childLocator);
        for (WebElement option : options) {
            String actualItem = option.getText();
            if (actualItem.equals(expectedItem)) {
                option.click();
                break;
            }
        }
    }

    public String getElementAttribute(WebDriver driver, String locator, String Attribute) {
        element = getElement(driver, locator);
        return element.getAttribute(Attribute);
    }

    public String getElementText(WebDriver driver, String locator) {
        element = getElement(driver, locator);
        return element.getText();
    }

    public int countElementsSize(WebDriver driver, String locator) {
        return getElements(driver, locator).size();
    }

    public void checkToCheckBox(WebDriver driver, String locator) {
        element = getElement(driver, locator);
        if (!element.isSelected()) {
            element.click();
        }
    }

    public boolean isOptionSelected(WebDriver driver, String locator) {
        return getElement(driver, locator).isSelected();
    }

    public void unCheckToCheckBox(WebDriver driver, String locator) {
        element = getElement(driver, locator);
        if (element.isSelected()) {
            element.click();
        }
    }

    public boolean isElementDisplayed(WebDriver driver, String locator,String... params) {
        locator = getDynamicLocator(locator, params);
        return getElement(driver, locator).isDisplayed();
    }

    public boolean isControlDisplayed(WebDriver driver, String locator) {
        boolean status = true;
        try {
            element = driver.findElement(By.xpath(locator));
            if (element.isDisplayed()) {
                return status;
            }
        } catch (Exception ex) {
            status = false;
        }
        return status;
    }

    public boolean isElementEnabled(WebDriver driver, String locator) {
        return getElement(driver, locator).isEnabled();
    }

    public boolean isElementIsSlected(WebDriver driver, String locator) {
        return getElement(driver, locator).isSelected();
    }

    public void switchToFrame(WebDriver driver, String locator) {
        driver.switchTo().frame(getElement(driver, locator));
    }

    public void switchToDefaultConten(WebDriver driver, String locator) {
        driver.switchTo().defaultContent();
    }

    // Action
    public void doubleClickToElement(WebDriver driver, String locator) {
        element = getElement(driver, locator);
        action = new Actions(driver);
        action.doubleClick(element).perform();
    }

    public void rightClickToElement(WebDriver driver, String locator) {
        element = getElement(driver, locator);
        action = new Actions(driver);
        action.contextClick(element).perform();
    }

    public void hoverMouseToElement(WebDriver driver, String locator) {
        element = getElement(driver, locator);
        getElement(driver, locator);
        action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    public void clickAndHoldElement(WebDriver driver, String locator) {
        element = getElement(driver, locator);
        getElement(driver, locator);
        action = new Actions(driver);
        action.clickAndHold(element).perform();
    }

    public void dragAndDropElement(WebDriver driver, String sourceLocator, String targetLocator) {
        action = new Actions(driver);
        action.dragAndDrop(getElement(driver, sourceLocator), getElement(driver, targetLocator)).perform();
    }

    public void sendKeyBoardToElement(WebDriver driver, String locator, Keys key) {
        action = new Actions(driver);
        action.sendKeys(getElement(driver, locator), key).perform();
    }

    public void sleepInSecond(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //JS execute
    public Object executeForBrowser(WebDriver driver, String javaScript) {
        jsExecutor = (JavascriptExecutor) driver;
        return jsExecutor.executeScript(javaScript);
    }

    public String getInnerText(WebDriver driver) {
        jsExecutor = (JavascriptExecutor) driver;
        return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
    }

    public boolean verifyTextInInnerText(WebDriver driver, String textExpected) {
        jsExecutor = (JavascriptExecutor) driver;
        String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
        return textActual.equals(textExpected);
    }

    public void scrollToBottomPage(WebDriver driver) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    public void navigateToUrlByJS(WebDriver driver, String url) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.location = '" + url + "'");
    }

    public void highlightElement(WebDriver driver, String locator) {
        jsExecutor = (JavascriptExecutor) driver;
        element = getElement(driver, locator);
        String originalStyle = element.getAttribute("style");
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
        sleepInSecond(1);
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
    }

    public void clickToElementByJS(WebDriver driver, String locator) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", getElement(driver, locator));
    }

    public void scrollToElement(WebDriver driver, String locator) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(driver, locator));
    }

    public void sendkeyToElementByJS(WebDriver driver, String locator, String value) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(driver, locator));
    }

    public void removeAttributeInDOM(WebDriver driver, String locator, String attributeRemove) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(driver, locator));
    }

    public boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
        explicitWait = new WebDriverWait(driver, 30);
        jsExecutor = (JavascriptExecutor) driver;

        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };

        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
            }
        };

        return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
    }

    public String getElementValidationMessage(WebDriver driver, String locator) {
        jsExecutor = (JavascriptExecutor) driver;
        return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(driver, locator));
    }

/*    public boolean isImageLoaded(WebDriver driver, String locator) {
        jsExecutor = (JavascriptExecutor) driver;
        boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", getElement(driver, locator));
        if (status) {
            return true;
        } else {
            return false;
        }
    }*/

    //Wait
    public boolean waitToElementVisible(WebDriver driver, String locator) {
        explicitWait = new WebDriverWait(driver, ASPIREConstants.LONG_TIMEOUT);
        By byLocator = By.xpath(locator);
        try {
            explicitWait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean waitToElementVisible(WebDriver driver, String locator, String... params) {
        explicitWait = new WebDriverWait(driver, ASPIREConstants.LONG_TIMEOUT);
        try {
            explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(getDynamicLocator(locator,params))));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void overrideTimeout(WebDriver driver, long timeOut) {
        driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
    }

    public void waitToElementInVisible(WebDriver driver, String locator) {
        By byLocator = By.xpath(locator);
        explicitWait = new WebDriverWait(driver, ASPIREConstants.SHORT_TIMEOUT);
        overrideTimeout(driver, ASPIREConstants.SHORT_TIMEOUT);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(byLocator));
        overrideTimeout(driver, ASPIREConstants.LONG_TIMEOUT);
    }

    public boolean isElementUndisplayed(WebDriver driver, String locator) {
        overrideTimeout(driver, ASPIREConstants.SHORT_TIMEOUT);
        elements = getElements(driver, locator);
        overrideTimeout(driver, ASPIREConstants.LONG_TIMEOUT);
        if (elements.size() == 0) {
            return true;
        } else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
            return true;
        } else {
            return false;
        }
    }

    public void waitToElementClickAble(WebDriver driver, String locator) {
        explicitWait = new WebDriverWait(driver, 30);
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(locator)));
    }
    public void waitToElementClickAble(WebDriver driver, String locator, String... params) {
        explicitWait = new WebDriverWait(driver, 30);
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(getDynamicLocator(locator,params))));
    }

    @Step("Create random phone number")
    public String getRandomPhoneNumber() {
        String phoneNum;
        return phoneNum = "091" + randomNumberAsString(7);
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

    public static String getRandomValidBirthDate(
            String dateFormat, int youngestYearOld, int oldestYearOld) {
        LocalDate randomValidBirthDate = getRandomValidBirthDate(youngestYearOld, oldestYearOld);
        return "YYYY-MM-DD".equalsIgnoreCase(dateFormat)
                ? randomValidBirthDate.toString() :
                getLocalDateAsString(randomValidBirthDate, dateFormat);
    }

    public static String getLocalDateAsString(LocalDate date, String dateFormat) {
        return date.format(DateTimeFormatter.ofPattern(dateFormat));
    }

    public static LocalDate getRandomValidBirthDate(int youngestYearOld, int oldestYearOld) {
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.add(Calendar.YEAR, -oldestYearOld);
        int minDay = (int) (currentCalendar.getTime().toInstant()
                .atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDate()).toEpochDay();
        currentCalendar = Calendar.getInstance();
        currentCalendar.add(Calendar.YEAR, -youngestYearOld);
        int maxDay = (int) (currentCalendar.getTime().toInstant()
                .atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDate()).toEpochDay();
        LocalDate randomBirthDate = LocalDate.ofEpochDay(getRandomNumberInRange(minDay, maxDay));
        return randomBirthDate;
    }

    public static String getDataAsString(String data) {
        return replaceVariablesInString(data);
    }

    public static String replaceVariablesInString(String source) {
        if (source == null || source.isEmpty()) {
            return source;
        }
        Pattern p1 = Pattern.compile(ASPIREConstants.VARIABLE_TEMPLATE);
        Matcher m1 = p1.matcher(source);
        while (m1.find()) {
            String foundVar = m1.group();
            Object varObj = getDataAsObject(foundVar);
            if (varObj != null) {
                source = source.replace(foundVar, varObj.toString());
            }
        }
        return source;
    }

    public static Object getDataAsObject(String data) {
        Object result = null;
        data = data.trim();
        if (data.startsWith(ASPIREConstants.CONFIG_VALUE_PREFIX) && data.endsWith("}")) {
            String dataValue = data.substring(2, data.length() - 1).trim();
            if (System.getProperty(dataValue) != null) {
                return System.getProperty(dataValue);
            }
        } else {
            result = data;
        }
        return result;
    }

    public String getDynamicLocator(String dynamicLocator, String... value) {
        String xpathValue = String.format(dynamicLocator, (Object[]) value);
        return xpathValue;
    }
}