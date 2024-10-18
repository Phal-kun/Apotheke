/*
drop procedure InsertProduct
drop procedure DeleteProduct
drop procedure ImportProduct
drop function CheckProductExists
*/

-- Procedure to Insert Product
CREATE PROCEDURE InsertProduct (
    @productID INT,
    @productName NVARCHAR(255),
    @categoryID INT,
    @originID INT,
    @manufacturer NVARCHAR(255),
    @description NVARCHAR(MAX),
    @componentDescription NVARCHAR(MAX) = NULL, -- This field can be NULL
    @unitString NVARCHAR(MAX), -- List of units as a delimited string (e.g. 'Tablet, 1.00; Capsule, 0.50')
    @componentString NVARCHAR(MAX), -- List of components as a delimited string (e.g. 'Salicylic Acid, 500, mg; Buffering Agent, 100, mg')
    @isActive BIT = 1 -- Default to active
)
AS
BEGIN
    -- Start a transaction to ensure atomicity
    BEGIN TRY
        BEGIN TRANSACTION;

        -- Insert into the product table
        INSERT INTO product (productID, productName, categoryID, originID, manufacturer, description, componentDescription, isActive)
        VALUES (@productID, @productName, @categoryID, @originID, @manufacturer, @description, @componentDescription, @isActive);

        -- Split and insert unit data into productUnit table
        DECLARE @unit NVARCHAR(255), @unitRate DECIMAL(10, 2), @baseUnitID INT = NULL;
        DECLARE @unitPos INT = 1;

        WHILE CHARINDEX(';', @unitString, @unitPos) > 0
        BEGIN
            SET @unit = LEFT(@unitString, CHARINDEX(',', @unitString) - 1);
            SET @unitRate = TRY_CAST(SUBSTRING(@unitString, CHARINDEX(',', @unitString) + 1, CHARINDEX(';', @unitString) - CHARINDEX(',', @unitString) - 1) AS DECIMAL(10, 2));

            -- Insert the unit for the product
            INSERT INTO productUnit (productID, unitName, unitToBaseConvertRate)
            VALUES (@productID, @unit, @unitRate);

            -- Check if the unitToBaseConvertRate is 1 and set it as baseUnitID
            IF @unitRate = 1.00
            BEGIN
                -- Store the ID of the base unit (the last inserted unit)
                SET @baseUnitID = SCOPE_IDENTITY();
            END

            -- Move to the next part of the string
            SET @unitPos = CHARINDEX(';', @unitString) + 1;
            SET @unitString = SUBSTRING(@unitString, @unitPos, LEN(@unitString));
        END

        -- If a base unit has been identified, update the product table with the baseUnitID
        IF @baseUnitID IS NOT NULL
        BEGIN
            UPDATE product
            SET baseUnitID = @baseUnitID
            WHERE productID = @productID;
        END

        -- Split and insert component data into componentProduct table
        DECLARE @component NVARCHAR(255), @quantity DECIMAL(10, 2), @componentUnit NVARCHAR(50);
        DECLARE @componentPos INT = 1;

        WHILE CHARINDEX(';', @componentString, @componentPos) > 0
        BEGIN
            SET @component = LEFT(@componentString, CHARINDEX(',', @componentString) - 1);
            SET @quantity = TRY_CAST(SUBSTRING(@componentString, CHARINDEX(',', @componentString) + 1, CHARINDEX(',', @componentString, CHARINDEX(',', @componentString) + 1) - CHARINDEX(',', @componentString) - 1) AS DECIMAL(10, 2));
            SET @componentUnit = SUBSTRING(@componentString, CHARINDEX(',', @componentString, CHARINDEX(',', @componentString) + 1) + 1, CHARINDEX(';', @componentString) - CHARINDEX(',', @componentString, CHARINDEX(',', @componentString) + 1) - 1);

            -- Insert the component for the product
            INSERT INTO componentProduct (componentName, productID, quantity, componentUnit)
            VALUES (@component, @productID, @quantity, @componentUnit);

            -- Move to the next part of the string
            SET @componentPos = CHARINDEX(';', @componentString) + 1;
            SET @componentString = SUBSTRING(@componentString, @componentPos, LEN(@componentString));
        END

        -- Commit the transaction
        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        -- Rollback transaction in case of an error
        ROLLBACK TRANSACTION;

        -- Raise the error
        DECLARE @ErrorMessage NVARCHAR(4000), @ErrorSeverity INT, @ErrorState INT;
        SELECT @ErrorMessage = ERROR_MESSAGE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorState = ERROR_STATE();
        RAISERROR (@ErrorMessage, @ErrorSeverity, @ErrorState);
    END CATCH
END;
GO

-- Procedure to Delete Product
CREATE PROCEDURE DeleteProduct(
    @productID INT
)
AS
BEGIN
    SET NOCOUNT ON;

    -- Delete from componentProduct table
    DELETE FROM componentProduct
    WHERE productID = @productID;

    -- Delete from productUnit table
    DELETE FROM productUnit
    WHERE productID = @productID;

    -- Delete from productDetail table
    DELETE FROM productDetail
    WHERE unitID = @productID;

    -- Delete from productImport table
    DELETE FROM productImport
    WHERE unitID = @productID;

    -- Delete the product from product table
    DELETE FROM product
    WHERE productID = @productID;
    
    -- Check if the product has been deleted successfully
    IF @@ROWCOUNT = 0
    BEGIN
        -- If no rows were deleted, return an error message
        THROW 50000, 'Product not found or could not be deleted.', 1;
    END
    ELSE
    BEGIN
        -- Return success message if the product was deleted
        PRINT 'Product and related data deleted successfully.';
    END
END;
GO 


-- Function to Check if Product Exists
CREATE FUNCTION CheckProductExists
(
    @productID INT
)
RETURNS BIT
AS
BEGIN
    DECLARE @exists BIT;

    -- Check if the productID exists in the product table
    IF EXISTS (SELECT 1 FROM product WHERE productID = @productID)
    BEGIN
        SET @exists = 1;  -- Product exists
    END
    ELSE
    BEGIN
        SET @exists = 0;  -- Product does not exist
    END

    RETURN @exists;
END;
GO 


-- Procedure to Import Product
CREATE PROCEDURE ImportProduct
    @unitID INT,
    @importPrice DECIMAL(10, 2),
    @quantity INT,
    @batchNo INT,
    @manufacturedDate DATE,
    @expiredDate DATE,
    @salePrice DECIMAL(10, 2)
AS
BEGIN
    -- Check if unitID exists in the productUnit table
    IF EXISTS (SELECT 1 FROM productUnit WHERE unitID = @unitID)
    BEGIN
        -- Check if a record with the same batchNo exists in productDetail
        IF EXISTS (SELECT 1 FROM productDetail WHERE batchNo = @batchNo)
        BEGIN
            -- Update avgImportPrice for the existing batchNo
            UPDATE productDetail
            SET avgImportPrice = (
                SELECT (AVG((importPrice * quantity) + (@importPrice * @quantity)) / (SUM(quantity) + @quantity))
                FROM productImport
                WHERE batchNo = @batchNo
            ),
            stock = stock + @quantity,
            baseSoldPrice = @salePrice
            WHERE batchNo = @batchNo;
        END
        ELSE
        BEGIN
            -- Insert a new record into productDetail if the batchNo is not found
            INSERT INTO productDetail (unitID, avgImportPrice, stock, baseSoldPrice, batchNo, manufactureDate, expiredDate, isActive)
            VALUES (@unitID, @importPrice, @quantity, @salePrice, @batchNo, @manufacturedDate, @expiredDate, 1);
        END;

        -- Insert into productImport
        INSERT INTO productImport (unitID, importPrice, quantity, batchNo, manufactureDate, expiredDate)
        VALUES (@unitID, @importPrice, @quantity, @batchNo, @manufacturedDate, @expiredDate);
    END
    ELSE
    BEGIN
        -- Handle error if unitID does not exist
        RAISERROR('The unitID does not exist in the productUnit table.', 16, 1);
    END;
END;
GO 