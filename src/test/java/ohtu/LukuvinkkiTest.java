package ohtu;

import java.util.ArrayList;
import java.sql.SQLException;
import lukuvinkit.models.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class LukuvinkkiTest {
    
    public static final String DATABASE_ADDRESS = "jdbc:sqlite:data.db";
    public Lukuvinkki lukuvinkki1;
    
    public LukuvinkkiTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        lukuvinkki1 = new Lukuvinkki("Testlukuvinkki", "https://yle.fi/", "Just testing here");
    }
    
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void databaseMethodsWorks() throws SQLException  {
        lukuvinkki1.save();
        ArrayList<Lukuvinkki> allLukuvinkit = Lukuvinkki.listAll();
        assertEquals(allLukuvinkit.get(allLukuvinkit.size() - 1).getTitle(), "Testlukuvinkki");
    }
    
    @Test
    public void getTitleWorks() throws SQLException  {
        lukuvinkki1.save();
        ArrayList<Lukuvinkki> allLukuvinkit = Lukuvinkki.listAll();
        assertEquals(allLukuvinkit.get(allLukuvinkit.size() - 1).getTitle(), "Testlukuvinkki");
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
