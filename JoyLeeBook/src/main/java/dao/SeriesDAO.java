package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Series;

/**
 * DAO class for handling database operations related to Series entity. Provides
 * methods to insert, update, delete, and retrieve Series records from the
 * database.
 * Assumes the Series table has columns: series_id (PK), author_name,
 * series_title, status, description, cover_image_url, created_at.
 *
 * @author Trunguyen
 */
public class SeriesDAO {

    private final Connection connection;

    /**
     * Constructor to initialize SeriesDAO with a database connection.
     */
    public SeriesDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Retrieves all series records from the database.
     *
     * @return List of Series objects.
     */
    public List<Series> getAllSeries() throws SQLException {
        List<Series> listSeries = new ArrayList<>();
        String sql = "SELECT * FROM Series";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Series series = mappingSeriesFromResultSet(rs);
                listSeries.add(series);
            }
        }
        return listSeries;
    }

    /**
     * Retrieves a specific series by its ID.
     *
     * @param series_id The series ID.
     * @return Series object if found, otherwise null.
     */
    public Series getSeriesById(int series_id) throws SQLException {
        String sql = "SELECT * FROM Series WHERE series_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, series_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mappingSeriesFromResultSet(rs);
                }
            }
        }
        return null;
    }

    /**
     * Inserts a new series into the database.
     *
     * @param series The Series object to add.
     * @return true if insertion is successful, false otherwise.
     */
    public boolean insertSeries(Series series) throws SQLException {
        String sql = "INSERT INTO Series (author_name, series_title, status, description, cover_image_url) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, series.getAuthorName());
            stmt.setString(2, series.getSeriesTitle());
            stmt.setString(3, series.getStatus());
            stmt.setString(4, series.getDescription());
            stmt.setString(5, series.getCoverImageUrl());
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Updates an existing series record.
     *
     * @param series The updated Series object.
     * @return true if update is successful, false otherwise.
     */
    public boolean updateSeries(Series series) throws SQLException {
        String sql = "UPDATE Series SET author_name= ?, series_title = ?, status = ?, description = ?, cover_image_url = ? "
                + "WHERE series_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, series.getAuthorName());
            stmt.setString(2, series.getSeriesTitle());
            stmt.setString(3, series.getStatus());
            stmt.setString(4, series.getDescription());
            stmt.setString(5, series.getCoverImageUrl());
            stmt.setInt(6, series.getSeriesId());

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a series by its ID.
     *
     * @param series_id The ID of the series to delete.
     * @return true if deletion is successful, false otherwise.
     */
    public boolean deleteSeries(int series_id) throws SQLException {
        String sql = "DELETE FROM Series WHERE series_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, series_id);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Searches for series by a partial title or author match.
     *
     * @param keyword Keyword to search in series titles.
     * @return List of Series matching the keyword.
     */
    public List<Series> searchSeries(String keyword) throws SQLException {
        List<Series> list = new ArrayList<>();
        String sql = "SELECT * FROM Series WHERE series_title LIKE ? OR author_name LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mappingSeriesFromResultSet(rs));
                }
            }
        }
        return list;
    }

    /**
     * Checks if a series title is available (i.e., not already used in the
     * database).
     *
     * @param seriesTitle The title of the series to check.
     * @return true if the title is not used (available), false if it already
     *         exists.
     * @throws SQLException If a database access error occurs.
     */
    public boolean checkTitleSeries(String seriesTitle) throws SQLException {
        String sql = "SELECT * FROM Series WHERE series_title = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, seriesTitle);
            try (ResultSet rs = stmt.executeQuery()) {
                return !rs.next(); // true if the title is not used (available)
            }
        }
    }

    /**
     * Saves a series to the user's library.
     * This method inserts a record into the UserLibraries table linking a user
     * 
     * @param seriesId The ID of the series to save.
     * @param userId   The ID of the user saving the series.
     * @return true if the series is successfully saved, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public boolean saveSeries(int seriesId, int userId) throws SQLException {
        String sql = "INSERT INTO UserLibraries (user_id, series_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, seriesId);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a saved series from the user's library.
     * This method removes a record from the UserLibraries table for a specific
     * 
     * @param seriesId The ID of the series to delete from the user's library.
     * @param userId   The ID of the user whose saved series is being deleted.
     * @return true if the series is successfully deleted, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public boolean deleteSavedSeries(int seriesId, int userId) throws SQLException {
        String sql = "DELETE FROM UserLibraries WHERE user_id = ? AND series_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, seriesId);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean isSeriesSaved(int seriesId, int userId) throws SQLException {
        String sql = "SELECT * FROM UserLibraries WHERE user_id = ? AND series_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, seriesId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Helper method to map a ResultSet row to a Series object.
     *
     * @param rs The ResultSet containing data from the Series table.
     * @return A Series object populated from the ResultSet.
     * @throws SQLException If accessing the ResultSet fails.
     */
    private Series mappingSeriesFromResultSet(ResultSet rs) throws SQLException {
        Series s = new Series();
        s.setSeriesId(rs.getInt("series_id"));
        s.setAuthorName(rs.getString("author_name"));
        s.setSeriesTitle(rs.getString("series_title"));
        s.setStatus(rs.getString("status"));
        s.setDescription(rs.getString("description"));
        s.setCoverImageUrl(rs.getString("cover_image_url"));
        s.setCreatedAt(rs.getTimestamp("created_at"));
        return s;
    }
}
