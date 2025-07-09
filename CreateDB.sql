USE [master]
GO

IF EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = 'JoyLeeBook')
BEGIN
	ALTER DATABASE JoyLeeBook SET OFFLINE WITH ROLLBACK IMMEDIATE;
	ALTER DATABASE JoyLeeBook SET ONLINE;
	DROP DATABASE JoyLeeBook;
END

GO

CREATE DATABASE JoyLeeBook
GO

USE JoyLeeBook
GO

CREATE TABLE [dbo].[Users] (
	[user_id] INT PRIMARY KEY IDENTITY(1,1),
	[role_name] NVARCHAR(15) NOT NULL,
	[username] NVARCHAR(15) UNIQUE NOT NULL,
    [email] NVARCHAR(100) UNIQUE NOT NULL,
	[user_password] NVARCHAR(40) NOT NULL
) 

CREATE TABLE [dbo].[Genres] (
	[genre_id] INT PRIMARY KEY IDENTITY(1,1),
	[genre_name] NVARCHAR(15) UNIQUE NOT NULL
)

CREATE TABLE [dbo].[Series] (
	[series_id] INT PRIMARY KEY IDENTITY(1,1),
	[author_name] NVARCHAR(50) NOT NULL,
	[series_title] NVARCHAR(50) NOT NULL,
	[status] INT NOT NULL,
	[description] TEXT NOT NULL,
	[cover_image_url] NVARCHAR(255) NOT NULL,
	[created_at] DATETIME DEFAULT getDate()
)

CREATE TABLE [dbo].[Categories] (
	[genre_id] INT FOREIGN KEY REFERENCES [dbo].[Genres]([genre_id]),
	[series_id] INT FOREIGN KEY REFERENCES [dbo].[Series]([series_id]),
	PRIMARY KEY([genre_id], [series_id])
)

CREATE TABLE [dbo].[Chapters] (
	[chapter_id] INT PRIMARY KEY IDENTITY(1,1),
	[series_id] INT FOREIGN KEY REFERENCES [dbo].[Series]([series_id]),
	[chapter_index] INT NOT NULL,
	[chapter_title] NVARCHAR(50) NOT NULL,
	[created_at] DATETIME DEFAULT getDate(),
	[content] NVARCHAR(MAX) NOT NULL
)

CREATE TABLE [dbo].[UserLibraries] (
	[user_id] INT FOREIGN KEY REFERENCES [dbo].[Users]([user_id]),
	[series_id] INT FOREIGN KEY REFERENCES [dbo].[Series]([series_id]),
	PRIMARY KEY([user_id], [series_id])
)

CREATE TABLE [dbo].[HistoryReading] (
	[history_id] INT PRIMARY KEY IDENTITY(1,1),
	[user_id] INT FOREIGN KEY REFERENCES [dbo].[Users]([user_id]),
	[series_id] INT FOREIGN KEY REFERENCES [dbo].[Series]([series_id]),
	[chapter_id] INT FOREIGN KEY REFERENCES [dbo].[Chapters]([chapter_id]),
	[last_read_at] DATETIME DEFAULT getDate()
)