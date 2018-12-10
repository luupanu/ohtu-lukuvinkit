package lukuvinkit.service;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lukuvinkit.database.CommentDao;
import lukuvinkit.database.ReadingTipDao;
import lukuvinkit.database.TagDao;

import lukuvinkit.domain.Comment;
import lukuvinkit.domain.ReadingTip;
import lukuvinkit.domain.ReadingTipListingUnit;
import lukuvinkit.domain.Tag;

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
            ReadingTipListingUnit listingUnit = new ReadingTipListingUnit(
                tip,
                commentsOfTip,
                tagsOfTip
            );
            readingTipListing.add(listingUnit);
        }

        return readingTipListing;
    }

    public void saveNewReadingTip(ReadingTip newTip, Tag allTagsTogether) throws SQLException {
        // Separate tags into individual Tag-instances.
        List<String> tagsAsString = new ArrayList<String>(
            Arrays.asList((allTagsTogether.getTagDescription()).split("\\s*,\\s*")));
        List<String> noDuplicateTags =  tagsAsString.stream()
                                                    .distinct()
                                                    .collect(Collectors.toList());

        ArrayList<Tag> tags = new ArrayList<Tag>();
        for (String t : noDuplicateTags) {
            if (t != null && !t.isEmpty()) {
                tags.add(new Tag(t));
            }
        }
        
        // Save all tags into database that are not yet there,
        // fetch IDs for all tags at the same time.
        int i = 0;
        while (i < tags.size()) {
            Tag tag = tags.get(i);
            tag = tagDao.save(tag);
            ++i;
        }
        
        // Find out and set the proper priority_unread value for the new reading tip.
        int priorityUnread = readingTipDao.findMaxUnreadPriority() + 1;
        newTip.setPriorityUnread(priorityUnread);
        
        // Save the new reading tip.
        newTip = readingTipDao.save(newTip);
        
        // Bind all tags to the new reading tip.
        readingTipDao.bindTagsToReadingTip(newTip, tags);
    }
    
    public void saveNewComment(Comment newComment, int readingTipId) throws SQLException {
        newComment.setReadingTipId(readingTipId);
        commentDao.save(newComment);
    }
    
    public void toggleReadingTipRead(int readingTipId) throws SQLException {
        ReadingTip tip = readingTipDao.findOne(readingTipId);
        
        if (!tip.isRead()) {
            tip.setRead(true);
            tip.setPriorityRead(readingTipDao.findMaxReadPriority());
        } else {
            tip.setRead(false);
            tip.setPriorityUnread(readingTipDao.findMaxUnreadPriority());
        }
        
        readingTipDao.update(tip);
    }
    
    public void swapPriorities(int readingTipId1, int readingTipId2) throws SQLException {
        ReadingTip tip1 = readingTipDao.findOne(readingTipId1);
        ReadingTip tip2 = readingTipDao.findOne(readingTipId2);
        
        if (tip1.isRead() != tip2.isRead()) {
            return;
        }
        
        int UnreadPriorityTip1 = tip1.getPriorityUnread();
        int ReadPriorityTip1 = tip1.getPriorityRead();
        tip1.setPriorityUnread(tip2.getPriorityUnread());
        tip1.setPriorityRead(tip2.getPriorityRead());
        tip2.setPriorityUnread(UnreadPriorityTip1);
        tip2.setPriorityRead(ReadPriorityTip1);
    }

}
