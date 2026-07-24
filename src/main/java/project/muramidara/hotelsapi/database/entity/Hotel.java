package project.muramidara.hotelsapi.database.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString(exclude = {"amenities"})
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, length = 128)
    String name;
    @Column(nullable = false,  columnDefinition = "TEXT")
    String description;
    @Column(nullable = false, length = 64)
    String brand;
    @JoinColumn(name = "address_id")
    @OneToOne
    Address address;
    @JoinColumn(name = "contacts_id")
    @OneToOne
    Contacts contacts;
    @JoinColumn(name = "arrival_time_id")
    @OneToOne
    ArrivalTime arrivalTime;
    @Builder.Default
    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    List<HotelAmenity> amenities = new ArrayList<>();

}
