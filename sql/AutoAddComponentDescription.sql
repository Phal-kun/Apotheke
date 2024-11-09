CREATE TRIGGER trg_UpdateComponentDescription
ON componentProduct
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    -- Declare variables
    DECLARE @productID INT;

    -- Determine the affected productID (from INSERTED or DELETED tables)
    IF EXISTS (SELECT 1 FROM INSERTED)
        SELECT TOP 1 @productID = productID FROM INSERTED;
    ELSE
        SELECT TOP 1 @productID = productID FROM DELETED;

    -- Update the componentDescription in the product table
    UPDATE p
    SET p.componentDescription = (
        SELECT STRING_AGG(
            CONCAT(cp.componentName, ' ', cp.quantity, ' ', cp.componentUnit), ', '
        ) AS componentDescription
        FROM componentProduct cp
        WHERE cp.productID = @productID
    )
    FROM product p
    WHERE p.productID = @productID;
END;
