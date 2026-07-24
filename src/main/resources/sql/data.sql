-- ============================================
-- 1. INSERT INDEPENDENT TABLES
--    (Address, Contacts, Arrival Time, Amenity)
-- ============================================

INSERT INTO address (id, house_number, street, city, country, post_code)
VALUES (1, 123, 'Main Street', 'New York', 'USA', '10001'),
       (2, 456, 'Ocean Drive', 'Los Angeles', 'USA', '90291'),
       (3, 789, 'Baker Street', 'London', 'UK', 'W1U 6RD');

INSERT INTO contacts (id, email, phone)
VALUES (1, 'grand.plaza@marriott.com', '+1-555-0101'),
       (2, 'sunset.inn@hilton.com', '+1-555-0102'),
       (3, 'royal.park@hyatt.com', '+44-20-7946-0958');

INSERT INTO arrival_time (id, check_in, check_out)
VALUES (1, '15:00', '11:00'),
       (2, '14:00', '10:00'),
       (3, '16:00', '12:00');

-- Insert all unique amenities (12 items)
INSERT INTO amenity (id, name)
VALUES (1, 'Rooftop Pool'),
       (2, 'Spa & Wellness'),
       (3, 'Fitness Center'),
       (4, 'Business Lounge'),
       (5, 'Outdoor Pool'),
       (6, 'Free Wi-Fi'),
       (7, 'Beach Access'),
       (8, 'Parking'),
       (9, 'Indoor Pool'),
       (10, 'Restaurant'),
       (11, 'Pet Friendly'),
       (12, 'Airport Shuttle');

-- ============================================
-- 2. INSERT HOTEL (Depends on Address, Contacts, Arrival Time)
-- ============================================

INSERT INTO hotel (id, name, description, brand, address_id, contacts_id, arrival_time_id)
VALUES (1,
        'Grand Plaza Downtown',
        'Luxury 5-star hotel with panoramic city views and a rooftop pool.',
        'Marriott',
        1, -- address_id
        1, -- contacts_id
        1 -- arrival_time_id
       ),
       (2,
        'Sunset Inn Beach Resort',
        'Beachfront hotel offering stunning sunset views and direct beach access.',
        'Hilton',
        2, -- address_id
        2, -- contacts_id
        2 -- arrival_time_id
       ),
       (3,
        'Royal Park Hotel',
        'Elegant boutique hotel located in the heart of the city center.',
        'Hyatt',
        3, -- address_id
        3, -- contacts_id
        3 -- arrival_time_id
       );

-- ============================================
-- 3. INSERT HOTEL_AMENITY (Junction Table - Many-to-Many)
--    Links hotels to their specific amenities
-- ============================================

INSERT INTO hotel_amenity (id, hotel_id, amenity_id)
VALUES
-- Hotel 1 (Grand Plaza) gets 4 amenities
(1, 1, 1),   -- Rooftop Pool
(2, 1, 2),   -- Spa & Wellness
(3, 1, 3),   -- Fitness Center
(4, 1, 4),   -- Business Lounge

-- Hotel 2 (Sunset Inn) gets 4 amenities
(5, 2, 5),   -- Outdoor Pool
(6, 2, 6),   -- Free Wi-Fi
(7, 2, 7),   -- Beach Access
(8, 2, 8),   -- Parking

-- Hotel 3 (Royal Park) gets 4 amenities
(9, 3, 9),   -- Indoor Pool
(10, 3, 10), -- Restaurant
(11, 3, 11), -- Pet Friendly
(12, 3, 12); -- Airport Shuttle