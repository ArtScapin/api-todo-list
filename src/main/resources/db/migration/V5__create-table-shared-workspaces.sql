CREATE TABLE shared_workspaces (
    user_id INT NOT NULL,
    workspace_id INT NOT NULL,
    PRIMARY KEY (user_id, workspace_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (workspace_id) REFERENCES workspaces(id)
)