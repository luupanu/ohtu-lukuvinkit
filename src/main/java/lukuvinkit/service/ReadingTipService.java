package lukuvinkit.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

/**
 * Provides the backend services needed by the frontend reading tip application.
 */
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

    
    /**
     * Generates a listing of all the reading tips and their tags and comments.
     * @return A list of ReadingTipListingUnits.
     * @throws SQLException Throws SQL exceptions.
     */
    public ArrayList<ReadingTipListingUnit> generateReadingTipListing() throws SQLException {
        ArrayList<ReadingTipListingUnit> readingTipListing = new ArrayList<>();
        
        for (ReadingTip tip : readingTipDao.findAll()) {
            readingTipListing.add(listingUnitMaker(tip));
        }
        Collections.sort(readingTipListing);
        
        return readingTipListing;
    }
    
    private ReadingTipListingUnit listingUnitMaker(ReadingTip tip) throws SQLException {
        ArrayList<Comment> commentsOfTip = commentDao.findAllForReadingTip(tip.getId());
        ArrayList<Tag> tagsOfTip = tagDao.findAllForReadingTip(tip.getId());
        
        ReadingTipListingUnit listingUnit = new ReadingTipListingUnit(
            tip,
            commentsOfTip,
            tagsOfTip
        );
        
        return listingUnit;
    }

    
    /**
     * Saves a new reading tip.
     * @param newTip The new reading tip to be saved.
     * @param allTagsTogether The tags of the new reading tip.
     * @throws SQLException Throws SQL exceptions.
     */
    public void saveNewReadingTip(ReadingTip newTip, Tag allTagsTogether) throws SQLException {
        ArrayList<Tag> tags = tagSeparator(allTagsTogether);
        
        saveNovelTagsAndUpdateIdsForAll(tags);
        
        // Find out and set the proper priority_unread value for the new reading tip.
        int lowestPriorityUnread = readingTipDao.findMaxUnreadPriority() + 1;
        newTip.setPriorityUnread(lowestPriorityUnread);
        
        // Save the new reading tip.
        newTip.setRead(false);
        readingTipDao.save(newTip);
        
        // Bind all tags to the new reading tip.
        readingTipDao.bindTagsToReadingTip(newTip, tags);
    }
    
    private ArrayList<Tag> tagSeparator(Tag allTagsTogether) {
        // Separate tags into individual Tag-instances.
        List<String> tagsAsString = new ArrayList<>(
            Arrays.asList((allTagsTogether.getTagDescription()).split("\\s*,\\s*")));
        List<String> noDuplicateTags =  tagsAsString.stream()
                                                    .distinct()
                                                    .collect(Collectors.toList());
        
        ArrayList<Tag> tags = new ArrayList<>();
        for (String t : noDuplicateTags) {
            if (t != null && !t.isEmpty()) {
                tags.add(new Tag(t));
            }
        }
        
        return tags;
    }
    
    private void saveNovelTagsAndUpdateIdsForAll(ArrayList<Tag> tags) throws SQLException {
        // Save all tags into database that are not yet there,
        // fetch IDs for all tags at the same time.
        int i = 0;
        while (i < tags.size()) {
            Tag tag = tags.get(i);
            tagDao.save(tag);
            ++i;
        }
    }
    
    
    /**
     * Saves a new comment for a reading tip.
     * @param newComment The new comment to be saved.
     * @param readingTipId The ID of the reading tip the comment is for.
     * @throws SQLException Throws SQL exceptions.
     */
    public void saveNewComment(Comment newComment, int readingTipId) throws SQLException {
        newComment.setReadingTipId(readingTipId);
        commentDao.save(newComment);
    }
    
    
    /**
     * Toggles a reading tip as read or unread and sets the lowest priority for it in
     * the new group that it ends up in. (read group or unread group.)
     * @param readingTipId The ID of the reading tip.
     * @throws SQLException Throws SQL exceptions.
     */
    public void toggleReadingTipRead(int readingTipId) throws SQLException {
        ReadingTip tip = readingTipDao.findOne(readingTipId);

        if (tip == null) {
            return;
        }
        
        toggleReadAndSetLowestPriority(tip);
        
        readingTipDao.update(tip);
    }
    
    private void toggleReadAndSetLowestPriority(ReadingTip tip) throws SQLException {
        if (!tip.isRead()) {
            tip.setRead(true);
            tip.setPriorityRead(readingTipDao.findMaxReadPriority() + 1);
        } else {
            tip.setRead(false);
            tip.setPriorityUnread(readingTipDao.findMaxUnreadPriority() + 1);
        }        
    }
    
    
    /**
     * Swaps the priorities of two reading tips. The tips must have the same read value.
     * @param readingTipId1 The ID of the reading tip 1.
     * @param readingTipId2 The ID of the reading tip 2.
     * @throws SQLException Throws SQL exceptions.
     */
    public void swapPriorities(int readingTipId1, int readingTipId2) throws SQLException {
        ReadingTip tip1 = readingTipDao.findOne(readingTipId1);
        ReadingTip tip2 = readingTipDao.findOne(readingTipId2);
        
        if (!bothAreEitherReadOrUnread(tip1, tip2)) {
            return;
        }
        
        swapPrioritiesOfTwoReadingTipInstances(tip1, tip2);
        readingTipDao.update(tip1);
        readingTipDao.update(tip2);
    }
    
    private boolean bothAreEitherReadOrUnread(ReadingTip tip1, ReadingTip tip2) {
        return tip1 != null
            && tip2 != null
            && tip1.isRead() == tip2.isRead()
            && tip1.getId() != tip2.getId();
    }
    
    private void swapPrioritiesOfTwoReadingTipInstances(ReadingTip tip1, ReadingTip tip2) {
        int unreadPriorityTip1 = tip1.getPriorityUnread();
        int readPriorityTip1 = tip1.getPriorityRead();

        tip1.setPriorityUnread(tip2.getPriorityUnread());
        tip1.setPriorityRead(tip2.getPriorityRead());
        tip2.setPriorityUnread(unreadPriorityTip1);
        tip2.setPriorityRead(readPriorityTip1);        
    }

}
