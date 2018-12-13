package lukuvinkit.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TagTest {
    
    private Tag tagConstructor1;

    @Test
    public void constructor1Works() {
        tagConstructor1 = new Tag(1, "tag1");
        assertEquals(tagConstructor1.getId(), 1);
        assertEquals(tagConstructor1.getTagDescription(), "tag1");
    }
    
    @Test
    public void constructor2Works() {
        Tag tagConstructor2 = new Tag("tag2");
        assertEquals(tagConstructor2.getTagDescription(), "tag2");
    }
    
    @Test
    public void settersWorkLikeShould() {
        tagConstructor1 = new Tag(1, "tag1");
        tagConstructor1.setId(2);
        assertEquals(tagConstructor1.getId(), 2);
        tagConstructor1.setTagDescription("New tag");
        assertEquals(tagConstructor1.getTagDescription(), "New tag");
    }
}
