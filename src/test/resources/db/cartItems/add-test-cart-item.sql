INSERT INTO cart_items (shopping_cart_id, book_id, quantity)
VALUES (
        (SELECT id FROM users WHERE email = 'test@example.com'),
        (SELECT id FROM books WHERE title = 'To Kill a Mockingbird'),
        1
       );
