package project.muramidara.hotelsapi.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HotelShortReadDto {
    Long id;
    String name;
    String description;
    String brand;
    String address;
    String phone;
}
