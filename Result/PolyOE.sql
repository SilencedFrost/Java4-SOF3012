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
	Email nvarchar(50) UNIQUE not null,
	FullName nvarchar(50) not null,
	RoleId int not null,
	foreign key (RoleId) references Roles(RoleId)
)
go

create table Video(
	VideoId nvarchar(20) PRIMARY KEY not null,
	Title nvarchar(100) not null,
	Poster nvarchar(50) not null,
	Link nvarchar(100) UNIQUE not null,
	ViewCount int not null,
	Descript nvarchar(500),
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

insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD001', '5 Satisfactory Tips that are Obvious Once You See Them', 'https://www.youtube.com/watch?v=x12trt4SMFg', 'poster1', 0, 'Basic video desc', 1)
go
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD002', 'How Im Using the Satisfactory 1.1 Features in My Factory', 'https://www.youtube.com/watch?v=uRjDFtabuVA', 'poster2', 0, 'Basic video desc', 1)
go
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD003', 'How One Drop of Water Cools Your Phone', 'https://www.youtube.com/watch?v=qAZ-q3KmDHM', 'poster3', 0, 'Basic video desc', 1)
go
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD004', 'The Secret of Incendiary Water | Metallic Fires', 'https://www.youtube.com/watch?v=Jgmy-796dtc', 'poster4', 0, 'Basic video desc', 1)
go
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD005', 'This 200-Year-Old Lighter Ignites Without a Spark', 'https://www.youtube.com/watch?v=Mcg9GcilBfU', 'poster5', 0, 'Basic video desc', 1)
go
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD006', 'Stronger 3D Prints & Less Supports: How to Design for 3D Printing', 'https://www.youtube.com/watch?v=vRA776CtTw0&', 'poster6', 0, 'Basic video desc', 1)
go
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD007', 'FreeCAD is the BEST for 3D Printing - 5 reasons WHY !', 'https://www.youtube.com/watch?v=Z8zmoXBMvyA', 'poster7', 0, 'Basic video desc', 1)
go
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD008', 'The Fatal Error No One Talks About – Get Incredibly Clean 3D Prints!', 'https://www.youtube.com/watch?v=7MOKjQxbP18', 'poster8', 0, 'Basic video desc', 1)
go
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD009', 'How to Build Belt Compressors - Priority Merger Guide Satisfactory 1.1', 'https://www.youtube.com/watch?v=HDMTRImkHC8', 'poster9', 0, 'Basic video desc', 1)
go
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD010', 'How do Physics Engines work?', 'https://www.youtube.com/watch?v=LmGofzJ8d3k', 'poster10', 0, 'Basic video desc', 1)
go
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD011', 'What Is Incremental Validity? - The Friendly Statistician', 'https://www.youtube.com/watch?v=OesCL-yKowU', 'poster11', 0, 'Basic video desc', 1)
go
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD012', 'You Dont Really Understand Mechanical Engineering', 'https://www.youtube.com/watch?v=VWS6CNJtldU', 'poster12', 0, 'Basic video desc', 1)
go
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD013', 'Only Real Mechanical Engineers Can Spot These Design Mistakes | Sheet Metal', 'https://www.youtube.com/watch?v=xHa52v5xNOU', 'poster13', 0, 'Basic video desc', 1)
go
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD014', 'Easy Lighting Tricks to Make Your Factory Pop!', 'https://www.youtube.com/watch?v=BtfCOKa2eNk', 'poster14', 0, 'Basic video desc', 1)
go
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD015', 'Brightest Flashlight Ive Ever Tested!', 'https://www.youtube.com/watch?v=7W3oWskU8t8', 'poster15', 0, 'Basic video desc', 1)
go
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD016', 'Why GPU Programming Is Chaotic', 'https://www.youtube.com/watch?v=oaOxMdKlJTc', 'poster16', 0, 'Basic video desc', 1)
go

insert into Favourite(UserId, VideoId, LikeDate) values ('SD001', 'VD001', '2024-01-15')
go
insert into Favourite(UserId, VideoId, LikeDate) values ('SD001', 'VD003', '2024-01-20')
go
insert into Favourite(UserId, VideoId, LikeDate) values ('SD001', 'VD010', '2024-02-05')
go
insert into Favourite(UserId, VideoId, LikeDate) values ('SD001', 'VD012', '2024-02-10')
go
insert into Favourite(UserId, VideoId, LikeDate) values ('SD002', 'VD002', '2024-01-18')
go
insert into Favourite(UserId, VideoId, LikeDate) values ('SD002', 'VD004', '2024-01-25')
go
insert into Favourite(UserId, VideoId, LikeDate) values ('SD002', 'VD006', '2024-02-01')
go
insert into Favourite(UserId, VideoId, LikeDate) values ('SD002', 'VD008', '2024-02-12')
go
insert into Favourite(UserId, VideoId, LikeDate) values ('SD002', 'VD011', '2024-02-15')
go
insert into Favourite(UserId, VideoId, LikeDate) values ('SD003', 'VD001', '2024-01-22')
go
insert into Favourite(UserId, VideoId, LikeDate) values ('SD003', 'VD005', '2024-01-28')
go
insert into Favourite(UserId, VideoId, LikeDate) values ('SD003', 'VD007', '2024-02-03')
go
insert into Favourite(UserId, VideoId, LikeDate) values ('SD003', 'VD009', '2024-02-08')
go
insert into Favourite(UserId, VideoId, LikeDate) values ('SD003', 'VD013', '2024-02-18')
go
insert into Favourite(UserId, VideoId, LikeDate) values ('SD004', 'VD014', '2024-01-30')
go
insert into Favourite(UserId, VideoId, LikeDate) values ('SD004', 'VD015', '2024-02-06')
go
insert into Favourite(UserId, VideoId, LikeDate) values ('SD004', 'VD016', '2024-02-14')
go

insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'VD001', 'colleague1@company.com', '2024-01-16')
go
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'VD003', 'friend@gmail.com', '2024-01-21')
go
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'VD010', 'student@university.edu', '2024-02-06')
go
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'VD002', 'teammate@work.com', '2024-01-19')
go
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'VD004', 'family@email.com', '2024-01-26')
go
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'VD006', 'designer@studio.com', '2024-02-02')
go
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'VD008', 'maker@fablab.org', '2024-02-13')
go
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'VD001', 'engineer@tech.com', '2024-01-23')
go
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'VD005', 'chemistry@lab.edu', '2024-01-29')
go
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'VD007', '3dprinting@community.org', '2024-02-04')
go
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'VD009', 'gamer@satisfactory.fan', '2024-02-09')
go
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'VD012', 'junior@engineering.com', '2024-01-31')
go
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'VD013', 'designer@mechanical.co', '2024-02-07')
go
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'VD014', 'architect@lighting.net', '2024-02-15')
go
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'VD015', 'outdoors@adventure.com', '2024-02-16')
go
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'VD012', 'mentor@company.com', '2024-02-11')
go
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'VD011', 'statistician@research.edu', '2024-02-16')
go
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'VD013', 'metalworker@factory.com', '2024-02-19')
go
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'VD016', 'developer@gpu.tech', '2024-02-17')
go

