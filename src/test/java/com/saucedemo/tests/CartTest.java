package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class CartTest extends BaseTest {

    private ProductsPage productsPage;
    private static final String PRODUCT_NAME = "Sauce Labs Backpack";

    @BeforeMethod
    public void loginAndAddProduct() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateTo();
        productsPage = loginPage.loginAsStandardUser();

        // Wait for products page to fully load
        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.className("inventory_item")));

        // Add product using index-based method
        productsPage.addProductToCartByName(PRODUCT_NAME);

        // Wait for cart badge to appear confirming item was added
        new WebDriverWait(getDriver(), Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.className("shopping_cart_badge")));
    }

    @Test(description = "TC_01: Product added should appear in cart")
    public void testProductAppearsInCart() {
        CartPage cartPage = productsPage.goToCart();
        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("cart"));
        Assert.assertTrue(cartPage.isCartPageDisplayed(),
                "Should be on cart page");
        Assert.assertTrue(cartPage.getCartItemNames().contains(PRODUCT_NAME),
                "Added product should appear in cart");
    }

    @Test(description = "TC_02: Removing only item should empty the cart")
    public void testRemoveItemFromCart() {
        CartPage cartPage = productsPage.goToCart();
        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("cart"));
        cartPage.removeFirstItem();
        Assert.assertTrue(cartPage.isCartEmpty(),
                "Cart should be empty after removing the only item");
    }

    @Test(description = "TC_03: Continue shopping should return to products page")
    public void testContinueShopping() {
        CartPage cartPage = productsPage.goToCart();
        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("cart"));
        ProductsPage backToProducts = cartPage.continueShopping();
        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("inventory"));
        Assert.assertTrue(backToProducts.isProductsPageDisplayed(),
                "Should return to products page");
    }

    @Test(description = "TC_04: Cart count should match number of items added")
    public void testCartCountAfterAddingItem() {
        CartPage cartPage = productsPage.goToCart();
        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("cart"));
        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.className("cart_item")));
        Assert.assertEquals(cartPage.getCartItemCount(), 1,
                "Cart should contain exactly 1 item");
    }
}