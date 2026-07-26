package project.muramidara.hotelsapi.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import org.springframework.boot.test.context.SpringBootTest;
import project.muramidara.hotelsapi.HotelsApiApplication;
import project.muramidara.hotelsapi.database.repository.HotelRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@Transactional
@RequiredArgsConstructor
@SpringBootTest(classes = {HotelsApiApplication.class})
public class HotelServiceTestIT {
    private final static Long HOTEL1_ID = 1L;
    private final static String HOTEL1_NAME = "Grand Plaza Downtown";
    private final static String HOTEL_BRAND = "Marriott";
    private final static String HOTEL_CITY = "London";
    private final static String HOTEL_COUNTRY = "USA";
    private final static String AMENITY = "Parking";

    private final HotelRepository hotelRepository;
    private final HotelService hotelService;
    //TODO: solve n+1 problem
    @Test
    void findAllTest() {
        var hotels = hotelService.findAll();
        assertThat(hotels).hasSize(15);
        var hotel1 = hotels.get(0);
        var hotel2 = hotels.get(1);
        var hotel3 = hotels.get(2);
        assertEquals("Grand Plaza Downtown", hotel1.getName());
        assertEquals("Sunset Inn Beach Resort", hotel2.getName());
        assertEquals("Royal Park Hotel", hotel3.getName());
    }

    @Test
    void findById() {
        var existingHotel = hotelService.findById(HOTEL1_ID);
        var fakeHotel = hotelService.findById(-1L);
        assertTrue(existingHotel.isPresent());
        assertTrue(fakeHotel.isEmpty());
        existingHotel.ifPresent(hotel ->
                assertEquals("Grand Plaza Downtown", hotel.getName())
        );
    }

    @Test
    void findAllByFilterTest() {
        var filterName = "name";
        var filterBrand = "brand";
        var filterCity = "city";
        var filterCountry = "country";
        // TODO: replace amenity with amenities
        var filterAmenity = "amenity";
        var hotelsByName = hotelService.findAllByFilter(filterName, HOTEL1_NAME);
        var hotelsByBrand = hotelService.findAllByFilter(filterBrand, HOTEL_BRAND);
        var hotelsByCity = hotelService.findAllByFilter(filterCity, HOTEL_CITY);
        var hotelsByCountry = hotelService.findAllByFilter(filterCountry, HOTEL_COUNTRY);
        var hotelsByAmenity = hotelService.findAllByFilter(filterAmenity, AMENITY);
        assertThat(hotelsByName).hasSize(1);
        assertThat(hotelsByBrand).hasSize(4);
        assertThat(hotelsByCity).hasSize(5);
        assertThat(hotelsByCountry).hasSize(10);
        assertThat(hotelsByAmenity).hasSize(6);

    }


    @Test
    void addAmenitiesTest() {
        var hotel = hotelRepository.getReferenceById(HOTEL1_ID);
        var testingName = "Testing hotel name";
        assertEquals("Grand Plaza Downtown", hotel.getName());
        hotel.setName(testingName);
        hotelRepository.flush();
        hotel = hotelRepository.getReferenceById(HOTEL1_ID);
        assertEquals(testingName, hotel.getName());
    }

    @Test
    void updateTest() {
        var existingHotel = hotelService.findById(HOTEL1_ID);
        var fakeHotel = hotelService.findById(-1L);
        assertTrue(existingHotel.isPresent());
        assertTrue(fakeHotel.isEmpty());
        existingHotel.ifPresent(hotel ->
                assertEquals("Grand Plaza Downtown", hotel.getName())
        );
    }

    @Test
    void deleteTest() {
        var existingHotel = hotelService.findById(HOTEL1_ID);
        var fakeHotel = hotelService.findById(-1L);
        assertTrue(existingHotel.isPresent());
        assertTrue(fakeHotel.isEmpty());
        existingHotel.ifPresent(hotel ->
                assertEquals("Grand Plaza Downtown", hotel.getName())
        );
    }

    @Test
    void createTest() {
        var existingHotel = hotelService.findById(HOTEL1_ID);
        var fakeHotel = hotelService.findById(-1L);
        assertTrue(existingHotel.isPresent());
        assertTrue(fakeHotel.isEmpty());
        existingHotel.ifPresent(hotel ->
                assertEquals("Grand Plaza Downtown", hotel.getName())
        );
    }

    @Test
    void groupByFilterTest() {
        var existingHotel = hotelService.findById(HOTEL1_ID);
        var fakeHotel = hotelService.findById(-1L);
        assertTrue(existingHotel.isPresent());
        assertTrue(fakeHotel.isEmpty());
        existingHotel.ifPresent(hotel ->
                assertEquals("Grand Plaza Downtown", hotel.getName())
        );
    }
}
