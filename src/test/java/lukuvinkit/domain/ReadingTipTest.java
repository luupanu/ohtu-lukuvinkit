package lukuvinkit.domain;

import static org.junit.Assert.assertEquals;

import lukuvinkit.domain.ReadingTip;

import org.junit.Before;
import org.junit.Test;

public class ReadingTipTest {

    private ReadingTip readingTipTestConstructor1;

    @Before
    public void setUp() {
        readingTipTestConstructor1 = new ReadingTip(1, "Tip 1 for testing", "https://yle.fi/", "Just testing here", "",
                "", "", false);
    }

    @Test
    public void settersWorkLikeShould() {
        readingTipTestConstructor1.setId(2);
        assertEquals(readingTipTestConstructor1.getId(), 2);
        readingTipTestConstructor1.setTitle("New title");
        assertEquals(readingTipTestConstructor1.getTitle(), "New title");
        readingTipTestConstructor1.setUrl("https://google.com/");
        assertEquals(readingTipTestConstructor1.getUrl(), "https://google.com/");
        readingTipTestConstructor1.setDescription("New description");
        assertEquals(readingTipTestConstructor1.getDescription(), "New description");
        readingTipTestConstructor1.setRead(true);
        assertEquals(readingTipTestConstructor1.isRead(), true);

    }

}
