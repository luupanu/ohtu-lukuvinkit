
package lukuvinkit.service;

import java.util.ArrayList;
import java.sql.SQLException;
import junit.framework.Assert;
import lukuvinkit.database.CommentDao;
import lukuvinkit.database.Database;
import lukuvinkit.database.ReadingTipDao;
import lukuvinkit.database.TagDao;
import lukuvinkit.domain.ReadingTip;
import lukuvinkit.domain.Tag;
import lukuvinkit.domain.ReadingTipListingUnit;

import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

public class ReadingTipServiceTest {
    
    private static Database database;
    private static ReadingTipService service;
    
    @BeforeClass
    public static void setUpClass() throws SQLException {
        database = new Database("jdbc:sqlite:testdata.db");
        
        ReadingTipDao readingTipDao = new ReadingTipDao(database);
        CommentDao commentDao = new CommentDao(database);
        TagDao tagDao = new TagDao(database);
        
        service = new ReadingTipService(readingTipDao, commentDao, tagDao);
    }
        
    @Before
    public void setUp() throws SQLException {
        database.clearDatabase();
        database.initializeDatabaseIfUninitialized();
    }
    
    //@Test
    public void oneReadingTipCanBeAddedAndListed() throws SQLException {
        service.saveNewReadingTip(
                new ReadingTip("Helsingin Sanomat", "Link", "http://hs.fi", "", 
                        "", "Hesarin nettisivut", false), 
                new Tag("nettisivu"));
        
        assertEquals(1, service.generateReadingTipListing().size());
        assertEquals("Helsingin Sanomat", service.generateReadingTipListing().get(0).readingTip.getTitle());
    }
    
    @Test
    public void prioritiesOfTwoTipsAreSwappedSuccesfully() throws SQLException {
        service.saveNewReadingTip(
                new ReadingTip("Helsingin Sanomat", "Link", "http://hs.fi", "", 
                        "", "Hesarin nettisivut", false), 
                new Tag("nettisivu"));
        
        service.saveNewReadingTip(
                new ReadingTip("IS", "Link", "http://is.fi", "", 
                        "", "IS nettisivut", false), 
                new Tag("nettisivu"));
        
        service.saveNewReadingTip(
                new ReadingTip("Aamulehti", "Link", "http://aamulehti.fi", "", 
                        "", "Aamulehden nettisivut", false), 
                new Tag("nettisivu"));
        
        assertEquals(3, service.generateReadingTipListing().size());
        service.swapPriorities(1, 3);
        assertEquals("Aamulehti", service.generateReadingTipListing().get(0).readingTip.getTitle());
        assertEquals(1, service.generateReadingTipListing().get(0).readingTip.getPriorityUnread());
    }
    
}
