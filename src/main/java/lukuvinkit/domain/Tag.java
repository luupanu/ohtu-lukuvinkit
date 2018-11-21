
package lukuvinkit.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

@Component
public class Tag {
    
    private int id;
    
    @NotEmpty
    private String description;
    
    public Tag() {
        // default constructor for Spring
    }
    
    public Tag(String description) {
        this.description = description;
    }

    public Tag(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
