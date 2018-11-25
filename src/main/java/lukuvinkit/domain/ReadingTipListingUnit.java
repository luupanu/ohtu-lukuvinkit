
package lukuvinkit.domain;

import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
public class ReadingTipListingUnit {

    public ReadingTip readingTip;
    public ArrayList<Comment> comments;
    public ArrayList<Tag> tags;

    public ReadingTipListingUnit() {
        // default constructor for Spring
    }

    public ReadingTipListingUnit(ReadingTip readingTip, ArrayList<Comment> comments, ArrayList<Tag> tags) {
        this.readingTip = readingTip;
        this.comments = comments;
        this.tags = tags;
    }

}
