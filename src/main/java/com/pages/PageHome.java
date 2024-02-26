package com.pages;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.BasePage;

import java.util.concurrent.TimeUnit;

public class PageHome{

    WebDriverWait wait;
    WebDriver driver;
    BasePage page;
        public PageHome(WebDriver driver){
             page = new BasePage(driver);
             this.driver = driver;

        }

    By clickUpdateRatesRoot = By.tagName("bdb-ml-multi-action");
    By clickUpdateRatesElement = By.id("card__0");
    By toastPageRoot = By.xpath("//bdb-at-toast[@type='SUCCESS']");
    By toastPageElement = By.className("bdb-at-toast__content__title");

    public void clickRates(ExtentTest test){
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        page.shadowClick(clickUpdateRatesRoot,clickUpdateRatesElement);
        test.log(LogStatus.INFO, "Click en Actualización de tasas");

    }

    public void textToast(ExtentTest test){
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        Assert.assertEquals("¡Actualizamos las tasas de hoy satisfactoriamente!", page.getText(toastPageRoot,toastPageElement));
        test.log(LogStatus.INFO, "Se visualiza toast ¡Actualizamos las tasas de hoy satisfactoriamente!");
    }
}
