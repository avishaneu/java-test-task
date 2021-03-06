DROP TABLE IF EXISTS location;
CREATE TABLE location (
  id   INTEGER AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(200) NOT NULL,
  x    DOUBLE       NOT NULL,
  y    DOUBLE       NOT NULL
);

DROP TABLE IF EXISTS route;
CREATE TABLE route (
  id   INTEGER AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(200) NOT NULL,
  head INTEGER      NOT NULL,
  FOREIGN KEY (head) REFERENCES location (id)
);

DROP TABLE IF EXISTS route_location;
CREATE TABLE route_location (
  route_id       INTEGER NOT NULL,
  location_id    INTEGER NOT NULL,
  location_order INTEGER,
  FOREIGN KEY (route_id) REFERENCES route (id)
    ON DELETE CASCADE,
  FOREIGN KEY (location_id) REFERENCES location (id)
    ON DELETE CASCADE,
  PRIMARY KEY(route_id, location_id),
  CHECK (location_id <> (SELECT head FROM route r WHERE r.id = route_id ))
);

DROP TABLE IF EXISTS route_plan_generation_queue;
CREATE TABLE route_plan_generation_queue (
  route_id INTEGER PRIMARY KEY,
  queue_id INTEGER,
  state    INTEGER,
  FOREIGN KEY (route_id) REFERENCES route (id)
    ON DELETE CASCADE
);


