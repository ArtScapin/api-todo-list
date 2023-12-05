CREATE TABLE users (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(50)NOT NULL,
    username VARCHAR(100)NOT NULL UNIQUE,
    password VARCHAR(60)NOT NULL ,
    permission varchar(4) NOT NULL
)