CREATE TABLE entrenadores (
                              id BIGSERIAL PRIMARY KEY,
                              nombre VARCHAR(100) NOT NULL,
                              especialidad VARCHAR(100) NOT NULL,
                              email VARCHAR(100) NOT NULL UNIQUE,
                              telefono VARCHAR(20),
                              activo BOOLEAN DEFAULT TRUE
);