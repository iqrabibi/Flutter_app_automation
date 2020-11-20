


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.net.URL;

public class fltter_test {
    public AppiumDriver<MobileElement> driver;
    public WebDriverWait wait;
    private static AppiumDriverLocalService service;

    @BeforeTest
    public void setUp() throws Exception {
//        service = AppiumDriverLocalService.buildDefaultService();
//        service.start();
//
//        if (service == null || !service.isRunning()) {
//            throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started!");
//        }



//           browser     String userName = System.getenv("BROWSERSTACK_USERNAME");
//                String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");


//             Browesr stack   _driver = new AndroidDriver<WebElement>(new URL("https://"+userName+":"+accessKey+"@hub-cloud.browserstack.com/wd/hub"), capabilitiesGenerator.getCapabilities());


        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformVersion", "9");
        capabilities.setCapability("deviceName", "Galaxy Tab A (8.0\\\", 2019)");
        capabilities.setCapability("noReset", true);

//        File classpathRoot = new File(System.getProperty("user.dir"));
//        File appDir = new File(classpathRoot, "/../apps");
//        File app = new File(appDir.getCanonicalPath(), "ios-sim-debug.zip");

//        System.out.println(app.getAbsolutePath());
        capabilities.setCapability("app", "//home//vend//Downloads//app-debug.apk");

        capabilities.setCapability("automationName", "Flutter");
        capabilities.setCapability("platformName", "Android");

        driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
//        driver = new AndroidDriver<MobileElement>(service.getUrl(), capabilities);
        wait = new WebDriverWait(driver, 10);
    }

    @AfterTest
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
        if (service != null) {
            service.stop();
        }
    }
}
