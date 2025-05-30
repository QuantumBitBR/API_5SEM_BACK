INSERT INTO DIM_PROJETO (id, nome) VALUES (3000, 'Novo projeto');
INSERT INTO DIM_SPRINT (id, nome) VALUES (3000, 'SPRINT-01');
INSERT INTO DIM_STATUS (id, tipo) VALUES (3000, 'CRIADO');
INSERT INTO DIM_USUARIO (id, nome, email, senha, id_gestor, require_reset, is_enable, role) VALUES (3000, 'Joao', 'admin3@gmail.com', '$2a$10$/tH4Y4bUnfJrJTNbsLvyROHcB9qxlvq1nSM4Xu5DgUUDBzf5g2x2q', null, false, true, 'ADMIN');

INSERT INTO DIM_USER_STORY (id, assunto, criado_em, finalizado_em, bloqueado, encerrado, data_limite, id_status, id_sprint, id_taiga, id_usuario, id_projeto)
VALUES (3000, 'Task 1', '2025-01-01', '2025-01-02', false, true, '2025-01-10', 3000, 3000, 3000, 3000, 3000)