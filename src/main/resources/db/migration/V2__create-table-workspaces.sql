CREATE TABLE workspaces (
    id SERIAL NOT NULL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
)