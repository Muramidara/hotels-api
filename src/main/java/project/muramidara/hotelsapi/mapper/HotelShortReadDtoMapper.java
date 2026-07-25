package project.muramidara.hotelsapi.mapper;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import project.muramidara.hotelsapi.database.entity.Hotel;
import project.muramidara.hotelsapi.dto.AddressDto;
import project.muramidara.hotelsapi.dto.HotelShortReadDto;

@Component
@RequiredArgsConstructor
public class HotelShortReadDtoMapper implements Mapper<Hotel, HotelShortReadDto> {
    private final AddressDtoMapper addressDtoMapper;
    private final ContactsDtoMapper contactsDtoMapper;

    @Override
    public HotelShortReadDto map(Hotel hotel) {
        var addressDto = addressDtoMapper.map(hotel.getAddress());
        var contactsDto = contactsDtoMapper.map(hotel.getContacts());
        return HotelShortReadDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .address(buildAddress(addressDto))
                .phone(contactsDto.getPhone())
                .build();
    }

    private static String buildAddress(AddressDto addressDto) {
        return String.join(", ",
                addressDto.getHouseNumber() + addressDto.getStreet(),
                addressDto.getCity(), addressDto.getPostCode()
                , addressDto.getCountry());
    }
}
