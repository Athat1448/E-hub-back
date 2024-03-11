package athat.ehubback.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "lineCategory")
public class LineCategory {
    @Id
    private int id;
    
    private String EnglishName;

    private String ThaiName;

    @OneToMany(mappedBy = "lineCategory")
    private List<Product> product;
}
