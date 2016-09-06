package service.interfaces;

import model.LogbookEntry;

public interface ILogbookEntryService {
    void addLogbookEntry(int userId, LogbookEntry entry);
    void updateLogbookEntry(int userId, LogbookEntry entry);
    void deleteLogbookEntry(int entryId);
}
