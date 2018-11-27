package lukuvinkit.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import lukuvinkit.domain.Tag;

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

    /*public ArrayList<Tag> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Tag");

        ResultSet result = statement.executeQuery();

        ArrayList<Tag> allTags = new ArrayList<>();

        while (result.next()) {
            allTags.add(new Tag(result.getInt("id"), result.getString("tagDescription")));
        }

        result.close();
        statement.close();
        connection.close();

        return allTags;
    }*/

    public ArrayList<Tag> findAllForReadingTip(int readingTipId) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT Tag.id, Tag.tagDescription "
            + "FROM ReadingTipTag, Tag "
            + "WHERE ReadingTipTag.readingtip_id = ? "
            + "AND ReadingTipTag.tag_id = Tag.id"
        );
        statement.setInt(1, readingTipId);

        ResultSet result = statement.executeQuery();

        ArrayList<Tag> allTagsForReadingTip = new ArrayList<>();

        while (result.next()) {
            allTagsForReadingTip.add(new Tag(
                result.getInt("id"),
                result.getString("tagDescription")
            ));
        }

        result.close();
        statement.close();
        connection.close();

        return allTagsForReadingTip;
    }

    public Tag save(Tag tag) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement;
        
        statement = connection.prepareStatement(
            "INSERT INTO Tag(tagDescription) values (?) ON CONFLICT (tagDescription) DO NOTHING"
        );
        statement.setString(1, tag.getTagDescription());
        int rowsInserted = statement.executeUpdate();
        
        int id;
        ResultSet result;
        if (rowsInserted == 0) {  // If the tag was already in the database
            statement = connection.prepareStatement("SELECT id FROM Tag WHERE tagDescription = ?");
            statement.setString(1, tag.getTagDescription());
            result = statement.executeQuery();
            id = result.getInt(1);
        } else {  // If new tag was inserted
            statement = connection.prepareStatement("select last_insert_rowid()");
            result = statement.executeQuery();
            result.next();
            id = result.getInt(1);           
        }
        
        result.close(); 
        statement.close();
        connection.close();
        
        tag.setId(id);
        return tag;
    }

}
