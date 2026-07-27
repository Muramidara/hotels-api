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
    @Test
    @DisplayName("Test update hotel functionality")
    public void giveHotelToUpdate_whenSave_thenBrandIsChanged(){
        //given
        String updatedBrand = "Updated brand";
        Hotel hotelToCreate = Hotel.builder()
                .name("test hotel")
                .brand("test brand")
                .description("description for test hotel")
                .build();
        hotelRepository.save(hotelToCreate);
        //when
        Hotel hotelToUpdate = hotelRepository.findById( hotelToCreate.getId()).orElse(null);
        hotelToUpdate.setBrand(updatedBrand);
        Hotel updatedHotel = hotelRepository.save(hotelToUpdate);
        //then
        assertThat(updatedHotel).isNotNull();
        assertThat(updatedHotel.getBrand()).isEqualTo(updatedBrand);
    }

    @Test
    @DisplayName("Test get hotel by id functionality")
    public void givenHotelCreated_whenGetById_thenHotelIsReturned(){
        //given
        Hotel hotelToSave = Hotel.builder()
                .name("test hotel")
                .brand("test brand")
                .description("description for test hotel")
                .build();
        hotelRepository.save(hotelToSave);
        //when
        Hotel obtainedHotel = hotelRepository.findById(hotelToSave.getId()).orElse(null);
        //then
        assertThat(obtainedHotel).isNotNull();
        assertThat(obtainedHotel.getBrand()).isEqualTo("test brand");
    }

    @Test
    @DisplayName("Test hotel not found functionality")
    public void givenHotelIsNotCreated_whenGetById_thenOptionalIsEmpty(){
        //given

        //when
        Hotel obtainedHotel = hotelRepository.findById(1L).orElse(null);
        //then
        assertThat(obtainedHotel).isNull();
    }
}

//given

//when

//then