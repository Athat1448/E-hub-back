package athat.ehubback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import athat.ehubback.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    Product findById(String id);

}
