Select * from product
select * from componentProduct
select * from productDetail
select * from productImport
select * from productUnit
select * from [user]
/*
EXEC InsertProduct 
	@productID = 14, 
	@productName = 'Product A', 
	@categoryID = 1,
	@originID = 1,
	@manufacturer = 'Manufacturer A', 
	@description = 'Description of Product A',
	@componentDescription = 'Component Description', 
	@unitString = 'Tablet, 1.00; Capsule, 10.00;', 
	@componentString = 'Salicylic Acid, 500, mg; Buffering Agent, 100, mg';

EXEC InsertProduct 
	@productID = 11, 
	@productName = 'Product A', 
	@categoryID = 1,
	@originID = 1,
	@manufacturer = 'Manufacturer A', 
	@description = 'Description of Product A', 
	@unitString = 'Tablet, 1.00; Capsule, 0.50', 
	@componentString = 'Salicylic Acid, 500, mg; Buffering Agent, 100, mg';




Select * from product
select * from componentProduct
select * from productDetail
select * from productImport
select * from productUnit

EXEC DeleteProduct @productID = 12;

SELECT dbo.CheckProductExists(10) AS ProductExists;

EXEC ImportProduct 
    @unitID = 1, 
    @importPrice = 8.0, 
    @quantity = 400, 
    @batchNo = '123', 
    @manufacturedDate = '2023-01-15', 
    @expiredDate = '2025-01-15', 
    @salePrice = 3.00;
*/