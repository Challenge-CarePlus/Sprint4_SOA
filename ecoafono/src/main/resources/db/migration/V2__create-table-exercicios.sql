CREATE TABLE exercicios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    descricao TEXT NOT NULL,
    instrucao TEXT NOT NULL,
    faixa_etaria VARCHAR(30) NOT NULL,
    objetivo VARCHAR(30) NOT NULL
);