package athat.ehubback.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Entity
@Getter
@Setter
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("lineid")
    private String lineId;

    @JsonProperty("lazadaid")
    private String lazadaId;

    private String[] imageUrls;

    private String name;

    private String description;

    private boolean isDisplay;

    @OneToMany(mappedBy = "product")
    private List<Variants> variants;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
    
}


