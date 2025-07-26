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
insert into Users(UserId, PasswordHash, FullName, Email, RoleId) values ('SD002', '$2a$12$6Lneezy3jribVCUJpYzV/uwJ8kay1ZGjE2adnzs6g0M9RbqEJpZEC', 'Trinh Thi Linh'  , 'linhttSD@gmail.com ', 3) 
insert into Users(UserId, PasswordHash, FullName, Email, RoleId) values ('SD003', '$2a$12$LpW4G3snr1.auHyur8T6LekqQpJG3Y.jQrCuz7bc0a8xAnM5iUVOu', 'Vo Anh Tuan'     , 'tuanvaSD@gmail.com ', 2) 
insert into Users(UserId, PasswordHash, FullName, Email, RoleId) values ('SD004', '$2a$12$2ETrqO7JiV9tkkjxd/VaWe2OD1Xl90M2.O2h3oT9TJvPcA44vqoP2', 'Nguyen Tan Thanh', 'thanhntSD@gmail.com', 1) 
insert into Users(UserId, PasswordHash, FullName, Email, RoleId) values ('SD005', '$2a$12$rnfhc4D/Wxd7jnUSWdpEUeF6wlusVXdOjb/u1QwCnwtec/t6GeYX2', 'Le Thi Mai'      , 'mailtSD@gmail.com'  , 1)
insert into Users(UserId, PasswordHash, FullName, Email, RoleId) values ('SD006', '$2a$12$6Lneezy3jribVCUJpYzV/uwJ8kay1ZGjE2adnzs6g0M9RbqEJpZEC', 'Pham Van Duc'    , 'ducpvSD@gmail.com'  , 1) 
insert into Users(UserId, PasswordHash, FullName, Email, RoleId) values ('SD007', '$2a$12$LpW4G3snr1.auHyur8T6LekqQpJG3Y.jQrCuz7bc0a8xAnM5iUVOu', 'Tran Thi Hoa'    , 'hoattSD@gmail.com'  , 1)
insert into Users(UserId, PasswordHash, FullName, Email, RoleId) values ('SD008', '$2a$12$2ETrqO7JiV9tkkjxd/VaWe2OD1Xl90M2.O2h3oT9TJvPcA44vqoP2', 'Hoang Van Long'  , 'longhvSD@gmail.com' , 1)
insert into Users(UserId, PasswordHash, FullName, Email, RoleId) values ('SD009', '$2a$12$rnfhc4D/Wxd7jnUSWdpEUeF6wlusVXdOjb/u1QwCnwtec/t6GeYX2', 'Nguyen Thi Lan'  , 'lanntSD@gmail.com'  , 1)
insert into Users(UserId, PasswordHash, FullName, Email, RoleId) values ('SD010', '$2a$12$6Lneezy3jribVCUJpYzV/uwJ8kay1ZGjE2adnzs6g0M9RbqEJpZEC', 'Vu Van Hung'     , 'hungvvSD@gmail.com' , 1)
insert into Users(UserId, PasswordHash, FullName, Email, RoleId) values ('SD011', '$2a$12$LpW4G3snr1.auHyur8T6LekqQpJG3Y.jQrCuz7bc0a8xAnM5iUVOu', 'Do Thi Thu'      , 'thudtSD@gmail.com'  , 1)
insert into Users(UserId, PasswordHash, FullName, Email, RoleId) values ('SD012', '$2a$12$2ETrqO7JiV9tkkjxd/VaWe2OD1Xl90M2.O2h3oT9TJvPcA44vqoP2', 'Bui Van Nam'     , 'nambvSD@gmail.com'  , 1)
insert into Users(UserId, PasswordHash, FullName, Email, RoleId) values ('SD013', '$2a$12$rnfhc4D/Wxd7jnUSWdpEUeF6wlusVXdOjb/u1QwCnwtec/t6GeYX2', 'Luu Thi Nga'     , 'ngaltSD@gmail.com'  , 1)
insert into Users(UserId, PasswordHash, FullName, Email, RoleId) values ('SD014', '$2a$12$6Lneezy3jribVCUJpYzV/uwJ8kay1ZGjE2adnzs6g0M9RbqEJpZEC', 'Dang Van Khai'   , 'khaidvSD@gmail.com' , 1)
go

insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('x12trt4SMFg', '5 Satisfactory Tips that are Obvious Once You See Them'                     , 'x12trt4SMFg-HD.jpg', 'https://www.youtube.com/watch?v=x12trt4SMFg', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('uRjDFtabuVA', 'How Im Using the Satisfactory 1.1 Features in My Factory'                   , 'uRjDFtabuVA-HD.jpg', 'https://www.youtube.com/watch?v=uRjDFtabuVA', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('qAZ-q3KmDHM', 'How One Drop of Water Cools Your Phone'                                     , 'qAZ-q3KmDHM-HD.jpg', 'https://www.youtube.com/watch?v=qAZ-q3KmDHM', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('Jgmy-796dtc', 'The Secret of Incendiary Water | Metallic Fires'                            , 'Jgmy-796dtc-HD.jpg', 'https://www.youtube.com/watch?v=Jgmy-796dtc', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('Mcg9GcilBfU', 'This 200-Year-Old Lighter Ignites Without a Spark'                          , 'Mcg9GcilBfU-HD.jpg', 'https://www.youtube.com/watch?v=Mcg9GcilBfU', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('vRA776CtTw0', 'Stronger 3D Prints & Less Supports: How to Design for 3D Printing'          , 'vRA776CtTw0-HD.jpg', 'https://www.youtube.com/watch?v=vRA776CtTw0', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('Z8zmoXBMvyA', 'FreeCAD is the BEST for 3D Printing - 5 reasons WHY !'                      , 'Z8zmoXBMvyA-HD.jpg', 'https://www.youtube.com/watch?v=Z8zmoXBMvyA', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('7MOKjQxbP18', 'The Fatal Error No One Talks About – Get Incredibly Clean 3D Prints!'       , '7MOKjQxbP18-HD.jpg', 'https://www.youtube.com/watch?v=7MOKjQxbP18', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('HDMTRImkHC8', 'How to Build Belt Compressors - Priority Merger Guide Satisfactory 1.1'     , 'HDMTRImkHC8-HD.jpg', 'https://www.youtube.com/watch?v=HDMTRImkHC8', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('LmGofzJ8d3k', 'How do Physics Engines work?'                                               , 'LmGofzJ8d3k-HD.jpg', 'https://www.youtube.com/watch?v=LmGofzJ8d3k', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('OesCL-yKowU', 'What Is Incremental Validity? - The Friendly Statistician'                  , 'OesCL-yKowU-HD.jpg', 'https://www.youtube.com/watch?v=OesCL-yKowU', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VWS6CNJtldU', 'You Dont Really Understand Mechanical Engineering'                          , 'VWS6CNJtldU-HD.jpg', 'https://www.youtube.com/watch?v=VWS6CNJtldU', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('xHa52v5xNOU', 'Only Real Mechanical Engineers Can Spot These Design Mistakes | Sheet Metal', 'xHa52v5xNOU-HD.jpg', 'https://www.youtube.com/watch?v=xHa52v5xNOU', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('BtfCOKa2eNk', 'Easy Lighting Tricks to Make Your Factory Pop!'                             , 'BtfCOKa2eNk-HD.jpg', 'https://www.youtube.com/watch?v=BtfCOKa2eNk', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('7W3oWskU8t8', 'Brightest Flashlight Ive Ever Tested!'                                      , '7W3oWskU8t8-HD.jpg', 'https://www.youtube.com/watch?v=7W3oWskU8t8', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('oaOxMdKlJTc', 'Why GPU Programming Is Chaotic'                                             , 'oaOxMdKlJTc-HD.jpg', 'https://www.youtube.com/watch?v=oaOxMdKlJTc', 0, 'Basic video desc', 1)
go

-- 50 Favourite entries with skewed user preferences and bell curve distribution
-- SD004, SD005, SD006 heavily prefer Satisfactory videos
-- SD007, SD008, SD009 heavily prefer Science/Physics videos  
-- SD010, SD011 heavily prefer 3D Printing videos
-- SD012, SD013, SD014 heavily prefer Engineering videos
-- Some videos have high popularity (bell curve peak), others moderate, 1-2 have none

-- Satisfactory enthusiasts (SD004, SD005, SD006) - 16 entries
insert into Favourite(UserId, VideoId, LikeDate) values ('SD004', 'x12trt4SMFg', '2024-01-15')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD004', 'uRjDFtabuVA', '2024-01-16')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD004', 'HDMTRImkHC8', '2024-01-17')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD004', 'BtfCOKa2eNk', '2024-01-18')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD005', 'x12trt4SMFg', '2024-01-20')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD005', 'uRjDFtabuVA', '2024-01-21')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD005', 'HDMTRImkHC8', '2024-01-22')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD005', 'BtfCOKa2eNk', '2024-01-23')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD006', 'x12trt4SMFg', '2024-01-25')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD006', 'uRjDFtabuVA', '2024-01-26')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD006', 'HDMTRImkHC8', '2024-01-27')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD006', 'BtfCOKa2eNk', '2024-01-28')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD001', 'x12trt4SMFg', '2024-02-01')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD002', 'uRjDFtabuVA', '2024-02-02')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD003', 'HDMTRImkHC8', '2024-02-03')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD007', 'x12trt4SMFg', '2024-02-04')

-- Science/Physics enthusiasts (SD007, SD008, SD009) - 15 entries
insert into Favourite(UserId, VideoId, LikeDate) values ('SD007', 'qAZ-q3KmDHM', '2024-01-10')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD007', 'Jgmy-796dtc', '2024-01-11')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD007', 'Mcg9GcilBfU', '2024-01-12')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD007', 'LmGofzJ8d3k', '2024-01-13')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD008', 'qAZ-q3KmDHM', '2024-01-14')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD008', 'Jgmy-796dtc', '2024-01-15')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD008', 'Mcg9GcilBfU', '2024-01-16')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD008', 'LmGofzJ8d3k', '2024-01-17')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD009', 'qAZ-q3KmDHM', '2024-01-18')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD009', 'Jgmy-796dtc', '2024-01-19')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD009', 'Mcg9GcilBfU', '2024-01-20')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD001', 'qAZ-q3KmDHM', '2024-02-05')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD002', 'Jgmy-796dtc', '2024-02-06')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD003', 'LmGofzJ8d3k', '2024-02-07')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD004', 'qAZ-q3KmDHM', '2024-02-08')

-- 3D Printing enthusiasts (SD010, SD011) - 9 entries
insert into Favourite(UserId, VideoId, LikeDate) values ('SD010', 'vRA776CtTw0', '2024-01-05')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD010', 'Z8zmoXBMvyA', '2024-01-06')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD010', '7MOKjQxbP18', '2024-01-07')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD011', 'vRA776CtTw0', '2024-01-08')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD011', 'Z8zmoXBMvyA', '2024-01-09')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD011', '7MOKjQxbP18', '2024-01-10')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD001', 'vRA776CtTw0', '2024-02-09')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD005', 'Z8zmoXBMvyA', '2024-02-10')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD009', 'vRA776CtTw0', '2024-02-11')

-- Engineering enthusiasts (SD012, SD013, SD014) - 6 entries
insert into Favourite(UserId, VideoId, LikeDate) values ('SD012', 'VWS6CNJtldU', '2024-01-01')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD012', 'xHa52v5xNOU', '2024-01-02')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD013', 'VWS6CNJtldU', '2024-01-03')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD013', 'xHa52v5xNOU', '2024-01-04')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD014', 'VWS6CNJtldU', '2024-01-05')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD003', 'xHa52v5xNOU', '2024-02-12')

-- Tech videos - moderate popularity (4 entries)
insert into Favourite(UserId, VideoId, LikeDate) values ('SD001', 'oaOxMdKlJTc', '2024-02-13')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD008', 'oaOxMdKlJTc', '2024-02-14')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD014', 'oaOxMdKlJTc', '2024-02-15')
insert into Favourite(UserId, VideoId, LikeDate) values ('SD002', '7W3oWskU8t8', '2024-02-16')

-- Statistics video (OesCL-yKowU) - NO FAVOURITES (one of the unpopular videos)
-- Flashlight video (7W3oWskU8t8) - only 1 favourite above (low popularity tail of bell curve)

insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'qAZ-q3KmDHM', 'friend1@gmail.com', '2023-03-20');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'qAZ-q3KmDHM', 'colleague@company.com', '2023-03-25');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'qAZ-q3KmDHM', 'family@example.com', '2023-04-08');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'qAZ-q3KmDHM', 'buddy@hotmail.com', '2023-04-18');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'Jgmy-796dtc', 'tech.friend@gmail.com', '2023-05-25');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'Jgmy-796dtc', 'engineer@workplace.com', '2023-06-02');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'Jgmy-796dtc', 'student@university.edu', '2023-06-15');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'Jgmy-796dtc', 'mentor@company.org', '2023-06-28');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'vRA776CtTw0', 'maker@creative.com', '2023-07-18');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'vRA776CtTw0', '3dprint@hobbyist.net', '2023-08-05');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'vRA776CtTw0', 'designer@studio.com', '2023-08-22');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'vRA776CtTw0', 'prototype@lab.edu', '2023-09-08');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'Z8zmoXBMvyA', 'opensource@dev.org', '2023-09-25');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'Z8zmoXBMvyA', 'cad.user@design.com', '2023-10-12');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'Z8zmoXBMvyA', 'freecad@community.org', '2023-10-30');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'Z8zmoXBMvyA', 'engineering@student.edu', '2023-11-15');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'LmGofzJ8d3k', 'physics@researcher.edu', '2023-12-02');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'LmGofzJ8d3k', 'gamedev@indie.com', '2023-12-18');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'LmGofzJ8d3k', 'simulation@lab.org', '2024-01-08');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'LmGofzJ8d3k', 'programming@bootcamp.edu', '2024-01-25');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'Mcg9GcilBfU', 'history@museum.org', '2024-02-12');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'Mcg9GcilBfU', 'collector@antique.com', '2024-02-28');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'Mcg9GcilBfU', 'vintage@enthusiast.net', '2024-03-15');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', '7MOKjQxbP18', 'quality@3dprint.org', '2024-04-02');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', '7MOKjQxbP18', 'troubleshoot@maker.com', '2024-04-20');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', '7MOKjQxbP18', 'printing@expert.net', '2024-05-08');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'HDMTRImkHC8', 'factory@satisfactory.game', '2024-05-25');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'HDMTRImkHC8', 'automation@gamer.com', '2024-06-12');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'HDMTRImkHC8', 'logistics@player.org', '2024-06-30');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'OesCL-yKowU', 'statistics@researcher.edu', '2024-07-18');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'OesCL-yKowU', 'data@analyst.com', '2024-08-05');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'VWS6CNJtldU', 'mechanical@engineer.org', '2024-08-22');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'VWS6CNJtldU', 'design@professional.com', '2024-09-10');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'VWS6CNJtldU', 'manufacturing@industry.net', '2024-09-28');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'xHa52v5xNOU', 'metalwork@specialist.org', '2024-10-15');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'xHa52v5xNOU', 'fabrication@expert.com', '2024-11-02');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'BtfCOKa2eNk', 'lighting@designer.com', '2024-11-20');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'BtfCOKa2eNk', 'visual@artist.org', '2024-12-08');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'x12trt4SMFg', 'gaming@tips.net', '2024-12-25');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'uRjDFtabuVA', 'update@gamer.com', '2025-01-12');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'qAZ-q3KmDHM', 'cooling@tech.org', '2025-01-30');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'Jgmy-796dtc', 'chemistry@science.edu', '2025-02-15');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'Mcg9GcilBfU', 'invention@history.com', '2025-03-05');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'vRA776CtTw0', 'printing@workshop.org', '2025-03-22');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'Z8zmoXBMvyA', 'software@free.org', '2025-04-10');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', '7MOKjQxbP18', 'repair@3d.com', '2025-04-28');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'HDMTRImkHC8', 'belt@conveyor.net', '2025-05-15');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'LmGofzJ8d3k', 'engine@physics.edu', '2025-06-02');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'OesCL-yKowU', 'validity@stats.org', '2025-06-20');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'VWS6CNJtldU', 'understanding@mech.com', '2025-07-08');
go

