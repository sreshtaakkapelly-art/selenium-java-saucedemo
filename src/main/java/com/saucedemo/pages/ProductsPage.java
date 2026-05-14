package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.saucedemo.config.ConfigReader;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ProductsPage — Page Object for the inventory page after login.
 * URL: /inventory.html
 */
public class ProductsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ── Locators ──────────────────────────────────────────────────────────────
    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    @FindBy(css = ".inventory_item_name")
    private List<WebElement> productNames;

    @FindBy(css = ".inventory_item_price")
    private List<WebElement> productPrices;

    @FindBy(css = ".btn_add_to_cart_container")
    private List<WebElement> addToCartButtons;

    @FindBy(css = ".shopping_cart_link")
    private WebElement cartIcon;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    // ── Constructor ───────────────────────────────────────────────────────────
    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
        PageFactory.initElements(driver, this);
    }

    // ── Actions ───────────────────────────────────────────────────────────────

    public boolean isProductsPageDisplayed() {
        return pageTitle.isDisplayed() && pageTitle.getText().equals("Products");
    }

    public String getPageTitle() {
        return pageTitle.getText();
    }

    public List<String> getAllProductNames() {
        return productNames.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public int getProductCount() {
        return productNames.size();
    }

    public void sortBy(String option) {
        // Options: "az", "za", "lohi", "hilo"
        Select select = new Select(sortDropdown);
        select.selectByValue(option);
    }

    public List<String> getProductPrices() {
        return productPrices.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public void addProductToCartByName(String productName) {
        List<WebElement> names = driver.findElements(By.className("inventory_item_name"));
        List<WebElement> buttons = driver.findElements(By.className("btn_inventory"));
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).getText().equals(productName)) {
                buttons.get(i).click();
                return;
            }
        }
        throw new RuntimeException("Product not found: " + productName);
    }

    public void addFirstProductToCart() {
        driver.findElement(By.cssSelector(".btn_inventory")).click();
    }

    public int getCartCount() {
        try {
            return Integer.parseInt(cartBadge.getText());
        } catch (Exception e) {
            return 0;
        }
    }

    public CartPage goToCart() {
        cartIcon.click();
        return new CartPage(driver);
    }

    public void logout() {
        menuButton.click();
        logoutLink.click();
    }

    public void clickProductByName(String name) {
        driver.findElement(By.linkText(name)).click();
    }
}
