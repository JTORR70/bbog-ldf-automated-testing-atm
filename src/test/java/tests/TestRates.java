package tests;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import pages.PageHome;
import pages.PageRates;
import util.BasePage;
import util.Reports;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static util.Reports.capture;

public class PrimerFlujoTest extends BasePage {
    PageHome page = new PageHome();
    PageRates rates = new PageRates();
    public PrimerFlujoTest() {
        super(driver);
    }

    static ExtentTest test;
    static ExtentReports report;
    @BeforeAll
    public static void startTest()
    {
        report = new ExtentReports(System.getProperty("Reports")+"TestReports.html");
        test = report.startTest("createRate");
        test = report.startTest("createRateFailed");
    }
    @Test()
    public void createRate() throws Exception {
        driver.get("https://ldf-digital-web-ui.bbogcreditocomercialtccstg.com/");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        page.clickRates();
        rates.ratesTrue();
        rates.clickButton();
        page.textToast();
        test.log(LogStatus.PASS, "Tasas cargadas correctamente");
        test.log(LogStatus.FAIL,test.addScreenCapture(capture(driver))+ "Test Failed");
    }

    @Test()
    public void createRateFailed() throws Exception {
        driver.get("https://ldf-digital-web-ui.bbogcreditocomercialtccstg.com/");
        page.clickRates();
        rates.rateFail();
        test.log(LogStatus.PASS, "El icono de error fue encontrado");
        test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Test Exitoso");
    }

    @AfterAll
    public static void tearDown() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
        report.endTest(test);
        report.flush();
    }
}