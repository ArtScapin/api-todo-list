CREATE TABLE lists (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    status BOOLEAN NOT NULL,
    position INT NOT NULL DEFAULT 0,
    color VARCHAR(20),
    workspace_id INT NOT NULL,
    FOREIGN KEY (workspace_id) REFERENCES workspaces(id) ON DELETE CASCADE
)
