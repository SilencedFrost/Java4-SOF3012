create database PolyOE
go

use PolyOE
go

create table Visits(
	VisitId int PRIMARY KEY IDENTITY(1, 1),
	VisitCount int,
)
go

create table Roles(
	RoleId int PRIMARY KEY IDENTITY(1, 1),
	RoleName nvarchar(20) UNIQUE not null
)
go

create table Users(
	UserId nvarchar(30) PRIMARY KEY,
	PasswordHash nvarchar(60) not null,
	FullName nvarchar(50) not null,
	Email nvarchar(50) UNIQUE not null,
	RoleId int not null,
	CreationDate datetime2(3) not null,
	foreign key (RoleId) references Roles(RoleId)
)
go

create table Video(
	VideoId nvarchar(20) PRIMARY KEY,
	Title nvarchar(100) not null,
	Thumbnail nvarchar(50) not null,
	Link nvarchar(100) UNIQUE not null,
	ViewCount int not null,
	Descript nvarchar(500),
	Active bit not null,
)
go

create table Favourite (
	FavouriteId bigint PRIMARY KEY IDENTITY(1, 1),
	UserId nvarchar(30) not null,
	VideoId nvarchar(20) not null,
	FavouriteDate datetime2(3) not null,
	foreign key (UserId) references Users(UserId),
	foreign key (VideoId) references Video(VideoId),
	CONSTRAINT unique_user_video UNIQUE (UserId, VideoId)
)
go

create table Share (
	ShareId bigint PRIMARY KEY IDENTITY(1,1),
	UserId nvarchar(30) not null,
	VideoId nvarchar(20) not null,
	Emails nvarchar(50) not null,
	ShareDate datetime2(3) not null,
	foreign key (UserId) references Users(UserId),
	foreign key (VideoId) references Video(VideoId)
)
go

create table Logs (
	LogId bigint PRIMARY KEY IDENTITY(1, 1),
	Link nvarchar(100),
	LoginTime datetime2(3) not null,
	UserId nvarchar(30) not null,
	foreign key (UserId) references Users(UserId)
)
go

create table Comments (
	CommentId bigint PRIMARY KEY IDENTITY(1,1),
	UserId nvarchar(30) not null,
	VideoId nvarchar(20) not null,
	CommentDate datetime2(3) not null,
	CommentContent nvarchar(500) not null,
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


insert into Users(UserId, PasswordHash, FullName, Email, RoleId, CreationDate) values ('SD001', '$2a$12$rnfhc4D/Wxd7jnUSWdpEUeF6wlusVXdOjb/u1QwCnwtec/t6GeYX2', 'Nguyen Quoc Minh', 'minhnqSD@gmail.com ', 4, '2023-01-17 08:12:45.137');
insert into Users(UserId, PasswordHash, FullName, Email, RoleId, CreationDate) values ('SD002', '$2a$12$6Lneezy3jribVCUJpYzV/uwJ8kay1ZGjE2adnzs6g0M9RbqEJpZEC', 'Trinh Thi Linh'  , 'linhttSD@gmail.com ', 3, '2023-03-05 21:06:11.903');
insert into Users(UserId, PasswordHash, FullName, Email, RoleId, CreationDate) values ('SD003', '$2a$12$LpW4G3snr1.auHyur8T6LekqQpJG3Y.jQrCuz7bc0a8xAnM5iUVOu', 'Vo Anh Tuan'     , 'tuanvaSD@gmail.com ', 2, '2023-06-22 14:33:59.047');
insert into Users(UserId, PasswordHash, FullName, Email, RoleId, CreationDate) values ('SD004', '$2a$12$2ETrqO7JiV9tkkjxd/VaWe2OD1Xl90M2.O2h3oT9TJvPcA44vqoP2', 'Nguyen Tan Thanh', 'thanhntSD@gmail.com', 1, '2023-09-10 07:25:10.500');
insert into Users(UserId, PasswordHash, FullName, Email, RoleId, CreationDate) values ('SD005', '$2a$12$rnfhc4D/Wxd7jnUSWdpEUeF6wlusVXdOjb/u1QwCnwtec/t6GeYX2', 'Le Thi Mai'      , 'mailtSD@gmail.com'  , 1, '2023-12-31 23:59:59.999');
insert into Users(UserId, PasswordHash, FullName, Email, RoleId, CreationDate) values ('SD006', '$2a$12$6Lneezy3jribVCUJpYzV/uwJ8kay1ZGjE2adnzs6g0M9RbqEJpZEC', 'Pham Van Duc'    , 'ducpvSD@gmail.com'  , 1, '2024-02-14 10:05:30.250');
insert into Users(UserId, PasswordHash, FullName, Email, RoleId, CreationDate) values ('SD007', '$2a$12$LpW4G3snr1.auHyur8T6LekqQpJG3Y.jQrCuz7bc0a8xAnM5iUVOu', 'Tran Thi Hoa'    , 'hoattSD@gmail.com'  , 1, '2024-04-01 00:00:00.000');
insert into Users(UserId, PasswordHash, FullName, Email, RoleId, CreationDate) values ('SD008', '$2a$12$2ETrqO7JiV9tkkjxd/VaWe2OD1Xl90M2.O2h3oT9TJvPcA44vqoP2', 'Hoang Van Long'  , 'longhvSD@gmail.com' , 1, '2024-06-15 18:44:12.777');
insert into Users(UserId, PasswordHash, FullName, Email, RoleId, CreationDate) values ('SD009', '$2a$12$rnfhc4D/Wxd7jnUSWdpEUeF6wlusVXdOjb/u1QwCnwtec/t6GeYX2', 'Nguyen Thi Lan'  , 'lanntSD@gmail.com'  , 1, '2024-09-09 09:09:09.090');
insert into Users(UserId, PasswordHash, FullName, Email, RoleId, CreationDate) values ('SD010', '$2a$12$6Lneezy3jribVCUJpYzV/uwJ8kay1ZGjE2adnzs6g0M9RbqEJpZEC', 'Vu Van Hung'     , 'hungvvSD@gmail.com' , 1, '2024-11-30 16:20:00.333');
insert into Users(UserId, PasswordHash, FullName, Email, RoleId, CreationDate) values ('SD011', '$2a$12$LpW4G3snr1.auHyur8T6LekqQpJG3Y.jQrCuz7bc0a8xAnM5iUVOu', 'Do Thi Thu'      , 'thudtSD@gmail.com'  , 1, '2025-01-01 01:23:45.678');
insert into Users(UserId, PasswordHash, FullName, Email, RoleId, CreationDate) values ('SD012', '$2a$12$2ETrqO7JiV9tkkjxd/VaWe2OD1Xl90M2.O2h3oT9TJvPcA44vqoP2', 'Bui Van Nam'     , 'nambvSD@gmail.com'  , 1, '2025-03-12 12:34:56.123');
insert into Users(UserId, PasswordHash, FullName, Email, RoleId, CreationDate) values ('SD013', '$2a$12$rnfhc4D/Wxd7jnUSWdpEUeF6wlusVXdOjb/u1QwCnwtec/t6GeYX2', 'Luu Thi Nga'     , 'ngaltSD@gmail.com'  , 1, '2025-05-05 05:05:05.555');
insert into Users(UserId, PasswordHash, FullName, Email, RoleId, CreationDate) values ('SD014', '$2a$12$6Lneezy3jribVCUJpYzV/uwJ8kay1ZGjE2adnzs6g0M9RbqEJpZEC', 'Dang Van Khai'   , 'khaidvSD@gmail.com' , 1, '2025-07-01 19:48:30.012');
go

insert into Video(VideoId, Title, Thumbnail, Link, ViewCount, Descript, Active) values ('x12trt4SMFg', '5 Satisfactory Tips that are Obvious Once You See Them'                     , 'x12trt4SMFg-HD.jpg', 'https://www.youtube.com/watch?v=x12trt4SMFg', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Thumbnail, Link, ViewCount, Descript, Active) values ('uRjDFtabuVA', 'How Im Using the Satisfactory 1.1 Features in My Factory'                   , 'uRjDFtabuVA-HD.jpg', 'https://www.youtube.com/watch?v=uRjDFtabuVA', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Thumbnail, Link, ViewCount, Descript, Active) values ('qAZ-q3KmDHM', 'How One Drop of Water Cools Your Phone'                                     , 'qAZ-q3KmDHM-HD.jpg', 'https://www.youtube.com/watch?v=qAZ-q3KmDHM', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Thumbnail, Link, ViewCount, Descript, Active) values ('Jgmy-796dtc', 'The Secret of Incendiary Water | Metallic Fires'                            , 'Jgmy-796dtc-HD.jpg', 'https://www.youtube.com/watch?v=Jgmy-796dtc', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Thumbnail, Link, ViewCount, Descript, Active) values ('Mcg9GcilBfU', 'This 200-Year-Old Lighter Ignites Without a Spark'                          , 'Mcg9GcilBfU-HD.jpg', 'https://www.youtube.com/watch?v=Mcg9GcilBfU', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Thumbnail, Link, ViewCount, Descript, Active) values ('vRA776CtTw0', 'Stronger 3D Prints & Less Supports: How to Design for 3D Printing'          , 'vRA776CtTw0-HD.jpg', 'https://www.youtube.com/watch?v=vRA776CtTw0', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Thumbnail, Link, ViewCount, Descript, Active) values ('Z8zmoXBMvyA', 'FreeCAD is the BEST for 3D Printing - 5 reasons WHY !'                      , 'Z8zmoXBMvyA-HD.jpg', 'https://www.youtube.com/watch?v=Z8zmoXBMvyA', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Thumbnail, Link, ViewCount, Descript, Active) values ('7MOKjQxbP18', 'The Fatal Error No One Talks About – Get Incredibly Clean 3D Prints!'       , '7MOKjQxbP18-HD.jpg', 'https://www.youtube.com/watch?v=7MOKjQxbP18', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Thumbnail, Link, ViewCount, Descript, Active) values ('HDMTRImkHC8', 'How to Build Belt Compressors - Priority Merger Guide Satisfactory 1.1'     , 'HDMTRImkHC8-HD.jpg', 'https://www.youtube.com/watch?v=HDMTRImkHC8', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Thumbnail, Link, ViewCount, Descript, Active) values ('LmGofzJ8d3k', 'How do Physics Engines work?'                                               , 'LmGofzJ8d3k-HD.jpg', 'https://www.youtube.com/watch?v=LmGofzJ8d3k', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Thumbnail, Link, ViewCount, Descript, Active) values ('OesCL-yKowU', 'What Is Incremental Validity? - The Friendly Statistician'                  , 'OesCL-yKowU-HD.jpg', 'https://www.youtube.com/watch?v=OesCL-yKowU', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Thumbnail, Link, ViewCount, Descript, Active) values ('VWS6CNJtldU', 'You Dont Really Understand Mechanical Engineering'                          , 'VWS6CNJtldU-HD.jpg', 'https://www.youtube.com/watch?v=VWS6CNJtldU', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Thumbnail, Link, ViewCount, Descript, Active) values ('xHa52v5xNOU', 'Only Real Mechanical Engineers Can Spot These Design Mistakes | Sheet Metal', 'xHa52v5xNOU-HD.jpg', 'https://www.youtube.com/watch?v=xHa52v5xNOU', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Thumbnail, Link, ViewCount, Descript, Active) values ('BtfCOKa2eNk', 'Easy Lighting Tricks to Make Your Factory Pop!'                             , 'BtfCOKa2eNk-HD.jpg', 'https://www.youtube.com/watch?v=BtfCOKa2eNk', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Thumbnail, Link, ViewCount, Descript, Active) values ('7W3oWskU8t8', 'Brightest Flashlight Ive Ever Tested!'                                      , '7W3oWskU8t8-HD.jpg', 'https://www.youtube.com/watch?v=7W3oWskU8t8', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Thumbnail, Link, ViewCount, Descript, Active) values ('oaOxMdKlJTc', 'Why GPU Programming Is Chaotic'                                             , 'oaOxMdKlJTc-HD.jpg', 'https://www.youtube.com/watch?v=oaOxMdKlJTc', 0, 'Basic video desc', 1)
go

-- 50 Favourite entries with skewed user preferences and bell curve distribution
-- SD004, SD005, SD006 heavily prefer Satisfactory videos
-- SD007, SD008, SD009 heavily prefer Science/Physics videos  
-- SD010, SD011 heavily prefer 3D Printing videos
-- SD012, SD013, SD014 heavily prefer Engineering videos
-- Some videos have high popularity (bell curve peak), others moderate, 1-2 have none

-- Satisfactory enthusiasts (SD004, SD005, SD006) - 16 entries
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD004', 'x12trt4SMFg', '2024-01-15 09:12:45.137')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD004', 'uRjDFtabuVA', '2024-01-16 13:05:11.903')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD004', 'HDMTRImkHC8', '2024-01-17 18:33:59.047')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD004', 'BtfCOKa2eNk', '2024-01-18 07:25:10.500')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD005', 'x12trt4SMFg', '2024-01-20 10:41:22.003')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD005', 'uRjDFtabuVA', '2024-01-21 15:06:30.250')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD005', 'HDMTRImkHC8', '2024-01-22 20:14:12.777')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD005', 'BtfCOKa2eNk', '2024-01-23 22:59:59.999')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD006', 'x12trt4SMFg', '2024-01-25 08:03:10.120')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD006', 'uRjDFtabuVA', '2024-01-26 11:27:44.333')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD006', 'HDMTRImkHC8', '2024-01-27 16:48:05.610')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD006', 'BtfCOKa2eNk', '2024-01-28 19:35:42.086')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD001', 'x12trt4SMFg', '2024-02-01 09:00:00.000')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD002', 'uRjDFtabuVA', '2024-02-02 10:12:34.567')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD003', 'HDMTRImkHC8', '2024-02-03 14:20:15.432')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD007', 'x12trt4SMFg', '2024-02-04 21:45:55.210')
go

-- Science/Physics enthusiasts (SD007, SD008, SD009) - 15 entries
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD007', 'qAZ-q3KmDHM', '2024-01-10 08:10:05.015')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD007', 'Jgmy-796dtc', '2024-01-11 09:22:18.240')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD007', 'Mcg9GcilBfU', '2024-01-12 11:35:49.875')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD007', 'LmGofzJ8d3k', '2024-01-13 20:05:33.004')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD008', 'qAZ-q3KmDHM', '2024-01-14 07:45:12.090')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD008', 'Jgmy-796dtc', '2024-01-15 12:30:00.300')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD008', 'Mcg9GcilBfU', '2024-01-16 17:10:27.654')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD008', 'LmGofzJ8d3k', '2024-01-17 22:58:41.020')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD009', 'qAZ-q3KmDHM', '2024-01-18 06:19:08.500')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD009', 'Jgmy-796dtc', '2024-01-19 13:40:40.040')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD009', 'Mcg9GcilBfU', '2024-01-20 19:55:22.220')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD001', 'qAZ-q3KmDHM', '2024-02-05 08:08:08.080')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD002', 'Jgmy-796dtc', '2024-02-06 10:16:24.360')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD003', 'LmGofzJ8d3k', '2024-02-07 15:45:10.010')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD004', 'qAZ-q3KmDHM', '2024-02-08 23:11:42.705')
go

-- 3D Printing enthusiasts (SD010, SD011) - 9 entries
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD010', 'vRA776CtTw0', '2024-01-05 09:30:00.000')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD010', 'Z8zmoXBMvyA', '2024-01-06 14:12:06.606')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD010', '7MOKjQxbP18', '2024-01-07 18:45:45.450')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD011', 'vRA776CtTw0', '2024-01-08 07:07:07.070')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD011', 'Z8zmoXBMvyA', '2024-01-09 11:29:31.310')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD011', '7MOKjQxbP18', '2024-01-10 20:20:20.200')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD001', 'vRA776CtTw0', '2024-02-09 08:55:00.123')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD005', 'Z8zmoXBMvyA', '2024-02-10 12:10:10.010')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD009', 'vRA776CtTw0', '2024-02-11 21:14:59.999')
go

-- Engineering enthusiasts (SD012, SD013, SD014) - 6 entries
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD012', 'VWS6CNJtldU', '2024-01-01 01:02:03.004')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD012', 'xHa52v5xNOU', '2024-01-02 02:13:21.345')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD013', 'VWS6CNJtldU', '2024-01-03 03:14:15.926')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD013', 'xHa52v5xNOU', '2024-01-04 04:20:00.500')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD014', 'VWS6CNJtldU', '2024-01-05 05:55:55.555')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD003', 'xHa52v5xNOU', '2024-02-12 16:45:30.270')
go

-- Tech videos - moderate popularity (4 entries)
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD001', 'oaOxMdKlJTc', '2024-02-13 09:09:09.090')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD008', 'oaOxMdKlJTc', '2024-02-14 10:10:10.100')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD014', 'oaOxMdKlJTc', '2024-02-15 11:11:11.111')
insert into Favourite(UserId, VideoId, FavouriteDate) values ('SD002', '7W3oWskU8t8', '2024-02-16 12:12:12.120')
go

-- Statistics video (OesCL-yKowU) - NO FAVOURITES (one of the unpopular videos)
-- Flashlight video (7W3oWskU8t8) - only 1 favourite above (low popularity tail of bell curve)

insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'qAZ-q3KmDHM', 'friend1@gmail.com', '2023-03-20 09:12:45.137');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'qAZ-q3KmDHM', 'colleague@company.com', '2023-03-25 14:05:11.903');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'qAZ-q3KmDHM', 'family@example.com', '2023-04-08 18:33:59.047');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'qAZ-q3KmDHM', 'buddy@hotmail.com', '2023-04-18 07:25:10.500');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'Jgmy-796dtc', 'tech.friend@gmail.com', '2023-05-25 10:41:22.003');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'Jgmy-796dtc', 'engineer@workplace.com', '2023-06-02 15:06:30.250');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'Jgmy-796dtc', 'student@university.edu', '2023-06-15 20:14:12.777');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'Jgmy-796dtc', 'mentor@company.org', '2023-06-28 22:59:59.999');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'vRA776CtTw0', 'maker@creative.com', '2023-07-18 08:03:10.120');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'vRA776CtTw0', '3dprint@hobbyist.net', '2023-08-05 11:27:44.333');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'vRA776CtTw0', 'designer@studio.com', '2023-08-22 16:48:05.610');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'vRA776CtTw0', 'prototype@lab.edu', '2023-09-08 19:35:42.086');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'Z8zmoXBMvyA', 'opensource@dev.org', '2023-09-25 09:00:00.000');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'Z8zmoXBMvyA', 'cad.user@design.com', '2023-10-12 10:12:34.567');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'Z8zmoXBMvyA', 'freecad@community.org', '2023-10-30 14:20:15.432');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'Z8zmoXBMvyA', 'engineering@student.edu', '2023-11-15 21:45:55.210');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'LmGofzJ8d3k', 'physics@researcher.edu', '2023-12-02 08:10:05.015');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'LmGofzJ8d3k', 'gamedev@indie.com', '2023-12-18 12:30:00.300');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'LmGofzJ8d3k', 'simulation@lab.org', '2024-01-08 17:10:27.654');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'LmGofzJ8d3k', 'programming@bootcamp.edu', '2024-01-25 22:58:41.020');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'Mcg9GcilBfU', 'history@museum.org', '2024-02-12 06:19:08.500');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'Mcg9GcilBfU', 'collector@antique.com', '2024-02-28 13:40:40.040');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'Mcg9GcilBfU', 'vintage@enthusiast.net', '2024-03-15 19:55:22.220');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', '7MOKjQxbP18', 'quality@3dprint.org', '2024-04-02 08:08:08.080');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', '7MOKjQxbP18', 'troubleshoot@maker.com', '2024-04-20 10:16:24.360');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', '7MOKjQxbP18', 'printing@expert.net', '2024-05-08 15:45:10.010');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'HDMTRImkHC8', 'factory@satisfactory.game', '2024-05-25 23:11:42.705');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'HDMTRImkHC8', 'automation@gamer.com', '2024-06-12 09:30:30.090');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'HDMTRImkHC8', 'logistics@player.org', '2024-06-30 18:44:12.777');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'OesCL-yKowU', 'statistics@researcher.edu', '2024-07-18 07:07:07.070');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'OesCL-yKowU', 'data@analyst.com', '2024-08-05 11:29:31.310');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'VWS6CNJtldU', 'mechanical@engineer.org', '2024-08-22 20:20:20.200');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'VWS6CNJtldU', 'design@professional.com', '2024-09-10 08:55:00.123');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'VWS6CNJtldU', 'manufacturing@industry.net', '2024-09-28 12:10:10.010');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'xHa52v5xNOU', 'metalwork@specialist.org', '2024-10-15 21:14:59.999');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'xHa52v5xNOU', 'fabrication@expert.com', '2024-11-02 01:02:03.004');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'BtfCOKa2eNk', 'lighting@designer.com', '2024-11-20 02:13:21.345');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'BtfCOKa2eNk', 'visual@artist.org', '2024-12-08 03:14:15.926');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'x12trt4SMFg', 'gaming@tips.net', '2024-12-25 04:20:00.500');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'uRjDFtabuVA', 'update@gamer.com', '2025-01-12 05:55:55.555');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'qAZ-q3KmDHM', 'cooling@tech.org', '2025-01-30 06:06:06.606');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'Jgmy-796dtc', 'chemistry@science.edu', '2025-02-15 07:07:07.707');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'Mcg9GcilBfU', 'invention@history.com', '2025-03-05 08:08:08.808');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'vRA776CtTw0', 'printing@workshop.org', '2025-03-22 09:09:09.909');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'Z8zmoXBMvyA', 'software@free.org', '2025-04-10 10:10:10.100');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', '7MOKjQxbP18', 'repair@3d.com', '2025-04-28 11:11:11.111');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'HDMTRImkHC8', 'belt@conveyor.net', '2025-05-15 12:12:12.120');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'LmGofzJ8d3k', 'engine@physics.edu', '2025-06-02 13:13:13.130');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'OesCL-yKowU', 'validity@stats.org', '2025-06-20 14:14:14.140');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'VWS6CNJtldU', 'understanding@mech.com', '2025-07-08 15:15:15.150');
go

-- Comments from Satisfactory enthusiasts (SD004, SD005, SD006)
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD004', 'x12trt4SMFg', '2024-01-15 10:30:22.345', 'These tips are game-changing! The belt optimization trick alone saved me hours of factory redesign.')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD005', 'x12trt4SMFg', '2024-01-20 14:25:33.567', 'Why didnt I think of the manifold approach before? This video is pure gold for factory builders!')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD006', 'x12trt4SMFg', '2024-01-25 16:45:15.890', 'Finally someone explains underclocking properly. My power efficiency just went through the roof!')

insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD004', 'uRjDFtabuVA', '2024-01-16 15:22:45.123', 'The 1.1 update features are incredible! Love how you integrated the new blueprints into your mega factory.')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD005', 'uRjDFtabuVA', '2024-01-21 18:30:12.789', 'That modular construction technique is brilliant. Definitely stealing this for my next playthrough!')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD006', 'uRjDFtabuVA', '2024-01-26 20:15:44.456', 'The way you handle dimensional depot integration is so clean. My factories always look like spaghetti compared to this.')

insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD004', 'HDMTRImkHC8', '2024-01-17 12:45:30.234', 'Priority mergers were confusing me until this video. Your explanations are always so clear and practical.')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD005', 'HDMTRImkHC8', '2024-01-22 09:20:55.678', 'Belt compressors make so much more sense now! This is going to revolutionize my production lines.')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD006', 'HDMTRImkHC8', '2024-01-27 21:30:18.901', 'The math behind the throughput calculations is fascinating. Thanks for showing the theory behind the practice!')

insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD004', 'BtfCOKa2eNk', '2024-01-18 19:10:25.567', 'Lighting makes such a huge difference! My factory went from industrial to absolutely stunning with these techniques.')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD005', 'BtfCOKa2eNk', '2024-01-24 11:45:40.123', 'The ambient lighting setup around the space elevator is gorgeous. Definitely trying this in my world.')
go

-- Comments from Science/Physics enthusiasts (SD007, SD008, SD009)
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD007', 'qAZ-q3KmDHM', '2024-01-10 12:30:15.234', 'The physics behind vapor chambers is mind-blowing! I never realized how elegant this cooling solution is.')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD008', 'qAZ-q3KmDHM', '2024-01-14 14:20:30.456', 'Incredible how such a simple phase change can be so effective. The thermodynamics explanation was perfect!')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD009', 'qAZ-q3KmDHM', '2024-01-18 16:45:22.789', 'This is why I love physics - seemingly magical phenomena with beautiful scientific explanations behind them.')

insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD007', 'Jgmy-796dtc', '2024-01-11 13:40:45.345', 'The chemistry of metallic fires is fascinating! The reaction with water creating hydrogen is particularly dangerous.')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD008', 'Jgmy-796dtc', '2024-01-15 15:50:12.678', 'Great demonstration of why you should never use water on metal fires. The safety implications are so important.')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD009', 'Jgmy-796dtc', '2024-01-19 18:25:33.901', 'The slow-motion footage of the reaction was incredible. You can actually see the hydrogen igniting!')

insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD007', 'Mcg9GcilBfU', '2024-01-12 14:15:20.456', 'Catalytic ignition is such an elegant solution! No sparks needed, just the right materials and chemistry.')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD008', 'Mcg9GcilBfU', '2024-01-16 16:30:45.789', 'The history behind these lighters is as interesting as the science. Amazing how old technology can be so clever.')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD009', 'Mcg9GcilBfU', '2024-01-20 20:10:15.123', 'I love how you explained the platinum catalyst mechanism. Makes me want to study more chemistry!')

insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD007', 'LmGofzJ8d3k', '2024-01-13 21:20:10.567', 'Physics engines are basically applied mathematics at its finest. The collision detection algorithms are brilliant!')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD008', 'LmGofzJ8d3k', '2024-01-17 23:15:55.890', 'The explanation of numerical integration for physics simulation was excellent. Euler vs Verlet methods finally make sense!')
go

-- Comments from 3D Printing enthusiasts (SD010, SD011)
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD010', 'vRA776CtTw0', '2024-01-05 11:45:30.234', 'These design principles are game-changers! My support material usage has dropped by 60% following these tips.')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD011', 'vRA776CtTw0', '2024-01-08 09:30:15.567', 'The overhang angle explanation is perfect. Finally understand why my bridges were failing at certain angles.')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD001', 'vRA776CtTw0', '2024-02-09 10:20:22.890', 'Excellent tutorial! The wall thickness guidelines have improved my print strength significantly.')

insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD010', 'Z8zmoXBMvyA', '2024-01-06 15:30:45.345', 'FreeCAD is underrated! Your workflow demonstrations make parametric design look so accessible.')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD011', 'Z8zmoXBMvyA', '2024-01-09 13:15:20.678', 'The constraint-based modeling approach is brilliant for 3D printing. No more accidental dimension changes!')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD005', 'Z8zmoXBMvyA', '2024-02-10 14:25:35.901', 'Switching from Fusion 360 to FreeCAD after watching this. The open-source approach is compelling.')

insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD010', '7MOKjQxbP18', '2024-01-07 19:20:10.456', 'This error has been plaguing my prints! The layer adhesion explanation is spot-on.')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD011', '7MOKjQxbP18', '2024-01-10 21:40:55.789', 'Temperature tower testing changed everything for me. Clean prints every time now!')
go

-- Comments from Engineering enthusiasts (SD012, SD013, SD014)
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD012', 'VWS6CNJtldU', '2024-01-01 02:30:15.123', 'This really opened my eyes to the depth of mechanical engineering. The interdisciplinary connections are fascinating!')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD013', 'VWS6CNJtldU', '2024-01-03 04:20:30.456', 'The materials science foundation is crucial. You cant design without understanding the properties deeply.')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD014', 'VWS6CNJtldU', '2024-01-05 06:45:45.789', 'Systems thinking is everything in engineering. This video perfectly captures the holistic approach needed.')

insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD012', 'xHa52v5xNOU', '2024-01-02 03:15:25.234', 'Sheet metal design mistakes are so common! The bend radius guidelines are particularly important.')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD013', 'xHa52v5xNOU', '2024-01-04 05:25:40.567', 'Manufacturing constraints drive design more than people realize. Great real-world examples here.')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD003', 'xHa52v5xNOU', '2024-02-12 17:30:15.890', 'As a mechanical engineer, I see these mistakes daily. This video should be required viewing!')
go

-- Additional comments from users on their favorited videos
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD001', 'x12trt4SMFg', '2024-02-01 10:15:30.345', 'Great beginner tips! This helped me understand Satisfactory optimization much better.')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD002', 'uRjDFtabuVA', '2024-02-02 11:30:45.678', 'The 1.1 features integration is smooth. Looking forward to trying these techniques!')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD003', 'HDMTRImkHC8', '2024-02-03 15:45:20.901', 'Belt compressors were confusing until this explanation. Crystal clear now!')

insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD001', 'qAZ-q3KmDHM', '2024-02-05 09:20:15.234', 'Physics explanations are always fascinating. The cooling mechanism is ingenious!')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD002', 'Jgmy-796dtc', '2024-02-06 11:40:30.567', 'The safety implications of metallic fires are terrifying. Important knowledge for everyone!')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD004', 'qAZ-q3KmDHM', '2024-02-08 23:30:50.890', 'Love the intersection of physics and engineering. Vapor chambers are brilliant!')

insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD009', 'vRA776CtTw0', '2024-02-11 21:45:15.123', '3D printing design principles apply to so many manufacturing processes. Great cross-disciplinary content!')
go

-- Comments on moderately popular tech videos
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD001', 'oaOxMdKlJTc', '2024-02-13 10:30:20.456', 'GPU programming complexity is both fascinating and terrifying. The parallel processing challenges are immense!')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD008', 'oaOxMdKlJTc', '2024-02-14 11:45:35.789', 'The race conditions and memory management issues explain why GPU code is so hard to debug.')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD014', 'oaOxMdKlJTc', '2024-02-15 12:20:50.012', 'As someone who works with CUDA, this video perfectly captures the development challenges we face daily.')

insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD002', '7W3oWskU8t8', '2024-02-16 13:15:25.345', 'That flashlight is insanely bright! The lumens rating must be off the charts.')
go

-- Some additional scattered comments from users who didn't favorite but still watched
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD003', 'LmGofzJ8d3k', '2024-02-07 16:20:40.678', 'Physics engines make gaming possible. The computational complexity behind smooth gameplay is amazing!')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD007', 'BtfCOKa2eNk', '2024-02-04 22:10:30.901', 'Even though this is about Satisfactory, the lighting principles apply to architectural visualization too!')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD011', 'Mcg9GcilBfU', '2024-02-20 08:30:15.234', 'Historical engineering solutions are often more elegant than modern ones. This lighter is proof!')
insert into Comments(UserId, VideoId, CommentDate, CommentContent) values ('SD013', '7MOKjQxbP18', '2024-02-25 14:45:20.567', 'Quality control in 3D printing has engineering principles that apply to traditional manufacturing too.')
go