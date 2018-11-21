
package lukuvinkit.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Component;

@Component
public class ReadingTip {
    
    private int id;
    
    @NotEmpty
    private String title;

    @NotEmpty
    @URL
    private String url;

    @NotEmpty
    private String description;
    
    private boolean read;
    
    public ReadingTip() {
        // default constructor for Spring
    }
    
    public ReadingTip(String title, String url, String description, boolean read) {
        this.title = title;
        this.url = url;
        this.description = description;
        this.read = read;
    }

    public ReadingTip(int id, String title, String url, String description, boolean read) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.description = description;
        this.read = read;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
    
    
    
}
