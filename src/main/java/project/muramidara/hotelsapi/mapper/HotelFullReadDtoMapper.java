package project.muramidara.hotelsapi.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.muramidara.hotelsapi.database.entity.Hotel;
import project.muramidara.hotelsapi.database.entity.HotelAmenity;
import project.muramidara.hotelsapi.dto.HotelFullReadDto;
import project.muramidara.hotelsapi.dto.HotelShortReadDto;

@Component
@RequiredArgsConstructor
public class HotelFullReadDtoMapper implements Mapper<Hotel, HotelFullReadDto>{
    private final AddressDtoMapper addressDtoMapper;
    private final ContactsDtoMapper contactsDtoMapper;
    private final AmenityDtoMapper amenityDtoMapper;
    private final ArrivalTimeDtoMapper arrivalTimeDtoMapper;

    @Override
    public HotelFullReadDto map(Hotel hotel) {
        var addressDto = addressDtoMapper.map(hotel.getAddress());
        var contactsDto = contactsDtoMapper.map(hotel.getContacts());
        var amenities = hotel.getAmenities().stream().map(HotelAmenity::getAmenity).toList();
        var amenityDtos = amenityDtoMapper.map(amenities);
        var arrivalTime = arrivalTimeDtoMapper.map(hotel.getArrivalTime());
        return HotelFullReadDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .brand(hotel.getBrand())
                .description(hotel.getDescription())
                .address(addressDto)
                .contacts(contactsDto)
                .arrivalTime(arrivalTime)
                .amenities(amenityDtos)
                .build();
    }
}
