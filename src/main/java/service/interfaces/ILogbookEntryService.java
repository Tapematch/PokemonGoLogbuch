package service.interfaces;

import model.LogbookEntry;

import java.sql.SQLException;
import java.util.List;

public interface ILogbookEntryService {
    List<LogbookEntry> getLogbookEntriesByUserId(int userId) throws SQLException, ReflectiveOperationException;
    void addLogbookEntry(LogbookEntry entry) throws SQLException, ReflectiveOperationException;
    void updateLogbookEntry(LogbookEntry entry) throws SQLException, ReflectiveOperationException;
    void deleteLogbookEntry(int entryId) throws SQLException, ReflectiveOperationException;
}
