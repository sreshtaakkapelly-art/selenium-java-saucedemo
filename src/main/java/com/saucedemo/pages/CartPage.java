package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.saucedemo.config.ConfigReader;

import java.time.Duration;
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

    @FindBy(css = "[data-test='checkout']")
    private WebElement checkoutButton;

    @FindBy(css = "[data-test='continue-shopping']")
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
        return pageTitle.isDisplayed() && pageTitle.getText().equals("Your Cart");
    }

    public List<String> getCartItemNames() {
        return cartItemNames.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public int getCartItemCount() {
        return cartItems.size();
    }

    public CheckoutPage clickCheckout() {
        checkoutButton.click();
        return new CheckoutPage(driver);
    }

    public ProductsPage continueShopping() {
        continueShoppingButton.click();
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
}
