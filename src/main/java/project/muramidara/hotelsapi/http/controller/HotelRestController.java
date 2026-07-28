package project.muramidara.hotelsapi.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import project.muramidara.hotelsapi.dto.*;
import project.muramidara.hotelsapi.exception.HotelNotFoundException;
import project.muramidara.hotelsapi.service.HotelService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/property-view")
@RequiredArgsConstructor
public class HotelRestController {
    private final HotelService hotelService;

    @GetMapping("/hotels")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(hotelService.findAll());
    }

    @GetMapping("/hotels/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            var dto = hotelService.findById(id);
            return ResponseEntity.ok(dto.get());
        } catch (HotelNotFoundException e) {
            return ResponseEntity.status(404).body(
                    ErrorDto.builder()
                            .status(404)
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    //TODO: make method more flexible
    @GetMapping("/search")
    public ResponseEntity<?> findByFilter(@RequestParam(required = false) String name,
                                            @RequestParam(required = false) String brand,
                                            @RequestParam(required = false) String city,
                                            @RequestParam(required = false) String country,
                                            @RequestParam(required = false) String amenity) {
        List<HotelShortReadDto> dtos = new ArrayList<>();
        if (name != null) {
            dtos = hotelService.findAllByFilter("name", name);
        }
        if (brand != null) {
            dtos = hotelService.findAllByFilter("brand", brand);
        }
        if (city != null) {
            dtos = hotelService.findAllByFilter("city", city);
        }
        if (country != null) {
            dtos = hotelService.findAllByFilter("country", country);
        }
        if (amenity != null) {
            dtos = hotelService.findAllByFilter("amenity", amenity);
        }
        if (dtos.isEmpty()) return ResponseEntity.badRequest().body("Filter not found" +
                " or no objects are matching the given filter");
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/hotels")
    public ResponseEntity<?> create(@RequestBody HotelCreateEditDto dto) {
        return ResponseEntity.ok( hotelService.create(dto));
    }

    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity<?> addAmenities(@PathVariable("id") Long id, @RequestBody List<AmenityDto> dtos) {
        try {
            var response = hotelService.addAmenities(id, dtos);
            return ResponseEntity.ok(response);
        } catch (HotelNotFoundException e) {
            return ResponseEntity.status(404).body(
                    ErrorDto.builder()
                            .status(404)
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    @GetMapping("/histogram/{param}")
    public ResponseEntity<?> findAllGroupByFilter(@PathVariable("param") String filter) {
        var response = hotelService.findAllGroupByFilter(filter);
        if (response.isEmpty()) return ResponseEntity.badRequest().body("Filter not found" +
                " or no objects are matching the given filter");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/hotels/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody HotelCreateEditDto dto) {
        try {
            var response = hotelService.update(id, dto);
            return ResponseEntity.ok(response);
        } catch (HotelNotFoundException e) {
            return ResponseEntity.status(404).body(
                    ErrorDto.builder()
                            .status(404)
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    @DeleteMapping("/hotels/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            var response = hotelService.delete(id);
            return ResponseEntity.ok(response);
        } catch (HotelNotFoundException e) {
            return ResponseEntity.status(404).body(
                    ErrorDto.builder()
                            .status(404)
                            .message(e.getMessage())
                            .build()
            );
        }
    }
}
