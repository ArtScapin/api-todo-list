INSERT INTO users (
                   name,
                   username,
                   password,
                   permission
                   ) VALUES (
                             'Administrador',
                             'adm',
                             '$2a$10$ttMf2NiRyP5rxlKX2eqp5.LM.HW6bV.pjEO6VkY2vkaPFuwpyVWQW',
                             'ADM'
                            );

INSERT INTO workspaces (
                        name,
                        user_id
                        ) VALUES (
                                  'Projeto Teste',
                                  1
                                 );

INSERT INTO lists (
                   name,
                   status,
                   workspace_id
                   ) VALUES (
                             'Backend',
                             false,
                             1
                            );

INSERT INTO lists (
                   name,
                   status,
                   workspace_id
                   ) VALUES (
                             'Frontend',
                             false,
                             1
                             );

INSERT INTO items (
                   name,
                   status,
                   list_id
                   ) VALUES (
                             'Finalizar testes na api',
                             true,
                             1
                            );

INSERT INTO items (
                   name,
                   status,
                   list_id
                   ) VALUES (
                             'Fazer migration para inserir dados de teste',
                             false,
                             1
                             );

INSERT INTO items (
                   name,
                   status,
                   list_id
                   ) VALUES (
                             'Projetar telas',
                             true,
                             2
                             );


INSERT INTO items (
                   name,
                   status,
                   list_id
                   ) VALUES (
                             'Desenvolver telas com angular',
                             false,
                             2
                             );