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
go

insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD001', '5 Satisfactory Tips that are Obvious Once You See Them'                     , 'https://www.youtube.com/watch?v=x12trt4SMFg', 'x12trt4SMFg-HD.jpg', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD002', 'How Im Using the Satisfactory 1.1 Features in My Factory'                   , 'https://www.youtube.com/watch?v=uRjDFtabuVA', 'uRjDFtabuVA-HD.jpg', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD003', 'How One Drop of Water Cools Your Phone'                                     , 'https://www.youtube.com/watch?v=qAZ-q3KmDHM', 'qAZ-q3KmDHM-HD.jpg', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD004', 'The Secret of Incendiary Water | Metallic Fires'                            , 'https://www.youtube.com/watch?v=Jgmy-796dtc', 'Jgmy-796dtc-HD.jpg', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD005', 'This 200-Year-Old Lighter Ignites Without a Spark'                          , 'https://www.youtube.com/watch?v=Mcg9GcilBfU', 'Mcg9GcilBfU-HD.jpg', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD006', 'Stronger 3D Prints & Less Supports: How to Design for 3D Printing'          , 'https://www.youtube.com/watch?v=vRA776CtTw0', 'vRA776CtTw0-HD.jpg', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD007', 'FreeCAD is the BEST for 3D Printing - 5 reasons WHY !'                      , 'https://www.youtube.com/watch?v=Z8zmoXBMvyA', 'Z8zmoXBMvyA-HD.jpg', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD008', 'The Fatal Error No One Talks About – Get Incredibly Clean 3D Prints!'       , 'https://www.youtube.com/watch?v=7MOKjQxbP18', '7MOKjQxbP18-HD.jpg', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD009', 'How to Build Belt Compressors - Priority Merger Guide Satisfactory 1.1'     , 'https://www.youtube.com/watch?v=HDMTRImkHC8', 'HDMTRImkHC8-HD.jpg', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD010', 'How do Physics Engines work?'                                               , 'https://www.youtube.com/watch?v=LmGofzJ8d3k', 'LmGofzJ8d3k-HD.jpg', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD011', 'What Is Incremental Validity? - The Friendly Statistician'                  , 'https://www.youtube.com/watch?v=OesCL-yKowU', 'OesCL-yKowU-HD.jpg', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD012', 'You Dont Really Understand Mechanical Engineering'                          , 'https://www.youtube.com/watch?v=VWS6CNJtldU', 'VWS6CNJtldU-HD.jpg', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD013', 'Only Real Mechanical Engineers Can Spot These Design Mistakes | Sheet Metal', 'https://www.youtube.com/watch?v=xHa52v5xNOU', 'xHa52v5xNOU-HD.jpg', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD014', 'Easy Lighting Tricks to Make Your Factory Pop!'                             , 'https://www.youtube.com/watch?v=BtfCOKa2eNk', 'BtfCOKa2eNk-HD.jpg', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD015', 'Brightest Flashlight Ive Ever Tested!'                                      , 'https://www.youtube.com/watch?v=7W3oWskU8t8', '7W3oWskU8t8-HD.jpg', 0, 'Basic video desc', 1)
insert into Video(VideoId, Title, Poster, Link, ViewCount, Descript, Active) values ('VD016', 'Why GPU Programming Is Chaotic'                                             , 'https://www.youtube.com/watch?v=oaOxMdKlJTc', 'oaOxMdKlJTc-HD.jpg', 0, 'Basic video desc', 1)
go

insert into Favourite(UserId, VideoId, LikeDate) values ('SD001', 'VD003', '2023-03-15');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD002', 'VD003', '2023-03-18');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD003', 'VD003', '2023-04-02');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD004', 'VD003', '2023-04-12');
go

insert into Favourite(UserId, VideoId, LikeDate) values ('SD001', 'VD004', '2023-05-20');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD002', 'VD004', '2023-05-25');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD003', 'VD004', '2023-06-08');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD004', 'VD004', '2023-06-15');
go

insert into Favourite(UserId, VideoId, LikeDate) values ('SD001', 'VD006', '2023-07-10');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD002', 'VD006', '2023-07-22');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD003', 'VD006', '2023-08-05');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD004', 'VD006', '2023-08-18');
go

insert into Favourite(UserId, VideoId, LikeDate) values ('SD001', 'VD007', '2023-09-12');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD002', 'VD007', '2023-09-28');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD003', 'VD007', '2023-10-15');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD004', 'VD007', '2023-10-30');
go

insert into Favourite(UserId, VideoId, LikeDate) values ('SD001', 'VD010', '2023-11-08');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD002', 'VD010', '2023-11-25');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD003', 'VD010', '2023-12-10');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD004', 'VD010', '2023-12-22');
go

insert into Favourite(UserId, VideoId, LikeDate) values ('SD001', 'VD005', '2024-01-15');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD002', 'VD005', '2024-02-08');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD003', 'VD005', '2024-02-28');
go

insert into Favourite(UserId, VideoId, LikeDate) values ('SD001', 'VD008', '2024-03-12');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD002', 'VD008', '2024-03-25');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD004', 'VD008', '2024-04-10');
go

insert into Favourite(UserId, VideoId, LikeDate) values ('SD001', 'VD009', '2024-04-22');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD003', 'VD009', '2024-05-15');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD004', 'VD009', '2024-05-30');
go

insert into Favourite(UserId, VideoId, LikeDate) values ('SD002', 'VD011', '2024-06-12');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD003', 'VD011', '2024-06-28');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD004', 'VD011', '2024-07-08');
go

insert into Favourite(UserId, VideoId, LikeDate) values ('SD001', 'VD012', '2024-07-15');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD003', 'VD012', '2024-07-30');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD004', 'VD012', '2024-08-18');
go

insert into Favourite(UserId, VideoId, LikeDate) values ('SD001', 'VD013', '2024-09-05');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD003', 'VD013', '2024-09-22');

insert into Favourite(UserId, VideoId, LikeDate) values ('SD002', 'VD014', '2024-10-10');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD004', 'VD014', '2024-10-28');
go

insert into Favourite(UserId, VideoId, LikeDate) values ('SD001', 'VD001', '2024-11-15');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD002', 'VD002', '2024-12-02');
go

insert into Favourite(UserId, VideoId, LikeDate) values ('SD004', 'VD005', '2025-01-25');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD003', 'VD008', '2025-02-12');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD002', 'VD009', '2025-02-28');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD001', 'VD011', '2025-03-15');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD002', 'VD012', '2025-04-02');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD004', 'VD013', '2025-04-20');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD003', 'VD014', '2025-05-08');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD003', 'VD001', '2025-05-25');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD004', 'VD002', '2025-06-12');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD001', 'VD015', '2025-06-30');
insert into Favourite(UserId, VideoId, LikeDate) values ('SD002', 'VD016', '2025-07-15');
go

insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'VD003', 'friend1@gmail.com', '2023-03-20');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'VD003', 'colleague@company.com', '2023-03-25');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'VD003', 'family@example.com', '2023-04-08');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'VD003', 'buddy@hotmail.com', '2023-04-18');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'VD004', 'tech.friend@gmail.com', '2023-05-25');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'VD004', 'engineer@workplace.com', '2023-06-02');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'VD004', 'student@university.edu', '2023-06-15');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'VD004', 'mentor@company.org', '2023-06-28');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'VD006', 'maker@creative.com', '2023-07-18');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'VD006', '3dprint@hobbyist.net', '2023-08-05');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'VD006', 'designer@studio.com', '2023-08-22');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'VD006', 'prototype@lab.edu', '2023-09-08');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'VD007', 'opensource@dev.org', '2023-09-25');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'VD007', 'cad.user@design.com', '2023-10-12');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'VD007', 'freecad@community.org', '2023-10-30');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'VD007', 'engineering@student.edu', '2023-11-15');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'VD010', 'physics@researcher.edu', '2023-12-02');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'VD010', 'gamedev@indie.com', '2023-12-18');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'VD010', 'simulation@lab.org', '2024-01-08');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'VD010', 'programming@bootcamp.edu', '2024-01-25');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'VD005', 'history@museum.org', '2024-02-12');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'VD005', 'collector@antique.com', '2024-02-28');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'VD005', 'vintage@enthusiast.net', '2024-03-15');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'VD008', 'quality@3dprint.org', '2024-04-02');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'VD008', 'troubleshoot@maker.com', '2024-04-20');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'VD008', 'printing@expert.net', '2024-05-08');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'VD009', 'factory@satisfactory.game', '2024-05-25');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'VD009', 'automation@gamer.com', '2024-06-12');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'VD009', 'logistics@player.org', '2024-06-30');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'VD011', 'statistics@researcher.edu', '2024-07-18');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'VD011', 'data@analyst.com', '2024-08-05');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'VD012', 'mechanical@engineer.org', '2024-08-22');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'VD012', 'design@professional.com', '2024-09-10');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'VD012', 'manufacturing@industry.net', '2024-09-28');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'VD013', 'metalwork@specialist.org', '2024-10-15');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'VD013', 'fabrication@expert.com', '2024-11-02');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'VD014', 'lighting@designer.com', '2024-11-20');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'VD014', 'visual@artist.org', '2024-12-08');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'VD001', 'gaming@tips.net', '2024-12-25');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'VD002', 'update@gamer.com', '2025-01-12');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'VD003', 'cooling@tech.org', '2025-01-30');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'VD004', 'chemistry@science.edu', '2025-02-15');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'VD005', 'invention@history.com', '2025-03-05');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'VD006', 'printing@workshop.org', '2025-03-22');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'VD007', 'software@free.org', '2025-04-10');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'VD008', 'repair@3d.com', '2025-04-28');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD001', 'VD009', 'belt@conveyor.net', '2025-05-15');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD003', 'VD010', 'engine@physics.edu', '2025-06-02');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD002', 'VD011', 'validity@stats.org', '2025-06-20');
insert into Share(UserId, VideoId, Emails, ShareDate) values ('SD004', 'VD012', 'understanding@mech.com', '2025-07-08');
go

