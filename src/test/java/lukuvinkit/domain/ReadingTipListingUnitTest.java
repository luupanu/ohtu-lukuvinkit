package lukuvinkit.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadingTipListingUnitTest {
    
    private Comment comment1;
    private Comment comment2;
    private Comment comment3;
    private Tag tag1;
    private Tag tag2;
    private ReadingTip tip;
    private ArrayList<Comment> comments;
    private ArrayList<Tag> tags;
    private ReadingTip readingTipRead;
    private ReadingTip readingTipNotRead;
    
    @Before
    public void setUp() {
        comment1 = new Comment(1, "comment1", 2);
        comment2 = new Comment(2, "comment2", 1);
        comment3 = new Comment(2, "comment3", 1);
        comments = new ArrayList();
        comments.add(comment1);
        comments.add(comment2);
        comments.add(comment3);
        tag1 = new Tag(1, "Tag1");
        tag1 = new Tag(2, "Tag2");
        tags = new ArrayList();
        tags.add(tag1);
        tags.add(tag2);
        readingTipRead = new ReadingTip(1, "Tip 1 for tesing", "https://yle.fi/", "Just testing here", true);
        readingTipNotRead = new ReadingTip(1, "Tip 2 for tesing", "https://hs.fi/", "Test test", false);
        
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
