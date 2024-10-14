-- Dropping foreign key constraints involving Manufacturer and Component
ALTER TABLE [product] DROP CONSTRAINT [FK__product__Manufac__056ECC6A]; --FK sửa theo db trên máy
GO

-- Now safely drop the Manufacturer table
DROP TABLE [Manufacturer];
GO

-- Dropping Component foreign key constraint from ComponentProduct
ALTER TABLE [ComponentProduct] DROP CONSTRAINT [FK__Component__compo__0B27A5C0]; --FK sửa theo db trên máy
GO

-- Now safely drop the Component table
DROP TABLE [Component];
GO

-- Changing ManufacturerID to manufacturerName in the product table
ALTER TABLE [product]
DROP COLUMN [ManufacturerID];
GO

ALTER TABLE [product]
ADD [manufacturerName] NVARCHAR(255);
GO

-- Dropping ComponentProduct table and recreating it with new structure
DROP TABLE [ComponentProduct];
GO

CREATE TABLE [ComponentProduct] (
  [componentName] NVARCHAR(255),
  [productID] INT FOREIGN KEY REFERENCES [product]([productID]),
  [quantity] NVARCHAR(255),
  [componentUnit] NVARCHAR(20)
);
GO

-- Dropping foreign key constraints involving ProductUnit
ALTER TABLE [productDetail] DROP CONSTRAINT [FK__productDe__produ__0A338187]; --FK sửa theo db trên máy
GO

-- Drop the old productUnit table if it exists
DROP TABLE IF EXISTS [productUnit];
GO

-- Create the new Unit table
CREATE TABLE [Unit] (
  [UnitID] INT PRIMARY KEY IDENTITY(1,1),
  [ProductID] INT FOREIGN KEY REFERENCES [product]([productID]),
  [UnitName] VARCHAR(255),
  [BaseUnitID] INT,
  [UnitToBaseConvertRate] INT
);
GO

-- Deleting volume from productDetail table and renaming it to productImport
ALTER TABLE [productDetail]
DROP COLUMN [volume];
GO

-- Dropping foreign key constraints involving Form
ALTER TABLE [product] DROP CONSTRAINT [FK__product__FormID__084B3915]; --FK sửa theo db trên máy
GO

-- Drop formID column from product
ALTER TABLE [product]
DROP COLUMN [formID];
GO

-- Drop Form table
DROP TABLE [Form];
GO

-- Add foreign key constraint for baseUnitID referencing unitID in Unit table
ALTER TABLE [Unit]
ADD CONSTRAINT FK_Unit_BaseUnitID FOREIGN KEY ([BaseUnitID]) REFERENCES [Unit]([UnitID]);
GO

-- Add the importPrice column to the productImport table
ALTER TABLE [productDetail]
ADD [importPrice] DECIMAL(18, 2);  -- Adjust the data type if needed
GO

-- Alter the price column from INT to DECIMAL in productImport table
ALTER TABLE [productDetail]
ALTER COLUMN [price] DECIMAL(18, 2);  -- Adjust the precision and scale if needed
GO

-- Add foreign key constraint for productUnitID referencing Unit.UnitID
ALTER TABLE [productDetail]
ADD CONSTRAINT FK_ProductImport_ProductUnitID FOREIGN KEY ([productUnitID]) REFERENCES [Unit]([UnitID]);
GO

-- Dropping foreign key constraints involving product
ALTER TABLE [productDetail] DROP CONSTRAINT [FK__productDe__produ__093F5D4E]; --FK sửa theo db trên máy
GO

-- Drop productID column from productImport table
ALTER TABLE [productDetail]
DROP COLUMN [productID];
GO

-- Rename importDate to manufacturerDate in productImport table
EXEC sp_rename 'productDetail.importDate', 'manufacturerDate', 'COLUMN';
GO

-- Rename productUnitID to UnitID in productImport table
EXEC sp_rename 'productDetail.productUnitID', 'UnitID', 'COLUMN';
GO

-- Rename productUnitID to UnitID in productImport table
EXEC sp_rename 'productDetail.importPrice', 'avgImportPrice', 'COLUMN';
GO

CREATE TABLE [productImport](
	orderImportID int primary key identity(1,1),
	unitID int,
	importPrice DECIMAL(18,2),
	quantity int,
	manufactureDate date,
	expiredDate date,
)

ALTER TABLE [productImport]
ADD CONSTRAINT FK_ProductImport_UnitID FOREIGN KEY ([UnitID]) REFERENCES [Unit]([UnitID]);
GO
