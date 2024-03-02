package athat.ehubback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import athat.ehubback.model.Variants;

public interface VariantsRepository extends JpaRepository<Variants, Long>{
    Variants findByLineId(String lineId);

    boolean existsByLineId(String lineId);
}
