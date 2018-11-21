package lukuvinkit.controllers;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.validation.Valid;

import lukuvinkit.database.*;
import lukuvinkit.domain.*;
import lukuvinkit.service.ReadingTipService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

// this can be turned into lukuvinkkicontroller if needed
@Controller
public class Controllers {
    
    private ReadingTipService service;

    @Autowired
    public Controllers() throws SQLException {
        Database database = new Database("jdbc:sqlite:data.db");
        database.initializeDatabase();
        ReadingTipDao readingTipDao = new ReadingTipDao(database);
        CommentDao commentDao = new CommentDao(database);
        TagDao tagDao = new TagDao(database);
        this.service = new ReadingTipService(readingTipDao, commentDao, tagDao);
    }

    @GetMapping("*")
    public String home(Model model) throws SQLException {
        ArrayList<ReadingTipListingUnit> tipListing = service.generateReadingTipListing();
        model.addAttribute("list", tipListing);
        model.addAttribute("newReadingTip", new ReadingTip());
        return "index";
    }

    @PostMapping(value = "/")
    public String post(@Valid @ModelAttribute
            ReadingTip newReadingTip, BindingResult bindingResult) throws SQLException {
        if (bindingResult.hasErrors()) {
            return "index";
        }

        service.saveNewReadingTip(newReadingTip);
        return "redirect:/";
    }
}
