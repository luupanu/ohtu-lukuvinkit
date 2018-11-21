
package lukuvinkit.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

@Component
public class Comment {
    
    private int id;
    
    @NotEmpty
    private String description;
    
    private int readingtip_id;
    
    public Comment() {
        // default constructor for Spring
    }
    
    public Comment(String description, int readingtip_id) {
        this.description = description;
        this.readingtip_id = readingtip_id;
    }

    public Comment(int id, String description, int readingtip_id) {
        this.id = id;
        this.description = description;
        this.readingtip_id = readingtip_id;
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

    public int getReadingtip_id() {
        return readingtip_id;
    }

    public void setReadingtip_id(int readingtip_id) {
        this.readingtip_id = readingtip_id;
    }
    
    
    
}
