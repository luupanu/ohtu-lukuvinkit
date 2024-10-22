package lukuvinkit.domain;

import javax.validation.constraints.AssertTrue;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Component;

/**
 * Represents a reading tip.
 */
@Component
public class ReadingTip {

    private int id;

    @NotEmpty
    private String title;

    private String type;

    @URL
    private String url;

    private String author;

    private String isbn;

    @NotEmpty
    private String description;

    private boolean read;
    
    private int priorityRead;
    
    private int priorityUnread;
    

    public ReadingTip() {
        // default constructor for Spring
    }

    public ReadingTip(String title, String type, String url, String author, 
        String isbn, String description, boolean read) {
        this.title = title;
        this.type = type;
        this.url = url;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.read = read;
    }

    public ReadingTip(int id, String title, String type, String url, String author, 
        String isbn, String description, boolean read) {
        this(title, type, url, author, isbn, description, read);
        this.id = id;
    }
    
    public ReadingTip(int id, String title, String type, String url, String author, 
        String isbn, String description, boolean read, int priorityRead, int priorityUnread) {
        
        this(title, type, url, author, isbn, description, read);
        
        this.id = id;
        this.priorityRead = priorityRead;
        this.priorityUnread = priorityUnread;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
    
    public void setPriorityRead(int priorityRead) {
        this.priorityRead = priorityRead;
    }
    
    public int getPriorityRead() {
        return this.priorityRead;
    }
    
    public void setPriorityUnread(int priorityUnread) {
        this.priorityUnread = priorityUnread;
    }
    
    public int getPriorityUnread() {
        return this.priorityUnread;
    }
    
    // Multi-field validators:
    
    @AssertTrue
    public boolean isLinkUrlOk() {
        if (this.type == null) {
            return false;
        }
        
        if (!this.type.equals("link")) {
            return true;
        }
        
        return !this.url.equals("") && this.author.equals("") && this.isbn.equals("");
    }
    
    @AssertTrue
    public boolean isArticleAuthorOk() {
        if (this.type == null) {
            return false;
        }
        
        if (!this.type.equals("article")) {
            return true;
        }
        
        return this.url.equals("") && !this.author.equals("") && this.isbn.equals("");
    }
    
    @AssertTrue
    public boolean isBookAuthorOk() {
        if (this.type == null) {
            return false;
        }
        
        if (!this.type.equals("book")) {
            return true;
        }
        
        return this.url.equals("") && !this.author.equals("");
    }
    
    @AssertTrue
    public boolean isBookIsbnOk() {
        if (this.type == null) {
            return false;
        }
        
        if (!this.type.equals("book")) {
            return true;
        }
        
        return this.url.equals("") && !this.isbn.equals("");
    }

}
