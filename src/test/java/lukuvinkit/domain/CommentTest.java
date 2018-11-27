package lukuvinkit.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CommentTest {
    
    Comment commentConstructor1;
    Comment commentConstructor2;
    
    public CommentTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        commentConstructor1 = new Comment("comment1", 1);
        commentConstructor2 = new Comment(2, "comment2", 2);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void attributesAreRightforConstructor1() {
        assertEquals(commentConstructor1.getCommentDescription(), "comment1");
        assertEquals(commentConstructor1.getReadingTipId(), 1);
    }
    
    @Test
    public void attributesAreRightforConstructor2() {
        assertEquals(commentConstructor2.getId(), 2);
        assertEquals(commentConstructor2.getCommentDescription(), "comment2");
        assertEquals(commentConstructor2.getReadingTipId(), 2);
    }
    
    @Test
    public void setersWorkLikeShould() {
        commentConstructor2.setId(3);
        assertEquals(commentConstructor2.getId(), 3);
        commentConstructor2.setCommentDescription("New comment");
        assertEquals(commentConstructor2.getCommentDescription(), "New comment");
        commentConstructor2.setReadingTipId(3);
        assertEquals(commentConstructor2.getReadingTipId(), 3);
    }
}