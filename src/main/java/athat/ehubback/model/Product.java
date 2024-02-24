package athat.ehubback.model;

import java.util.List;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @NonNull
    private String lineId;

    @NonNull
    private String lazadaId;

    @NonNull
    private String[] imageUrls;

    @NonNull
    private String name;

    private String description;

    private boolean isDisplay;

    @OneToMany(mappedBy = "product")
    private List<Variants> variants;
    
}


