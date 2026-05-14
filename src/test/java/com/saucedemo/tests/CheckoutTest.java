package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * CheckoutTest — covers the full end-to-end checkout journey
 *
 * Test cases:
 *   TC_01 Complete checkout with valid info → confirmation shown
 *   TC_02 Continue without first name → error
 *   TC_03 Continue without last name → error
 *   TC_04 Continue without postal code → error
 *   TC_05 Cancel checkout returns to cart
 */
public class CheckoutTest extends BaseTest {

    private CheckoutPage checkoutPage;
    private CartPage cartPage;

    @BeforeMethod
    public void loginAddProductAndGoToCheckout() {
        // Full setup: Login → Add product → Cart → Checkout
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateTo();
        ProductsPage productsPage = loginPage.loginAsStandardUser();
        productsPage.addProductToCartByName("Sauce Labs Backpack");
        cartPage = productsPage.goToCart();
        checkoutPage = cartPage.clickCheckout();
    }

    @Test(description = "TC_01: Full checkout with valid details should show order confirmation")
    public void testSuccessfulCheckout() {
        checkoutPage.fillCheckoutInfo("John", "Doe", "12345");
        checkoutPage.clickFinish();
        Assert.assertTrue(checkoutPage.isOrderConfirmed(),
                "Order confirmation header should be visible");
    }

    @Test(description = "TC_02: Checkout without first name should show error")
    public void testCheckoutWithoutFirstName() {
        checkoutPage.fillCheckoutInfo("", "Doe", "12345");
        Assert.assertTrue(checkoutPage.isErrorDisplayed(),
                "Error should show when first name is empty");
        Assert.assertTrue(checkoutPage.getErrorMessage().contains("First Name is required"),
                "Error text mismatch");
    }

    @Test(description = "TC_03: Checkout without last name should show error")
    public void testCheckoutWithoutLastName() {
        checkoutPage.fillCheckoutInfo("John", "", "12345");
        Assert.assertTrue(checkoutPage.isErrorDisplayed(),
                "Error should show when last name is empty");
        Assert.assertTrue(checkoutPage.getErrorMessage().contains("Last Name is required"),
                "Error text mismatch");
    }

    @Test(description = "TC_04: Checkout without postal code should show error")
    public void testCheckoutWithoutPostalCode() {
        checkoutPage.fillCheckoutInfo("John", "Doe", "");
        Assert.assertTrue(checkoutPage.isErrorDisplayed(),
                "Error should show when postal code is empty");
        Assert.assertTrue(checkoutPage.getErrorMessage().contains("Postal Code is required"),
                "Error text mismatch");
    }

    @Test(description = "TC_05: Order total should contain dollar sign after filling info")
    public void testOrderTotalVisible() {
        checkoutPage.fillCheckoutInfo("John", "Doe", "12345");
        String total = checkoutPage.getOrderTotal();
        Assert.assertTrue(total.contains("$"),
                "Order total should contain a price with $ sign, got: " + total);
    }
}
