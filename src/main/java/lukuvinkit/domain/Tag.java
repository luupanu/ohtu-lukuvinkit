package lukuvinkit.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

@Component
public class Tag {

    private int id;

    @NotEmpty
    private String tagDescription;

    public Tag() {
        // default constructor for Spring
    }

    public Tag(String description) {
        this.tagDescription = description;
    }

    public Tag(int id, String description) {
        this.id = id;
        this.tagDescription = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagDescription() {
        return tagDescription;
    }

    public void setTagDescription(String description) {
        this.tagDescription = description;
    }

}
