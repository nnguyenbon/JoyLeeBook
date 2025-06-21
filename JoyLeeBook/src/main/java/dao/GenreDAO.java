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

    public ArrayList<Genre> getAllGenresOfSeries(int seriesId) throws SQLException, ClassNotFoundException {
        ArrayList<Genre> genres = new ArrayList<>();
        String sql = "SELECT c.genre_id,genre_name FROM Genres g JOIN Categories c ON c.series_id = ? and g.genre_id = c.genre_id";
        Connection conn = (Connection) DBConnection.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, seriesId);
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
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            Genre genre = new Genre();
            genre.setGenreId(rs.getInt("genre_id"));
            genre.setGenreName(rs.getString("genre_name"));
            rs.close();
            pstmt.close();
            conn.close();
            return genre;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String insertGenre(Genre genre) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Genres Values (?)";
        Connection conn = (Connection) DBConnection.getConnection();
        try {
            if (!checkDuplicatesGenreName(genre.getGenreName())) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, genre.getGenreName());
                pstmt.executeUpdate();
                pstmt.close();
                return "Insert Successfully";
            } else {
                return "This name id duplicate";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Fail to insert";
    }

    public String insertGenreOfSeries(Genre iGenre, int series_id) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Genres Values (?)";
        Connection conn = (Connection) DBConnection.getConnection();
        try {
            ArrayList<Genre> genresOfSeries = getAllGenresOfSeries(series_id);
            for (Genre genre : genresOfSeries) {
                if (iGenre.getGenreName().equalsIgnoreCase(genre.getGenreName()))
                    return "This genre is exist in this series";
            }
            insertGenre(iGenre);
            ArrayList<Genre> genres = getAll();
            int genre_id = genres.size();
            for (Genre genre : genres) {
                if (genre.getGenreName().equalsIgnoreCase(iGenre.getGenreName())) {
                    genre_id = genre.getGenreId();
                }
            }
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Categories Values (?,?)");
            pstmt.setInt(1, genre_id);
            pstmt.setInt(2, series_id);
            pstmt.close();
            conn.close();
            return "Insert Successfully";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Fail to insert";
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

    public boolean checkDuplicatesGenreName(String name)
            throws SQLException, ClassNotFoundException {
        String sql = "SELECT genre_name FROM Genres";
        Connection conn = (Connection) DBConnection.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String valueTable = rs.getString("genre_name");
                if (valueTable.equalsIgnoreCase(name)) {
                    return true;
                }
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
