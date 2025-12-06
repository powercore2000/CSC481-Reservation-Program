/* ---------- APP_USERS ---------- */
INSERT INTO app_users (full_name, email, phone, password_hash)
VALUES
  ('Jonathan Martinez', 'jonathan.martinez@example.com', '555-0101', 'hash1'),
  ('Samuel Omenwu',     'samuel.omenwu@example.com',     '555-0102', 'hash2'),
  ('Daniel Anzora',     'daniel.anzora@example.com',     '555-0103', 'hash3'),
  ('Corvalis Cohen',    'corvalis.cohen@example.com',    '555-0104', 'hash4'),
  ('Eric Cordova',      'eric.cordova@example.com',      '555-0105', 'hash5');


/* ---------- RESTAURANTS ---------- */
/* IDs will be 1, 2, 3 in this order */
INSERT INTO restaurants (name, address, city, state)
VALUES
  ('Cedar Grill',  '123 Main St', 'Claremont', 'CA'),
  ('Vista Patio',  '77 Oak Ave',  'Pomona',    'CA'),
  ('Golden Spoon', '55 Pine Rd',  'Upland',    'CA');


/* ---------- WEEKLY SCHEDULES (Mon–Sun) ---------- */
INSERT INTO restaurant_schedules (restaurant_id, weekday, open_time, close_time)
VALUES
  -- Cedar Grill (id = 1)
  (1,0,'10:00:00','22:00:00'),(1,1,'10:00:00','22:00:00'),(1,2,'10:00:00','22:00:00'),
  (1,3,'10:00:00','22:00:00'),(1,4,'10:00:00','23:00:00'),(1,5,'09:00:00','23:00:00'),
  (1,6,'09:00:00','21:00:00'),

  -- Vista Patio (id = 2)
  (2,0,'09:00:00','21:00:00'),(2,1,'09:00:00','21:00:00'),(2,2,'09:00:00','21:00:00'),
  (2,3,'09:00:00','21:00:00'),(2,4,'09:00:00','23:00:00'),(2,5,'08:00:00','23:00:00'),
  (2,6,'08:00:00','20:00:00'),

  -- Golden Spoon (id = 3) – brunch / dessert spot
  (3,0,'08:00:00','20:00:00'),(3,1,'08:00:00','20:00:00'),(3,2,'08:00:00','20:00:00'),
  (3,3,'08:00:00','20:00:00'),(3,4,'08:00:00','22:00:00'),(3,5,'08:00:00','22:00:00'),
  (3,6,'08:00:00','20:00:00');


/* ---------- RESTAURANT TAGS (for “what are you in the mood for?”) ---------- */
INSERT INTO restaurant_tags (restaurant_id, tag_name)
VALUES
  (1, 'Grill'),
  (1, 'American'),
  (1, 'Family Friendly'),
  (2, 'Mexican'),
  (2, 'Spicy'),
  (2, 'Patio'),
  (3, 'Dessert'),
  (3, 'Brunch'),
  (3, 'Cafe');


/* ---------- FOOD ITEMS (global) ---------- */
INSERT INTO food (name, description, price_cents, category)
VALUES
  ('Classic Burger',      'Beef burger with fries',                1499, 'Entree'),
  ('Grilled Chicken',     'Herb-marinated chicken breast',         1599, 'Entree'),
  ('Street Tacos',        'Three carne asada tacos',               1299, 'Entree'),
  ('Spicy Nachos',        'Loaded nachos with jalapeños',          1099, 'Appetizer'),
  ('Avocado Toast',       'Sourdough, avocado, and egg',           1199, 'Brunch'),
  ('Berry Pancakes',      'Stack of pancakes with berries',        1299, 'Brunch'),
  ('Chocolate Lava Cake', 'Warm chocolate cake with ice cream',     899, 'Dessert');


/* ---------- WHICH RESTAURANT SERVES WHICH FOOD ---------- */
/* Cedar Grill (1) */
INSERT INTO restaurant_food (restaurant_id, food_id) VALUES
  (1, 1),  -- Classic Burger
  (1, 2),  -- Grilled Chicken
  (1, 7);  -- Chocolate Lava Cake

/* Vista Patio (2) */
INSERT INTO restaurant_food (restaurant_id, food_id) VALUES
  (2, 3),  -- Street Tacos
  (2, 4),  -- Spicy Nachos
  (2, 7);  -- Chocolate Lava Cake

/* Golden Spoon (3) */
INSERT INTO restaurant_food (restaurant_id, food_id) VALUES
  (3, 5),  -- Avocado Toast
  (3, 6),  -- Berry Pancakes
  (3, 7);  -- Chocolate Lava Cake


/* ---------- RESERVATIONS ---------- */
/* These will get ids 1..5 in insert order */
INSERT INTO reservations (user_id, restaurant_id, reservation_at, party_size, status, confirmation_code, special_requests)
VALUES
  (1, 1, '2025-10-20 18:00:00', 4, 'CONFIRMED', 'AB7K2F', 'Window seat'),
  (2, 1, '2025-10-21 19:30:00', 2, 'PENDING',   'XZ4M9Q', NULL),
  (3, 2, '2025-10-22 17:45:00', 3, 'CONFIRMED', 'QR8Z7J', 'Birthday dinner'),
  (4, 3, '2025-10-23 18:15:00', 5, 'CONFIRMED', 'LM4T2P', NULL),
  (5, 1, '2025-10-24 19:00:00', 2, 'CANCELLED', 'JK9N8C', 'Allergic to nuts');


/* ---------- RESERVATION ↔ FOOD (pre-ordered items) ---------- */
/* assumes reservation ids = 1..5, food ids = 1..7 */
INSERT INTO reservation_food (reservation_id, food_id, quantity)
VALUES
  (1, 1, 2),  -- Jonathan orders 2 Classic Burgers
  (1, 7, 1),  -- plus one Lava Cake
  (2, 2, 1),  -- Samuel’s table one Grilled Chicken
  (3, 3, 3),  -- Daniel’s group 3x Street Tacos
  (4, 5, 2),  -- Corvalis brunch Avocado Toast x2
  (4, 7, 3),  -- and 3 desserts
  (5, 1, 1);  -- Eric had pre-ordered a burger before cancel