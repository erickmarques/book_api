CREATE TABLE livro (
    cd_livro BIGINT AUTO_INCREMENT PRIMARY KEY,
    ds_titulo VARCHAR(100) NOT NULL,
    nm_autor VARCHAR(50) NOT NULL,
    nr_contador BIGINT NULL,
    dt_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    dt_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
