package util;

import java.util.Locale;
import com.relevantcodes.extentreports.ExtentReports;

public class BdbReports {

    private BdbReports() {
        throw new IllegalStateException("Utility class");
    }

    public static ExtentReports getInstance(String servicio) {
        ExtentReports extent;
        ConfigFile env = new ConfigFile();

        String path = env.getProperty("report") + servicio + ".html";
        extent = new ExtentReports(path, false, Locale.ENGLISH);
        extent
                .addSystemInfo("Selenium Version", "4.1.2")
                .addSystemInfo("Plataform", "Mac");
        return extent;
    }
}
