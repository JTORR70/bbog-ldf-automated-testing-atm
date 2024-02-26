package util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.io.FileReader;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class BrowserStack {

    private String nameCase = "caseName";

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void setDriver(ThreadLocal<WebDriver> pDriver) {
        BrowserStack.driver = pDriver;
    }

    public WebDriver getDriver() {
        return driver.get();
    }

  /*  void clean() {
        driver.remove();
    }*/

    public static void markTestStatus(String status, String reason, WebDriver drivers) {
        JavascriptExecutor jse = (JavascriptExecutor) drivers;
        jse.executeScript(
                "browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"" + status
                        + "\", \"reason\": \"" + reason + "\"}}");
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters(value = {"config", "environment", "nameTestCase"})
    @SuppressWarnings("unchecked")

    public void setUp(String configFile, String environment, String nameTestCase,
                      ITestContext context) throws Exception {

        if (!configFile.isEmpty()) {
            context.setAttribute(nameCase, nameTestCase);

            JSONParser parser = new JSONParser();
            JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resources/conf/" + configFile));
            JSONObject envs = (JSONObject) config.get("environments");

            MutableCapabilities capabilities = new MutableCapabilities();

            Map<String, String> envCapabilities = (Map<String, String>) envs.get(environment);
            Iterator<Map.Entry<String, String>> it = envCapabilities.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> pair = it.next();
                capabilities.setCapability(pair.getKey(), pair.getValue());
            }

            Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
            it = commonCapabilities.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> pair = it.next();
                if (capabilities.getCapability(pair.getKey()) == null) {
                    capabilities.setCapability(pair.getKey(), pair.getValue());
                }
            }

            capabilities.setCapability("name", nameTestCase);


            driver.set(new RemoteWebDriver(new URL(
                    "https://" + config.get("user") + ":" + config.get("key") + "@" + config.get("server") + "/wd/hub"),
                    capabilities));

        } else {
            context.setAttribute(nameCase, nameTestCase);
        }
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult testResult, ITestContext context) throws Exception {
        Object device = context.getAttribute("device");
        if (device != null && device.equals("browser")) {
            getDriver().quit();
        }
    }
}
