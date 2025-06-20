/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBConnection;
import model.Genre;

/**
 *
 * @author PC
 */
public class GenreDAO {

    public ArrayList<Genre> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Genre> genres = new ArrayList<>();
        String sql = "SELECT * FROM Genres";
        Connection conn = (Connection) DBConnection.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setGenreId(rs.getInt("genre_id"));
                genre.setGenreName(rs.getString("genre_name"));
                genres.add(genre);
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }

    public Genre getGenreById(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Genres WHERE genre_id = ?";
        Connection conn = (Connection) DBConnection.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Genre genre = new Genre();
                genre.setGenreId(rs.getInt("genre_id"));
                genre.setGenreName(rs.getString("genre_name"));
                return genre;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertGenre(Genre genre) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Genres Values ( ?)";
        Connection conn = (Connection) DBConnection.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, genre.getGenreName());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGenre(Genre genre) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Genres SET genre_name = ? WHERE genre_id = ?";
        Connection conn = (Connection) DBConnection.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, genre.getGenreName());
            pstmt.setInt(2, genre.getGenreId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteGenre(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Genres WHERE genre_id = ?";
        Connection conn = (Connection) DBConnection.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
