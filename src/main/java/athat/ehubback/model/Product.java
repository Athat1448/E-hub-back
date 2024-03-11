package athat.ehubback.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lineId;

    private String lazadaId;

    private String[] imageUrls;

    private String name;

    private String code;

    private String description;

    private boolean isDisplay;

    @OneToMany(mappedBy = "product")
    private List<Variants> variants;

    @OneToMany(mappedBy = "product")
    private List<VariantOptions> variantOptions;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "line_category_id")
    private LineCategory lineCategory;
    
}


