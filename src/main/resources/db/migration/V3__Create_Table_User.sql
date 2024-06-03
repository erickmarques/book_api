CREATE TABLE usuario (
    cd_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    ds_login VARCHAR(100) NOT NULL,
    ds_senha VARCHAR(200) NOT NULL,
    ds_papel VARCHAR(100) NOT NULL
);
