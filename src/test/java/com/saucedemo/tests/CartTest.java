package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * CartTest — covers shopping cart operations
 *
 * Test cases:
 *   TC_01 Added product appears in cart
 *   TC_02 Removing item from cart empties it
 *   TC_03 Continue shopping returns to products page
 *   TC_04 Cart persists correct item name
 */
public class CartTest extends BaseTest {

    private ProductsPage productsPage;
    private static final String PRODUCT_NAME = "Sauce Labs Backpack";

    @BeforeMethod
    public void loginAndAddProduct() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateTo();
        productsPage = loginPage.loginAsStandardUser();
        productsPage.addProductToCartByName(PRODUCT_NAME);
    }

    @Test(description = "TC_01: Product added should appear in cart")
    public void testProductAppearsInCart() {
        CartPage cartPage = productsPage.goToCart();
        Assert.assertTrue(cartPage.isCartPageDisplayed(),
                "Should be on cart page");
        Assert.assertTrue(cartPage.getCartItemNames().contains(PRODUCT_NAME),
                "Added product should appear in cart");
    }

    @Test(description = "TC_02: Removing only item should empty the cart")
    public void testRemoveItemFromCart() {
        CartPage cartPage = productsPage.goToCart();
        cartPage.removeFirstItem();
        Assert.assertTrue(cartPage.isCartEmpty(),
                "Cart should be empty after removing the only item");
    }

    @Test(description = "TC_03: Continue shopping should return to products page")
    public void testContinueShopping() {
        CartPage cartPage = productsPage.goToCart();
        ProductsPage backToProducts = cartPage.continueShopping();
        Assert.assertTrue(backToProducts.isProductsPageDisplayed(),
                "Should return to products page after Continue Shopping");
    }

    @Test(description = "TC_04: Cart count should match number of items added")
    public void testCartCountAfterAddingItem() {
        CartPage cartPage = productsPage.goToCart();
        Assert.assertEquals(cartPage.getCartItemCount(), 1,
                "Cart should contain exactly 1 item");
    }
}
