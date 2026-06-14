CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(200) NOT NULL UNIQUE,
    cep VARCHAR(8) NOT NULL,
    logradouro VARCHAR(200),
    cidade VARCHAR(100),
    estado VARCHAR(2),
    faixa_etaria VARCHAR(30) NOT NULL,
    objetivo VARCHAR(30) NOT NULL
);