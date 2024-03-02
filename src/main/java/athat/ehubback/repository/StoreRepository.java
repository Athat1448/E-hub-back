package athat.ehubback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import athat.ehubback.model.Store;

public interface StoreRepository extends JpaRepository<Store , Long> {
    Store findByName(String Name);
}
