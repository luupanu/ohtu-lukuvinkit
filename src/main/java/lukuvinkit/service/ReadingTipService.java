
package lukuvinkit.service;

import lukuvinkit.database.ReadingTipDao;
import lukuvinkit.database.CommentDao;
import lukuvinkit.database.TagDao;
import lukuvinkit.domain.*;

import java.util.ArrayList;
import java.sql.SQLException;
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
        
        for(ReadingTip tip : allReadingTips) {
            ArrayList<Comment> commentsOfTip = commentDao.findAllForReadingTip(tip.getId());
            ArrayList<Tag> tagsOfTip = tagDao.findAllForReadingTip(tip.getId());
            ReadingTipListingUnit listingUnit = new ReadingTipListingUnit(tip, commentsOfTip, tagsOfTip);
            readingTipListing.add(listingUnit);
        }
        
        return readingTipListing;
    }
    
    public void saveNewReadingTip(ReadingTip newTip) throws SQLException {
        readingTipDao.save(newTip);
    }
    
}
