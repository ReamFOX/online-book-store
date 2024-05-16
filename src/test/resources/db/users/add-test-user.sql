INSERT INTO users (email, password, first_name, last_name)
VALUES ('test@example.com', '$2a$10$2Pjnfwyb1RMRe2i6eIsxre3lyOMj.uYrlO.BCBkcuUYOy7Qs0qfOG', 'test', 'test');

INSERT INTO users_roles (role_id, user_id)
VALUES (
           (SELECT id FROM roles WHERE name = 'USER'),
           (SELECT id FROM users WHERE email = 'test@example.com')
       );

INSERT INTO shopping_carts (user_id)
VALUES (
           (SELECT id FROM users WHERE email = 'test@example.com')
       );
