package athat.ehubback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import athat.ehubback.model.VariantOptions;

public interface VariantOptionRepository extends JpaRepository<VariantOptions, Long> {
}
