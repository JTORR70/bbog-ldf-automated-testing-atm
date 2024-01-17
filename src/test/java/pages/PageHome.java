package pages;

import org.openqa.selenium.By;
import utils.BasePage;

public class PageHome extends BasePage {

    public PageHome(){
        super(driver);
    }
    By clickUodateRatesRoot = By.tagName("bdb-ml-multi-action");
    By clickUpdateRatesElement = By.id("card__0");

    public void clickRates(){
        shadowClick(clickUodateRatesRoot,clickUpdateRatesElement);
    }
}
