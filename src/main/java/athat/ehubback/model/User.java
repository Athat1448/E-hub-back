package athat.ehubback.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class User {
    
    @Id
    @GeneratedValue
    private UUID id;

    @NonNull
    @Column(unique = true)
    private String username;

    @NonNull
    private String password;
}
