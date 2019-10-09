package caps;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.parser.JSONParser;
import enums.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import utils.PropUtils;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class DesiredCaps {

    public AppiumDriver<MobileElement> driver;
    public static boolean installApp;
    public static String appPaths = "app_paths_dev.properties";

    protected DesiredCaps(){}

    protected DesiredCaps(AppiumDriver<MobileElement> driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        this.driver = driver;
    }


    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        setAppPaths();
    }

    @BeforeClass
    protected void setUp(String configFile,String environment, String os, String testName) throws Exception {

        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resources/conf/" + configFile));
        JSONObject envs = (JSONObject) config.get("environments");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        Map<String, String> envCapabilities = (Map<String, String>) envs.get(environment);
        Iterator it = envCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            capabilities.setCapability(pair.getKey().toString(), pair.getValue());
        }

        Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
        it = commonCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (capabilities.getCapability(pair.getKey().toString()) == null) {
                capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
            }
        }

        // create the complete URL based on config.properties information
        String completeURL = "http://" + PropUtils.readProperty("run.ip", appPaths) + ":" + PropUtils.readProperty("run.port", appPaths)
                + "/wd/hub";

        if (os.equals(Platform.ANDROID.getPlatformName())) {
            if(installApp) {
                capabilities.setCapability(MobileCapabilityType.APP, new File(PropUtils.readProperty("app.android.path", appPaths)).getAbsolutePath());
                capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
                capabilities.setCapability(MobileCapabilityType.FULL_RESET, true);

            }else {

                capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,
                        PropUtils.readProperty("app.android.appPackage", appPaths));
                capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
                        PropUtils.readProperty("app.android.appActivity", appPaths));
            }

            driver = new AndroidDriver<>(new URL(completeURL), capabilities);

        } else {

            if(installApp) {
                capabilities.setCapability(MobileCapabilityType.APP, new File(PropUtils.readProperty("app.ios.path", appPaths)).getAbsolutePath());
            }else {
                capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, PropUtils.readProperty("app.ios.bundleId", appPaths));
            }

            driver = new IOSDriver<>(new URL(completeURL), capabilities);
        }

    }
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

    /**
     * Set application paths depends on environment
     *
     * Environment can be: prod or dev
     *
     * Android application paths: app package and app activity
     * iOS application paths: app bundle id
     *
     * @return application paths
     */
    private void setAppPaths() {
        String envProdOrDev = System.getProperty("envProdOrDev");

        if(StringUtils.isEmpty(envProdOrDev)) {
            envProdOrDev = "dev";
        }

        if(envProdOrDev.equalsIgnoreCase("prod")) {
            appPaths = "app_paths_prod.properties";
        }else {
            appPaths = "app_paths_dev.properties";
        }
    }
}
