package project.muramidara.hotelsapi.mapper;

import org.springframework.stereotype.Component;
import project.muramidara.hotelsapi.database.entity.ArrivalTime;
import project.muramidara.hotelsapi.dto.ArrivalTimeDto;

@Component
public class ArrivalTimeDtoMapper implements BiDirectionalMapper<ArrivalTime, ArrivalTimeDto> {
    @Override
    public ArrivalTimeDto map(ArrivalTime entity) {
        return new ArrivalTimeDto(
                entity.getCheckIn(),
                entity.getCheckOut()
        );
    }

    @Override
    public ArrivalTime mapFrom(ArrivalTimeDto dto) {
        var arrivalTime = new ArrivalTime();
        arrivalTime.setCheckIn(dto.getCheckIn());
        arrivalTime.setCheckOut(dto.getCheckOut());
        return arrivalTime;
    }
}
