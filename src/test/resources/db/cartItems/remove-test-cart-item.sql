DELETE FROM cart_items where book_id = (SELECT id FROM books WHERE title = 'To Kill a Mockingbird');
