DELETE FROM users_roles WHERE user_id = (SELECT id FROM users WHERE email = 'test@example.com');

DELETE FROM shopping_carts WHERE user_id = (SELECT id FROM users WHERE email = 'test@example.com');

DELETE FROM users WHERE email = 'test@example.com';
