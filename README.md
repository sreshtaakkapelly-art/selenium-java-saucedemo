# Selenium Java Automation Framework — SauceDemo

![CI](https://github.com/YOUR_USERNAME/selenium-saucedemo/actions/workflows/ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-17-blue)
![Selenium](https://img.shields.io/badge/Selenium-4.18-green)
![TestNG](https://img.shields.io/badge/TestNG-7.9-orange)

A production-ready Selenium WebDriver automation framework built with Java, following the **Page Object Model (POM)** design pattern. Automates end-to-end test scenarios for [saucedemo.com](https://www.saucedemo.com).

---

## Tech Stack

| Tool | Purpose |
|---|---|
| Java 17 | Programming language |
| Selenium 4 | Browser automation |
| TestNG | Test framework & assertions |
| Maven | Build tool & dependency management |
| WebDriverManager | Auto-manages browser drivers |
| Extent Reports | HTML test reporting with screenshots |
| Log4j2 | Logging |
| GitHub Actions | CI/CD pipeline |

---

## Framework Architecture

```
src/
├── main/java/com/saucedemo/
│   ├── base/          # BaseTest (setup, teardown, reporting)
│   ├── config/        # ConfigReader (reads config.properties)
│   ├── pages/         # Page Object classes
│   │   ├── LoginPage.java
│   │   ├── ProductsPage.java
│   │   ├── CartPage.java
│   │   └── CheckoutPage.java
│   └── utils/         # DriverFactory, ExtentReportManager, ScreenshotUtils
│
└── test/
    ├── java/com/saucedemo/tests/
    │   ├── LoginTest.java      (6 test cases)
    │   ├── ProductTest.java    (6 test cases)
    │   ├── CartTest.java       (4 test cases)
    │   └── CheckoutTest.java   (5 test cases)
    └── resources/
        ├── config.properties   # Base URL, browser, credentials
        └── testng.xml          # Test suite configuration
```

---

## Key Features

- **Page Object Model** — locators and actions encapsulated per page
- **Cross-browser support** — Chrome and Firefox via `testng.xml` parameters
- **Data-driven ready** — `@DataProvider` pattern implemented
- **Auto screenshots on failure** — attached directly to HTML report
- **ThreadLocal WebDriver** — safe for parallel execution
- **CI/CD via GitHub Actions** — runs on every push and pull request
- **Extent Reports** — rich HTML reports with dark theme

---

## Test Coverage

| Module | Test Cases |
|---|---|
| Login | Valid login, invalid creds, empty fields, locked user, logout |
| Products | Count, sort A-Z/Z-A/price, add to cart |
| Cart | Item appears, remove item, continue shopping |
| Checkout | Full flow, missing fields validation, order total |

---

## Running the Tests

**Prerequisites:** Java 17+, Maven 3.8+, Chrome browser

```bash
# Clone the repo
git clone https://github.com/YOUR_USERNAME/selenium-saucedemo.git
cd selenium-saucedemo

# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=LoginTest

# Run with Firefox
mvn test -Dbrowser=firefox
```

**View the report:** Open `test-output/ExtentReport.html` in your browser.

---

## CI/CD

Every push to `main` or `develop` automatically:
1. Spins up a fresh Ubuntu runner
2. Sets up Java 17 and Chrome
3. Runs all tests in headless mode
4. Uploads the Extent Report as a build artifact
5. Uploads failure screenshots if any test fails

---

## Author

**Your Name** — [LinkedIn](https://linkedin.com/in/yourprofile) · [GitHub](https://github.com/YOUR_USERNAME)
