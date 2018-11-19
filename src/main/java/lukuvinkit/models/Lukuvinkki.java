package lukuvinkit.models;

import lukuvinkit.controllers.Controllers;

import org.springframework.stereotype.Component;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class Lukuvinkki {

    @NotEmpty
    private String title;

    @NotEmpty
    @URL
    private String url;

    @NotEmpty
    private String description;

    public Lukuvinkki() {
        // default constructor for Spring
    }

    public Lukuvinkki(String title, String url, String description) {
        this.title = title;
        this.url = url;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    // Database methods

    public void save() throws SQLException {
        Connection connection = DriverManager.getConnection(Controllers.DATABASE_ADDRESS);
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Lukuvinkki(title, url, description) values (?, ?, ?)");
        statement.setString(1, this.title);
        statement.setString(2, this.url);
        statement.setString(3, this.description);

        int success = statement.executeUpdate();
        System.out.println("SUCCESS: " + success);

        statement.close();
        connection.close();
    }

    public static ArrayList<Lukuvinkki> listAll() throws SQLException {
        Connection connection = DriverManager.getConnection(Controllers.DATABASE_ADDRESS);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Lukuvinkki");

        ResultSet result = statement.executeQuery();

        ArrayList<Lukuvinkki> allBookmarks = new ArrayList<>();

        while (result.next()) {
            allBookmarks.add(new Lukuvinkki(result.getString("title"), result.getString("url"), result.getString("description")));
        }

        result.close();
        statement.close();
        connection.close();

        return allBookmarks;
    }

}
