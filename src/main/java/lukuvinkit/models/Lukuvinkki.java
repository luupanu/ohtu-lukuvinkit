package lukuvinkit.models;

import org.springframework.stereotype.Component;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

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

}