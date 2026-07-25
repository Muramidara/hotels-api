package project.muramidara.hotelsapi.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.muramidara.hotelsapi.database.entity.Amenity;
import project.muramidara.hotelsapi.database.entity.Hotel;
import project.muramidara.hotelsapi.database.repository.AmenityRepository;
import project.muramidara.hotelsapi.dto.AmenityDto;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AmenityDtoMapper implements BiDirectionalMapper<Amenity, AmenityDto> {

    @Override
    public AmenityDto map(Amenity amenity) {
        return new AmenityDto(amenity.getName());
    }

    @Override
    public Amenity mapFrom( AmenityDto dto) {
        var newAmenity = new Amenity();
        newAmenity.setName(dto.getName());
        return newAmenity;
    }

    public List<AmenityDto> map(List<Amenity> amenities){
        return amenities.stream().map(this::map).toList();
    }

    public List<Amenity> mapFrom(List<AmenityDto> amenityDtos){
        return amenityDtos.stream().map(this::mapFrom).toList();
    }
}
