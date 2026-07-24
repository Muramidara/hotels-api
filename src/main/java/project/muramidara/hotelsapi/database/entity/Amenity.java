package project.muramidara.hotelsapi.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"hotelAmenities"})
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, length = 64)
    String name;
    @Builder.Default
    @OneToMany(mappedBy = "amenity", fetch = FetchType.LAZY)
    List<HotelAmenity> hotelAmenities = new ArrayList<>();
}
