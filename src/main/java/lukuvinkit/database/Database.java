package lukuvinkit.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Component;

@Component
public class Database {
    
    private String address;
    
    public Database() {
        // default constructor for Spring
    }
    
    public Database(String address) {
        this.address = address;
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.address);
    }
    
    public void initializeDatabaseIfUninitialized() throws SQLException {
        if (countTables() == 0) {
            createTables();
        }
    }
    
    private int countTables() throws SQLException {
        Connection connection = this.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "select count(*) from sqlite_master"
        );
        ResultSet result = statement.executeQuery();
        
        result.next();
        int tableCount = result.getInt(1);
        
        result.close();
        statement.close();
        connection.close();
        
        return tableCount;
    }
    
    private void createTables() throws SQLException {
        Connection connection = this.getConnection();

        PreparedStatement statement = connection.prepareStatement(
            "CREATE TABLE ReadingTip"
            + "("
                + "id INTEGER PRIMARY KEY, "
                + "title TEXT, "
                + "type TEXT, "
                + "url TEXT, "
                + "author TEXT, "
                + "isbn TEXT, "
                + "description TEXT, "
                + "read BOOLEAN, "
                + "priority_read INTEGER, "
                + "priority_unread INTEGER"
            + ")"
        );
        statement.executeUpdate();

        statement = connection.prepareStatement(
            "CREATE TABLE Tag"
            + "("
                + "id INTEGER PRIMARY KEY, "
                + "tagDescription TEXT UNIQUE"
            + ")"
        );
        statement.executeUpdate();

        statement = connection.prepareStatement(
            "CREATE TABLE ReadingTipTag"
            + "("
                + "readingtip_id INTEGER, "
                + "tag_id INTEGER, "
                + "FOREIGN KEY(readingtip_id) REFERENCES ReadingTip(id), "
                + "FOREIGN KEY(tag_id) REFERENCES Tag(id)"
            + ")"
        );
        statement.executeUpdate();

        statement = connection.prepareStatement(
            "CREATE TABLE Comment"
            + "("
                + "id INTEGER PRIMARY KEY, "
                + "description TEXT, "
                + "readingtip_id INTEGER, "
                + "FOREIGN KEY(readingtip_id) REFERENCES ReadingTip(id)"
            + ")"
        );
        statement.executeUpdate();

        statement.close();
        connection.close();        
    }
    
    public void clearDatabase() throws SQLException {
        Connection connection = this.getConnection();

        // Delete tables if tables exist.
        if (countTables() != 0) {
            connection.prepareStatement("DROP TABLE ReadingTip").executeUpdate();
            connection.prepareStatement("DROP TABLE Tag").executeUpdate();
            connection.prepareStatement("DROP TABLE ReadingTipTag").executeUpdate();
            connection.prepareStatement("DROP TABLE Comment").executeUpdate();
        }

        connection.close();
    }
    
    protected static int lastInsertRowid(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select last_insert_rowid()");
        ResultSet result = statement.executeQuery();
        
        int id;

        if (result.next()) {
            id = result.getInt(1);
        } else {
            throw new SQLException("Couldn't find row id of last insert.");
        }
        
        result.close();
        statement.close();
        
        return id;
    }
    
}
