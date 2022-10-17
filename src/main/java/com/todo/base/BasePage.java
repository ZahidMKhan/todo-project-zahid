package com.todo.base;

import com.todo.pages.HomePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BasePage {

    public static Properties prop;
    public static FileInputStream fis;
    public static WebDriver driver;
    public static WebDriverWait wait;
    public static String url = null;

    protected static Logger log = LogManager.getLogger();

    public BasePage() {

        prop = new Properties();
        try {
            fis = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/todo/config/config.properties");
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        url = prop.getProperty("url");
    }

    private void initialize(String browserName, String browserMode) {

        log.info("Launching the browser...");

        switch (browserName) {

            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                EdgeOptions options = new EdgeOptions();
                options.addArguments(browserMode);

                driver = new EdgeDriver(options);
            }
            default -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments(browserMode);

                driver = new ChromeDriver(options);
            }
        }

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public HomePage launchApp(String browserName, String browserMode) {

        try {
            initialize(browserName, browserMode);

            driver.get(url);
            return new HomePage();
        } catch (Exception e) {
            log.error("error : unable to load the homepage");
            e.printStackTrace();
            return null;
        }
    }

    public void quit() {

        if(driver!=null) {
            log.info("Quitting the browser session...");
            driver.close();
            driver.quit();
        }
    }


    //-----common driver methods used across page classes
    public WebElement setText(By locator, CharSequence text) {

        waitForElementToBeVisible(locator);
        WebElement ele = driver.findElement(locator);
        return setText(ele, text);
    }

    public WebElement setText(WebElement ele, CharSequence text) {

        ele.sendKeys(text);
        return ele;
    }

    public String getText(By locator) {

        waitForElementToBeVisible(locator);
        return driver.findElement(locator).getText();
    }

    public String getText(WebElement ele) {

        return ele.getText();
    }

    public WebElement clearText(WebElement ele) {

        while (!ele.getAttribute("value").equals(""))
            ele.sendKeys(Keys.BACK_SPACE);

        return ele;
    }

    public void click(By locator) {

        waitForElementToBeVisible(locator);
        driver.findElement(locator).click();
    }

    public void waitForElementToBeVisible(By locator) {

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public WebElement waitForElementToBeVisible(WebElement ele) {

        return wait.until(ExpectedConditions.visibilityOf(ele));
    }


}