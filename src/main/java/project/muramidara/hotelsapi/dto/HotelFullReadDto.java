package project.muramidara.hotelsapi.dto;

import lombok.Builder;
import lombok.Data;
import project.muramidara.hotelsapi.database.entity.HotelAmenity;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class HotelFullReadDto {
    Long id;
    String name;
    String description;
    String brand;
    AddressDto address;
    ContactsDto contacts;
    ArrivalTimeDto arrivalTime;
    List<AmenityDto> amenities;
}
