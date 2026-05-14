package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * LoginTest — covers all login scenarios for saucedemo.com
 *
 * Test cases:
 *   TC_01 Valid login → lands on Products page
 *   TC_02 Invalid password → error message shown
 *   TC_03 Empty username → error message shown
 *   TC_04 Locked out user → specific error shown
 *   TC_05 Logout → returns to login page
 */
public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void initPage() {
        loginPage = new LoginPage(getDriver());
        loginPage.navigateTo();
    }

    @Test(description = "TC_01: Valid credentials should navigate to Products page")
    public void testValidLogin() {
        ProductsPage productsPage = loginPage.loginAsStandardUser();
        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "Expected to land on Products page after valid login");
    }

    @Test(description = "TC_02: Invalid password should display error message")
    public void testInvalidPassword() {
        loginPage.login("standard_user", "wrong_password");
        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message should be visible for wrong password");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"),
                "Error text mismatch");
    }

    @Test(description = "TC_03: Empty username should show validation error")
    public void testEmptyUsername() {
        loginPage.login("", "secret_sauce");
        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message should be visible for empty username");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"),
                "Error text mismatch");
    }

    @Test(description = "TC_04: Empty password should show validation error")
    public void testEmptyPassword() {
        loginPage.login("standard_user", "");
        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message should be visible for empty password");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Password is required"),
                "Error text mismatch");
    }

    @Test(description = "TC_05: Locked out user should see specific error")
    public void testLockedOutUser() {
        loginPage.login("locked_out_user", "secret_sauce");
        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message should be visible for locked out user");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Sorry, this user has been locked out"),
                "Locked out error message mismatch");
    }

    @Test(description = "TC_06: Logout should redirect back to login page")
    public void testLogout() {
        ProductsPage productsPage = loginPage.loginAsStandardUser();
        productsPage.logout();
        // Add explicit wait for login button to appear
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Should return to login page after logout");
    }
    }
