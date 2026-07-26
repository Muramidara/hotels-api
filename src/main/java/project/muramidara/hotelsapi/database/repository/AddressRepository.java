package project.muramidara.hotelsapi.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.muramidara.hotelsapi.database.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
