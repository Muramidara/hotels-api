package project.muramidara.hotelsapi.mapper;

import org.springframework.stereotype.Component;
import project.muramidara.hotelsapi.database.entity.Address;
import project.muramidara.hotelsapi.dto.AddressDto;

@Component
public class AddressDtoMapper implements BiDirectionalMapper<Address, AddressDto> {
    @Override
    public AddressDto map(Address address) {
        return AddressDto.builder()
                .houseNumber(address.getHouseNumber())
                .street(address.getStreet())
                .city(address.getCity())
                .country(address.getCountry())
                .postCode(address.getPostCode())
                .build();
    }

    @Override
    public Address mapFrom(AddressDto dto) {
        return Address.builder()
                .houseNumber(dto.getHouseNumber())
                .street(dto.getStreet())
                .city(dto.getCity())
                .country(dto.getCountry())
                .postCode(dto.getPostCode())
                .build();
    }
}
