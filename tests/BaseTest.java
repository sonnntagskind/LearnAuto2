package battlemetrics_rust.tests;

import battlemetrics_rust.steps.LogsSteps;
import battlemetrics_rust.utils.DriverFactory;
import battlemetrics_rust.utils.PropertyReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.util.concurrent.TimeUnit;

public abstract class BaseTest {

    private static WebDriver driver;

    public LogsSteps logsSteps;

    public static WebDriver getDriver() {
        return driver;
    }

    private DriverFactory driverFactory = new DriverFactory();
    private PropertyReader propertyReader = new PropertyReader();

    @BeforeClass
    public void setup() {
        driver = driverFactory.getDriver(propertyReader.getBrowser());
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        logsSteps = new LogsSteps();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @DataProvider(name = "servers")
    public Object[] dataProviderMethod() {
        return new Object[]{/*"5372805",*/"4883700"};
    }

}




