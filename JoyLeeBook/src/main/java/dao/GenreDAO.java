/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Genre;

/**
 *
 * @author PC
 */
public class GenreDAO {

    private final Connection connection;

    // public GenreDAO() throws SQLException, ClassNotFoundException {
    // connection = (Connection) DBConnection.getConnection();
    // }

    /**
     *
     * @param connection
     */

    public GenreDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public ArrayList<Genre> getAll() throws SQLException {
        ArrayList<Genre> genres = new ArrayList<>();
        String sql = "SELECT * FROM Genres";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setGenreId(rs.getInt("genre_id"));
                genre.setGenreName(rs.getString("genre_name"));
                genres.add(genre);
            }
        } catch (SQLException e) {
            throw e;
        }
        return genres;
    }

    /**
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public Genre getGenreById(int id) throws SQLException {
        String sql = "SELECT * FROM Genres WHERE genre_id = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, id);
            try (
                    ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    Genre genre = new Genre();
                    genre.setGenreId(rs.getInt("genre_id"));
                    genre.setGenreName(rs.getString("genre_name"));
                    return genre;
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        return null;
    }

    /**
     *
     * @param genre
     * @return
     * @throws SQLException
     */
    public int insertGenre(Genre genre) throws SQLException {
        String sql = "INSERT INTO Genres Values (?)";
        // Connection conn = (Connection) DBConnection.getConnection();
        int existId = checkDuplicatesName(genre.getGenreName());
        if (existId != -1) {
            return existId;
        }
        try (
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, genre.getGenreName());
            if (ps.executeUpdate() > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
            return -1;
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     *
     * @param genre
     * @throws SQLException
     */
    public void updateGenre(Genre genre) throws SQLException {
        String sql = "UPDATE Genres SET genre_name = ? WHERE genre_id = ?";
        // Connection conn = (Connection) DBConnection.getConnection();
        try (
                PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setString(1, genre.getGenreName());
            ps.setInt(2, genre.getGenreId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     *
     * @param id
     * @throws SQLException
     */
    public void deleteGenre(int id) throws SQLException {
        String sql = "DELETE FROM Genres WHERE genre_id = ?";
        // Connection conn = (Connection) DBConnection.getConnection();
        try (
                PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     *
     * @param name
     * @return
     * @throws SQLException
     */
    public int checkDuplicatesName(String name)
            throws SQLException {
        String sql = "SELECT genre_id FROM Genres WHERE genre_name = ?";
        // Connection conn = (Connection) DBConnection.getConnection();
        try (
                PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setString(1, name);
            try (
                    ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    return rs.getInt("genre_id");
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        return -1;
    }
}
