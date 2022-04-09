package pageUIs;

public class CreateProductPageUI {
    public static final String TOGGLE_MENU ="//input[@name='name']";
    public static final String UPDATE_QUANTITY_BUTTON = "//button[child::span[text()='Update Quantity']]";
    public static final String PRODUCTNAME_TEXTBOX= "//label[text()='Product Name']/following-sibling::h1//input";
    public static final String COUNTED_QUANTITY_TEXTBOX = "//a[@name='product_id' and text()='%s']/parent::td/following-sibling::td/input[@name='inventory_quantity']";
}
