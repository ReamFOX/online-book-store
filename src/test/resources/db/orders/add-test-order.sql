INSERT INTO orders (id, user_id, status, total, order_date, shipping_address)
VALUES (
        1,
        1,
        'PENDING',
        100,
        '2012-12-12 00:00:00',
        'Kyiv, Shevchenko ave, 1'
       );

INSERT INTO order_items (id, order_id, book_id, quantity, price)
VALUES (1, 1, 1, 10, 100);
