package ohtu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import lukuvinkit.controllers.Controllers;
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
    public static void setUpClass() throws SQLException {
        // Database initialization
        Connection connection = DriverManager.getConnection(DATABASE_ADDRESS);

        // Check if database table is already created.
        PreparedStatement statementTableCheck = connection.prepareStatement(
            "select count(*) from sqlite_master"
        );
        ResultSet result = statementTableCheck.executeQuery();
        result.next();
        int tableExists = result.getInt(1);
        result.close();
        statementTableCheck.close();

        // Create database table if it is not yet created.
        if (tableExists == 0) {
            PreparedStatement statementCreateTable = connection.prepareStatement(
                "CREATE TABLE Lukuvinkki(title TEXT, url TEXT, description TEXT)"
            );
            statementCreateTable.executeUpdate();
            statementCreateTable.close();
        }

        connection.close();
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
