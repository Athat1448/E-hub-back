package athat.ehubback.model;

import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String name;

    private String lineApiKey;

    private String lazadaApiKey;

    @NonNull
    @OneToMany(mappedBy = "store")
    private List<User> user;

    @OneToMany(mappedBy = "store")
    private List<Product> product;

    @OneToMany(mappedBy = "store")
    private List<LineOrder> order;
    
    public void setLineApiKey(String apiKey) {
        this.lineApiKey = Base64.encodeBase64String(apiKey.getBytes());
    }

    public String getLineApiKey() {
        if (lineApiKey == null) {
            return null;
        }
        return new String(Base64.decodeBase64(lineApiKey));
    }

    public void setLazadaApiKey(String apiKey) {
        this.lazadaApiKey = Base64.encodeBase64String(apiKey.getBytes());
    }

    public String getLazadaApiKey() {
        if (lazadaApiKey == null) {
            return null;
        }
        return new String(Base64.decodeBase64(lazadaApiKey));
    }
}
