-- Disable foreign key checks (for SQL Server)
EXEC sp_MSforeachtable "ALTER TABLE ? NOCHECK CONSTRAINT all";

-- Delete from child tables first (no particular order within each block)

DELETE FROM orderDetail;
DELETE FROM blogTag;
DELETE FROM productImport;
DELETE FROM componentProduct;
DELETE FROM productDetail;
DELETE FROM productUnit;
DELETE FROM [order];
DELETE FROM blog;

-- Now delete from parent tables
DELETE FROM [user];
DELETE FROM role;
DELETE FROM category;
DELETE FROM origin;
DELETE FROM product;
DELETE FROM tag;
DELETE FROM orderStatus;

-- Re-enable foreign key checks
EXEC sp_MSforeachtable "ALTER TABLE ? CHECK CONSTRAINT all";
