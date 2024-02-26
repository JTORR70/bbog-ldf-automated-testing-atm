package com.ldf;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import com.pages.PageHome;
import com.pages.PageRates;
import util.*;


import static util.Reports.capture;
import app.getxray.xray.testng.annotations.XrayTest;

import java.io.IOException;

@Listeners({app.getxray.xray.testng.listeners.XrayListener.class})
public class TestRates extends BrowserStack {

    private WebDriver driver;
    private PageHome page;
    private PageRates rates;
    private Browser browser;
    private ExtentTest testSuccess;
    private ExtentTest testFailed;
    private ExtentReports reportSuccess;
    private ExtentReports reportFailed;
    private ConfigFile env = new ConfigFile();
    private String url = env.getProperty("url");
    private String report = env.getProperty("report");
    private String successfulReport = env.getProperty("SUCCESSFUL_REPORT");
    private String failedReport = env.getProperty("FAILED_REPORT");
    private String mobile = "browser";

    private String testName = "";

    @BeforeMethod
    @Parameters({"Caso", "device"})
    public void beforeMethod(String caso, String device, ITestContext context)
            throws IOException {

        context.setAttribute("device", device);
        testName = caso;
        if (device.equals(mobile)) {
            driver = getDriver();
            getDriver().get(url);
        } else {
            Browser browser = new Browser();
            driver = browser.browser(url, caso);
        }
        driver.manage().window().maximize();
        reportSuccess = BdbReports.getInstance(successfulReport);
        testSuccess = reportSuccess.startTest(caso);
        reportFailed = BdbReports.getInstance(failedReport);
        testFailed = reportFailed.startTest(caso);
        page = new PageHome(driver);
        rates = new PageRates(driver);


    }

    @Test
    @XrayTest(key="LDF-2842")
    public void createRate() throws Exception {
        page.clickRates(testSuccess);
        rates.ratesTrue(testSuccess);
        rates.clickButton(testSuccess);
        page.textToast(testSuccess);
        testSuccess.log(LogStatus.PASS, "Tasas cargadas correctamente");
        testFailed.log(LogStatus.FAIL, testFailed.addScreenCapture(capture(driver)) + "Test Failed");
    }

    @Test()
    public void createRateFailed() throws Exception {
        page.clickRates(testSuccess);
        rates.rateFail();
        testSuccess.log(LogStatus.PASS, "El icono de error fue encontrado");
        testSuccess.log(LogStatus.PASS, testSuccess.addScreenCapture(capture(driver)) + "Test Exitoso");
    }

    @AfterMethod
    @Parameters({"Caso"})
    public void after(final ITestResult result, final String caso) {

        String rep = (env.getProperty(report)) + (env.getProperty(successfulReport)) + ".html";

      /*  if (result.getStatus() == ITestResult.FAILURE) {
            String rep = (env.getProperty(report)) + (env.getProperty(failedReport)) + ".html";
            driver.quit();
            reportFailed.endTest(testFailed);
            reportFailed.flush();
        } else {
            String rep = (env.getProperty(report)) + (env.getProperty(successfulReport)) + ".html";
            driver.quit();
            reportSuccess.endTest(testSuccess);
            reportSuccess.flush();

        }*/

    }

}
