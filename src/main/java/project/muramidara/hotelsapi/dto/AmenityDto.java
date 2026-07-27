package project.muramidara.hotelsapi.dto;

import lombok.Data;

@Data
public class AmenityDto {
    String name;

    public AmenityDto(String name) {
        this.name = name;
    }
}
