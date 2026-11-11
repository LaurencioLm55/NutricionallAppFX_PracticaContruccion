-- Completa la base de datos `nutriapp` para que funcione el login de NutricionalAppFX.
-- Revisa el script antes de ejecutarlo. Solo debe correrse UNA vez.
-- Requiere que ya existan `colaboradores` y `roles` (1 Administrador, 2 Nutriologo, 3 Invitado).

USE nutriapp;

-- 1. Catalogo de estatus (el codigo usa 1 = Activo y 3 = Suspendido)
CREATE TABLE IF NOT EXISTS colaboradoresEstatus (
    idEstatus INT NOT NULL PRIMARY KEY,
    estatus   VARCHAR(30) NOT NULL
);

INSERT INTO colaboradoresEstatus (idEstatus, estatus) VALUES
    (1, 'Activo'),
    (2, 'Inactivo'),
    (3, 'Suspendido');

-- 2. Columna idEstatus en colaboradores (por defecto Activo)
ALTER TABLE colaboradores
    ADD COLUMN idEstatus INT NOT NULL DEFAULT 1,
    ADD CONSTRAINT fk_colaboradores_estatus
        FOREIGN KEY (idEstatus) REFERENCES colaboradoresEstatus (idEstatus);

-- 3. Usuarios de prueba (la contrasena se guarda como SHA-256 en hexadecimal minusculas,
--    igual que Utilidades.generarCifradoSHA256)
--    N001 / Test1234  -> Activo     (login correcto)
--    N002 / Test1234  -> Suspendido (muestra el aviso de cuenta suspendida)
INSERT INTO colaboradores
    (noPersonal, nombre, apellidoPaterno, apellidoMaterno, fechaContratacion, password, idRol, idEstatus)
VALUES
    ('N001', 'Usuario', 'Prueba', 'Activo', CURDATE(), SHA2('Test1234', 256), 1, 1),
    ('N002', 'Usuario', 'Prueba', 'Suspendido', CURDATE(), SHA2('Test1234', 256), 2, 3);
