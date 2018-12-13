package lukuvinkit.database;

import lukuvinkit.domain.Comment;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Component;

@Component
public class CommentDao {
    
    private Database database;
    
    public CommentDao() {
        // default constructor for Spring
    }
    
    public CommentDao(Database database) {
        this.database = database;
    }
    
    /**
     * Finds all comments of a reading tip in the database.
     * @param readingTipId The ID of the reading tip whose comments are being looked for.
     * @return A list of comments.
     * @throws SQLException 
     */
    public ArrayList<Comment> findAllForReadingTip(int readingTipId) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM Comment WHERE readingtip_id = ?"
        );
        statement.setInt(1, readingTipId);
        ResultSet result = statement.executeQuery();

        ArrayList<Comment> allCommentsForReadingTip = extractComments(result);

        result.close();
        statement.close();
        connection.close();

        return allCommentsForReadingTip;
    }
    
    private ArrayList<Comment> extractComments(ResultSet result) throws SQLException {
        ArrayList<Comment> allCommentsForReadingTip = new ArrayList<>();

        while (result.next()) {
            allCommentsForReadingTip.add(new Comment(
                result.getInt("id"),
                result.getString("description"),
                result.getInt("readingtip_id")
            ));
        }
        
        return allCommentsForReadingTip;
    }
    
    /**
     * Saves one comment into the database.
     * @param comment The new comments to be saved, must have readingtip_id set.
     * @throws SQLException
     */
    public void save(Comment comment) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO Comment(description, readingtip_id) values (?, ?)"
        );
        statement.setString(1, comment.getCommentDescription());
        statement.setInt(2, comment.getReadingTipId());
        statement.executeUpdate();

        statement.close();
        connection.close();
    }    
    
}
