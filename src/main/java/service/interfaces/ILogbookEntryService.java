package service.interfaces;

import model.LogbookEntry;

import java.util.List;

public interface ILogbookEntryService {
    List<LogbookEntry> getLogbookEntriesByUserId(int userId);

    void addLogbookEntry(int userId, LogbookEntry entry);
    void updateLogbookEntry(int userId, LogbookEntry entry);
    void deleteLogbookEntry(int entryId);
}
