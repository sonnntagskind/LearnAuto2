package battlemetrics_rust.pages;

import battlemetrics_rust.model.Logs;
import battlemetrics_rust.utils.DateUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class LogsPage extends BasePage {

    public LogsPage() {
        super();
    }

    @FindBy(xpath = "//*[@id=\"PlayerInstancesPage\"]/div/ul/li[*]/p/a")
    private List<WebElement> listWebPlayerNames;
    @FindBy(xpath = "//*[@id=\"PlayerInstancesPage\"]/div/ul/li[*]/dl/dd[4]/time[1]")
    private List<WebElement> listWebLastSeenTimes;
    @FindBy(xpath = "//*[@id=\"PlayerInstancesPage\"]/div/ul/li[*]/dl/dd[1]/div")
    private List<WebElement> listWebOnlineType;

    @FindBy(xpath = "//*[@id=\"PlayerInstancesPage\"]/div/ul/li[1]/dl/dd[4]/time[1]")
    private WebElement firstWebLastSeenTime;
    @FindBy(xpath = "//*[@id=\"PlayerInstancesPage\"]/div/ul/li[10]/dl/dd[4]/time[1]")
    private WebElement lastWebLastSeenTime;

    @FindBy(xpath = "//ol/li[3]/a")
    private WebElement serverLink;

    @FindBy(xpath = "//*[@id=\"PlayerInstancesPage\"]/div/nav/ul/li[2]/a")
    private WebElement nextButton;

    private long intervalMs = 1500000;
    private List<Logs> logs = new ArrayList<Logs>();

    private Long parseStringTimeToLongTime(String stringTime) throws ParseException {
        return new DateUtil().parseStringTimeToLongTime(stringTime);
    }

    private Long searchLastSeenTime(WebElement xWebLastSeenTime) throws ParseException {
        return parseStringTimeToLongTime(parseWebTimetoStringTime(xWebLastSeenTime));
    }

    public void enterPage(String text) {
        driver.get("https://www.battlemetrics.com/servers/rust/" + text + "/players?sort=-lastSeen");
    }

    public void printServiceInfo() {
        System.out.println("Server name: " + serverLink.getText());
        System.out.println("Server link: " + serverLink.getAttribute("href"));
        System.out.println("First player last seen: " + parseWebTimetoStringTime(firstWebLastSeenTime) + "\n");
    }

    public List<Logs> parseWebListsToStringLists() throws ParseException {
        Long firstPlayer = searchLastSeenTime(firstWebLastSeenTime);
        Long lastPlayer = searchLastSeenTime(lastWebLastSeenTime);

        logs.addAll(createList(listWebPlayerNames, listWebLastSeenTimes, listWebOnlineType));

        while (intervalMs > (firstPlayer - lastPlayer)) {
            nextButton.click();

            lastPlayer = searchLastSeenTime(lastWebLastSeenTime);

            logs.addAll(createList(listWebPlayerNames, listWebLastSeenTimes, listWebOnlineType));
        }
        return logs;
    }

    private Logs parseLog(WebElement webPlayerName, WebElement webLastSeenTimes, WebElement webOnlineType) throws ParseException {
        String playerID = parseWebElementToPlayerId(webPlayerName);
        String playerName = webPlayerName.getText();
        String lastSeenTime = parseWebTimetoStringTime(webLastSeenTimes);
        String onlineType = webOnlineType.getAttribute("class");
        String recordID = playerID + "_" + ((parseStringTimeToLongTime(lastSeenTime)));

        return new Logs(recordID, playerID, playerName, lastSeenTime, onlineType);
    }

    private List<Logs> createList(List<WebElement> listWebPlayerNames, List<WebElement> listWebLastSeenTimes, List<WebElement> listWebOnlineType) throws ParseException {
        List<Logs> list = new ArrayList<>();
        for (int i = 0; i < listWebPlayerNames.size(); i++) {
            Logs LL = parseLog(listWebPlayerNames.get(i), listWebLastSeenTimes.get(i), listWebOnlineType.get(i));
            list.add(LL);
        }
        return list;
    }
}



