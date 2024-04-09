package util;

import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    WebDriverWait wait;
    WebDriver driver;

    public BasePage(WebDriver driver){
        this.driver = driver;
        //wait = new WebDriverWait(driver, 30);
    }
/*    @BeforeAll
    public void setUpDriver(){
        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver","driver/chromedriver");
        options.addArguments("start-maximized","--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }*/



    public WebElement Find(String locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
    }

    public void text(String locator){
        Find(locator).getText();
    }

    public void shadowClick(By root, By element) {
        WebElement preRoot = driver.findElement(root);
        SearchContext locator = preRoot.getShadowRoot();
        locator.findElement(element).click();
    }

    public void shadowSendKeys(By root, By element, String data) {
        WebElement preRoot = driver.findElement(root);
        SearchContext locator = preRoot.getShadowRoot();
        locator.findElement(element).clear();
        locator.findElement(element).sendKeys(data);
    }

    public void shadowClear(By root, By element) {
        WebElement preRoot = driver.findElement(root);
        SearchContext locator = preRoot.getShadowRoot();
        locator.findElement(element).clear();
    }

    public String getText(By root, By element) {
        WebElement preRoot = driver.findElement(root);
        SearchContext locator = preRoot.getShadowRoot();
        return locator.findElement(element).getText();
    }

    public  WebElement getIcon(By root, By element) {
        WebElement preRoot = driver.findElement(root);
        SearchContext locator = preRoot.getShadowRoot();
        return locator.findElement(element);
    }
}
