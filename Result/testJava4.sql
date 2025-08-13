create database testJava4
go

use testJava4
go

create table Categories(
	CategoryId varchar(20) primary key,
	CategoryName nvarchar(30) not null
)
go

create table Products(
	ProductId int primary key identity(1, 1),
	ProductName varchar(20),
	Price int,
	CategoryId varchar(20),
	foreign key (CategoryId) references Categories(CategoryId)
)
go

insert into Categories(CategoryId, CategoryName) values ('cat01', N'Điện tử');
insert into Categories(CategoryId, CategoryName) values ('cat02', N'Thực phẩm');
insert into Categories(CategoryId, CategoryName) values ('cat03', N'Đồ uống');
insert into Categories(CategoryId, CategoryName) values ('cat04', N'Văn phòng phẩm');
go

insert into Products(ProductName, Price, CategoryId) values ('Laptop', 350, 'cat01');
insert into Products(ProductName, Price, CategoryId) values ('Iphone', 1350, 'cat01');
insert into Products(ProductName, Price, CategoryId) values ('Tivi', 120, 'cat01');
insert into Products(ProductName, Price, CategoryId) values ('Bia Tiger', 20000, 'cat03');
insert into Products(ProductName, Price, CategoryId) values ('Pepsi', 5000, 'cat03');
go