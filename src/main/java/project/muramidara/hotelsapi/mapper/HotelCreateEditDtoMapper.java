package project.muramidara.hotelsapi.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.muramidara.hotelsapi.database.entity.Hotel;
import project.muramidara.hotelsapi.dto.HotelCreateEditDto;

@Component
@RequiredArgsConstructor
public class HotelCreateEditDtoMapper implements Mapper<HotelCreateEditDto, Hotel> {
    private final AddressDtoMapper addressDtoMapper;
    private final ContactsDtoMapper contactsDtoMapper;
    private final ArrivalTimeDtoMapper arrivalTimeDtoMapper;

    @Override
    public Hotel map(HotelCreateEditDto dto) {
        var address = addressDtoMapper.mapFrom(dto.getAddress());
        var contacts = contactsDtoMapper.mapFrom(dto.getContacts());
        var arrivalTime = arrivalTimeDtoMapper.mapFrom(dto.getArrivalTime());
        var hotel = Hotel.builder()
                .name(dto.getName())
                .brand(dto.getBrand())
                .description(dto.getDescription())
                .address(address)
                .contacts(contacts)
                .arrivalTime(arrivalTime)
                .build();
                return hotel;
    }

    public Hotel map(HotelCreateEditDto dto, Hotel hotel) {
        var address = addressDtoMapper.mapFrom(dto.getAddress());
        var contacts = contactsDtoMapper.mapFrom(dto.getContacts());
        var arrivalTime = arrivalTimeDtoMapper.mapFrom(dto.getArrivalTime());
        hotel.setName(dto.getName());
        hotel.setBrand(dto.getBrand());
        hotel.setDescription(dto.getDescription());
        hotel.setAddress(address);
        hotel.setContacts(contacts);
        hotel.setArrivalTime(arrivalTime);
        return hotel;
    }
}
