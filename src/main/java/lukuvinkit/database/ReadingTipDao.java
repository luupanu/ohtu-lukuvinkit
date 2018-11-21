
package lukuvinkit.database;

import lukuvinkit.domain.ReadingTip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    
    public ArrayList<ReadingTip> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM ReadingTip"
        );

        ResultSet result = statement.executeQuery();

        ArrayList<ReadingTip> allReadingTips = new ArrayList<>();

        while (result.next()) {
            allReadingTips.add(new ReadingTip(
                result.getInt("id"),
                result.getString("title"),
                result.getString("url"),
                result.getString("description"),
                result.getBoolean("read")
            ));
        }

        result.close();
        statement.close();
        connection.close();

        return allReadingTips;
    }
    
    public ArrayList<ReadingTip> findAllForTag(int tag_id) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT ReadingTip.id, ReadingTip.title, ReadingTip.url, ReadingTip.description, ReadingTip.read FROM ReadingTip, ReadingTipTag "
                    + "WHERE ReadingTipTag.tag_id = ? AND ReadingTipTag.readingtip_id = ReadingTip.id"
        );
        statement.setInt(1, tag_id);

        ResultSet result = statement.executeQuery();

        ArrayList<ReadingTip> allReadingTipsForTag = new ArrayList<>();

        while (result.next()) {
            allReadingTipsForTag.add(new ReadingTip(
                result.getInt("id"),
                result.getString("title"),
                result.getString("url"),
                result.getString("description"),
                result.getBoolean("read")
            ));
        }

        result.close();
        statement.close();
        connection.close();

        return allReadingTipsForTag;
    }
    
    public void save(ReadingTip tip) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO ReadingTip(title, url, description, read) values (?, ?, ?, ?)"
        );

        statement.setString(1, tip.getTitle());
        statement.setString(2, tip.getUrl());
        statement.setString(3, tip.getDescription());
        statement.setBoolean(4, tip.isRead());

        statement.executeUpdate();

        statement.close();
        connection.close();
    }
    
    public void update(ReadingTip tip) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "UPDATE ReadingTip SET title = ?, url = ?, description = ?, read = ? WHERE id = ?"
        );
        
        statement.setString(1, tip.getTitle());
        statement.setString(2, tip.getUrl());
        statement.setString(3, tip.getDescription());
        statement.setBoolean(4, tip.isRead());
        
        statement.executeUpdate();

        statement.close();
        connection.close();
    }
    
}
