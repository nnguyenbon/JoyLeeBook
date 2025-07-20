USE [master]
GO

-- Xóa database cũ nếu tồn tại để tạo lại từ đầu
IF EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = 'JoyLeeBook')
    BEGIN
        ALTER DATABASE JoyLeeBook SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
        DROP DATABASE JoyLeeBook;
    END
GO

CREATE DATABASE JoyLeeBook
GO

USE JoyLeeBook
GO

-- TẠO LẠI CÁC BẢNG VỚI KHÓA NGOẠI ĐÚNG

CREATE TABLE [dbo].[Users] (
                               [user_id] INT PRIMARY KEY IDENTITY(1,1),
                               [role_name] NVARCHAR(15) NOT NULL,
                               [username] NVARCHAR(15) UNIQUE NOT NULL,
                               [email] NVARCHAR(100) UNIQUE NOT NULL,
                               [user_password] NVARCHAR(40) NOT NULL
);

CREATE TABLE [dbo].[Genres] (
                                [genre_id] INT PRIMARY KEY IDENTITY(1,1),
                                [genre_name] NVARCHAR(15) UNIQUE NOT NULL
);

CREATE TABLE [dbo].[Series] (
                                [series_id] INT PRIMARY KEY IDENTITY(1,1),
                                [author_name] NVARCHAR(50) NOT NULL,
                                [series_title] NVARCHAR(50) NOT NULL,
                                [status] INT NOT NULL,
                                [description] TEXT NOT NULL,
                                [cover_image_url] NVARCHAR(255) NOT NULL,
                                [created_at] DATETIME DEFAULT GETDATE()
);

-- Khi một Series bị xóa, các Chapters liên quan cũng sẽ tự động bị xóa.
CREATE TABLE [dbo].[Chapters] (
                                  [chapter_id] INT PRIMARY KEY IDENTITY(1,1),
                                  [series_id] INT FOREIGN KEY REFERENCES [dbo].[Series]([series_id]) ON DELETE CASCADE,
                                  [chapter_index] INT NOT NULL,
                                  [chapter_title] NVARCHAR(50) NOT NULL,
                                  [created_at] DATETIME DEFAULT GETDATE(),
                                  [content] NVARCHAR(MAX) NOT NULL
);

-- Khi một Series hoặc Genre bị xóa, các Categories liên quan cũng sẽ tự động bị xóa.
CREATE TABLE [dbo].[Categories] (
                                    [genre_id] INT FOREIGN KEY REFERENCES [dbo].[Genres]([genre_id]) ON DELETE CASCADE,
                                    [series_id] INT FOREIGN KEY REFERENCES [dbo].[Series]([series_id]) ON DELETE CASCADE,
                                    PRIMARY KEY([genre_id], [series_id])
);

-- Khi một User hoặc Series bị xóa, các UserLibraries liên quan cũng sẽ tự động bị xóa.
CREATE TABLE [dbo].[UserLibraries] (
                                       [user_id] INT FOREIGN KEY REFERENCES [dbo].[Users]([user_id]) ON DELETE CASCADE,
                                       [series_id] INT FOREIGN KEY REFERENCES [dbo].[Series]([series_id]) ON DELETE CASCADE,
                                       PRIMARY KEY([user_id], [series_id])
);

-- Khi một Chapter bị xóa, HistoryReading liên quan cũng sẽ tự động bị xóa.
CREATE TABLE [dbo].[HistoryReading] (
                                        [history_id] INT PRIMARY KEY IDENTITY(1,1),
                                        [user_id] INT FOREIGN KEY REFERENCES [dbo].[Users]([user_id]),
                                        [series_id] INT FOREIGN KEY REFERENCES [dbo].[Series]([series_id]), -- Không cần cascade ở đây
                                        [chapter_id] INT FOREIGN KEY REFERENCES [dbo].[Chapters]([chapter_id]) ON DELETE CASCADE,
                                        [last_read_at] DATETIME DEFAULT GETDATE()
);

GO
PRINT 'Database JoyLeeBook created successfully with ON DELETE CASCADE constraints.'