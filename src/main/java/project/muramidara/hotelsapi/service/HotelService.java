package project.muramidara.hotelsapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.muramidara.hotelsapi.database.entity.Amenity;
import project.muramidara.hotelsapi.database.entity.Hotel;
import project.muramidara.hotelsapi.database.entity.HotelAmenity;
import project.muramidara.hotelsapi.database.repository.*;
import project.muramidara.hotelsapi.dto.AmenityDto;
import project.muramidara.hotelsapi.dto.HotelCreateEditDto;
import project.muramidara.hotelsapi.dto.HotelFullReadDto;
import project.muramidara.hotelsapi.dto.HotelShortReadDto;
import project.muramidara.hotelsapi.mapper.AmenityDtoMapper;
import project.muramidara.hotelsapi.mapper.HotelCreateEditDtoMapper;
import project.muramidara.hotelsapi.mapper.HotelFullReadDtoMapper;
import project.muramidara.hotelsapi.mapper.HotelShortReadDtoMapper;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelAmenityRepository hotelAmenityRepository;
    private final AmenityRepository amenityRepository;
    private final ContactsRepository contactsRepository;
    private final ArrivalTimeRepository arrivalTimeRepository;
    private final AddressRepository addressRepository;
    private final HotelShortReadDtoMapper hotelShortReadDtoMapper;
    private final HotelFullReadDtoMapper hotelFullReadDtoMapper;
    private final HotelCreateEditDtoMapper hotelCreateEditDtoMapper;
    private final AmenityDtoMapper amenityDtoMapper;

    public List<HotelShortReadDto> findAll() {
        var hotels = hotelRepository.findAll();
        var hotelDtos = hotels.stream()
                .map(hotelShortReadDtoMapper::map).toList();
        return hotelDtos;
    }

    public Optional<HotelFullReadDto> findById(Long id) {
        var hotel = hotelRepository.findById(id);
        var hotelDto = hotel.map(hotelFullReadDtoMapper::map);
        return hotelDto;
    }

    //TODO: remake method using Querydsl
    public List<HotelShortReadDto> findAllByFilter(String filterName, String filterValue) {
        List<Hotel> hotels = new ArrayList<>();
        switch (filterName) {
            case "name":
                hotels = hotelRepository.findAllByNameIgnoreCase(filterValue);
                break;
            case "brand":
                hotels = hotelRepository.findAllByBrandIgnoreCase(filterValue);
                break;
            case "city":
                hotels = hotelRepository.findAllByCity(filterValue);
                break;
            case "country":
                hotels = hotelRepository.findAllByCountry(filterValue);
                break;
            //TODO: replace with findAllByAmenities()
            //TODO: move this method to AmenityService?
            case "amenity":

                var hotelAmenities = amenityRepository.findAmenityByName(filterValue).map(Amenity::getHotelAmenities);
                hotels = hotelAmenities.map(amenities -> amenities.stream().map(HotelAmenity::getHotel).toList()).orElseGet(ArrayList::new);
                break;
            default:
                break;
        }
        List<HotelShortReadDto> dtos = hotels.stream().map(hotelShortReadDtoMapper::map).toList();
        return dtos;
    }

    @Transactional(readOnly = false)
    public HotelShortReadDto create(HotelCreateEditDto dto) {
        var hotel = hotelCreateEditDtoMapper.map(dto);
//        addressRepository.save(hotel.getAddress());
//        arrivalTimeRepository.save(hotel.getArrivalTime());
//        contactsRepository.save(hotel.getContacts());
        hotel = hotelRepository.save(hotel);
        var responseDto = hotelShortReadDtoMapper.map(hotel);
        return responseDto;
    }

    @Transactional(readOnly = false)
    public HotelFullReadDto addAmenities(Long id, List<AmenityDto> dtos) {
        HotelFullReadDto hotelDto = null;
        var hotel = hotelRepository.findById(id);
        hotel.ifPresent(h -> {
            var amenities = dtos.stream().map(amenityDtoMapper::mapFrom).toList();
            amenities.forEach(amenity -> {
                var hotelAmenity = new HotelAmenity();
                var maybeExistingAmenity = amenityRepository.findAmenityByName(amenity.getName()).orElse(amenity);
                if (maybeExistingAmenity.getId() == null) {
                    amenity = amenityRepository.save(amenity);
                } else {
                    amenity = maybeExistingAmenity;
                }
                hotelAmenity.setAmenity(amenity);
                hotelAmenity.setHotel(h);
                hotelAmenityRepository.save(hotelAmenity);
            });
        });
        if (hotel.isPresent()) {
            hotelDto = hotelFullReadDtoMapper.map(hotel.get());
        }
        return hotelDto;
    }

    //TODO: remake method using Querydsl
    public Map<String, Long> findAllGroupByFilter(String filter) {
        var hotels = hotelRepository.findAll();
        Map<String, Long> map = new HashMap<>();
        switch (filter) {
            case "brand":
                map = hotels.stream().collect(
                        Collectors.groupingBy(Hotel::getBrand, Collectors.counting()));
                break;
            case "city":
                map = hotels.stream().collect(
                        Collectors.groupingBy(
                                hotel -> hotel.getAddress().getCity(),
                                Collectors.counting()));

                break;
            case "country":
                map = hotels.stream().collect(
                        Collectors.groupingBy(
                                hotel -> hotel.getAddress().getCountry(),
                                Collectors.counting()));

                break;
            //TODO: move this method to AmenityService?
            //TODO: replace with findAllByAmenities()
            case "amenity":
                var amenities = amenityRepository.findAll();
                map = amenities.stream().collect(
                        Collectors.toMap(
                                Amenity::getName,
                                amenity -> Long.valueOf(amenity.getHotelAmenities().size())
                                ));
                break;
            default:
                break;
        }
        return map;
    }

    //TODO: make amenities also updatable
    public HotelFullReadDto update(Long id, HotelCreateEditDto dto){
        var optionalHotelInDatabase = hotelRepository.findById(id);
        if(optionalHotelInDatabase.isEmpty()) return null;
        Hotel hotelInDatabase = optionalHotelInDatabase.get();
        var hotelWithUpdates = hotelCreateEditDtoMapper.map(dto, hotelInDatabase);

        addressRepository.save(hotelWithUpdates.getAddress());
        arrivalTimeRepository.save(hotelWithUpdates.getArrivalTime());
        contactsRepository.save(hotelWithUpdates.getContacts());
        hotelRepository.save(hotelWithUpdates);
        var responseDto = hotelFullReadDtoMapper.map(hotelWithUpdates);
        return responseDto;
    }

    public boolean delete(Long id){
        if(hotelRepository.findById(id).isPresent()){
            hotelRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
