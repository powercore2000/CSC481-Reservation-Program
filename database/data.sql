-- APP_USERS
INSERT INTO app_users (full_name, email, phone, password_hash)
VALUES
  ('Jonathan Martinez', 'jonathan.martinez@example.com', '555-0101', 'hash1'),
  ('Samuel Omenwu',     'samuel.omenwu@example.com',     '555-0102', 'hash2'),
  ('Daniel Anzora',     'daniel.anzora@example.com',     '555-0103', 'hash3'),
  ('Corvalis Cohen',    'corvalis.cohen@example.com',    '555-0104', 'hash4'),
  ('Eric Cordova',      'eric.cordova@example.com',      '555-0105', 'hash5');

-- RESTAURANTS
INSERT INTO restaurants (name, address, city, state)
VALUES
  ('Cedar Grill',  '123 Main St', 'Claremont', 'CA'),
  ('Vista Patio',  '77 Oak Ave',  'Pomona',    'CA'),
  ('Golden Spoon', '55 Pine Rd',  'Upland',    'CA');

-- WEEKLY SCHEDULES (Mon–Sun)
INSERT INTO restaurant_schedules (restaurant_id, weekday, open_time, close_time)
VALUES
  (1,0,'10:00:00','22:00:00'),(1,1,'10:00:00','22:00:00'),(1,2,'10:00:00','22:00:00'),
  (1,3,'10:00:00','22:00:00'),(1,4,'10:00:00','23:00:00'),(1,5,'09:00:00','23:00:00'),
  (1,6,'09:00:00','21:00:00'),
  (2,0,'09:00:00','21:00:00'),(2,1,'09:00:00','21:00:00'),(2,2,'09:00:00','21:00:00'),
  (2,3,'09:00:00','21:00:00'),(2,4,'09:00:00','23:00:00'),(2,5,'08:00:00','23:00:00'),
  (2,6,'08:00:00','20:00:00');

-- RESERVATIONS
INSERT INTO reservations (user_id, restaurant_id, reservation_at, party_size, status, confirmation_code, special_requests)
VALUES
  (1, 1, '2025-10-20 18:00:00', 4, 'CONFIRMED', 'AB7K2F', 'Window seat'),
  (2, 1, '2025-10-21 19:30:00', 2, 'PENDING',   'XZ4M9Q', NULL),
  (3, 2, '2025-10-22 17:45:00', 3, 'CONFIRMED', 'QR8Z7J', 'Birthday dinner'),
  (4, 3, '2025-10-23 18:15:00', 5, 'CONFIRMED', 'LM4T2P', NULL),
  (5, 1, '2025-10-24 19:00:00', 2, 'CANCELLED', 'JK9N8C', 'Allergic to nuts');
