package athat.ehubback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import athat.ehubback.model.Product;
import athat.ehubback.model.Store;

public interface ProductRepository extends JpaRepository<Product, Long>{
    Product findByLineId(String lineId);

    boolean existsByLineId(String lineId);

    List<Product> findByStore(Store store);
}
