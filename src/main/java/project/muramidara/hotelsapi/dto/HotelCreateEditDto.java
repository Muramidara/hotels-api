package project.muramidara.hotelsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;
@Builder
@AllArgsConstructor
@Value
public class HotelCreateEditDto {
    String name;
    String description;
    String brand;
    AddressDto address;
    ContactsDto contacts;
    ArrivalTimeDto arrivalTime;
}
