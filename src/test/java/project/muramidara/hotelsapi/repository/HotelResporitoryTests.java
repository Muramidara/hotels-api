package project.muramidara.hotelsapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import project.muramidara.hotelsapi.database.repository.HotelRepository;

@DataJpaTest
public class HotelResporitoryTests {
    @Autowired
    private HotelRepository hotelRepository;

    @BeforeEach
    public void setUp(){
        hotelRepository.deleteAll();
    }
}
