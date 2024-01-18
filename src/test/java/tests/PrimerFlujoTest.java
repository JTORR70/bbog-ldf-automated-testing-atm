package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.PageHome;
import pages.PageRates;
import utils.BasePage;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class PrimerFlujoTest extends BasePage{
    PageHome page = new PageHome();
    PageRates rates = new PageRates();
    public PrimerFlujoTest() {
        super(driver);
    }

    @Test()
    public void createRate() throws Exception {
        driver.get("https://ldf-digital-web-ui.bbogcreditocomercialtccstg.com/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        page.clickRates();
        rates.sendkeysRates();
        rates.clickButton();
        page.textToast();
    }

    @AfterAll
    public static void tearDown() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }
}
