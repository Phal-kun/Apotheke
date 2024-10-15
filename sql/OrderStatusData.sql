-- Insert data into the orderStatus table
INSERT INTO orderStatus (statusName, description)
VALUES 
    ('Pending', 'Order has been placed and waiting for approved'),
    ('Processing', 'Order is approved and ready for shipping'),
    ('Delivered', 'Order has been delivered to the customer'),
    ('Rejected', 'Order was rejected by staff member');