package battlemetrics_rust.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static battlemetrics_rust.pages.LogsPage.*;

public class LogsDAO {
    private static List<String> listPlayerID = getListPlayerID();
    private static List<String> listLastSeenTimes = getListLastSeenTimes();
    private static List<String> listPlayerNames = getListPlayerNames();
    private static List<String> listOnlineType = getListOnlineType();
    private static List<String> listRecordID = new ArrayList<String>();
    private static List<Logs> logs = new ArrayList<Logs>();

    public static List<String> createListRecordID() throws ParseException {

        for (int i = 0; i < listPlayerID.toArray().length; i++) {
            String string = (listPlayerID.get(i) + "_"
                    + String.valueOf((parseStringTimeToLongTime(listLastSeenTimes.get(i)))));
            listRecordID.add(string); }
        return listRecordID; }

    public static List<Logs> createListLogs() throws ParseException {
        createListRecordID();

        for (int i = 0; i < listRecordID.toArray().length; i++) {
            Logs LL = new Logs(listRecordID.get(i), listPlayerID.get(i),
                    listPlayerNames.get(i), listLastSeenTimes.get(i), listOnlineType.get(i));
            logs.add(LL); }
        return logs; }
}
