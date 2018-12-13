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

    /**
     * Finds all tags of a reading tip in the database.
     * @param readingTipId The ID of the reading tip whose tags are being looked for.
     * @return A list of tags.
     * @throws SQLException Throws SQL exceptions.
     */
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

        ArrayList<Tag> allTagsForReadingTip = extractTags(result);

        result.close();
        statement.close();
        connection.close();

        return allTagsForReadingTip;
    }
    
    private ArrayList<Tag> extractTags(ResultSet result) throws SQLException {
        ArrayList<Tag> allTagsForReadingTip = new ArrayList<>();

        while (result.next()) {
            allTagsForReadingTip.add(new Tag(
                result.getInt("id"),
                result.getString("tagDescription")
            ));
        }
        
        return allTagsForReadingTip;
    }
    

    /**
     * Saves new tag to the database.
     *      Does nothing if a tag with the same tagDescription is already in the database.
     * @param tag The tag to be saved in the database.
     * @return The tag which was just saved, with the id that was given
     *      for it in the database. If the tag was already in the database, then
     *      the tag is returned with the id that was already in the database.
     * @throws SQLException Throws SQL exceptions.
     */
    public Tag save(Tag tag) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO Tag(tagDescription) values (?) ON CONFLICT (tagDescription) DO NOTHING"
        );
        statement.setString(1, tag.getTagDescription());
        int rowsInserted = statement.executeUpdate();
        
        if (rowsInserted == 0) {  // If the tag was already in the database
            tag.setId(idOfTag(tag.getTagDescription()));
        } else {                  // If new tag was inserted
            tag.setId(Database.lastInsertRowid(connection));         
        }
        
        statement.close();
        connection.close();
        
        return tag;
    }
    
    private int idOfTag(String tagDescription) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT id FROM Tag WHERE tagDescription = ?"
        );
        statement.setString(1, tagDescription);
        ResultSet result = statement.executeQuery();
        
        int id = result.getInt(1);
        
        result.close(); 
        statement.close();
        connection.close();
        
        return id;
    }

}
