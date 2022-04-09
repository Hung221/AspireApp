package pageUIs;

public class ManufacturingPageUI {
    public static final String PRODUCTNAME_DROPDOWN = "//label[text()='Product']/parent::td/following-sibling::td//input";
    public static final String PRODUCTNAME_OPTION = "//li[@class='ui-menu-item']/a";
    public static final String CURRENT_STATE = "//button[@title='Current state' and contains(text(), '%s')]";
    public static final String MANUFACTORING_REFERENCE_NAME = "//span[@placeholder='Manufacturing Reference']";
    public static final String REMOVE_MANUFACTURING_ORDER = "//span[text()='Manufacturing Orders']/parent::div/following-sibling::i";
    public static final String SEARCH_TEXTBOX = "//input[@title='Search for records']";
    public static final String MARK_AS_DONE_BUTTON = "//span[text()='Mark as Done']/parent::button[@class='btn btn-primary']";
}
