CREATE TABLE lists (
    id SERIAL NOT NULL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    status BOOLEAN NOT NULL,
    workspace_id INT NOT NULL,
    FOREIGN KEY (workspace_id) REFERENCES workspaces(id)
)