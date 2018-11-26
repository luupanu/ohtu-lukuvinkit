
package lukuvinkit.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

@Component
public class Comment {

    private int id;

    @NotEmpty
    private String commentDescription;

    private int readingTipId;

    public Comment() {
        // default constructor for Spring
    }

    public Comment(String description, int readingTipId) {
        this.commentDescription = description;
        this.readingTipId = readingTipId;
    }

    public Comment(int id, String description, int readingTipId) {
        this.id = id;
        this.commentDescription = description;
        this.readingTipId = readingTipId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommentDescription() {
        return commentDescription;
    }

    public void setCommentDescription(String description) {
        this.commentDescription = description;
    }

    public int getReadingTipId() {
        return readingTipId;
    }

    public void setReadingTipId(int readingTipId) {
        this.readingTipId = readingTipId;
    }

}
