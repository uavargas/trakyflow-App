-- Agregar foreign keys después de que todas las tablas existan
ALTER TABLE categories 
    ADD CONSTRAINT fk_categories_user_id 
    FOREIGN KEY (user_id) 
    REFERENCES users(id) 
    ON DELETE CASCADE;

ALTER TABLE transactions 
    ADD CONSTRAINT fk_transactions_user_id 
    FOREIGN KEY (user_id) 
    REFERENCES users(id) 
    ON DELETE CASCADE;

ALTER TABLE transactions 
    ADD CONSTRAINT fk_transactions_category_id 
    FOREIGN KEY (category_id) 
    REFERENCES categories(id) 
    ON DELETE SET NULL;