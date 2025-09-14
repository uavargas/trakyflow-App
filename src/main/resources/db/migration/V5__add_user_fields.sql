-- Agregar nuevos campos a la tabla users
ALTER TABLE users 
ADD COLUMN is_enabled BOOLEAN DEFAULT TRUE NOT NULL,
ADD COLUMN last_login TIMESTAMP,
ADD COLUMN email_verified BOOLEAN DEFAULT FALSE NOT NULL;

-- Crear índices para los nuevos campos
CREATE INDEX idx_users_enabled ON users(is_enabled);
CREATE INDEX idx_users_email_verified ON users(email_verified);
