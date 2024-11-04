--create database SWP391
--drop database SWP391

-- Role Table
CREATE TABLE [role] (
  roleID INTEGER IDENTITY(1,1) PRIMARY KEY, -- Auto-increment for roleID
  roleName VARCHAR(100)
);

-- User Table
CREATE TABLE [user] (
  userID INTEGER IDENTITY(1,1) PRIMARY KEY, -- Auto-increment for userID
  fullname VARCHAR(255),
  phone NVARCHAR(20),
  username VARCHAR(255),
  password VARCHAR(255), 
  gender VARCHAR(10),
  status BIT,
  roleID INTEGER,
  address VARCHAR(255),
  FOREIGN KEY (roleID) REFERENCES role(roleID)
);

-- Origin Table
CREATE TABLE origin (
  originID INTEGER IDENTITY(1,1) PRIMARY KEY, -- Auto-increment for originID
  originName NVARCHAR(255)
);

-- Category Table
CREATE TABLE category (
  categoryID INTEGER IDENTITY(1,1) PRIMARY KEY, -- Auto-increment for categoryID
  parentCategoryID INTEGER,
  categoryName VARCHAR(255),
  description VARCHAR(1000),
  FOREIGN KEY (parentCategoryID) REFERENCES category(categoryID)
);

-- Product Unit Table (Moved before Product Table)
CREATE TABLE productUnit (
  unitID INTEGER IDENTITY(1,1) PRIMARY KEY, -- Auto-increment for unitID
  productID INTEGER,
  unitName VARCHAR(50),
  unitToBaseConvertRate DECIMAL(10, 4),
);

-- Product Table (no IDENTITY for productID)
CREATE TABLE product (
  productID INTEGER PRIMARY KEY, -- No IDENTITY for productID
  productName VARCHAR(255),
  categoryID INTEGER,
  originID INTEGER,
  manufacturer NVARCHAR(255),
  componentDescription NVARCHAR(255),
  description VARCHAR(1000),
  baseUnitID INTEGER NULL, -- Initially NULL
  isActive BIT,
  FOREIGN KEY (categoryID) REFERENCES category(categoryID),
  FOREIGN KEY (originID) REFERENCES origin(originID),
  FOREIGN KEY (baseUnitID) REFERENCES productUnit(unitID)
);

-- Adding foreign key
ALTER TABLE productUnit
ADD CONSTRAINT FK_productUnit_product
FOREIGN KEY (productID) REFERENCES product(productID);

-- Product Detail Table
CREATE TABLE productDetail (
  productDetailID INTEGER IDENTITY(1,1) PRIMARY KEY, -- Auto-increment for productDetailID
  unitID INTEGER,
  avgImportPrice DECIMAL(10, 2),
  stock DECIMAL(10, 2),
  baseSoldPrice DECIMAL(10, 2),
  batchNo INTEGER,
  manufactureDate DATE,
  expiredDate DATE,
  isActive BIT,
  FOREIGN KEY (unitID) REFERENCES productUnit(unitID)
);

-- Blog Table
CREATE TABLE blog (
  blogID INTEGER IDENTITY(1,1) PRIMARY KEY, -- Auto-increment for blogID
  title NVARCHAR(255),
  content NVARCHAR(1000),
  publicDate DATE,
  userID INTEGER,
  status BIT,
  FOREIGN KEY (userID) REFERENCES [user](userID)
);

-- Tag Table
CREATE TABLE tag (
  tagID INTEGER IDENTITY(1,1) PRIMARY KEY, -- Auto-increment for tagID
  tagName NVARCHAR(100)
);

-- BlogTag Table (Composite Key, no need for auto-increment)
CREATE TABLE blogTag (
  blogID INTEGER,
  tagID INTEGER,
  PRIMARY KEY (blogID, tagID),
  FOREIGN KEY (blogID) REFERENCES blog(blogID),
  FOREIGN KEY (tagID) REFERENCES tag(tagID)
);


-- Order Status Table
CREATE TABLE orderStatus (
statusID INTEGER IDENTITY(1,1) PRIMARY KEY, -- Auto-increment for statusID
  statusName NVARCHAR(50),
  description NVARCHAR(255)
);

-- Order Table
CREATE TABLE [order] (
  orderID INTEGER IDENTITY(1,1) PRIMARY KEY, -- Auto-increment for orderID
  userID INTEGER,
  orderDate DATE,
  orderCompleted date,
  statusID INTEGER,
  totalPrice DECIMAL(10, 2),
  shipName NVARCHAR(255),
  shipAddress NVARCHAR(255),
  shipPhone NVARCHAR(20),
  shipNote NVARCHAR(550),
  rejectReason NVARCHAR(255),
  FOREIGN KEY (userID) REFERENCES [user](userID),
  FOREIGN KEY (statusID) REFERENCES orderStatus(statusID)
);

-- Order Detail Table
CREATE TABLE orderDetail (
  orderDetailID INTEGER IDENTITY(1,1) PRIMARY KEY, -- Auto-increment for orderDetailID
  orderID INTEGER,
  productID INTEGER,
  productDetailID INTEGER,
  unitID INTEGER,
  soldPrice DECIMAL(10, 2),
  quantity INTEGER,
  totalProductPrice AS soldPrice * quantity, -- Automatically calculates total price
  FOREIGN KEY (orderID) REFERENCES [order](orderID),
  FOREIGN KEY (productID) REFERENCES product(productID),
  FOREIGN KEY (productDetailID) REFERENCES productDetail(productDetailID),
  FOREIGN KEY (unitID) REFERENCES productUnit(unitID)
);

-- Product Import Table
CREATE TABLE productImport (
  orderImportID INTEGER IDENTITY(1,1) PRIMARY KEY, -- Auto-increment for orderImportID
  unitID INTEGER,
  importPrice DECIMAL(10, 2),
  quantity INTEGER,
  batchNo INTEGER,
  manufactureDate DATE,
  expiredDate DATE,
  FOREIGN KEY (unitID) REFERENCES productUnit(unitID)
);

CREATE TABLE componentProduct(
	componentName NVARCHAR(255),
	productID INTEGER,
	quantity DECIMAL(10,2),
	componentUnit NVARCHAR(31),
	FOREIGN KEY (productID) REFERENCES product(productID)
)