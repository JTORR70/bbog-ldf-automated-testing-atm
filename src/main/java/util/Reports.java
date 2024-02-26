package util;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class Reports {

    public static String capture(WebDriver driver) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String directoryPath = "ReportImages";

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File dest = new File(directory, "ReportImages" + System.currentTimeMillis() + ".jpg");
        String filePath = dest.getAbsolutePath();
        FileUtils.copyFile(scrFile, dest);
        return filePath;
    }
}
