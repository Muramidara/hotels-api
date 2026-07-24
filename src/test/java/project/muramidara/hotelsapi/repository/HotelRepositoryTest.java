package project.muramidara.hotelsapi.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.muramidara.hotelsapi.HotelsApiApplication;

import project.muramidara.hotelsapi.database.repository.HotelRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@Transactional
@RequiredArgsConstructor
@DataJpaTest
public class HotelRepositoryTest {
    private final HotelRepository hotelRepository;

    @Test
    void findAllTest(){
        var hotels = hotelRepository.findAll();
        assertThat(hotels).hasSize(3);
        var hotel1 = hotels.get(0);
        var hotel2 = hotels.get(1);
        var hotel3 = hotels.get(2);
        assertEquals( "Grand Plaza Downtown", hotel1.getName());
        assertEquals( "Sunset Inn Beach Resort", hotel2.getName());
        assertEquals( "Royal Park Hotel", hotel3.getName());
    }
}
