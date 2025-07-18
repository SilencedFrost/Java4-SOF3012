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

insert into Users(UserId, PasswordHash, FullName, Email) values ('SD001', '$2a$12$rnfhc4D/Wxd7jnUSWdpEUeF6wlusVXdOjb/u1QwCnwtec/t6GeYX2', 'Nguyen Quoc Minh', 'minhnqSD@gmail.com ')
go
insert into Users(UserId, PasswordHash, FullName, Email) values ('SD002', '$2a$12$6Lneezy3jribVCUJpYzV/uwJ8kay1ZGjE2adnzs6g0M9RbqEJpZEC', 'Trinh Thi Linh'  , 'linhttSD@gmail.com ') 
go
insert into Users(UserId, PasswordHash, FullName, Email) values ('SD003', '$2a$12$LpW4G3snr1.auHyur8T6LekqQpJG3Y.jQrCuz7bc0a8xAnM5iUVOu', 'Vo Anh Tuan'     , 'tuanvaSD@gmail.com ') 
go
insert into Users(UserId, PasswordHash, FullName, Email) values ('SD004', '$2a$12$2ETrqO7JiV9tkkjxd/VaWe2OD1Xl90M2.O2h3oT9TJvPcA44vqoP2', 'Nguyen Tan Thanh', 'thanhntSD@gmail.com') 
go

insert into Roles(UserId, RoleType) values ('SD001', 'Admin  ')
go
insert into Roles(UserId, RoleType) values ('SD002', 'User   ')
go
insert into Roles(UserId, RoleType) values ('SD003', 'Manager')
go
insert into Roles(UserId, RoleType) values ('SD004', 'Admin  ')
go