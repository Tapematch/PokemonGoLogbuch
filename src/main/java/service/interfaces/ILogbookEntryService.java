package service.interfaces;

import model.LogbookEntry;

import java.sql.SQLException;
import java.util.List;

public interface ILogbookEntryService {
    List<LogbookEntry> getLogbookEntriesByUserId(int userId) throws SQLException, ReflectiveOperationException;
    void addLogbookEntry(int userId, LogbookEntry entry);
    void updateLogbookEntry(int userId, LogbookEntry entry);
    void deleteLogbookEntry(int entryId);
}
