-- ============================================================
-- 1. INSERT ADDRESSES (15 unique addresses)
--    NYC (hotels 1,4,5,12,15), LA (2,6,7,8,13), London (3,9,10,11,14)
-- ============================================================
INSERT INTO address (id, house_number, street, city, country, post_code)
VALUES (1, 123, 'Main Street', 'New York', 'USA', '10001'),
       (2, 456, 'Ocean Drive', 'Los Angeles', 'USA', '90291'),
       (3, 789, 'Baker Street', 'London', 'UK', 'W1U 6RD'),
       (4, 101, 'Broadway', 'New York', 'USA', '10002'),
       (5, 202, '5th Avenue', 'New York', 'USA', '10003'),
       (6, 303, 'Hollywood Boulevard', 'Los Angeles', 'USA', '90292'),
       (7, 404, 'Santa Monica Boulevard', 'Los Angeles', 'USA', '90293'),
       (8, 505, 'Rodeo Drive', 'Los Angeles', 'USA', '90294'),
       (9, 606, 'Westminster Avenue', 'London', 'UK', 'SW1A 1AA'),
       (10, 707, 'Tower Bridge Road', 'London', 'UK', 'SE1 2UP'),
       (11, 808, 'Covent Garden', 'London', 'UK', 'WC2E 8RA'),
       (12, 909, 'Greenwich Street', 'New York', 'USA', '10004'),
       (13, 111, 'Malibu Road', 'Los Angeles', 'USA', '90295'),
       (14, 222, 'Soho Square', 'London', 'UK', 'W1D 3QW'),
       (15, 333, 'Brooklyn Boulevard', 'New York', 'USA', '10005');

-- ============================================================
-- 2. INSERT CONTACTS (15 unique email/phone pairs)
-- ============================================================
INSERT INTO contacts (id, email, phone)
VALUES (1, 'grand.plaza@marriott.com', '+1-555-0101'),
       (2, 'sunset.inn@hilton.com', '+1-555-0102'),
       (3, 'royal.park@hyatt.com', '+44-20-7946-0958'),
       (4, 'times.square@marriott.com', '+1-555-0104'),
       (5, 'empire.lodge@ihg.com', '+1-555-0105'),
       (6, 'hollywood.hills@hilton.com', '+1-555-0106'),
       (7, 'santa.monica@marriott.com', '+1-555-0107'),
       (8, 'beverly.hills@fourseasons.com', '+1-555-0108'),
       (9, 'westminster.palace@hyatt.com', '+44-20-7946-0959'),
       (10, 'tower.bridge@hilton.com', '+44-20-7946-0960'),
       (11, 'covent.garden@ihg.com', '+44-20-7946-0961'),
       (12, 'greenwich.village@marriott.com', '+1-555-0112'),
       (13, 'malibu.coastal@fourseasons.com', '+1-555-0113'),
       (14, 'soho.house@hyatt.com', '+44-20-7946-0962'),
       (15, 'brooklyn.bridge@hilton.com', '+1-555-0115');

-- ============================================================
-- 3. INSERT ARRIVAL_TIMES (15 unique check-in/out times)
-- ============================================================
INSERT INTO arrival_time (id, check_in, check_out)
VALUES (1, '15:00', '11:00'),
       (2, '14:00', '10:00'),
       (3, '16:00', '12:00'),
       (4, '15:30', '11:30'),
       (5, '13:00', '09:00'),
       (6, '14:30', '10:30'),
       (7, '16:30', '12:30'),
       (8, '15:00', '10:00'),
       (9, '14:00', '11:00'),
       (10, '16:00', '10:00'),
       (11, '13:30', '09:30'),
       (12, '15:30', '11:00'),
       (13, '14:00', '09:00'),
       (14, '16:30', '11:30'),
       (15, '15:00', '12:00');

-- ============================================================
-- 4. INSERT AMENITIES (12 unique amenities)
-- ============================================================
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

-- ============================================================
-- 5. INSERT HOTELS (15 hotels, each with unique address/contacts/arrival_time)
-- ============================================================
INSERT INTO hotel (id, name, description, brand, address_id, contacts_id, arrival_time_id)
VALUES (1, 'Grand Plaza Downtown',
        'Luxury 5-star hotel with panoramic city views and a rooftop pool.',
        'Marriott', 1, 1, 1),
       (2, 'Sunset Inn Beach Resort',
        'Beachfront hotel offering stunning sunset views and direct beach access.',
        'Hilton', 2, 2, 2),
       (3, 'Royal Park Hotel',
        'Elegant boutique hotel located in the heart of the city center near Hyde Park.',
        'Hyatt', 3, 3, 3),
       (4, 'Times Square Suites',
        'Modern suites in the heart of Broadway, perfect for theater lovers.',
        'Marriott', 4, 4, 4),
       (5, 'Empire State Lodge',
        'Classic New York stay with stunning views of the Empire State Building.',
        'IHG', 5, 5, 5),
       (6, 'Hollywood Hills Hotel',
        'Nestled in the hills with panoramic views of the Hollywood sign and the city.',
        'Hilton', 6, 6, 6),
       (7, 'Santa Monica Pier Inn',
        'Relaxed coastal retreat just steps away from the famous Santa Monica Pier.',
        'Marriott', 7, 7, 7),
       (8, 'Beverly Hills Grand',
        'Ultra-luxury stay on Rodeo Drive, featuring world-class spa services.',
        'Four Seasons', 8, 8, 8),
       (9, 'Westminster Palace Hotel',
        'Historic hotel overlooking the Houses of Parliament and Big Ben.',
        'Hyatt', 9, 9, 9),
       (10, 'Tower Bridge Inn',
        'Contemporary hotel with spectacular views of the iconic Tower Bridge.',
        'Hilton', 10, 10, 10),
       (11, 'Covent Garden Boutique',
        'Stylish modern hotel surrounded by the theaters and markets of Covent Garden.',
        'IHG', 11, 11, 11),
       (12, 'Greenwich Village Inn',
        'Charming boutique hotel in the historic Greenwich Village district.',
        'Marriott', 12, 12, 12),
       (13, 'Malibu Coastal Resort',
        'Oceanfront villas with private beach access and breathtaking Pacific views.',
        'Four Seasons', 13, 13, 13),
       (14, 'Soho House Hotel',
        'Trendy art-deco hotel in the vibrant Soho district, popular with creatives.',
        'Hyatt', 14, 14, 14),
       (15, 'Brooklyn Bridge Hotel',
        'Budget-friendly option with spectacular views of the Brooklyn Bridge.',
        'Hilton', 15, 15, 15);

-- ============================================================
-- 6. INSERT HOTEL_AMENITY (60 entries, 4 per hotel)
-- ============================================================
INSERT INTO hotel_amenity (id, hotel_id, amenity_id)
VALUES
-- Hotel 1
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 1, 4),
-- Hotel 2
(5, 2, 5),
(6, 2, 6),
(7, 2, 7),
(8, 2, 8),
-- Hotel 3
(9, 3, 9),
(10, 3, 10),
(11, 3, 11),
(12, 3, 12),
-- Hotel 4
(13, 4, 3),
(14, 4, 6),
(15, 4, 8),
(16, 4, 10),
-- Hotel 5
(17, 5, 4),
(18, 5, 6),
(19, 5, 11),
(20, 5, 12),
-- Hotel 6
(21, 6, 1),
(22, 6, 6),
(23, 6, 8),
(24, 6, 10),
-- Hotel 7
(25, 7, 5),
(26, 7, 7),
(27, 7, 10),
(28, 7, 12),
-- Hotel 8
(29, 8, 2),
(30, 8, 3),
(31, 8, 4),
(32, 8, 11),
-- Hotel 9
(33, 9, 4),
(34, 9, 6),
(35, 9, 9),
(36, 9, 10),
-- Hotel 10
(37, 10, 1),
(38, 10, 6),
(39, 10, 8),
(40, 10, 11),
-- Hotel 11
(41, 11, 2),
(42, 11, 6),
(43, 11, 10),
(44, 11, 12),
-- Hotel 12
(45, 12, 3),
(46, 12, 8),
(47, 12, 11),
(48, 12, 12),
-- Hotel 13
(49, 13, 5),
(50, 13, 7),
(51, 13, 2),
(52, 13, 10),
-- Hotel 14
(53, 14, 1),
(54, 14, 6),
(55, 14, 9),
(56, 14, 11),
-- Hotel 15
(57, 15, 6),
(58, 15, 8),
(59, 15, 10),
(60, 15, 12);