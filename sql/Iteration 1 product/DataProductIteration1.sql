-- Inserting into Form table with identity for formID
INSERT INTO [Form] ([formName])
VALUES
('Tablet'),
('Capsule'),
('Syrup'),
('Injection');
GO

-- Inserting into Manufacturer table with identity for manufacturerID
INSERT INTO [Manufacturer] ([manufacturerName])
VALUES
('Pfizer'),
('Moderna'),
('Johnson & Johnson'),
('AstraZeneca');
GO

-- Inserting into Origin table with identity for originID
INSERT INTO [Origin] ([originName])
VALUES
('USA'),
('UK'),
('Germany'),
('India');
GO

-- Inserting into Component table with identity for componentID
INSERT INTO [Component] ([componentName])
VALUES
('Paracetamol'),
('Ibuprofen'),
('Amoxicillin'),
('Dexamethasone');
GO

-- Inserting into Category table with identity for CategoryID
INSERT INTO [Category] ([ParentCategoryID], [CategoryName], [Description])
VALUES
(NULL, 'Pharmaceuticals', 'Medicinal products'),
(1, 'Painkillers', 'Medicines to relieve pain'),
(1, 'Antibiotics', 'Medicines to fight infections'),
(1, 'Steroids', 'Medicines to reduce inflammation');
GO

-- Inserting into product table with identity for productID
INSERT INTO [product] ([productName], [CategoryID], [originID], [ManufacturerID], [FormID], [Description], [isActive])
VALUES
('Paracetamol 500mg', 2, 1, 1, 1, 'Used for pain relief and fever reduction', 1),
('Ibuprofen 200mg', 2, 1, 2, 2, 'Used to reduce inflammation and pain', 1),
('Amoxicillin 250mg', 3, 2, 3, 2, 'Antibiotic used to treat infections', 1),
('Dexamethasone 10mg', 4, 3, 4, 4, 'Steroid to reduce inflammation', 1);
GO

-- Inserting into ComponentProduct table without the need for identity as componentID and productID are foreign keys
INSERT INTO [ComponentProduct] ([componentID], [productID], [quantity])
VALUES
(1, 1, 500),  -- Paracetamol 500mg contains 1 unit of Paracetamol
(2, 2, 200),  -- Ibuprofen 200mg contains 1 unit of Ibuprofen
(3, 3, 250),  -- Amoxicillin 250mg contains 2 units of Amoxicillin
(4, 4, 10);  -- Dexamethasone 10mg contains 1 unit of Dexamethasone
GO

