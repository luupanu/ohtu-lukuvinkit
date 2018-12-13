package lukuvinkit.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import lukuvinkit.domain.ReadingTip;
import lukuvinkit.domain.Tag;

import org.springframework.stereotype.Component;

@Component
public class ReadingTipDao {
    
    private Database database;
    
    public ReadingTipDao() {
        // default constructor for Spring
    }
    
    public ReadingTipDao(Database database) {
        this.database = database;
    }
    
    /**
     * Lists all reading tips in the database.
     * @throws SQLException 
     */
    public ArrayList<ReadingTip> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM ReadingTip"
        );
        ResultSet result = statement.executeQuery();
        
        ArrayList<ReadingTip> allReadingTips = extractReadingTips(result);

        result.close();
        statement.close();
        connection.close();
        
        return allReadingTips;
    }
    
    private ArrayList<ReadingTip> extractReadingTips(ResultSet result) throws SQLException {
        ArrayList<ReadingTip> allReadingTips = new ArrayList<>();

        while (result.next()) {
            allReadingTips.add(new ReadingTip(
                result.getInt("id"),
                result.getString("title"),
                result.getString("type"),
                result.getString("url"),
                result.getString("author"),
                result.getString("isbn"),
                result.getString("description"),
                result.getBoolean("read"),
                result.getInt("priority_read"),
                result.getInt("priority_unread")
            ));
        }
        
        return allReadingTips;
    }

    /**
     * Saves one reading tip into the database.
     * @param tip The new reading tip to be saved, id will be ignored. 
     * @return The reading tip which was just saved, with the id that was given
     * for it in the database.
     * @throws SQLException
     */
    public ReadingTip save(ReadingTip tip) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO ReadingTip"
            + "("
                + "title, type, url, author, isbn, description, "
                + "read, priority_read, priority_unread"
            + ") "
            + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );
        setAttributesOfReadingTipInsertQuery(statement, tip);
        statement.executeUpdate();
        
        tip.setId(Database.lastInsertRowid(connection));
        
        statement.close();
        connection.close();
        
        return tip;
    }
    
    private void setAttributesOfReadingTipInsertQuery(PreparedStatement statement, ReadingTip tip) throws SQLException {
        statement.setString(1, tip.getTitle());
        statement.setString(2, tip.getType());
        statement.setString(3, tip.getUrl());
        statement.setString(4, tip.getAuthor());
        statement.setString(5, tip.getIsbn());
        statement.setString(6, tip.getDescription());
        statement.setBoolean(7, tip.isRead());
        statement.setInt(8,tip.getPriorityRead());
        statement.setInt(9, tip.getPriorityUnread());      
    }
 
    /**
     * Toggles the read value of a reading tip in the database.
     * @param readingTipId The reading tip which read value will be toggled.
     * @throws SQLException 
     */
    public void toggleRead(int readingTipId) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "UPDATE ReadingTip SET read=CASE read WHEN true THEN false ELSE true END WHERE id=?"
        );
        statement.setInt(1, readingTipId);
        statement.executeUpdate();

        statement.close();
        connection.close();
    }
    
    /**
     * Binds tags which are already in the databese to a reading tip which is already in the databese.
     * @param tip The reading tip with ID.
     * @param tags The tags of the reading tip with IDs.
     * @throws SQLException 
     */
    public void bindTagsToReadingTip(ReadingTip tip, ArrayList<Tag> tags) throws SQLException {        
        Connection connection = database.getConnection();
        
        for (Tag tag : tags) {
            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO ReadingTipTag(readingtip_id, tag_id) values (?, ?)"
            );
            statement.setInt(1, tip.getId());
            statement.setInt(2, tag.getId());
            statement.executeUpdate();
            statement.close();
        }

        connection.close();
    }
    
    /**
     * Searches for a reading tip in the database.
     * @param readingTipId The ID of the reading tip which is being searched.
     * @return The reading tip that was searched or null if not found.
     * @throws SQLException 
     */
    public ReadingTip findOne(int readingTipId) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM ReadingTip WHERE id = ?"
        );
        statement.setInt(1, readingTipId);
        ResultSet result = statement.executeQuery();
        
        ReadingTip tip = extractReadingTip(result);

        result.close();
        statement.close();
        connection.close();

        return tip;
    }
    
    private ReadingTip extractReadingTip(ResultSet result) throws SQLException {
        ReadingTip tip = null;
        
        if (result.next()) {
            tip = new ReadingTip(
                result.getInt("id"),
                result.getString("title"),
                result.getString("type"),
                result.getString("url"),
                result.getString("author"),
                result.getString("isbn"),
                result.getString("description"),
                result.getBoolean("read"),
                result.getInt("priority_read"),
                result.getInt("priority_unread")
            );
        } 
        
        return tip;
    }
    
    /**
     * Updates the attributes of a reading tip in the database.
     * @param tip The reading tip which attributes are being updated. Must have ID.
     * @throws SQLException 
     */
    public void update(ReadingTip tip) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "UPDATE ReadingTip "
                + "SET title = ?, type = ?, url = ?, author = ?, isbn = ?, "
                + "description = ?, read = ?, priority_read = ?, priority_unread = ? "
                + "WHERE id = ?"
        );
        setAttributesOfReadingTipUpdateQuery(statement, tip);
        statement.executeUpdate();

        statement.close();
        connection.close();
    }
    
    private void setAttributesOfReadingTipUpdateQuery(PreparedStatement statement, ReadingTip tip) throws SQLException {
        statement.setString(1, tip.getTitle());
        statement.setString(2, tip.getType());
        statement.setString(3, tip.getUrl());
        statement.setString(4, tip.getAuthor());
        statement.setString(5, tip.getIsbn());
        statement.setString(6, tip.getDescription());
        statement.setBoolean(7, tip.isRead());
        statement.setInt(8, tip.getPriorityRead());
        statement.setInt(9, tip.getPriorityUnread());
        statement.setInt(10, tip.getId());        
    }
    
    /**
     * Finds the maximum value of the attribute priority_read in the database's reading tip table.
     * @return The maximum value of the attribute priority_read, 0 if there is no rows in the table, -1 if error occured.
     * @throws SQLException 
     */
    public int findMaxReadPriority() throws SQLException {        
        return findMaxValue("priority_read");
    }
    
    /**
     * Finds the maximum value of the attribute priority_unread in the database's reading tip table.
     * @return The maximum value of the attribute priority_unread, 0 if there is no rows in the table, -1 if error occured.
     * @throws SQLException 
     */
    public int findMaxUnreadPriority() throws SQLException {        
        return findMaxValue("priority_unread");
    }
    
    /**
     * Finds the maximum value of a column in the database's reading tip table.
     * @param columnName  The coulmn name which maximum value is wanted. User input not allowed in this parameter!
     * @return The maximum value of the column, 0 if there is no rows in the table, -1 if error occured.
     * @throws SQLException 
     */
    private int findMaxValue(String columnName) throws SQLException {        
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT MAX(" + columnName + ") FROM ReadingTip"
        );
        ResultSet result = statement.executeQuery();
        
        int priority;
        if (result.next()) {
            priority = result.getInt(1);
        } else {
            priority = -1;
        }

        result.close();
        statement.close();
        connection.close();

        return priority;
    }
    
}
