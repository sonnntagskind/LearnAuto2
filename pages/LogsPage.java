package battlemetrics_rust.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class LogsPage extends BasePage {

    @FindBy(xpath = "//*[@id=\"PlayerInstancesPage\"]/div/ul/li[*]/p/a")
    private static List<WebElement> listWebPlayerNames;
    @FindBy(xpath = "//*[@id=\"PlayerInstancesPage\"]/div/ul/li[*]/dl/dd[4]/time[1]")
    private static List<WebElement> listWebLastSeenTimes;
    @FindBy(xpath = "//*[@id=\"PlayerInstancesPage\"]/div/ul/li[*]/dl/dd[1]/div")
    private static List<WebElement> listWebOnlineType;

//    @FindBy(xpath = "//*[@id=\"PlayerInstancesPage\"]/div/ul/li[*]/dl/dd[1]/div")
//    private static WebElement tempColor;
    @FindBy(xpath = "//*[@id=\"PlayerInstancesPage\"]/div/ul/li[1]/dl/dd[4]/time[1]")
    private static WebElement firstWebLastSeenTime;
    @FindBy(xpath = "//*[@id=\"PlayerInstancesPage\"]/div/ul/li[10]/dl/dd[4]/time[1]")
    private static WebElement lastWebLastSeenTime;

    @FindBy(xpath = "//ol/li[3]/a")
    private static WebElement serverLink;
//    @FindBy(xpath = "//*[@id=\"sessions-at\"]")
//    private static WebElement reportTime;
    @FindBy(xpath = "//*[@id=\"PlayerInstancesPage\"]/div/nav/ul/li[2]/a")
    private static WebElement nextButton;


    public LogsPage() {
        super();
    }

    public void enterPage(String text) {
        driver.get("https://www.battlemetrics.com/servers/rust/" + text + "/players?sort=-lastSeen"); }

    public static String getServerID() {
        return serverLink.getAttribute("href").substring(43);
    }

    public void printStatistics() {
        System.out.println("Server name: " + serverLink.getText());
        System.out.println("Server link: " + serverLink.getAttribute("href"));
        System.out.println("First player last seen: " + parseWebTimetoStringTime(firstWebLastSeenTime) + "\n");
    }

    public static void parseWebListsToStringLists() throws ParseException {
        Long first = parseStringTimeToLongTime(parseWebTimetoStringTime(firstWebLastSeenTime));

        listPlayerNames = webGetTextPlayerNames();
        listPlayerID = webConvertWebtoPlayerId();
        listLastSeenTimes = webConvertWebtoTime();
        listOnlineType = webGetAttributeOnlineType();

        while (1500000 > (first - parseStringTimeToLongTime(parseWebTimetoStringTime(lastWebLastSeenTime)))) {

            nextButton.click();

            listPlayerID.addAll(webConvertWebtoPlayerId());
            listPlayerNames.addAll(webGetTextPlayerNames());
            listLastSeenTimes.addAll(webConvertWebtoTime());
            listOnlineType.addAll(webGetAttributeOnlineType());
        }
    }

    private static List<String> listPlayerID;
    private static List<String> webConvertWebtoPlayerId() {
        return listWebPlayerNames.stream().map(e -> parseWebElementToPlayerId(e)).collect(Collectors.toList()); }
    public static List<String> getListPlayerID() {
        return listPlayerID;
    }

    private static List<String> listPlayerNames;
    private static List<String> webGetTextPlayerNames() {
        return listWebPlayerNames.stream().map(e -> e.getText()).collect(Collectors.toList()); }
    public static List<String> getListPlayerNames() {
        return listPlayerNames;
    }

    private static List<String> listLastSeenTimes;
    private static List<String> webConvertWebtoTime() {
        return listWebLastSeenTimes.stream().map(e -> parseWebTimetoStringTime(e)).collect(Collectors.toList()); }
    public static List<String> getListLastSeenTimes() {
        return listLastSeenTimes;
    }

    private static List<String> listOnlineType;
    private static List<String> webGetAttributeOnlineType() {
        return listWebOnlineType.stream().map(e -> e.getAttribute("class")).collect(Collectors.toList()); }
    public static List<String> getListOnlineType() {
        return listOnlineType;
    }

}



