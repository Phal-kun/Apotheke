--Iteration 1
ALTER TABLE [product]
DROP COLUMN [ImportDate],[ExpiredDate];
GO

ALTER TABLE [product]
ADD [isActive] BIT;
GO


-- Corrected ALTER TABLE for productDetail
ALTER TABLE [productDetail]
ADD [ImportDate] DATE,
    [ExpiredDate] DATE,
    [isActive] BIT;
GO


Alter Table [Component]
ADD [ComponentMeasureUnit] nvarchar(20);
GO

ALTER TABLE [product]
DROP COLUMN [measureUnitID]
GO

