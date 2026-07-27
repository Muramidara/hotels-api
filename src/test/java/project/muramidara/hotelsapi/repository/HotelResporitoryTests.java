package project.muramidara.hotelsapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import project.muramidara.hotelsapi.database.entity.Hotel;
import project.muramidara.hotelsapi.database.repository.HotelRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class HotelResporitoryTests {
    @Autowired
    private HotelRepository hotelRepository;

    @BeforeEach
    public void setUp(){
        hotelRepository.deleteAll();
    }

    @Test
    @DisplayName("Test save hotel functionality")
    public void givenHotelObject_whenSave_thenHotelIsCreated(){
        //given
        Hotel hotelToSave = Hotel.builder()
                .name("test hotel")
                .brand("test brand")
                .description("description for test hotel")
                .build();
        //when
        Hotel savedHotel = hotelRepository.save(hotelToSave);
        //then
        assertThat(savedHotel).isNotNull();
        assertThat(savedHotel.getId()).isNotNull();
    }
}
