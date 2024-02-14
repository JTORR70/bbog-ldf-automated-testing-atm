package util;

import com.sun.security.auth.login.ConfigFile;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class Browser {
    public WebDriver browser(String url) {

        WebDriver driver;
        ConfigFile env = new ConfigFile();
        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver");

        driver = new ChromeDriver(options);
        driver.navigate().to(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }
}
