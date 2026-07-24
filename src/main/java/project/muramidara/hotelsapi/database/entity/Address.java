package project.muramidara.hotelsapi.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "house_number", nullable = false)
    Integer houseNumber;
    @Column(nullable = false, length = 64)
    String street;
    @Column(nullable = false, length = 64)
    String city;
    @Column(nullable = false, length = 64)
    String country;
    @Column(name = "post_code",nullable = false, length = 64)
    String postCode;
}
