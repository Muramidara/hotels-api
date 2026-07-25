package project.muramidara.hotelsapi.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HotelShortReadDto {
    String name;
    String description;
    String brand;
    String address;
    String phone;
}
