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
                   position,
                   color,
                   workspace_id
                   ) VALUES (
                             'Backend',
                             false,
                             0,
                             '#3B82F6',
                             1
                            );

INSERT INTO lists (
                   name,
                   status,
                   position,
                   color,
                   workspace_id
                   ) VALUES (
                             'Frontend',
                             false,
                             1,
                             '#A855F7',
                             1
                             );

INSERT INTO items (
                   name,
                   status,
                   priority,
                   position,
                   user_id,
                   list_id
                   ) VALUES (
                             'Finalizar testes na api',
                             true,
                             'HIGH',
                             0,
                             1,
                             1
                            );

INSERT INTO items (
                   name,
                   status,
                   priority,
                   position,
                   user_id,
                   list_id
                   ) VALUES (
                             'Fazer migration para inserir dados de teste',
                             false,
                             'MEDIUM',
                             1,
                             1,
                             1
                             );

INSERT INTO items (
                   name,
                   status,
                   priority,
                   position,
                   user_id,
                   list_id
                   ) VALUES (
                             'Projetar telas',
                             true,
                             'MEDIUM',
                             0,
                             1,
                             2
                             );


INSERT INTO items (
                   name,
                   status,
                   priority,
                   position,
                   user_id,
                   list_id
                   ) VALUES (
                             'Desenvolver telas com angular',
                             false,
                             'HIGH',
                             1,
                             1,
                             2
                             );
