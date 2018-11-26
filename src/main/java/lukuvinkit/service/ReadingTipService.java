
package lukuvinkit.service;

import lukuvinkit.database.ReadingTipDao;
import lukuvinkit.database.CommentDao;
import lukuvinkit.database.TagDao;
import lukuvinkit.domain.*;

import java.util.ArrayList;
import java.sql.SQLException;
import java.util.Arrays;
import org.springframework.stereotype.Component;

@Component
public class ReadingTipService {

    private ReadingTipDao readingTipDao;
    private CommentDao commentDao;
    private TagDao tagDao;

    public ReadingTipService() {
        // default constructor for Spring
    }

    public ReadingTipService(ReadingTipDao readingTipDao, CommentDao commentDao, TagDao tagDao) {
        this.readingTipDao = readingTipDao;
        this.commentDao = commentDao;
        this.tagDao = tagDao;
    }

    public ArrayList<ReadingTipListingUnit> generateReadingTipListing() throws SQLException {

        ArrayList<ReadingTipListingUnit> readingTipListing = new ArrayList<>();

        ArrayList<ReadingTip> allReadingTips = readingTipDao.findAll();

        for (ReadingTip tip : allReadingTips) {
            ArrayList<Comment> commentsOfTip = commentDao.findAllForReadingTip(tip.getId());
            ArrayList<Tag> tagsOfTip = tagDao.findAllForReadingTip(tip.getId());
            ReadingTipListingUnit listingUnit = new ReadingTipListingUnit(tip, commentsOfTip, tagsOfTip);
            readingTipListing.add(listingUnit);
        }

        return readingTipListing;
    }

    public void saveNewReadingTip(ReadingTip newTip, Tag allTagsTogether) throws SQLException {
        // Separate tags into individual Tag-instances.
        ArrayList<Tag> tags = new ArrayList<Tag>();
        ArrayList<String> tagsAsString = new ArrayList<String>(
                Arrays.asList((allTagsTogether.getTagDescription()).split("\\s*,\\s*")));
        for (String t : tagsAsString) {
            tags.add(new Tag(t));
        }
        
        // Save all tags into database that are not yet there, fetch IDs for all tags at the same time.
        int i = 0;
        while (i < tags.size()) {
            Tag tag = tags.get(i);
            tag = tagDao.save(tag);
            ++i;
        }
        
        // Save the new reading tip.
        newTip = readingTipDao.save(newTip);
        
        // Bind all tags to the new reading tip.
        readingTipDao.bindTagsToReadingTip(newTip, tags);
    }
    
    public void saveNewComment(Comment newComment) throws SQLException {
        commentDao.save(newComment);
    }
    
    public void toggleReadingTipRead(int readingTipID) throws SQLException {
        readingTipDao.toggleRead(readingTipID);
    }

}
