package service;

import model.LogbookEntry;
import service.interfaces.ILogbookEntryService;

import java.util.List;

public class LogbookEntryService implements ILogbookEntryService {
    @Override
    public List<LogbookEntry> getLogbookEntriesByUserId(int userId) {
        return null;
    }

    @Override
    public void addLogbookEntry(int userId, LogbookEntry entry) {

    }

    @Override
    public void updateLogbookEntry(int userId, LogbookEntry entry) {

    }

    @Override
    public void deleteLogbookEntry(int entryId) {

    }
}
