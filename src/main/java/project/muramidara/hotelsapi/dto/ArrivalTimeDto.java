package project.muramidara.hotelsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ArrivalTimeDto {
    String checkIn;
    String checkOut;
}
