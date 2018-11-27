package lukuvinkit.domain;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TagTest {
    
    private Tag tagConstrcutor1;
    private Tag tagConstrcutor2;
    
    public TagTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void constructor1Works() {
        tagConstrcutor1 = new Tag(1, "tag1");
        assertEquals(tagConstrcutor1.getId(), 1);
        assertEquals(tagConstrcutor1.getTagDescription(), "tag1");
    }
    
    @Test
    public void constructor2Works() {
        tagConstrcutor2 = new Tag("tag2");
        assertEquals(tagConstrcutor2.getTagDescription(), "tag2");
    }
    
    @Test
    public void settersWorkLikeShould() {
        tagConstrcutor1 = new Tag(1, "tag1");
        tagConstrcutor1.setId(2);
        assertEquals(tagConstrcutor1.getId(), 2);
        tagConstrcutor1.setTagDescription("New tag");
        assertEquals(tagConstrcutor1.getTagDescription(), "New tag");
    }
}
