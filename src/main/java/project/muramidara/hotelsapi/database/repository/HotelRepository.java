package project.muramidara.hotelsapi.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.muramidara.hotelsapi.database.entity.Hotel;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findAllByNameIgnoreCase(String name);
    List<Hotel> findAllByBrandIgnoreCase(String brand);
    @Query(value = "SELECT h FROM Hotel h JOIN FETCH Address a ON h.address = a WHERE a.city = :city")
    List<Hotel> findAllByCity(@Param("city") String city);
    @Query(value = "SELECT h FROM Hotel h JOIN FETCH Address a ON h.address = a WHERE a.country = :country")
    List<Hotel> findAllByCountry(@Param("country") String country);


    Hotel findHotelByName(String s);


}
