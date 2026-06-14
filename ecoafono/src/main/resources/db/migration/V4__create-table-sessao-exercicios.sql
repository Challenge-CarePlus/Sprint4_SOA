CREATE TABLE sessao_exercicios (
    sessao_id BIGINT NOT NULL,
    exercicio_id BIGINT NOT NULL,

    PRIMARY KEY (sessao_id, exercicio_id),

    CONSTRAINT fk_sessao_exercicios_sessao
        FOREIGN KEY (sessao_id)
        REFERENCES sessoes(id),

    CONSTRAINT fk_sessao_exercicios_exercicio
        FOREIGN KEY (exercicio_id)
        REFERENCES exercicios(id)
);