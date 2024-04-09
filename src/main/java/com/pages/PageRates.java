package com.pages;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import util.BasePage;
import util.ExcelData;
import java.util.concurrent.TimeUnit;


public class PageRates{

    WebDriver driver;
    public PageRates(WebDriver driver){
        this.driver = driver;
        page = new BasePage(driver);
    }

    BasePage page;
    ExcelData data = new ExcelData();
    ExcelData readFile = new ExcelData();

    By rateDtfRoot = By.id("DTF");
    By rateIbr1MRoot = By.id("IBR 1M");
    By rateIbr3MRoot = By.id("IBR 3M");
    By rateIbr6MRoot = By.id("IBR 6M");
    By rateElement = By.id("bdb-at-input");
    By saveButton = By.id("btnSave");

    By iconDtfErrorRoot = By.xpath("//bdb-at-input[@id='DTF']");
    By iconIbr1mErrorRoot = By.xpath("//bdb-at-input[@id='IBR 1M']");
    By iconIbr3mErrorRoot = By.xpath("//bdb-at-input[@id='IBR 3M']");
    By iconIbr6mErrorRoot = By.xpath("//bdb-at-input[@id='IBR 6M']");
    By iconErrorElement = By.id("icon");



    public void ratesTrue(ExtentTest test) throws Exception {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        String filepath = "Excel/tasas_ldf.xlsx";
        String DTF = readFile.excelData(filepath, 1,1);
        String IBR1M = readFile.excelData(filepath, 2,1);
        String IBR3M = readFile.excelData(filepath, 3,1);
        String IBR6M = readFile.excelData(filepath, 4,1);
        page.shadowSendKeys(rateDtfRoot,rateElement,DTF);
        test.log(LogStatus.INFO, "Ingresó DTF correctamente");
        page.shadowSendKeys(rateIbr1MRoot,rateElement,IBR1M);
        test.log(LogStatus.INFO, "Ingresó IBR1M correctamente");
        page.shadowSendKeys(rateIbr3MRoot,rateElement,IBR3M);
        test.log(LogStatus.INFO, "Ingresó IBR3M correctamente");
        page.shadowSendKeys(rateIbr6MRoot,rateElement,IBR6M);
        test.log(LogStatus.INFO, "Ingresó IBR6M correctamente");
    }

    public void rateFail() throws Exception {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        String filepath = "Excel/tasas-ldf-fail.xlsx";
        String DTF = readFile.excelData(filepath, 1,1);
        String IBR1M = readFile.excelData(filepath, 2,1);
        String IBR3M = readFile.excelData(filepath, 3,1);
        String IBR6M = readFile.excelData(filepath, 4,1);
        page.shadowSendKeys(rateDtfRoot,rateElement,DTF);
        page.shadowSendKeys(rateIbr1MRoot,rateElement,IBR1M);
        page.shadowSendKeys(rateIbr3MRoot,rateElement,IBR3M);
        page.shadowSendKeys(rateIbr6MRoot,rateElement,IBR6M);
        page.shadowClick(rateIbr1MRoot,rateElement);

        WebElement iconElementDtf = page.getIcon(iconDtfErrorRoot, iconErrorElement);
        WebElement iconElementIbr1m = page.getIcon(iconIbr1mErrorRoot, iconErrorElement);
        WebElement iconElementIbr3m = page.getIcon(iconIbr3mErrorRoot, iconErrorElement);
        WebElement iconElementIbr6m = page.getIcon(iconIbr6mErrorRoot, iconErrorElement);

        if (iconElementDtf != null && iconElementDtf.isDisplayed()) {
            System.out.println("Alerta: Icono de error encontrado, la tasa DTF no puede estar vacía.");
        } else if (iconElementIbr1m != null && iconElementIbr1m.isDisplayed()) {
            System.out.println("Alerta: Icono de error encontrado, la tasa IBR 1M no puede estar vacía.");
        } else if (iconElementIbr3m != null && iconElementIbr3m.isDisplayed()) {
            System.out.println("Alerta: Icono de error encontrado, la tasa IBR 3M no puede estar vacía..");
        } else if (iconElementIbr6m != null && iconElementIbr6m.isDisplayed()) {
            System.out.println("Alerta: Icono de error encontrado, la tasa IBR 6M no puede estar vacía.");
        } else {
            System.out.println("Prueba fallida: No se encontró el icono de error.");
        }
    }

    public void clickButton(ExtentTest test){
        driver.findElement(saveButton).click();
        test.log(LogStatus.INFO, "Click en botón guardar");
    }
}
