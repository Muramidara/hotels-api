package project.muramidara.hotelsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
@Builder
@AllArgsConstructor
public class HotelCreateEditDto {
    Long id;
    String name;
    String description;
    String brand;
    AddressDto address;
    ContactsDto contacts;
    ArrivalTimeDto arrivalTime;
}
