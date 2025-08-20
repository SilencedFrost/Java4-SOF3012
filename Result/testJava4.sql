create database testJava4
go

use testJava4
go

create table Authors(
	AuthorId varchar(20) primary key,
	AuthorName nvarchar(30) not null
)
go

create table Books(
	BookId int primary key identity(1, 1),
	Title nvarchar(30),
	Price int,
	AuthorId varchar(20),
	foreign key (AuthorId) references Authors(AuthorId)
)
go

insert into Authors(AuthorId, AuthorName) values ('au01', N'Huy Cận');
insert into Authors(AuthorId, AuthorName) values ('au02', N'Nguyễn Bính');
insert into Authors(AuthorId, AuthorName) values ('au03', N'Nguyễn Nhật Ánh');
go

insert into Books(Title, Price, AuthorId) values (N'Đất nở hoa', 23000, 'au01');
insert into Books(Title, Price, AuthorId) values (N'Trời mỗi ngày lại sáng', 45000, 'au01');
insert into Books(Title, Price, AuthorId) values (N'Vũ trụ ca', 16700, 'au01');
insert into Books(Title, Price, AuthorId) values (N'Kính Vạn Hoa', 22000, 'au03');
go