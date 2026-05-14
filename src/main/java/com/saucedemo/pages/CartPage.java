package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.saucedemo.config.ConfigReader;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CartPage — Page Object for the shopping cart page.
 * URL: /cart.html
 */
public class CartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ── Locators ──────────────────────────────────────────────────────────────
    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(css = ".cart_item_label .inventory_item_name")
    private List<WebElement> cartItemNames;

    @FindBy(css = ".inventory_item_price")
    private List<WebElement> cartItemPrices;

    @FindBy(css = ".cart_item")
    private List<WebElement> cartItems;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(css = ".cart_button")
    private List<WebElement> removeButtons;

    // ── Constructor ───────────────────────────────────────────────────────────
    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
        PageFactory.initElements(driver, this);
    }

    // ── Actions ───────────────────────────────────────────────────────────────

    public boolean isCartPageDisplayed() {
        try {
            return driver.getCurrentUrl().contains("cart");
        } catch (Exception e) {
            return false;
        }
    }

    public int getCartItemCount() {
        try {
            List<WebElement> items = driver.findElements(By.className("cart_item"));
            return items.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public CheckoutPage clickCheckout() {
        driver.findElement(By.id("checkout")).click();
        return new CheckoutPage(driver);
    }

    public ProductsPage continueShopping() {
        driver.findElement(By.id("continue-shopping")).click();
        return new ProductsPage(driver);
    }

    public void removeFirstItem() {
        if (!removeButtons.isEmpty()) {
            removeButtons.get(0).click();
        }
    }

    public boolean isCartEmpty() {
        return cartItems.isEmpty();
    }
    public List<String> getCartItemNames() {
        return cartItemNames.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}
