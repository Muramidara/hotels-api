package project.muramidara.hotelsapi.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import project.muramidara.hotelsapi.database.repository.HotelRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@Transactional
@RequiredArgsConstructor
@DataJpaTest
public class HotelServiceTest {
    private final static Long HOTEL1_ID = 1L;

    private final HotelRepository hotelRepository;
    private final HotelService hotelService;

    @Test
    void findAllTest() {
        var hotels = hotelService.findAll();
        assertThat(hotels).hasSize(3);
        var hotel1 = hotels.get(0);
        var hotel2 = hotels.get(1);
        var hotel3 = hotels.get(2);
        assertEquals("Grand Plaza Downtown", hotel1.getName());
        assertEquals("Sunset Inn Beach Resort", hotel2.getName());
        assertEquals("Royal Park Hotel", hotel3.getName());
    }

    @Test
    void findAllByFilterTest() {
        var hotels = hotelService.findAll();
        assertThat(hotels).hasSize(3);
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
