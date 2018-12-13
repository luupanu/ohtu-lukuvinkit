package lukuvinkit.domain;

import java.util.ArrayList;
import org.springframework.stereotype.Component;

/**
 * A container that holds one reading tip, the comments of that reading tip
 * and the tags associated to that reading tip.
 */
@Component
public class ReadingTipListingUnit implements Comparable<ReadingTipListingUnit> {

    public ReadingTip readingTip;
    public ArrayList<Comment> comments;
    public ArrayList<Tag> tags;

    public ReadingTipListingUnit() {
        // default constructor for Spring
    }

    public ReadingTipListingUnit(ReadingTip readingTip,
            ArrayList<Comment> comments, ArrayList<Tag> tags) {
        this.readingTip = readingTip;
        this.comments = comments;
        this.tags = tags;
    }

    @Override
    public int compareTo(ReadingTipListingUnit other) {
        int result = Boolean.compare(this.readingTip.isRead(), other.readingTip.isRead());
        
        if (result != 0) {
            return result;
        }

        if (this.readingTip.isRead()) {
            return Integer.compare(
                this.readingTip.getPriorityRead(),
                other.readingTip.getPriorityRead()
            );
        }
        return Integer.compare(
            this.readingTip.getPriorityUnread(),
            other.readingTip.getPriorityUnread()
        );
    }
    
}
