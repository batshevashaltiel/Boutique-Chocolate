-- יצירת מסד הנתונים
CREATE DATABASE IF NOT EXISTS ecommerce_db;
USE ecommerce_db;

-- 1. טבלת משתמשים (שימי לב: העמודה היא email)
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    role VARCHAR(20) DEFAULT 'customer' 
);

-- הכנסת משתמשים ראשוניים (מנהל ולקוח לדוגמה)
INSERT INTO users (email, password, role) VALUES ('admin@choc.com', '1234', 'admin');
INSERT INTO users (email, password, role) VALUES ('test@gmail.com', '1234', 'customer');

-- 2. טבלת מוצרים
DROP TABLE IF EXISTS products;
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    price DOUBLE,
    description VARCHAR(255),
    quantity INT DEFAULT 10
);

-- מוצרים לדוגמה (אלו השמות שמופיעים אצלך בצילומי המסך)
INSERT INTO products (name, price, description, quantity) VALUES 
('24K Gold Truffle', 18.0, 'Dark ganache dusted with edible 24k gold leaf', 6),
('Salted Caramel Dome', 12.5, 'Milk chocolate shell filled with oozing salted caramel', 9),
('Ruby Raspberry Heart', 14.0, 'Natural ruby chocolate with fresh raspberry puree', 6),
('Hazelnut Praline Bar', 22.0, 'Crunchy roasted hazelnuts in 60% dark chocolate', 9),
('שוקולד חלב', 8.0, 'נוגט, אגוזים', 4);

-- 3. טבלת הזמנות (כאן יישמר המייל של הקונה בתוך customer_name)
DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100), 
    items_summary VARCHAR(500),
    total_price DOUBLE,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);