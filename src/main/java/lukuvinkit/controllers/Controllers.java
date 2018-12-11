package lukuvinkit.controllers;

import java.sql.SQLException;

import java.util.ArrayList;

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

/**
 * A centralized controller class.
 * Has the Spring @SessionAttributes("list") annotation to preserve list
 * on redirects and page refreshes.
 */
@Controller
@SessionAttributes("list")
public class Controllers {

    private ReadingTipService service;

    /**
     * <p>The constructor is annotated with @Autowired for spring dependency injections.</p>
     * <p>The database is initialized in this constructor with
     * {@link Database#initializeDatabaseIfUninitialized()}.</p>
     * <p>A service for handling database actions is created through
     * {@link ReadingTipService#ReadingTipService()}.</p>
     * @throws SQLException in case of database errors.
     */
    @Autowired
    public Controllers() throws SQLException {
        Database database = new Database("jdbc:sqlite:data.db");
        database.initializeDatabaseIfUninitialized();
        ReadingTipDao readingTipDao = new ReadingTipDao(database);
        CommentDao commentDao = new CommentDao(database);
        TagDao tagDao = new TagDao(database);
        this.service = new ReadingTipService(readingTipDao, commentDao, tagDao);
    }

    /**
     * <p>This method handles all of the GET-requests in our single-page app.</p>
     * <p>A list of {@link ReadingTipListingUnit#ReadingTipListingUnit()}
     * objects is made and added to the model as an attribute to be shown in the view.</p>
     * <p>Attributes for comments, new tips and tags are also added for data handling.</p>
     * @param model the models being sent to the view.
     * @return index - the main view.
     * @throws SQLException in case of database errors.
     */
    @GetMapping("*")
    public String home(Model model) throws SQLException {
        ArrayList<ReadingTipListingUnit> tipListing = service.generateReadingTipListing();
        
        model.addAttribute("list", tipListing);
        model.addAttribute("comment", new Comment());
        model.addAttribute("readingTip", new ReadingTip());
        model.addAttribute("tag", new Tag());
        return "index";
    }

    /**
     * <p>A method that handles saving new comments that 
     * have been sent as a POST-request from the view.</p>
     * <p>The comments are saved with {@link ReadingTipService#saveNewComment(Comment, int)}.</p>
     * @param id takes the id of the associated tip from the view
     * to tie it together with the comment parameter.
     * @param comment the contents of the comment that's being added.
     * @return redirect:/ - redirects to the main page.
     * @throws SQLException in case of database errors.
     */
    @PostMapping("/comment")
    public String createComment(@RequestParam Integer id, @ModelAttribute Comment comment)
            throws SQLException {
        service.saveNewComment(comment, id);
        return "redirect:/";
    }

    /**
     * <p>A method that handles saving new tips that 
     * have been sent as a POST-request from the view.</p>
     * <p>The comments are saved with 
     * {@link ReadingTipService#saveNewReadingTip(ReadingTip, Tag)}.</p>
     * @param readingTip information of the tip being saved down to the database.
     * @param tag a list of possible tags being added together with the new tip.
     * @param bindingResultTip holds the result for the validation for tips.
     * @param bindingResultTag holds the result for the validation of tags.
     * @return redirect:/ - redirects to the main page. In case of validation errors,
     * redirects without saving tip.
     * @throws SQLException in case of database errors.
     */
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

    /**
     * <p>A method that handles setting tips as read and unread
     * with a PATCH-request from the view.</p>
     * <p>The toggling is handled by {@link ReadingTipService#toggleReadingTipRead(int)}</p>
     * @param id the id of the tip being toggled as read/unread.
     * @return redirect:/ - redirects to the main page.
     * @throws SQLException in case of database errors.
     */
    @PatchMapping("/toggleread")
    public String toggleRead(@RequestParam Integer id) throws SQLException {
        service.toggleReadingTipRead(id);
        return "redirect:/";
    }

    /**
     * <p>A method that handles swapping priorities of two different ReadingTips
     * with a PATCH-request from the view.</p>
     * <p>The swapping is handled by {@link ReadingTipService#swapPriorities(int, int)}</p>
     * @param id1 the id of the first tip being swapped.
     * @param id2 the id of the other tip being swapped.
     * @return redirect:/ - redirects to the main page.
     * @throws SQLException in case of database errors.
     */
    @PatchMapping("/swappriorities")
    public String swapPriorities(@RequestParam Integer id1,
        @RequestParam Integer id2) throws SQLException {
        service.swapPriorities(id1, id2);
        return "redirect:/";
    }
}
