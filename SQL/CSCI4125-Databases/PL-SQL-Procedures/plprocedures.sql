CREATE OR REPLACE PROCEDURE Invoice (
    customerId Customer.ID%type, 
    orderDate Orders.OrderDate%type
) 
AS 
    cname Customer.Name%type;
    
    CURSOR MyData IS (
        SELECT P.Name, L.Quantity, P.Price
        FROM Product P, Orders O, Lineitem L
        WHERE P.ID = L.ProductID
        AND O.OrderNumber = L.OrderNumber
        AND O.CustID = customerId
        AND O.Orderdate = orderDate
    );
    
    MyRec MyData%ROWTYPE;
    
    productCost NUMBER := 0;
    
BEGIN
    SELECT Name INTO cname FROM Customer WHERE ID = customerId;
    DBMS_OUTPUT.PUT_LINE(orderDate || ' invoice for ' || customerId || ': ' || cname);
    
    FOR MyRec in MyData LOOP
        DBMS_OUTPUT.PUT_LINE(MyRec.Name || ' ' || MyRec.Quantity || ' ' || MyRec.Price);
        productCost := productCost + MyRec.Price * MyRec.Quantity;
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('ProductCost:  ' || productCost);
END;
/

CALL Invoice('C07', '30-JAN-25');