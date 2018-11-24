
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
    
    public void initializeDatabase() throws SQLException {
        Connection connection = this.getConnection();
        
        // Check if database table is already created.
        PreparedStatement statementTableCheck = connection.prepareStatement(
            "select count(*) from sqlite_master"
        );
        ResultSet result = statementTableCheck.executeQuery();
        result.next();
        int tableExists = result.getInt(1);
        result.close();
        statementTableCheck.close();

        // Create database tables if it is not yet created.
        if (tableExists == 0) {
            PreparedStatement statementCreateTable = connection.prepareStatement(
                "CREATE TABLE ReadingTip(id INTEGER PRIMARY KEY, title TEXT, url TEXT, description TEXT, read BOOLEAN)"
            );
            statementCreateTable.executeUpdate();
            
            statementCreateTable = connection.prepareStatement(
                "CREATE TABLE Tag(id INTEGER PRIMARY KEY, tagDescription TEXT UNIQUE)"
            );
            statementCreateTable.executeUpdate();
            
            statementCreateTable = connection.prepareStatement(
                "CREATE TABLE ReadingTipTag(readingtip_id INTEGER, tag_id INTEGER, FOREIGN KEY(readingtip_id) REFERENCES ReadingTip(id), FOREIGN KEY(tag_id) REFERENCES Tag(id))"
            );
            statementCreateTable.executeUpdate();
            
            statementCreateTable = connection.prepareStatement(
                "CREATE TABLE Comment(id INTEGER PRIMARY KEY, description TEXT, readingtip_id INTEGER, FOREIGN KEY(readingtip_id) REFERENCES ReadingTip(id))"
            );
            statementCreateTable.executeUpdate();
            
            statementCreateTable.close();
        }

        connection.close();
    }
    
}
