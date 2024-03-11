package athat.ehubback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import athat.ehubback.model.LineOrder;

public interface LineOrderRepository extends JpaRepository<LineOrder, Long> {

}