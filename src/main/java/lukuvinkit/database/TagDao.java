
package lukuvinkit.database;

import lukuvinkit.domain.Tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
public class TagDao {
    
    private Database database;
    
    public TagDao() {
        // default constructor for Spring
    }
    
    public TagDao(Database database) {
        this.database = database;
    }
    
    public ArrayList<Tag> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM Tag"
        );

        ResultSet result = statement.executeQuery();

        ArrayList<Tag> allTags = new ArrayList<>();

        while (result.next()) {
            allTags.add(new Tag(
                result.getInt("id"),
                result.getString("description")
            ));
        }

        result.close();
        statement.close();
        connection.close();

        return allTags;
    }
   
    public ArrayList<Tag> findAllForReadingTip(int readingtip_id) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT Tag.id, Tag.description FROM ReadingTipTag, Tag "
                    + "WHERE ReadingTipTag.readingtip_id = ? AND ReadingTipTag.tag_id = Tag.id"
        );
        statement.setInt(1, readingtip_id);

        ResultSet result = statement.executeQuery();

        ArrayList<Tag> allTagsForReadingTip = new ArrayList<>();

        while (result.next()) {
            allTagsForReadingTip.add(new Tag(
                result.getInt("id"),
                result.getString("description")
            ));
        }

        result.close();
        statement.close();
        connection.close();

        return allTagsForReadingTip;
    }
    
    public void save(Tag tag) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO Tag(description) values (?)"
        );

        statement.setString(1, tag.getDescription());

        statement.executeUpdate();

        statement.close();
        connection.close();
    }
        
}
