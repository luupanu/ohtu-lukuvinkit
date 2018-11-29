package lukuvinkit.controllers;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collections;

import javax.validation.Valid;

import lukuvinkit.database.CommentDao;
import lukuvinkit.database.Database;
import lukuvinkit.database.ReadingTipDao;
import lukuvinkit.database.TagDao;

import lukuvinkit.domain.Comment;
import lukuvinkit.domain.ReadingTip;
import lukuvinkit.domain.ReadingTipListingUnit;
import lukuvinkit.domain.Tag;

import lukuvinkit.service.ReadingTipService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

// this can be turned into lukuvinkkicontroller if needed
@Controller
@SessionAttributes("list")
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
        Collections.sort(tipListing);

        model.addAttribute("list", tipListing);
        model.addAttribute("comment", new Comment());
        model.addAttribute("readingTip", new ReadingTip());
        model.addAttribute("tag", new Tag());
        return "index";
    }

    @PostMapping("/comment")
    public String createComment(@RequestParam Integer readingTipId, @ModelAttribute Comment comment)
            throws SQLException {
        service.saveNewComment(comment, readingTipId);
        return "redirect:/";
    }

    @PostMapping("/readingtip")
    public String createReadingTip(@Valid @ModelAttribute ReadingTip readingTip, 
            BindingResult bindingResultTip, @ModelAttribute Tag tag, 
            BindingResult bindingResultTag) throws SQLException {
        if (bindingResultTip.hasErrors() || bindingResultTag.hasErrors()) {
            return "index";
        }

        service.saveNewReadingTip(readingTip, tag);

        return "redirect:/";
    }

    @PatchMapping("/")
    public String toggleRead(@RequestParam Integer id) throws SQLException {
        System.out.println(id);
        service.toggleReadingTipRead(id);
        return "redirect:/";
    }
}
