package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.saucedemo.config.ConfigReader;

import java.time.Duration;

/**
 * CheckoutPage — covers all 3 checkout steps:
 *   Step 1: /checkout-step-one.html  (fill info)
 *   Step 2: /checkout-step-two.html  (review order)
 *   Complete: /checkout-complete.html
 */
public class CheckoutPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ── Step 1 Locators ───────────────────────────────────────────────────────
    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    // ── Step 2 Locators ───────────────────────────────────────────────────────
    @FindBy(css = ".summary_total_label")
    private WebElement totalLabel;

    @FindBy(css = ".summary_subtotal_label")
    private WebElement subtotalLabel;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    // ── Confirmation Locators ─────────────────────────────────────────────────
    @FindBy(className = "complete-header")
    private WebElement confirmationHeader;

    @FindBy(className = "complete-text")
    private WebElement confirmationText;

    @FindBy(id = "back-to-products")
    private WebElement backToProductsButton;

    // ── Constructor ───────────────────────────────────────────────────────────
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
        PageFactory.initElements(driver, this);
    }

    // ── Step 1 Actions ────────────────────────────────────────────────────────

    public void enterFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOf(firstNameField));
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
    }

    public void enterPostalCode(String postalCode) {
        postalCodeField.clear();
        postalCodeField.sendKeys(postalCode);
    }

    public void clickContinue() {
        continueButton.click();
    }

    public void fillCheckoutInfo(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
        clickContinue();
    }

    public boolean isErrorDisplayed() {
        try {
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    // ── Step 2 Actions ────────────────────────────────────────────────────────

    public String getOrderTotal() {
        wait.until(ExpectedConditions.visibilityOf(totalLabel));
        return totalLabel.getText();
    }

    public String getSubtotal() {
        return subtotalLabel.getText();
    }

    public void clickFinish() {
        finishButton.click();
    }

    public void clickCancel() {
        cancelButton.click();
    }

    // ── Confirmation Actions ──────────────────────────────────────────────────

    public boolean isOrderConfirmed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(confirmationHeader));
            return confirmationHeader.getText().equalsIgnoreCase("Thank you for your order!");
        } catch (Exception e) {
            return false;
        }
    }

    public String getConfirmationMessage() {
        return confirmationText.getText();
    }

    public ProductsPage backToProducts() {
        backToProductsButton.click();
        return new ProductsPage(driver);
    }
}
