package TCs;

import commons.AbstractTest;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.*;

public class TC_Verify_Manufacturing_Created_Success extends AbstractTest {
    public WebDriver driver;
    LoginPage loginPage;
    HomePage homePage;
    InventoryPage inventoryPage;
    CreateProductPage createProductPage;
    Manufacturing manufacturing;
    String productName = "QA challenge" + randomNumberAsString(3);
    String manufacturingName, quantity;
    int productQuantity = 12;
    String manufacturingQuantity = "1.00";

    @Parameters({"browser", "url"})
    @BeforeClass
    public void beforeClass(@Optional("chrome") String browser, String url) {
        log.info("Open url " + url);
        driver = getBrowserDriver(browser, url);
        loginPage = PageManager.getLoginPage(driver);
        loginPage.loginAspireOdooPage();
        homePage = PageManager.getHomePage(driver);
    }

    @Test
    public void verifyCreateNewManufacturingSuccess() {
        homePage.clickOnFeature("Inventory");
        inventoryPage = PageManager.getInventoryPage(driver);
        log.info("Go to manage product page");
        inventoryPage.selectProductItemFromMenu();
        createProductPage = PageManager.getCreateProductPage(driver);

        log.info("Create new product with name " + productName);
        createProductPage.createNewProduct(productName);

        log.info("Update quantity for the product " + productQuantity);
        createProductPage.updateQuantityProduct(productName, productQuantity);

        log.info("Go to Home Page");
        createProductPage.clickOnHomePageIcon();
        homePage = PageManager.getHomePage(driver);

        log.info("Go to manage Manufacturing");
        homePage.clickOnFeature("Manufacturing");
        manufacturing = PageManager.getManufacturing(driver);

        log.info("Create new manufacturing for product " + productName);
        manufacturing.createNewManufacturing(productName);
        manufacturingName = manufacturing.getManufacturingName();

        log.info(String.format("Verify the new manufacturing %s at expected state", manufacturing));
        Assert.assertTrue(manufacturing.inState("Done"));

        log.info("Search for the manufacturing " + manufacturing);
        manufacturing.searchForManufacturing(manufacturingName);

        log.info("Verify expected manufacturing is displayed");
        Assert.assertTrue(manufacturing.expectedManufacturingDisplayed
                (manufacturingName, productName, manufacturingQuantity));
    }

    @AfterClass
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

}
