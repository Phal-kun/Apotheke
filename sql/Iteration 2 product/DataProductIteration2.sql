-- Insert data into origin without specifying originID
INSERT INTO origin (originName)
VALUES 
('USA'), ('Germany'), ('India'), ('Canada'), ('China'),
('Brazil'), ('France'), ('United Kingdom'), ('Japan'), ('South Korea'),
('Italy'), ('Switzerland'), ('Mexico'), ('Australia'), ('Russia'),
('Argentina'), ('Spain'), ('Netherlands'), ('Singapore'), ('Thailand');

-- Insert data into category without specifying categoryID
INSERT INTO category (parentCategoryID, categoryName, description)
VALUES 
(NULL, 'Pain Relief', 'Medicines that alleviate pain.'),
(NULL, 'Cough and Cold', 'Products that help with coughs, colds, and congestion.'),
(1, 'Anti-Inflammatory', 'Sub-category of pain relief targeting inflammation.'),
(2, 'Syrups', 'Cough syrups for adults and children.');

-- Insert data into product table with baseUnitID set to NULL
INSERT INTO product (productID, productName, categoryID, originID, manufacturer, description, baseUnitID, isActive)
VALUES 
(1, 'Aspirin', 1, 1, 'Bayer', 'Pain reliever and fever reducer', NULL, 1),
(2, 'Ibuprofen', 1, 2, 'Advil', 'Anti-inflammatory and pain relief', NULL, 1),
(3, 'Cough Syrup', 2, 3, 'Robitussin', 'Treats cough and chest congestion', NULL, 1);

INSERT INTO productUnit (productID, unitName, unitToBaseConvertRate)
VALUES 
(1, 'Tablet', 1.00),
(2, 'Capsule', 1.00),
(3, 'Bottle', 1.00);

-- Update baseUnitID in product table
UPDATE product
SET baseUnitID = (
    SELECT unitID FROM productUnit WHERE productUnit.productID = product.productID
);

INSERT INTO componentProduct (componentName, productID, quantity, componentUnit)
VALUES 
('Salicylic Acid', 1, 500.00, 'mg'),
('Buffering Agent', 1, 100.00, 'mg'),
('Ibuprofen', 2, 200.00, 'mg'),
('Menthol', 3, 5.00, 'ml'),
('Dextromethorphan', 3, 10.00, 'ml');

INSERT INTO productDetail (unitID, avgImportPrice, stock, baseSoldPrice, batchNo, manufactureDate, expiredDate, isActive)
VALUES 
(1, 2.50, 1000, 3.00, 101, '2023-01-15', '2025-01-15', 1),
(2, 0.10, 500, 0.20, 102, '2023-02-01', '2025-02-01', 1),
(3, 5.00, 300, 7.00, 103, '2023-03-20', '2025-03-20', 1);

INSERT INTO productImport (unitID, importPrice, quantity, batchNo, manufactureDate, expiredDate)
VALUES 
(1, 2.00, 1000, 101, '2023-01-15', '2025-01-15'),
(2, 0.08, 500, 102, '2023-02-01', '2025-02-01'),
(3, 4.50, 300, 103, '2023-03-20', '2025-03-20');

