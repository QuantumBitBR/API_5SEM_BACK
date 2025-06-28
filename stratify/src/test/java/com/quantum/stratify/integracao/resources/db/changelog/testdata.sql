-- Dados básicos para todas as tabelas dimensionais
INSERT INTO dim_usuario (id, nome, email, senha, role, is_enable) VALUES 
(1, 'Usuário Teste', 'teste@email.com', 'senha123', 'OPERADOR', true),
(2, 'Gestor Teste', 'gestor@email.com', 'senha123', 'GESTOR', true);

INSERT INTO dim_projeto (id, nome) VALUES 
(1, 'Projeto Teste 1'),
(2, 'Projeto Teste 2');

INSERT INTO dim_status (id, tipo) VALUES 
(1, 'Novo'),
(2, 'Em Progresso'),
(3, 'Concluído');

-- Dados para tabelas de fatos (exemplos)
INSERT INTO fato_status_user_story (id, id_status, id_projeto, quantidade_user_story) VALUES 
(1, 1, 1, 5),
(2, 2, 1, 3),
(3, 3, 1, 2);

-- Configuração para modo leitura (opcional)
REVOKE INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public FROM PUBLIC;