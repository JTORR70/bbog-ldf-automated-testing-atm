package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import utils.BasePage;

import java.util.concurrent.TimeUnit;

public class PageHome extends BasePage {

    public PageHome(){
        super(driver);
    }
    By clickUpdateRatesRoot = By.tagName("bdb-ml-multi-action");
    By clickUpdateRatesElement = By.id("card__0");
    By toastPageRoot = By.xpath("//bdb-at-toast[@type='SUCCESS']");
    By toastPageElement = By.className("bdb-at-toast__content__title");
    public void clickRates(){
        shadowClick(clickUpdateRatesRoot,clickUpdateRatesElement);
    }

    public void textToast(){
        Assert.assertEquals("¡Actualizamos las tasas de hoy satisfactoriamente!", getText(toastPageRoot,toastPageElement));
    }

}
