package lukuvinkit.domain;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CommentTest {
    
    private Comment commentConstructor1;
    private Comment commentConstructor2;
    
    @Before
    public void setUp() {
        commentConstructor1 = new Comment("comment1", 1);
        commentConstructor2 = new Comment(2, "comment2", 2);
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
