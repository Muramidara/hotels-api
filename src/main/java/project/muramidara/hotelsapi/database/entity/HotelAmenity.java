package project.muramidara.hotelsapi.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"hotel", "amenity"})
public class HotelAmenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    Hotel hotel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "amenity_id")
    Amenity amenity;

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
        hotel.getAmenities().add(this);
    }

    public void setAmenity(Amenity amenity) {
        this.amenity = amenity;
        amenity.getHotelAmenities().add(this);
    }
}
