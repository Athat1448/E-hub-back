package athat.ehubback.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LineCategory {
    @Id
    private int id;

    private String en;
    
    private String th;
}
