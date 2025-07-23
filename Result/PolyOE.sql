create database PolyOE
go

use PolyOE
go

create table Roles(
	RoleId int PRIMARY KEY IDENTITY(1, 1),
	RoleName nvarchar(20) UNIQUE not null,
)
go

create table Users(
	UserId nvarchar(20) PRIMARY KEY not null,
	PasswordHash nvarchar(60) not null,
	Email nvarchar(50) not null,
	FullName nvarchar(50) not null,
	RoleId int not null,
	foreign key (RoleId) references Roles(RoleId)
)
go

create table Video(
	VideoId nvarchar(20) PRIMARY KEY not null,
	Title nvarchar(50) not null,
	Poster nvarchar(50) not null,
	ViewCount int not null,
	Descript nvarchar(200),
	Active bit not null,
)
go

create table Favourite (
	FavouriteId bigint PRIMARY KEY IDENTITY(1, 1),
	UserId nvarchar(20) not null,
	VideoId nvarchar(20) not null,
	LikeDate Date not null,
	foreign key (UserId) references Users(UserId),
	foreign key (VideoId) references Video(VideoId),
	CONSTRAINT unique_user_video UNIQUE (UserId, VideoId)
)
go

create table Share (
	ShareId bigint PRIMARY KEY IDENTITY(1,1) not null,
	UserId nvarchar(20) not null,
	VideoId nvarchar(20) not null,
	Emails nvarchar(50),
	ShareDate Date not null,
	foreign key (UserId) references Users(UserId),
	foreign key (VideoId) references Video(VideoId)
)
go

insert into Roles(RoleName) values ('User')
go
insert into Roles(RoleName) values ('Employee')
go
insert into Roles(RoleName) values ('Manager')
go
insert into Roles(RoleName) values ('Admin')
go


insert into Users(UserId, PasswordHash, FullName, Email, RoleId) values ('SD001', '$2a$12$rnfhc4D/Wxd7jnUSWdpEUeF6wlusVXdOjb/u1QwCnwtec/t6GeYX2', 'Nguyen Quoc Minh', 'minhnqSD@gmail.com ', 4)
go
insert into Users(UserId, PasswordHash, FullName, Email, RoleId) values ('SD002', '$2a$12$6Lneezy3jribVCUJpYzV/uwJ8kay1ZGjE2adnzs6g0M9RbqEJpZEC', 'Trinh Thi Linh'  , 'linhttSD@gmail.com ', 1) 
go
insert into Users(UserId, PasswordHash, FullName, Email, RoleId) values ('SD003', '$2a$12$LpW4G3snr1.auHyur8T6LekqQpJG3Y.jQrCuz7bc0a8xAnM5iUVOu', 'Vo Anh Tuan'     , 'tuanvaSD@gmail.com ', 1) 
go
insert into Users(UserId, PasswordHash, FullName, Email, RoleId) values ('SD004', '$2a$12$2ETrqO7JiV9tkkjxd/VaWe2OD1Xl90M2.O2h3oT9TJvPcA44vqoP2', 'Nguyen Tan Thanh', 'thanhntSD@gmail.com', 3) 
go

insert into Video(VideoId, Title, Poster, ViewCount, Descript, Active) values ('VD001', 'Video 1', 'poster1', 0, 'Basic video desc', 1)
go
insert into Video(VideoId, Title, Poster, ViewCount, Descript, Active) values ('VD002', 'Video 2', 'poster2', 0, 'Basic video desc', 1)
go
insert into Video(VideoId, Title, Poster, ViewCount, Descript, Active) values ('VD003', 'Video 3', 'poster3', 0, 'Basic video desc', 1)
go
insert into Video(VideoId, Title, Poster, ViewCount, Descript, Active) values ('VD004', 'Video 4', 'poster4', 0, 'Basic video desc', 1)
go

