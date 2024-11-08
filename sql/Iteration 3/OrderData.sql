-- Insert data into the [order] table
INSERT INTO [order] (userID, orderDate, orderCompleted, statusI, shipName, shipAddress, shipPhone, shipNote, rejectReason)
VALUES 
    (1, '2024-09-01', '2024-09-05', 3, 'John Doe', '123 Main St', '1234567890', 'Leave at the door', NULL),
    (2, '2024-09-02', '2024-09-04', 3, 'Jane Smith', '456 Oak St', '0987654321', 'Please call on arrival', NULL),
    (3, '2024-09-03', NULL, 1, 'Alice Johnson', '789 Maple St', '2223334444', 'Handle with care', NULL),
    (4, '2024-09-04', '2024-09-06', 3, 'Bob Brown', '101 Pine St', '5556667777', '', NULL),
    (5, '2024-09-05', '2024-09-07', 4, 'Chris Lee', '102 Elm St', '8889990000', 'Urgent delivery', 'Incorrect address provided'),
    (6, '2024-09-06', '2024-09-08', 3, 'David White', '104 Oak St', '1112223333', 'Call before delivery', NULL),
    (7, '2024-09-07', NULL, 2, 'Emma Harris', '200 Birch St', '4445556666', 'Leave with a neighbor', NULL),
    (8, '2024-09-08', NULL, 1, 'Frank Wright', '300 Spruce St', '7778889999', 'Fragile items inside', NULL),
    (9, '2024-09-09', '2024-09-10', 3, 'Grace Adams', '400 Cherry St', '1233211234', 'Ring the doorbell twice', NULL),
    (10, '2024-09-10', NULL, 2, 'Hannah Scott', '500 Redwood St', '9876543210', 'Deliver after 5 PM', NULL),
    (11, '2024-09-11', '2024-09-12', 3, 'Ivy Thompson', '600 Walnut St', '1110009999', 'Signature required', NULL),
    (12, '2024-09-12', NULL, 1, 'Jack Johnson', '700 Cedar St', '5552221111', 'Please don’t bend', NULL),
    (13, '2024-09-13', '2024-09-14', 4, 'Karen Turner', '800 Palm St', '4443332222', '', 'Customer cancelled the order'),
    (14, '2024-09-14', NULL, 1, 'Liam King', '900 Maple St', '2221114444', 'Careful with fragile products', NULL),
    (15, '2024-09-15', NULL, 2, 'Mia Walker', '1000 Elm St', '9998887777', 'Call before you arrive', NULL),
    (16, '2024-09-16', '2024-09-18', 3, 'Noah Young', '1100 Cedar St', '3332221111', '', NULL),
    (17, '2024-09-17', NULL, 1, 'Olivia Baker', '1200 Oak St', '5554443333', 'Deliver to the front porch', NULL),
    (18, '2024-09-18', '2024-09-19', 3, 'Paul Green', '1300 Birch St', '6667778888', '', NULL),
    (19, '2024-09-19', NULL, 2, 'Quinn Price', '1400 Cherry St', '8889991111', 'Be careful with the package', NULL),
    (20, '2024-09-20', '2024-09-21', 4, 'Rachel Bell', '1500 Redwood St', '2223334444', 'Return by 5 PM', 'Item not available');
