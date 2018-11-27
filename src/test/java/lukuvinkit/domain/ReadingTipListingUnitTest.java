package lukuvinkit.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class ReadingTipListingUnitTest {
    
    Comment comment1;
    Comment comment2;
    Comment comment3;
    Tag tag1;
    Tag tag2;
    ReadingTip tip;
    ArrayList<Comment> comments;
    ArrayList<Tag> tags;
    ReadingTip readingTipRead;
    ReadingTip readingTipNotRead;
    
    public ReadingTipListingUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {

    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
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
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void constructorWorks() {
        ReadingTipListingUnit unit = new ReadingTipListingUnit(readingTipRead, comments, tags);
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
