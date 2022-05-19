CREATE TABLE cars (
    id    integer PRIMARY KEY,
    brand text    not null,
    model text,
    cost  integer not null
);

CREATE TABLE users (
    id      integer PRIMARY KEY,
    name    text    not null,
    age     integer,
    license boolean not null,
    car_id  integer REFERENCES cars (id)
);