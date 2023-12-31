DROP ALL OBJECTS;

CREATE TABLE IF NOT EXISTS statuses
(
    id BIGINT PRIMARY KEY,
    name VARCHAR NOT NULL
);
CREATE TABLE IF NOT EXISTS employee
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    title VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS suggestions
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    employe_id BIGINT NOT NULL REFERENCES employee(id) ON DELETE CASCADE,
    topic VARCHAR(60),
    content VARCHAR  NOT NULL,
    last_update TIMESTAMP NOT NULL,
    status_id BIGINT NOT NULL REFERENCES statuses(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS reviews
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    employe_id BIGINT NOT NULL REFERENCES employee(id) ON DELETE CASCADE,
    suggestion_id BIGINT NOT NULL REFERENCES suggestions(id) ON DELETE CASCADE,
    content VARCHAR NOT NULL,
    approve BOOLEAN
);