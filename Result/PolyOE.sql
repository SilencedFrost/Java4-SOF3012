create database PolyOE
go

use PolyOE
go

create table Users(
	UserId nvarchar(20) NOT NULL,
	PasswordHash nvarchar(60) not null,
	FullName nvarchar(50) not null,
	Email nvarchar(50) not null,
	primary key(UserId)
)
go

create table Roles(
	RoleId int identity(1,1) primary key,
	UserId nvarchar(20) NOT NULL UNIQUE,
	RoleType nvarchar(20) NOT NULL,
	FOREIGN KEY (UserId) REFERENCES Users(UserId)
)
go

insert into Users(UserId, PasswordHash, FullName, Email) values ('SD001', 'DefaultP4$$', 'Nguyen Quoc Minh', 'minhnqSD@gmail.com ')
go
insert into Users(UserId, PasswordHash, FullName, Email) values ('SD002', 'DefaultP4$$', 'Trinh Thi Linh'  , 'linhttSD@gmail.com ') 
go
insert into Users(UserId, PasswordHash, FullName, Email) values ('SD003', 'DefaultP4$$', 'Vo Anh Tuan'     , 'tuanvaSD@gmail.com ') 
go
insert into Users(UserId, PasswordHash, FullName, Email) values ('SD004', 'DefaultP4$$', 'Nguyen Tan Thanh', 'thanhntSD@gmail.com') 
go

insert into Roles(UserId, RoleType) values ('SD001', 'Admin  ')
go
insert into Roles(UserId, RoleType) values ('SD002', 'User   ')
go
insert into Roles(UserId, RoleType) values ('SD003', 'Manager')
go
insert into Roles(UserId, RoleType) values ('SD004', 'Admin  ')
go