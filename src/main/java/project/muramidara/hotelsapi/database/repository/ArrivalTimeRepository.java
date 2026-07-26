package project.muramidara.hotelsapi.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.muramidara.hotelsapi.database.entity.ArrivalTime;
@Repository
public interface ArrivalTimeRepository extends JpaRepository<ArrivalTime, Long> {
}
