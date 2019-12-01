DROP TABLE IF EXISTS city_weather;

CREATE TABLE city_weather
(
    name varchar(255) NOT NULL,
    last_sync timestamp without time zone,
    status varchar(255),
    temp decimal,
    CONSTRAINT city_weather_pkey PRIMARY KEY (name)
)