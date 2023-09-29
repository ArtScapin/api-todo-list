CREATE TABLE items (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    status BOOLEAN NOT NULL,
    list_id INT NOT NULL,
    FOREIGN KEY (list_id) REFERENCES lists(id)
)