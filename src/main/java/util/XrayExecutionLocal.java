package util;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * Esta clase se utiliza para interactuar con la API de Xray
 * y enviar resultados de ejecución de pruebas de TestNG.
 */
public class XrayExecutionLocal {
    /**
     * looger.
     */
    @SuppressWarnings("checkstyle:LineLength")
    private static final Logger XRAY_LOGGER =
            Logger.getLogger("XrayExecutionLocal");
    /**
     * XRAY_API_URL.
     */
    private static final String XRAY_API_URL =
            "https://xray.cloud.getxray.app/api/v2/import/execution/testng";
    /**
     * XRAY_BEARER_TOKEN.
     */
    private static final String XRAY_BEARER_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0ZW5hbnQiOiJiZTYyOWQyNy00NmY5LTNmNDItYWJmNC1kZTJiNTkzNWY4MzQiLCJhY2NvdW50SWQiOiI2MzE2NTNiYzYyZmUxZTZlYWM2ZDQ3MzciLCJpc1hlYSI6ZmFsc2UsImlhdCI6MTcwMTgxMjQyMCwiZXhwIjoxNzAxODk4ODIwLCJhdWQiOiI3NzQ5MkUyNDRFQjQ0NTBFQjdGQkU2NzBBN0VBODE2NyIsImlzcyI6ImNvbS54cGFuZGl0LnBsdWdpbnMueHJheSIsInN1YiI6Ijc3NDkyRTI0NEVCNDQ1MEVCN0ZCRTY3MEE3RUE4MTY3In0.yZLx8R7o1sZyL_imAIw-mW9KHeuV-sA-lX7NcHRuBPE";
    /**
     * CONTENT_TYPE_XML.
     */
    private static final String CONTENT_TYPE_XML = "application/xml";
    /**
     * PROJECTKEY.
     */
    private String projectKey = "LDF";
    /**
     * TESTPLANKEY.
     */
    private String testPlanKey = "LDF-2370";

    /**
     * DRIVER.
     */
    public WebDriver driver;

    /**
     * Constructor de la clase XrayExecutionLocal.
     *
     * @param webDriver Instancia de WebDriver.
     */

    public XrayExecutionLocal(final WebDriver webDriver) {
        this.driver = webDriver;
        PageFactory.initElements(new AjaxElementLocatorFactory(webDriver, 15),
                this);
    }

    /**
     * Envía una solicitud POST a la API de Xray
     * con los resultados de la ejecución de pruebas.
     *
     * @param testExecKey Clave de la ejecución de pruebas.
     * @return true si la solicitud fue exitosa, false en caso contrario.
     */

    public boolean sendPostRequest(final String testExecKey) {
        String apiUrl = buildApiUrl(projectKey, testPlanKey, testExecKey);
        String filePath = "target/surefire-reports/testng-results.xml";

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = executeHttpPost(apiUrl,
                     filePath)) {

            int statusCode = response.getCode();
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null && statusCode == 200) {
                XRAY_LOGGER.info(
                        "Solicitud HTTP a Xray exitosa. Los resultados se han marcado correctamente.");
                return true;
            } else {
                XRAY_LOGGER.info(
                        "Error en la solicitud HTTP a Xray. Los resultados no se han marcado correctamente.");
            }
        } catch (IOException e) {
            XRAY_LOGGER.info("Error durante la solicitud HTTP a Xray: " + e.getMessage());
        }

        return false;
    }


    private String buildApiUrl(final String projectKeyParam,
                               final String testPlanKeyParam,
                               final String testExecKey) {
        return XRAY_API_URL + "?projectKey=" + projectKeyParam + "&testPlanKey=" + testPlanKeyParam + "&testExecKey=" + testExecKey;
    }

    private CloseableHttpResponse executeHttpPost(final String apiUrl,
                                                  final String filePath)
            throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(apiUrl);
        httpPost.addHeader("Authorization", "Bearer " + XRAY_BEARER_TOKEN);
        httpPost.addHeader("Content-Type", CONTENT_TYPE_XML);
        String xmlData = Utils.readFileContent(filePath);
        StringEntity entity =
                new StringEntity(xmlData, ContentType.APPLICATION_XML);
        httpPost.setEntity(entity);
        return httpClient.execute(httpPost);
    }

    private static final class Utils {
        /**
         * Lee el contenido de un archivo y lo devuelve como una cadena.
         *
         * @param filePath Ruta del archivo.
         * @return Contenido del archivo como cadena.
         * @throws IOException Si ocurre un error al leer el archivo.
         */

        private static String readFileContent(final String filePath)
                throws IOException {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        }
    }
}

