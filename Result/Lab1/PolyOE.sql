create database PolyOE
go

use PolyOE
go

create table Users(
	Id nvarchar(20) NOT NULL,
	Password nvarchar(50) not null,
	Fullname nvarchar(50) not null,
	Email nvarchar(50) not null,
	Admin bit not null,
	primary key(Id)
)
go

insert into Users(Id, Password, Fullname, Email, Admin) values ('SD001', 'DefaultP4$$', 'Nguyen Quoc Minh', 'minhnqSD@gmail.com ', 1)
go
insert into Users(Id, Password, Fullname, Email, Admin) values ('SD002', 'DefaultP4$$', 'Trinh Thi Linh'  , 'linhttSD@gmail.com ', 0) 
go
insert into Users(Id, Password, Fullname, Email, Admin) values ('SD003', 'DefaultP4$$', 'Vo Anh Tuan'     , 'tuanvaSD@gmail.com ', 0) 
go
insert into Users(Id, Password, Fullname, Email, Admin) values ('SD004', 'DefaultP4$$', 'Nguyen Tan Thanh', 'thanhntSD@gmail.com', 1) 
go