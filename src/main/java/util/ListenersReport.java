package util;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import util.BdbReports;

public class ListenersReport implements ITestListener {


    private String caseTest = "caseName";
    private ConfigFile env = new ConfigFile();
    private String device;
    private String report = "ldfReport";
    private String reportFail = "failedReport";

    private static final Logger LOGGER = Logger.getLogger("Listeners");
    private final ExtentReports reports = BdbReports.getInstance(env.getProperty(report));
    private final ExtentReports reportsFail = BdbReports.getInstance(env.getProperty(reportFail));

    public static final ThreadLocal<ExtentTest> TEST = new ThreadLocal<>();
    public static final ThreadLocal<ExtentTest> TEST_FAIL = new ThreadLocal<>();

    private BrowserStack browserSck = new BrowserStack();

    @Override
    public void onTestSuccess(ITestResult result) {
        device = (String) result.getTestContext().getAttribute("device");
        ListenersReport.TEST.get().log(LogStatus.PASS, "Test Exitoso");
        try {
            if (!device.equals("desktop")) {
                BrowserStack.markTestStatus("Passed", "Succes test", browserSck.getDriver());
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        device = (String) result.getTestContext().getAttribute("device");
        ListenersReport.TEST_FAIL.get().log(LogStatus.FAIL, "Test Fallido");
        try {
            if (!device.equals("desktop")) {
                BrowserStack.markTestStatus("Failed", "Failed Test", browserSck.getDriver());
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        TEST.set(reports.startTest((String) result.getTestContext().getAttribute(caseTest)));
        TEST_FAIL.set(reportsFail.startTest((String) result.getTestContext().getAttribute(caseTest)));
    }

    @Override
    public void onFinish(ITestContext context) {
        if (context.getAttribute("result").equals("pass")) {
            reports.endTest(TEST.get());
            reports.flush();
            TEST.remove();
        } else {
            reportsFail.endTest(TEST_FAIL.get());
            reportsFail.flush();
            TEST_FAIL.remove();
        }
    }
}
