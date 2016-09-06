package dto;

import model.LogbookEntry;

public class LogbookEntryPutModel {
    private int userId;
    private LogbookEntry entry;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LogbookEntry getEntry() {
        return entry;
    }

    public void setEntry(LogbookEntry entry) {
        this.entry = entry;
    }
}
