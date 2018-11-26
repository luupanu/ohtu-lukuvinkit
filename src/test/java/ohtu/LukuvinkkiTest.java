package ohtu;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import lukuvinkit.controllers.Controllers;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LukuvinkkiTest {
    
    public LukuvinkkiTest() {
    }
    
//    @BeforeClass
//    public static void setUpClass() throws SQLException {
//        // Database setUp tänne
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() {
//        lukuvinkki1 = new Lukuvinkki("Testlukuvinkki", "https://yle.fi/", "Just testing here");
//    }
//    
//    
//    @After
//    public void tearDown() {
//    }
//    
//    @Test
//    public void databaseMethodsWorks() throws SQLException  {
//        lukuvinkki1.save();
//        ArrayList<Lukuvinkki> allLukuvinkit = Lukuvinkki.listAll();
//        assertEquals(allLukuvinkit.get(allLukuvinkit.size() - 1).getTitle(), "Testlukuvinkki");
//    }
//    
//    @Test
//    public void getTitleWorks() throws SQLException  {
//        lukuvinkki1.save();
//        ArrayList<Lukuvinkki> allLukuvinkit = Lukuvinkki.listAll();
//        assertEquals(allLukuvinkit.get(allLukuvinkit.size() - 1).getTitle(), "Testlukuvinkki");
//    }

}
