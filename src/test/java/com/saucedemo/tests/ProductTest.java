package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ProductTest — covers product listing and sorting scenarios
 *
 * Test cases:
 *   TC_01 All 6 products are visible
 *   TC_02 Sort A→Z works correctly
 *   TC_03 Sort Z→A works correctly
 *   TC_04 Sort price Low→High works
 *   TC_05 Add product to cart updates badge
 */
public class ProductTest extends BaseTest {

    private ProductsPage productsPage;

    @BeforeMethod
    public void loginAndNavigate() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateTo();
        productsPage = loginPage.loginAsStandardUser();
    }

    @Test(description = "TC_01: Products page should display 6 products")
    public void testProductsCount() {
        int count = productsPage.getProductCount();
        Assert.assertEquals(count, 6, "Expected 6 products on the page");
    }

    @Test(description = "TC_02: Products sorted A-Z should be in alphabetical order")
    public void testSortAtoZ() {
        productsPage.sortBy("az");
        List<String> names = productsPage.getAllProductNames();
        List<String> sorted = new ArrayList<>(names);
        Collections.sort(sorted);
        Assert.assertEquals(names, sorted, "Products are not sorted A-Z correctly");
    }

    @Test(description = "TC_03: Products sorted Z-A should be in reverse alphabetical order")
    public void testSortZtoA() {
        productsPage.sortBy("za");
        List<String> names = productsPage.getAllProductNames();
        List<String> sorted = new ArrayList<>(names);
        sorted.sort(Collections.reverseOrder());
        Assert.assertEquals(names, sorted, "Products are not sorted Z-A correctly");
    }

    @Test(description = "TC_04: Products sorted Low-High price should be in ascending order")
    public void testSortPriceLowToHigh() {
        productsPage.sortBy("lohi");
        List<String> priceStrings = productsPage.getProductPrices();

        List<Double> prices = new ArrayList<>();
        for (String p : priceStrings) {
            prices.add(Double.parseDouble(p.replace("$", "")));
        }

        List<Double> sorted = new ArrayList<>(prices);
        Collections.sort(sorted);
        Assert.assertEquals(prices, sorted, "Products are not sorted by price Low-High correctly");
    }

    @Test(description = "TC_05: Adding product to cart should update cart badge to 1")
    public void testAddProductToCart() {
        productsPage.addProductToCartByName("Sauce Labs Backpack");
        int cartCount = productsPage.getCartCount();
        Assert.assertEquals(cartCount, 1, "Cart badge should show 1 after adding a product");
    }

    @Test(description = "TC_06: Adding multiple products should update badge correctly")
    public void testAddMultipleProductsToCart() {
        productsPage.addProductToCartByName("Sauce Labs Backpack");
        productsPage.addProductToCartByName("Sauce Labs Bike Light");
        int cartCount = productsPage.getCartCount();
        Assert.assertEquals(cartCount, 2, "Cart badge should show 2 after adding 2 products");
    }
}
