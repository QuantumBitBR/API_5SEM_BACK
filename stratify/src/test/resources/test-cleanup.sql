-- Limpeza de dados de teste
-- Gera e executa o comando TRUNCATE para todas as tabelas do schema public

-- Execute o resultado deste SELECT manualmente se DO $$ não for suportado:
SELECT CONCAT('TRUNCATE TABLE ', string_agg(format('%I.%I', schemaname, tablename), ', '), ' CASCADE') AS truncate_sql
FROM pg_tables
WHERE schemaname = 'public';

-- Copie e execute o comando TRUNCATE gerado acima.