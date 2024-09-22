SELECT p.productID, p.productName, p.Description, p.isActive,
       c.CategoryID, c.CategoryName, c.Description AS categoryDescription,
       o.originID, o.originName,
       m.manufacturerID, m.manufacturerName,
       f.formID, f.formName
FROM product p
LEFT JOIN Category c ON p.CategoryID = c.CategoryID
LEFT JOIN Origin o ON p.originID = o.originID
LEFT JOIN Manufacturer m ON p.ManufacturerID = m.manufacturerID
LEFT JOIN Form f ON p.FormID = f.formID;

SELECT *
FROM ComponentProduct cp
JOIN Component c ON cp.componentID = c.componentID
