-- Usuários
INSERT INTO dim_usuario (id, nome, email, senha, role, is_enable)
VALUES (1, 'Gabriel', 'gabriel@example.com', 'senha123', 'OPERADOR', true);

-- Projeto
INSERT INTO dim_projeto (id, nome)
VALUES (1, 'Projeto Integração');

-- Status
INSERT INTO dim_status (id, tipo)
VALUES (1, 'Finalizado');

-- User Story
INSERT INTO dim_user_story (id, assunto, criado_em, finalizado_em, bloqueado, encerrado, data_limite, id_status, id_taiga, id_usuario, id_projeto)
VALUES (1, 'História de Teste', NOW(), NOW(), false, false, NOW(), 1, 123456, 1, 1);

-- Eficiência
INSERT INTO fato_eficiencia_user_story (id, id_usuario, id_user_story, tempo_medio, id_projeto)
VALUES (1, 1, 1, 12.5, 1);

