package athat.ehubback.model;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Entity
@Getter
@Setter
public class Product {

    @Id
    private String id;

    @NonNull
    private String imageUrl;

    @NonNull
    private String name;
}
