--create database SWP391
-- drop database SWP391

CREATE TABLE [user] (
  [userID] INT PRIMARY KEY IDENTITY(1,1),
  [fullname] NVARCHAR(255),
  [phone] NVARCHAR(255),
  [username] NVARCHAR(255),
  [password] NVARCHAR(255),
  [gender] NVARCHAR(255),
  [status] BIT,
  [role] INT,
  [address] NVARCHAR(255)
);
GO

CREATE TABLE [role] (
  [roleID] INT PRIMARY KEY IDENTITY(1,1),
  [roleName] NVARCHAR(255)
);
GO

CREATE TABLE [product] (
  [productID] INT PRIMARY KEY IDENTITY(1,1),
  [productName] NVARCHAR(255),
  [CategoryID] INT,
  [originID] INT,
  [MeasureUnitID] INT,
  [ManufacturerID] INT,
  [FormID] INT,
  [Description] NVARCHAR(255),
  [ImportDate] DATE,
  [ExpiredDate] DATE,
);
GO

CREATE TABLE [Component] (
  [componentID] INT PRIMARY KEY IDENTITY(1,1),
  [componentName] NVARCHAR(255)
);
GO

CREATE TABLE [ComponentProduct] (
  [componentID] INT,
  [productID] INT,
  [quantity] INT,
  PRIMARY KEY ([componentID], [productID])
);
GO

CREATE TABLE [Origin] (
  [originID] INT PRIMARY KEY IDENTITY(1,1),
  [originName] NVARCHAR(255)
);
GO

CREATE TABLE [ProductUnit] (
  [productUnitID] INT PRIMARY KEY IDENTITY(1,1),
  [productUnitName] NVARCHAR(255)
);
GO

CREATE TABLE [Form] (
  [formID] INT PRIMARY KEY IDENTITY(1,1),
  [formName] NVARCHAR(255)
);
GO

CREATE TABLE [Manufacturer] (
  [manufacturerID] INT PRIMARY KEY IDENTITY(1,1),
  [manufacturerName] NVARCHAR(255)
);
GO

CREATE TABLE [Category] (
  [CategoryID] INT PRIMARY KEY IDENTITY(1,1),
  [ParentCategoryID] INT,
  [CategoryName] NVARCHAR(255),
  [Description] NVARCHAR(255)
);
GO

CREATE TABLE [productDetail] (
  [productDetailID] INT PRIMARY KEY IDENTITY(1,1),
  [productID] INT,
  [productUnitID] INT,
  [volume] INT,
  [stock] INT,
  [price] INT,

);
GO

CREATE TABLE [blog] (
  [blogID] INT PRIMARY KEY IDENTITY(1,1),
  [title] NVARCHAR(255),
  [content] NVARCHAR(MAX),
  [publicDate] DATE,
  [userID] INT,
  [status] BIT
);
GO

CREATE TABLE [tag] (
  [tagID] INT PRIMARY KEY IDENTITY(1,1),
  [tagName] NVARCHAR(255)
);
GO

CREATE TABLE [blogTag] (
  [blogID] INT,
  [tagID] INT,
  PRIMARY KEY ([blogID], [tagID])
);
GO

-- Adding foreign keys
ALTER TABLE [user] ADD FOREIGN KEY ([role]) REFERENCES [role] ([roleID]);
GO

ALTER TABLE [product] ADD FOREIGN KEY ([originID]) REFERENCES [Origin] ([originID]);
GO

ALTER TABLE [product] ADD FOREIGN KEY ([ManufacturerID]) REFERENCES [Manufacturer] ([manufacturerID]);
GO

ALTER TABLE [Category] ADD FOREIGN KEY ([ParentCategoryID]) REFERENCES [Category] ([CategoryID]);
GO

ALTER TABLE [product] ADD FOREIGN KEY ([CategoryID]) REFERENCES [Category] ([CategoryID]);
GO

ALTER TABLE [product] ADD FOREIGN KEY ([FormID]) REFERENCES [Form] ([formID]);
GO

ALTER TABLE [productDetail] ADD FOREIGN KEY ([productID]) REFERENCES [product]([productID]);
GO

ALTER TABLE [productDetail] ADD FOREIGN KEY ([productUnitID]) REFERENCES [productUnit]([productUnitID]);
GO

-- Component-Product relations
ALTER TABLE [ComponentProduct] ADD FOREIGN KEY ([componentID]) REFERENCES [Component] ([componentID]);
GO

ALTER TABLE [ComponentProduct] ADD FOREIGN KEY ([productID]) REFERENCES [product] ([productID]);
GO

-- Blog relations
ALTER TABLE [blog] ADD FOREIGN KEY ([userID]) REFERENCES [user] ([userID]);
GO

ALTER TABLE [blogTag] ADD FOREIGN KEY ([blogID]) REFERENCES [blog] ([blogID]);
GO

ALTER TABLE [blogTag] ADD FOREIGN KEY ([tagID]) REFERENCES [tag] ([tagID]);
GO

