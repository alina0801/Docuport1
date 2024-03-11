package app.docuport.utilities;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.time.Duration;

public class Driver {
    /*
    Creating the private constractor so this class object is not reachable from outside
     */

    private Driver(){
    }

    /*
    making driver instance private, so it's not reachable from outside the class

    we make it static -> because we want it to run before everything else and also use it in static method
     */

    // private static WebDriver driver;
    // implement threadLocal to achieve multi thread locally
    private static InheritableThreadLocal <WebDriver> driverPool = new InheritableThreadLocal<>();
    private static WebDriver driver;
    private static ChromeOptions chromeOptions = new ChromeOptions();
    private static FirefoxOptions firefoxOptions = new FirefoxOptions();
    private static DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
    /*
    reusable method that will return the same driver instance every time called
     */

    /**
     * singleton pattern
     * @return driver
     * @author Loop Academy
     */

    // Creating re-usable utility method that will return same 'driver' instance everytime we call it 
    public static WebDriver getDriver(){
        if(driverPool.get()==null){

            // we read our browser type from configuration.properties file using .getProperty method we are creating in ConfigurationReader class.

            String browserType = ConfigurationReader.getProperty("browser");

            //Depending on a browser type our switch statement will determine to open specific type of browser/driver
            
            switch (browserType.toLowerCase()){

                //regular chrome driver
                case "chrome":
                    WebDriverManager.chromedriver().clearDriverCache().setup();
                    driverPool.set(new ChromeDriver());
                    driverPool.get().manage().window().maximize();
                    driverPool.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                    break;

                case "headless-linux":
                    chromeOptions.addArguments("--headless"); // enable headless made
//                    chromeOptions.addArguments("--start-maximized"); // maximized
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    WebDriverManager.chromedriver().clearDriverCache().setup();
                    driverPool.set(new ChromeDriver(chromeOptions));
                    driverPool.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                    break;

                /**
                 * These added because of EC@2 Jenkins on Linux was not running the ones above because of graphical issues.
                 */
                case "chrome-linux":
//                    chromeOptions.addArguments("--headless"); // enable headless made
//                    chromeOptions.addArguments("--start-maximized"); // maximized
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    WebDriverManager.chromedriver().clearDriverCache().setup();
                    driverPool.set(new ChromeDriver(chromeOptions));
                    driverPool.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                    break;

                case "remote-chrome-linux":
                    try {
                        // assign your grid server address
                        String gridAddress = "3.92.199.191";
                        URL url = new URL("http://" + gridAddress + ":4444/wd/hub");
                        chromeOptions = new ChromeOptions();
                        chromeOptions.addArguments("--headless");
                        chromeOptions.addArguments("--no-sandbox");
                        chromeOptions.addArguments("--disable-dev-shm-usage");
                        desiredCapabilities.merge(chromeOptions);
                        driver = new RemoteWebDriver(url, desiredCapabilities);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case "remote-firefox-linux":
                    try {
                        // assign your grid server address
                        String gridAddress = "3.92.199.191";
                        URL url = new URL("http://" + gridAddress + ":4444/wd/hub");
                        desiredCapabilities.setBrowserName("firefox");
                        firefoxOptions.addArguments("--headless");
                        firefoxOptions.addArguments("--disable-gpu");
                        firefoxOptions.addArguments("--no-sandbox");
                        desiredCapabilities.merge(firefoxOptions);
                        driver = new RemoteWebDriver(url, desiredCapabilities);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        // Same driver will be returned every time we call Driver.getDriver(); method
        return driverPool.get();
    }
        // this method makes sure we have some form of driver session or driver id has. Either null or not null it must exist
    /**
     * closing driver
     * @author nadir
     */
    public static void closeDriver(){
        if(driverPool.get() != null){
            driverPool.get().quit();
            driverPool.remove();
        }
    }
}
