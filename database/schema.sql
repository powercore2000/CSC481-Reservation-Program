/* ------------------ CLEAN OLD TABLES (safe if missing) ------------------ */
/* Drop children first because of foreign keys */
DROP TABLE IF EXISTS reservation_food;
DROP TABLE IF EXISTS restaurant_food;
DROP TABLE IF EXISTS restaurant_tags;
DROP TABLE IF EXISTS food;

DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS restaurant_schedules;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS app_users;

/* ------------------ SQLITE SCHEMA ------------------ */

/* APP USERS */
CREATE TABLE IF NOT EXISTS app_users
(
  id            INTEGER PRIMARY KEY AUTOINCREMENT,
  full_name     VARCHAR(120) NOT NULL,
  email         VARCHAR(120) NOT NULL,
  phone         VARCHAR(32),
  password_hash VARCHAR(255) NOT NULL,
  created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (email)
);

/* RESTAURANTS */
CREATE TABLE IF NOT EXISTS restaurants
(
  id         INTEGER PRIMARY KEY AUTOINCREMENT,
  name       VARCHAR(120) NOT NULL,
  address    VARCHAR(255),
  city       VARCHAR(100),
  state      VARCHAR(32),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP
);

/* WEEKLY SCHEDULE (0=Mon … 6=Sun) */
CREATE TABLE IF NOT EXISTS restaurant_schedules
(
  id            INTEGER PRIMARY KEY AUTOINCREMENT,
  restaurant_id INTEGER NOT NULL,
  weekday       SMALLINT NOT NULL,            -- 0..6
  open_time     TIME NOT NULL,
  close_time    TIME NOT NULL,
  CONSTRAINT fk_sched_rest FOREIGN KEY (restaurant_id) REFERENCES restaurants(id),
  CONSTRAINT uq_sched_rest_day UNIQUE (restaurant_id, weekday)
);

/* FOOD MENU ITEMS */
CREATE TABLE IF NOT EXISTS food
(
  id           INTEGER PRIMARY KEY AUTOINCREMENT,
  name         VARCHAR(120) NOT NULL,
  description  TEXT,
  price_cents  INT NOT NULL,             -- store price as cents
  category     VARCHAR(64)               -- e.g. 'Entree', 'Dessert', 'Vegan'
);

/* TAGS PER RESTAURANT (Italian, Spicy, Vegan, etc.) */
CREATE TABLE IF NOT EXISTS restaurant_tags
(
  id             INTEGER PRIMARY KEY AUTOINCREMENT,
  restaurant_id  INTEGER NOT NULL,
  tag_name       VARCHAR(64) NOT NULL,
  CONSTRAINT fk_tag_rest FOREIGN KEY (restaurant_id) REFERENCES restaurants(id),
  CONSTRAINT uq_rest_tag UNIQUE (restaurant_id, tag_name)
);

/* WHICH RESTAURANT OFFERS WHICH FOOD ITEMS */
CREATE TABLE IF NOT EXISTS restaurant_food
(
  id             INTEGER PRIMARY KEY AUTOINCREMENT,
  restaurant_id  INTEGER NOT NULL,
  food_id        INTEGER NOT NULL,
  CONSTRAINT fk_rf_rest FOREIGN KEY (restaurant_id) REFERENCES restaurants(id),
  CONSTRAINT fk_rf_food FOREIGN KEY (food_id) REFERENCES food(id),
  CONSTRAINT uq_rest_food UNIQUE (restaurant_id, food_id)
);

/* RESERVATIONS (User ↔ Restaurant) */
CREATE TABLE IF NOT EXISTS reservations
(
  id                INTEGER PRIMARY KEY AUTOINCREMENT,
  user_id           INTEGER NOT NULL,
  restaurant_id     INTEGER NOT NULL,
  reservation_at    TIMESTAMP NOT NULL,    -- date + time combined
  party_size        INT NOT NULL,
  status            VARCHAR(16) NOT NULL,  -- PENDING | CONFIRMED | CANCELLED
  confirmation_code VARCHAR(32) NOT NULL,
  special_requests  TEXT,
  created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_res_user  FOREIGN KEY (user_id)       REFERENCES app_users(id),
  CONSTRAINT fk_res_rest  FOREIGN KEY (restaurant_id) REFERENCES restaurants(id),
  CONSTRAINT uq_res_confirmation UNIQUE (confirmation_code)
);

/* FOOD ATTACHED TO A RESERVATION (MANY-TO-MANY) */
CREATE TABLE IF NOT EXISTS reservation_food
(
  id              INTEGER PRIMARY KEY AUTOINCREMENT,
  reservation_id  INTEGER NOT NULL,
  food_id         INTEGER NOT NULL,
  quantity        INT NOT NULL DEFAULT 1,
  CONSTRAINT fk_rf_res FOREIGN KEY (reservation_id) REFERENCES reservations(id),
  CONSTRAINT fk_rf_food2 FOREIGN KEY (food_id)       REFERENCES food(id)
);

/* Helpful indexes */
CREATE INDEX IF NOT EXISTS idx_res_user_time   ON reservations (user_id, reservation_at);
CREATE INDEX IF NOT EXISTS idx_res_rest_time   ON reservations (restaurant_id, reservation_at);
CREATE INDEX IF NOT EXISTS idx_tag_name        ON restaurant_tags (tag_name);
CREATE INDEX IF NOT EXISTS idx_res_food_res    ON reservation_food (reservation_id);
CREATE INDEX IF NOT EXISTS idx_res_food_food   ON reservation_food (food_id);