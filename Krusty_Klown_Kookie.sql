/* this sql initializes the db and creates test tables */

PRAGMA foreign_keys = OFF;

DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS ingredients;
DROP TABLE IF EXISTS cookies;
DROP TABLE IF EXISTS recipes;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS cookieOrders;
DROP TABLE IF EXISTS pallets;
DROP TABLE IF EXISTS locations;

PRAGMA foreign_keys = ON;

/*Skapa tabeller*/
CREATE TABLE customers(
name varchar(20) not null,
address varchar(40),
primary key(name)
);

CREATE TABLE ingredients(
name varchar(30) not null,
stock int,
unit varchar(10),
lastDelivered date,
amountDelivered int,
primary key(name)
);

CREATE TABLE cookies(
	name varchar(30) not null,
	isBlocked boolean,
	primary key (name)
);

CREATE TABLE recipes(
	cookie_name varchar(30),
	ingredient_name varchar(30),
	amount int,
	unit varchar (10),
	primary key (cookie_name, ingredient_name),
	foreign key (cookie_name) REFERENCES cookies(name),
	foreign key (ingredient_name) REFERENCES ingredients(name)
);

CREATE TABLE orders(
	ID INTEGER,
	orderDate date,
	customer_name varchar (30) NOT NULL,
	primary key (ID),
	foreign key (customer_name) REFERENCES customers(name)
);

CREATE TABLE cookieOrders(
	orderID int,
	cookie_name varchar(30),
	nbrpallets int,
	primary key (orderID, cookie_name),
	foreign key (orderID) REFERENCES orders(ID),
	foreign key (cookie_name) REFERENCES cookies(name)
);

CREATE TABLE pallets(
ID integer,
production_date date,
cookie_name varchar(20),
location varchar(30),
orderID int,
delivery_date date,
isDelivered boolean,
isPalletBlocked boolean,
primary key (ID),
foreign key (cookie_name) REFERENCES cookies(name),
foreign key (orderID) REFERENCES orders(ID),
foreign key (location) references locations(name)
);

CREATE TABLE locations(
 name varchar(30),
 primary key (name)
);

/* Insert Customers */
INSERT INTO customers (name, address) VALUES 
('Finnkakor AB', 'Helsingborg'),
('Småbröd AB', 'Malmö'),
('Kaffebröd AB', 'Landskrona'),
('Bjudkakor AB', 'Ystad'),
('Kalaskakor AB', 'Trelleborg'),
('Partykakor AB', 'Kristianstad'),
('Gästkakor AB', 'Hässleholm'),
('Skånekakor AB', 'Perstorp');

/*Instert cookies*/
INSERT INTO cookies (name, isBlocked) VALUES
('Nut ring', 1),
('Nut cookie', 0),
('Amneris', 0),
('Tango', 0),
('Almond delight', 0),
('Berliner', 0);

/* Insert ingredients*/
INSERT INTO ingredients(name, stock, unit, lastDelivered, amountDelivered) VALUES
('Flour', 4000000, 'g', date(), 100000),
('Butter', 4000000, 'g', date(), 100000),
('Icing sugar', 1000000, 'g', date(), 100000),
('Roasted, chopped nuts',1000000, 'g', date(), 100000),
('Fine-ground nuts', 1000000, 'g', date(), 100000),
('Ground, roasted nuts', 1000000, 'g', date(), 100000),
('Bread crumbs', 1000000, 'g', date(), 100000),
('Sugar', 5000000, 'g', date(), 100000),
('Egg whites', 1000000, 'dl', date(), 100000),
('Chocolate', 1000000, 'g', date(), 100000),
('Marzipan', 2000000, 'g', date(), 100000),
('Eggs', 1000000, 'g', date(), 100000),
('Potato starch', 1000000, 'g', date(), 100000),
('Wheat flour', 1000000, 'g', date(), 100000),
('Sodium bicarbonate', 100000, 'g', date(), 100000),
('Vanilla', 100000, 'g', date(), 100000),
('Chopped almonds', 1000000, 'g', date(), 100000),
('Cinnamon', 1000000, 'g', date(), 100000),
('Vanilla sugar', 1000000, 'g', date(), 100000);

/*Insert order values*/
INSERT INTO orders(customer_name, orderDate) VALUES
('Finnkakor AB', date()),
('Kalaskakor AB', date()),
('Småbröd AB', date());

/*Cookike orders: */
INSERT INTO cookieOrders(orderID, cookie_name, nbrPallets) VALUES
(1, 'Berliner', 100),
(1, 'Nut ring', 150),
(2, 'Nut ring', 100),
(2, 'Almond delight', 50),
(2, 'Tango', 100),
(3, 'Tango', 750);

/* Vilka locations har vi? */
INSERT INTO locations(name) VALUES
('Production'),
('Freezing'),
('Packaging'),
('Storage');

/*Insert recipes*/
INSERT INTO recipes(cookie_name, ingredient_name, amount, unit) VALUES

/*Nut ring*/
('Nut ring', 'Flour', 450, 'g'),
('Nut ring', 'Butter', 450, 'g'),
('Nut ring', 'Icing sugar', 190, 'g'),
('Nut ring', 'Roasted, chopped nuts', 225, 'g'),

/*Nut cookie*/
('Nut cookie', 'Fine-ground nuts', 750, 'g'),
('Nut cookie', 'Ground, roasted nuts', 625, 'g'),
('Nut cookie', 'Bread crumbs', 125, 'g'),
('Nut cookie', 'Sugar', 375, 'g'),
('Nut cookie', 'Egg whites', 3.5, 'dl'),
('Nut cookie', 'Chocolate', 50, 'g'),

/*Amneris*/
('Amneris', 'Marzipan', 750, 'g'),
('Amneris', 'Butter', 250, 'g'),
('Amneris', 'Eggs', 250, 'g'),
('Amneris', 'Potato starch', 25, 'g'),
('Amneris', 'Wheat flour', 25, 'g'),

/*Tango*/
('Tango', 'Butter', 200, 'g'),
('Tango', 'Sugar', 250, 'g'),
('Tango', 'Flour', 300, 'g'),
('Tango', 'Sodium bicarbonate', 4, 'g'),
('Tango', 'Vanilla', '2', 'g'),

/*Almond delight*/
('Almond delight', 'Butter', 400, 'g'),
('Almond delight', 'Sugar', 270, 'g'),
('Almond delight', 'Chopped almonds', 279, 'g'),
('Almond delight', 'Flour', 400, 'g'),
('Almond delight', 'Cinnamon', 10, 'g'),

/*Berliner*/
('Berliner', 'Flour', 350, 'g'),
('Berliner', 'Butter', 250, 'g'),
('Berliner', 'Icing sugar', 100, 'g'),
('Berliner', 'Eggs', 50, 'g'),
('Berliner', 'Vanilla sugar', 5, 'g'),
('Berliner', 'Chocolate', 50, 'g');


SELECT * FROM customers;
SELECT * FROM ingredients;
SELECT * FROM cookies;
SELECT * FROM recipes;
SELECT * FROM orders;
SELECT * FROM cookieOrders;
SELECT * FROM pallets;
SELECT * FROM locations;
