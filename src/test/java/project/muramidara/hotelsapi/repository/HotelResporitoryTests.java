package project.muramidara.hotelsapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.util.CollectionUtils;
import project.muramidara.hotelsapi.database.entity.Hotel;
import project.muramidara.hotelsapi.database.repository.HotelRepository;
import project.muramidara.hotelsapi.util.DataUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class HotelResporitoryTests {
    @Autowired
    private HotelRepository hotelRepository;

    @BeforeEach
    public void setUp() {
        hotelRepository.deleteAll();
    }

    @Test
    @DisplayName("Test save hotel functionality")
    public void givenHotelObject_whenSave_thenHotelIsCreated() {
        //given
        Hotel hotelToSave = DataUtils.getTestHotelTransient();
        //when
        Hotel savedHotel = hotelRepository.save(hotelToSave);
        //then
        assertThat(savedHotel).isNotNull();
        assertThat(savedHotel.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test update hotel functionality")
    public void giveHotelToUpdate_whenSave_thenBrandIsChanged() {
        //given
        String updatedBrand = "Updated brand";
        Hotel hotelToCreate = DataUtils.getTestHotelTransient();
        hotelRepository.save(hotelToCreate);
        //when
        Hotel hotelToUpdate = hotelRepository.findById(hotelToCreate.getId()).orElse(null);
        hotelToUpdate.setBrand(updatedBrand);
        Hotel updatedHotel = hotelRepository.save(hotelToUpdate);
        //then
        assertThat(updatedHotel).isNotNull();
        assertThat(updatedHotel.getBrand()).isEqualTo(updatedBrand);
    }

    @Test
    @DisplayName("Test get hotel by id functionality")
    public void givenHotelCreated_whenGetById_thenHotelIsReturned() {
        //given
        Hotel hotelToSave = DataUtils.getTestHotelTransient();
        hotelRepository.save(hotelToSave);
        //when
        Hotel obtainedHotel = hotelRepository.findById(hotelToSave.getId()).orElse(null);
        //then
        assertThat(obtainedHotel).isNotNull();
        assertThat(obtainedHotel.getBrand()).isEqualTo("test brand");
    }

    @Test
    @DisplayName("Test hotel not found functionality")
    public void givenHotelIsNotCreated_whenGetById_thenOptionalIsEmpty() {
        //given

        //when
        Hotel obtainedHotel = hotelRepository.findById(1L).orElse(null);
        //then
        assertThat(obtainedHotel).isNull();
    }

    @Test
    @DisplayName("Test get all hotels functionality")
    public void givenThreeHotelsAreStored_whenFindAll_thenAllHotelsAreReturned() {
        //given
        Hotel hotel1 = DataUtils.getTestHotelTransient();
        Hotel hotel2 = DataUtils.getTestHotel2Transient();
        Hotel hotel3 = DataUtils.getTestHotel3Transient();

        hotelRepository.saveAll(List.of(hotel1, hotel2, hotel3));
        //when
        List<Hotel> obtainedHotels = hotelRepository.findAll();
        //then
        assertThat(CollectionUtils.isEmpty(obtainedHotels)).isFalse();

    }

    @Test
    @DisplayName("Test get hotel by name functionality")
    public void givenHotelSaved_whenGetByName_thenHotelIsReturned(){
        //given
        Hotel hotel = DataUtils.getTestHotelTransient();
        hotelRepository.save(hotel);
        //when
        Hotel obtainedHotel = hotelRepository.findHotelByName("test hotel");
        //then
        assertThat(obtainedHotel).isNotNull();
        assertThat(obtainedHotel.getName()).isEqualTo(hotel.getName());
    }

    @Test
    @DisplayName("Test get all hotels by brand functionality")
    public void givenThreeHotelsAndTwoAreHelton_whenFindAllByBrand_thenReturnOnlyTwoHotels(){
        //given
        Hotel hotel1 = DataUtils.getTestHotelTransient();
        Hotel hotel2 = DataUtils.getTestHotel2Transient();
        Hotel hotel3 = DataUtils.getTestHotel3Transient();

        hotelRepository.saveAll(List.of(hotel1, hotel2, hotel3));
        //when
        List<Hotel> obtainedHotels = hotelRepository.findAllByBrandIgnoreCase("Helton");
        //then
        assertThat(CollectionUtils.isEmpty(obtainedHotels)).isFalse();
        assertThat(obtainedHotels.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test delete hotel by id functionality")
    public void givenHotelIsSaved_whenDeleteById_thenHotelIsRemovedFromDatabase(){
        //given
        Hotel hotel = DataUtils.getTestHotelTransient();
        hotelRepository.save(hotel);
        //when
        hotelRepository.deleteById(hotel.getId());
        //then
        Hotel obtainedHotel = hotelRepository.findById(hotel.getId()).orElse(null);
        assertThat(obtainedHotel).isNull();
    }
}

        //given

        //when

        //then