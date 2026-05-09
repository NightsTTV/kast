-- 1. Find all customers we mailed a gift card to.
SELECT DISTINCT Referrer.ID, Referrer.Name 
FROM customer NewCustomer, customer Referrer
WHERE NewCustomer.referrerid = Referrer.ID;
-- 2. Find all product costs. Hint: this was derived.
SELECT OrderNumber, '$' || TO_CHAR(SUM(Quantity * Price), 9999.99) AS "Product Cost"
FROM lineitem, product
WHERE lineitem.productid = Product.ID
GROUP BY OrderNumber;
-- 3. For the shipping cost, determine which IDs have a shipping cost.
SELECT OrderNumber, '$' || TO_CHAR(SUM(Quantity * Price), 9999.99) AS "Product Cost"
FROM lineitem, product
WHERE lineitem.productid = Product.ID
GROUP BY OrderNumber
HAVING SUM(Quantity * Price) < 35;
-- 4. For the shipping cost, determine which IDs do not have a shipping cost. 
SELECT OrderNumber, '$' || TO_CHAR(SUM(Quantity * Price), 9999.99) AS "Product Cost"
FROM lineitem, product
WHERE lineitem.productid = Product.ID
GROUP BY OrderNumber
HAVING SUM(Quantity * Price) > 34.99;
-- 5. If there is a shipping cost, compute the total cost.
SELECT OrderNumber, '$' || TO_CHAR(SUM(Quantity * Price) + 10, 9999.99) AS "Product Cost"
FROM lineitem, product
WHERE lineitem.productid = Product.ID
GROUP BY OrderNumber
HAVING SUM(Quantity * Price) < 35;









