CREATE TABLE peoples (id BIGSERIAL PRIMARY KEY, name varchar, age int, driver_license BOOLEAN, car_id BIGSERIAL REFERENCES cars (id));

CREATE TABLE cars (id BIGSERIAL PRIMARY KEY, car_make varchar, model varchar, price MONEY);