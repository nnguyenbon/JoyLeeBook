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
import java.util.List;
import model.Genre;

/**
 *
 * @author PC
 */
public class CategoryDAO {

    private final Connection connection;

    /**
     * Constructor to initialize ChapterDAO with a database connection.
     *
     * @param connection Active SQL connection to be used in DAO methods.
     */
    public CategoryDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     *
     * @param seriesId
     * @param genreIds
     * @throws SQLException
     */
    public void addCategories(int seriesId, List<Integer> genreIds) throws SQLException {
        String sql = "INSERT INTO Categories (series_id, genre_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int genreId : genreIds) {
                ps.setInt(1, seriesId);
                ps.setInt(2, genreId);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    /**
     *
     * @param seriesId
     * @return
     * @throws SQLException
     */
    public List<Genre> getGenresBySeriesId(int seriesId) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT g.genre_id, g.genre_name FROM Genres g "
                + "JOIN Categories c ON g.genre_id = c.genre_id "
                + "WHERE c.series_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, seriesId);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Genre genre = new Genre();
                    genre.setGenreId(rs.getInt("genre_id"));
                    genre.setGenreName(rs.getString("genre_name"));
                    genres.add(genre);
                }
            }
        }
        return genres;

    }

    /**
     *
     * @param seriesId
     * @throws SQLException
     */
    public void deleteBySeriesId(int seriesId) throws SQLException {
        String sql = "DELETE FROM Categories WHERE series_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, seriesId);
            ps.executeUpdate();

        }
    }
}
