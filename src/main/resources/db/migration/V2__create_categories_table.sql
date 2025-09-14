CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    type VARCHAR(10) CHECK (type IN ('INCOME', 'EXPENSE')) NOT NULL,
    user_id BIGINT,  -- Foreign key se agregará después
    is_default BOOLEAN DEFAULT FALSE,
    icon VARCHAR(50),
    color VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_categories_user_id ON categories(user_id);

INSERT INTO categories (name, description, type, is_default, icon, color) VALUES
('Alimentación', 'Gastos en comida y restaurantes', 'EXPENSE', true, 'utensils', 'red'),
('Transporte', 'Transporte público, gasolina, taxi', 'EXPENSE', true, 'bus', 'blue'),
('Vivienda', 'Renta, hipoteca, servicios', 'EXPENSE', true, 'home', 'green'),
('Salario', 'Salario principal', 'INCOME', true, 'money-bill', 'green'),
('Freelance', 'Trabajos independientes', 'INCOME', true, 'laptop', 'blue');