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
        Connection connection = this.getConnection();
        
        // Check if database tables are already created.
        PreparedStatement statementTableCheck = connection.prepareStatement(
            "select count(*) from sqlite_master"
        );
        ResultSet result = statementTableCheck.executeQuery();
        result.next();
        int tableExists = result.getInt(1);
        result.close();
        statementTableCheck.close();

        // Create database tables if they are not yet created.
        if (tableExists == 0) {
            PreparedStatement statementCreateTable = connection.prepareStatement(
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
            statementCreateTable.executeUpdate();
            
            statementCreateTable = connection.prepareStatement(
                "CREATE TABLE Tag"
                + "("
                    + "id INTEGER PRIMARY KEY, "
                    + "tagDescription TEXT UNIQUE"
                + ")"
            );
            statementCreateTable.executeUpdate();
            
            statementCreateTable = connection.prepareStatement(
                "CREATE TABLE ReadingTipTag"
                + "("
                    + "readingtip_id INTEGER, "
                    + "tag_id INTEGER, "
                    + "FOREIGN KEY(readingtip_id) REFERENCES ReadingTip(id), "
                    + "FOREIGN KEY(tag_id) REFERENCES Tag(id)"
                + ")"
            );
            statementCreateTable.executeUpdate();
            
            statementCreateTable = connection.prepareStatement(
                "CREATE TABLE Comment"
                + "("
                    + "id INTEGER PRIMARY KEY, "
                    + "description TEXT, "
                    + "readingtip_id INTEGER, "
                    + "FOREIGN KEY(readingtip_id) REFERENCES ReadingTip(id)"
                + ")"
            );
            statementCreateTable.executeUpdate();
            
            statementCreateTable.close();
        }

        connection.close();
    }
    
    public void clearDatabase() throws SQLException {
        Connection connection = this.getConnection();
        
        // Check if database tables exist.
        PreparedStatement statementTableCheck = connection.prepareStatement(
            "select count(*) from sqlite_master"
        );
        ResultSet result = statementTableCheck.executeQuery();
        result.next();
        int tableExists = result.getInt(1);
        result.close();
        statementTableCheck.close();

        // Delete tables if tables exist.
        if (tableExists != 0) {
            connection.prepareStatement("DROP TABLE ReadingTip").executeUpdate();
            connection.prepareStatement("DROP TABLE Tag").executeUpdate();
            connection.prepareStatement("DROP TABLE ReadingTipTag").executeUpdate();
            connection.prepareStatement("DROP TABLE Comment").executeUpdate();
        }

        connection.close();
    }
    
}
