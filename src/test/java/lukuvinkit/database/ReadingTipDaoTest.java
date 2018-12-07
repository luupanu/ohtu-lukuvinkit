package lukuvinkit.database;

import java.sql.SQLException;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author yllapitaja
 */
public class ReadingTipDaoTest {
    
    public ReadingTipDaoTest() {
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
    
    /* I used this method to test MaxPrioritySearch -> it can be used to test dao if needed
    @Test
    public void testDao() throws SQLException {
        Database database = new Database("jdbc:sqlite:data.db");
        database.initializeDatabase();
        ReadingTipDao readingTipDao = new ReadingTipDao(database);
        int maxPriorityId = readingTipDao.findMaxReadPriority();
        System.out.println("XXXXXX: MaxPrio: " + maxPriority + " XXXXXXXXXX");
        assertEquals(200, maxPriorityId);
    }
    */

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
