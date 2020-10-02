package battlemetrics_rust.model;

public class Logs {
    private String recordID, playerID, playerName, lastSeenTime, onlineType;

    public Logs(String recordID, String playerID, String playerName, String lastSeenTime, String onlineType) {
        this.recordID = recordID;
        this.playerID = playerID;
        this.playerName = playerName;
        this.lastSeenTime = lastSeenTime;
        this.onlineType = onlineType;
    }

    public String getRecordID() {
        return recordID;
    }
    public String getPlayerID() {
        return playerID;
    }
    public String getPlayerName() {
        return playerName;
    }
    public String getLastSeenTime() {
        return lastSeenTime;
    }
    public String getOnlineType() {
        return onlineType;
    }

}
