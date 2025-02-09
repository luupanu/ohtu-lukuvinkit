package lukuvinkit.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ReadingTipListingUnitTest {

    private Tag tag1;
    private Tag tag2;
    private ArrayList<Comment> comments;
    private ArrayList<Tag> tags;
    private ReadingTip readingTipRead;
    private ReadingTip readingTipNotRead;

    @Before
    public void setUp() {
        Comment comment1 = new Comment(1, "comment1", 2);
        Comment comment2 = new Comment(2, "comment2", 1);
        Comment comment3 = new Comment(2, "comment3", 1);
        comments = new ArrayList();
        comments.add(comment1);
        comments.add(comment2);
        comments.add(comment3);

        tag1 = new Tag(1, "Tag1");
        tag2 = new Tag(2, "Tag2");
        tags = new ArrayList();
        tags.add(tag1);
        tags.add(tag2);

        readingTipRead = new ReadingTip(1, "Tip 1 for testing", "https://yle.fi/", "Just testing here", "", "", "",
                true);
        readingTipNotRead = new ReadingTip(1, "Tip 2 for testing", "https://hs.fi/", "Test test", "", "", "", false);

    }

    @Test
    public void comparableWorks() {
        ReadingTipListingUnit unit1 = new ReadingTipListingUnit(readingTipRead, comments, tags);
        ReadingTipListingUnit unit2 = new ReadingTipListingUnit(readingTipNotRead, comments, tags);
        int result1 = unit1.compareTo(unit2);
        int result2 = unit2.compareTo(unit1);
        assertEquals(result1, 1);
        assertEquals(result2, -1);
    }

}
