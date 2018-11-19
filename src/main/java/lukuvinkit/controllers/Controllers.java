package lukuvinkit.controllers;

import java.util.ArrayList;
import javax.validation.Valid;

import lukuvinkit.models.Lukuvinkki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// this can be turned into lukuvinkkicontroller if needed
@Controller
public class Controllers {

    public static final String DATABASE_ADDRESS = "jdbc:sqlite:data.db";

    @Autowired
    public Controllers() throws SQLException {
        // Database initialization
        Connection connection = DriverManager.getConnection(Controllers.DATABASE_ADDRESS);

        // Check if database table is already created.
        PreparedStatement statementTableCheck = connection.prepareStatement("select count(*) from sqlite_master");
        ResultSet result = statementTableCheck.executeQuery();
        result.next();
        int tableExists = result.getInt(1);
        result.close();
        statementTableCheck.close();

        // Create database table if it is not yet created.
        if (tableExists == 0) {
            PreparedStatement statementCreateTable = connection
                    .prepareStatement("CREATE TABLE Lukuvinkki(title TEXT, url TEXT, description TEXT)");
            statementCreateTable.executeUpdate();
            statementCreateTable.close();
        }

        connection.close();
    }

    @GetMapping("*")
    public String home(Model model) throws SQLException {
        ArrayList<Lukuvinkki> allBookmarks = Lukuvinkki.listAll();
        model.addAttribute("list", allBookmarks);
        model.addAttribute("lukuvinkki", new Lukuvinkki());
        return "index";
    }

    @PostMapping(value = "/")
    public String post(@Valid @ModelAttribute Lukuvinkki lukuvinkki, BindingResult bindingResult) throws SQLException {
        if (bindingResult.hasErrors()) {
            return "index";
        }

        lukuvinkki.save();
        return "redirect:/";
    }
}
