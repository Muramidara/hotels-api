package project.muramidara.hotelsapi.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import project.muramidara.hotelsapi.HotelsApiApplication;
import project.muramidara.hotelsapi.database.entity.Hotel;
import project.muramidara.hotelsapi.database.repository.HotelRepository;
import project.muramidara.hotelsapi.dto.*;
import project.muramidara.hotelsapi.mapper.HotelFullReadDtoMapper;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
    private final static HotelCreateEditDto HOTEL_CREATE_EDIT_DTO = new HotelCreateEditDto(
            "test name",
            "Test description",
            "Hilton",
            new AddressDto(111, "Test street", "London", "UK", "111000"),
            new ContactsDto("test@@mail.com", "+11111111"),
            new ArrivalTimeDto("10:00", "12:00")
    );

    private final HotelFullReadDtoMapper hotelFullReadDtoMapper;
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
    void createTest() {
        var response = hotelService.create(HOTEL_CREATE_EDIT_DTO);
        var hotelInDatabase = hotelService.findById(response.getId());
        assertTrue(hotelInDatabase.isPresent());
        assertEquals(HOTEL_CREATE_EDIT_DTO.getName(), hotelInDatabase.get().getName());

    }

    @Test
    void addAmenitiesTest() {
        var hotel = hotelService.findById(HOTEL1_ID).get();
        String amenity1Name = "Test amenity 1";
        String amenity2Name = AMENITY;
        assertFalse(hotel.getAmenities().stream().anyMatch(amenityDto -> amenityDto.getName().equals(amenity1Name)));
        assertFalse(hotel.getAmenities().stream().anyMatch(amenityDto -> amenityDto.getName().equals(amenity2Name)));
        List<AmenityDto> amenityDtos = new ArrayList<>();
        amenityDtos.add(new AmenityDto(amenity1Name));
        amenityDtos.add(new AmenityDto(amenity2Name));
        hotelService.addAmenities(HOTEL1_ID, amenityDtos);
        hotel = hotelService.findById(HOTEL1_ID).get();
        assertTrue(hotel.getAmenities().stream().anyMatch(amenityDto -> amenityDto.getName().equals(amenity1Name)));
        assertTrue(hotel.getAmenities().stream().anyMatch(amenityDto -> amenityDto.getName().equals(amenity2Name)));

    }

    @Test
    void groupByFilterTest() {
        var groupByBrand = "brand";
        var groupByCity = "city";
        var groupByCountry = "country";
        // TODO: replace amenity with amenities
        var groupByAmenity = "amenity";
        var hotelsByBrand = hotelService.findAllGroupByFilter(groupByBrand);
        var hotelsByCity = hotelService.findAllGroupByFilter(groupByCity);
        var hotelsByCountry = hotelService.findAllGroupByFilter(groupByCountry);
        var hotelsByAmenity = hotelService.findAllGroupByFilter(groupByAmenity);
        assertThat(hotelsByBrand).hasSize(5);
        assertThat(hotelsByCity).hasSize(3);
        assertThat(hotelsByCountry).hasSize(2);
        assertThat(hotelsByCountry.get("USA")).isEqualTo(10);
        assertThat(hotelsByCountry.get("UK")).isEqualTo(5);
        assertThat(hotelsByAmenity).hasSize(12);
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
}
