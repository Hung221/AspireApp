package pageObjects;

import org.openqa.selenium.WebDriver;

public class PageManager {
    public static LoginPage loginPage;
    public static HomePage homePage;
    public static CreateProductPage createProductPage;
    public static InventoryPage inventoryPage;
    public static  Manufacturing manufacturing;

    public static LoginPage getLoginPage(WebDriver driver){
        if (loginPage == null){
            loginPage = new LoginPage(driver);
        }
        return loginPage;
    }
    public static HomePage getHomePage(WebDriver driver){
        if (homePage == null){
            homePage = new HomePage(driver);
        }
        return homePage;
    }
    public static CreateProductPage getCreateProductPage(WebDriver driver){
        if (createProductPage == null){
            createProductPage = new CreateProductPage(driver);
        }
        return createProductPage;
    }
    public static InventoryPage  getInventoryPage(WebDriver driver){
        if (inventoryPage == null){
            inventoryPage = new InventoryPage(driver);
        }
        return inventoryPage;
    }
    public static Manufacturing getManufacturing(WebDriver driver){
        if (manufacturing == null){
            manufacturing = new Manufacturing(driver);
        }
        return manufacturing;
    }
}
