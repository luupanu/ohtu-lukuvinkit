
package lukuvinkit.database;

import lukuvinkit.domain.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    
    public ArrayList<Comment> findAllForReadingTip(int readingtip_id) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM Comment WHERE readingtip_id = ?"
        );
        statement.setInt(1, readingtip_id);

        ResultSet result = statement.executeQuery();

        ArrayList<Comment> allCommentsForReadingTip = new ArrayList<>();

        while (result.next()) {
            allCommentsForReadingTip.add(new Comment(
                result.getInt("id"),
                result.getString("description"),
                result.getInt("readingtip_id")
            ));
        }

        result.close();
        statement.close();
        connection.close();

        return allCommentsForReadingTip;
    }
    
    public void save(Comment comment) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO Comment(description, readingtip_id) values (?, ?)"
        );

        statement.setString(1, comment.getDescription());
        statement.setInt(2, comment.getReadingtip_id());

        statement.executeUpdate();

        statement.close();
        connection.close();
    }    
    
}
