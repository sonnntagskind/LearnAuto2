package battlemetrics_rust.tests;

import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;

public class LogsTest extends BaseTest {

    @Test(dataProvider = "servers")
    public void start(String serverID) throws ParseException, IOException {

        logsSteps
                .enterPage(serverID)
                .printServiceInfo()
                .writeExcel(serverID)
        ;
    }
}

