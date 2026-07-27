package project.muramidara.hotelsapi.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.server.ResponseStatusException;
import project.muramidara.hotelsapi.dto.AmenityDto;
import project.muramidara.hotelsapi.dto.HotelCreateEditDto;
import project.muramidara.hotelsapi.dto.HotelFullReadDto;
import project.muramidara.hotelsapi.dto.HotelShortReadDto;
import project.muramidara.hotelsapi.service.HotelService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/property-view")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @GetMapping("/hotels")
    public List<HotelShortReadDto> findAll(){
        return hotelService.findAll();
    }

    @GetMapping("/hotels/{id}")
    public HotelFullReadDto findById(@PathVariable("id") Long id){
        var dto = hotelService.findById(id);
        return dto.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    //TODO: make method more flexible
    @GetMapping("/search")
    public List<HotelShortReadDto> findById(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) String brand,
                                     @RequestParam(required = false) String city,
                                     @RequestParam(required = false) String country,
                                     @RequestParam(required = false) String amenity){
        List<HotelShortReadDto> dtos = new ArrayList<>();
        if(name != null){
            dtos = hotelService.findAllByFilter("name", name);
        }
        if(brand != null){
            dtos = hotelService.findAllByFilter("brand", brand);
        }
        if(city != null){
            dtos = hotelService.findAllByFilter("city", city);
        }
        if(country != null){
            dtos = hotelService.findAllByFilter("country", country);
        }
        if(amenity != null){
            dtos = hotelService.findAllByFilter("amenity", amenity);
        }
        if(dtos.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return dtos;
    }

    @PostMapping("/hotels")
    public HotelShortReadDto create(@RequestBody HotelCreateEditDto dto){
        return hotelService.create(dto);
    }

    @PostMapping("/hotels/{id}/amenities")
    public HotelFullReadDto create(@PathVariable("id") Long id, @RequestBody List<AmenityDto> dtos){
        var response = hotelService.addAmenities(id, dtos);
        if(response == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return response;
    }

    @GetMapping("/histogram/{param}")
    public Map<String , Long> findAllGroupByFilter(@PathVariable("param") String filter){
        var response = hotelService.findAllGroupByFilter(filter);
        if(response.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return response;
    }
}
