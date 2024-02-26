package util;

import java.time.Duration;


import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.remote.CapabilityType;

public class Browser {

    public WebDriver browser(String url, String caso) {

        WebDriver driver;

        ConfigFile env = new ConfigFile();
        ChromeOptions options = new ChromeOptions();

        System.setProperty("webdriver.chrome.driver", env.getProperty("driverPath"));
        options.addArguments("--incognito");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.navigate().to(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }
}
