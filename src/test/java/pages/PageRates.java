package pages;

import org.openqa.selenium.By;
import utils.BasePage;
import utils.ExcelData;

import java.io.IOException;
import java.util.logging.SocketHandler;

public class PageRates extends BasePage{

    public PageRates(){
        super(driver);
    }
    ExcelData data = new ExcelData();
    ExcelData readFile = new ExcelData();

    By rateDtfRoot = By.id("DTF");
    By rateIbr1MRoot = By.id("IBR 1M");
    By rateIbr3MRoot = By.id("IBR 3M");
    By rateIbr6MRoot = By.id("IBR 6M");
    By rateElement = By.id("bdb-at-input");

    By saveButton = By.id("btnSave");
    public void sendkeysRates() throws Exception {
        String filepath = "Excel/tasas_ldf.xlsx";
        String DTF = readFile.excelData(filepath, 1,1);
        String IBR1M = readFile.excelData(filepath, 2,1);
        String IBR3M = readFile.excelData(filepath, 3,1);
        String IBR6M = readFile.excelData(filepath, 4,1);
        shadowSendKeys(rateDtfRoot,rateElement,DTF);
        shadowSendKeys(rateIbr1MRoot,rateElement,IBR1M);
        shadowSendKeys(rateIbr3MRoot,rateElement,IBR3M);
        shadowSendKeys(rateIbr6MRoot,rateElement,IBR6M);
    }

    public void clickButton(){
        driver.findElement(saveButton).click();
    }
}
