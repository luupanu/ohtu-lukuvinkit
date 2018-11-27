package lukuvinkit.domain;

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
import lukuvinkit.domain.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadingTipTest {
    
    ReadingTip readingTipTestConstructor1;
    ReadingTip readingTipTestConstructor2;
    
    public ReadingTipTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws SQLException {
        // Database setUp tänne
    }
    
   @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        readingTipTestConstructor1 = new ReadingTip(1, "Tip 1 for testing", "https://yle.fi/", "Just testing here", false);
        readingTipTestConstructor2 = new ReadingTip("Tip 2 for tesing", "https://hs.fi/", "Test test", true);
    }
    
    
    @After
    public void tearDown() {
    }
    
    
    @Test
    public void attributesAreRightforConstructor1() { 
        assertEquals(readingTipTestConstructor1.getId(), 1);
        assertEquals(readingTipTestConstructor1.getTitle(), "Tip 1 for testing");
        assertEquals(readingTipTestConstructor1.getUrl(), "https://yle.fi/");
        assertEquals(readingTipTestConstructor1.getDescription(), "Just testing here");
        assertEquals(readingTipTestConstructor1.isRead(), false);
    }
    
    @Test
    public void attributesAreRightforConstructor2() { 
        assertEquals(readingTipTestConstructor2.getTitle(), "Tip 2 for tesing");
        assertEquals(readingTipTestConstructor2.getUrl(), "https://hs.fi/");
        assertEquals(readingTipTestConstructor2.getDescription(), "Test test");
        assertEquals(readingTipTestConstructor2.isRead(), true);
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