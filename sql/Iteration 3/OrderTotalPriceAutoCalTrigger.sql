CREATE TRIGGER trg_CalculateTotalPrice
ON orderDetail
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    -- Update totalPrice for affected orders by summing totalProductPrice from orderDetail
    UPDATE o
    SET o.totalPrice = (
        SELECT ISNULL(SUM(od.totalProductPrice), 0)
        FROM orderDetail od
        WHERE od.orderID = o.orderID
    )
    FROM [order] o
    WHERE o.orderID IN (
        SELECT DISTINCT orderID
        FROM inserted
        UNION
        SELECT DISTINCT orderID
        FROM deleted
    );
END;
