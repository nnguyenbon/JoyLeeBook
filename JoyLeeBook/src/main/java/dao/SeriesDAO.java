package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.Series;

/**
 * DAO class for handling database operations related to Series entity. Provides
 * methods to insert, update, delete, and retrieve chapter records from the
 * database.
 *
 * Assumes the Chapter table has columns: user_id (PK), role_name, username,
 * email, password.
 *
 * @author Trunguyen
 */
public class SeriesDAO {

    private final Connection connection;

    /**
     * Constructor to initialize SeriesDAO with a database connection.
     *
     * @param connection Active SQL connection to be used in DAO methods.
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
        List<Series> list = new ArrayList<>();
        String sql = "SELECT * FROM Series";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Series s = extractSeriesFromResultSet(rs);
                list.add(s);
            }

        }
        return list;
    }

    /**
     * Retrieves a specific series by its ID.
     *
     * @param id The series ID.
     * @return Series object if found, otherwise null.
     */
    public Series getSeriesById(int id) throws SQLException {
        String sql = "SELECT * FROM Series WHERE series_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractSeriesFromResultSet(rs);
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
        String sql = "INSERT INTO Series (author_id, series_title, status, summary, cover_image_url, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, series.getAuthorId());
            stmt.setString(2, series.getSeriesTitle());
            stmt.setString(3, series.getStatus());
            stmt.setString(4, series.getSummary());
            stmt.setString(5, series.getCoverImageUrl());
            stmt.setTimestamp(6, new Timestamp(series.getCreatedAt().getTime()));

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
        String sql = "UPDATE Series SET author_id = ?, series_title = ?, status = ?, summary = ?, cover_image_url = ? "
                + "WHERE series_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, series.getAuthorId());
            stmt.setString(2, series.getSeriesTitle());
            stmt.setString(3, series.getStatus());
            stmt.setString(4, series.getSummary());
            stmt.setString(5, series.getCoverImageUrl());
            stmt.setInt(6, series.getSeriesId());

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a series by its ID.
     *
     * @param id The ID of the series to delete.
     * @return true if deletion is successful, false otherwise.
     */
    public boolean deleteSeries(int id) throws SQLException {
        String sql = "DELETE FROM Series WHERE series_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // /**
    //  * Retrieves all series authored by a specific author.
    //  *
    //  * @param authorId The author ID.
    //  * @return List of Series by the author.
    //  */
    // public List<Series> getSeriesByAuthorId(int authorId) throws SQLException {
    //     List<Series> list = new ArrayList<>();
    //     String sql = "SELECT * FROM Series WHERE author_id = ?";
    //     try (PreparedStatement stmt = connection.prepareStatement(sql)) {
    //         stmt.setInt(1, authorId);
    //         ResultSet rs = stmt.executeQuery();
    //         while (rs.next()) {
    //             list.add(extractSeriesFromResultSet(rs));
    //         }
    //     }
    //     return list;
    // }
    // /**
    //  * Searches for series by a partial title match.
    //  *
    //  * @param keyword Keyword to search in series titles.
    //  * @return List of Series matching the keyword.
    //  */
    // public List<Series> searchSeries(String keyword) throws SQLException {
    //     List<Series> list = new ArrayList<>();
    //     String sql = "SELECT * FROM Series WHERE series_title LIKE ?";
    //     try (PreparedStatement stmt = connection.prepareStatement(sql)) {
    //         stmt.setString(1, "%" + keyword + "%");
    //         ResultSet rs = stmt.executeQuery();
    //         while (rs.next()) {
    //             list.add(extractSeriesFromResultSet(rs));
    //         }
    //     }
    //     return list;
    // }

    /**
     * Helper method to map a ResultSet row to a Series object.
     *
     * @param rs The ResultSet containing data from the Series table.
     * @return A Series object populated from the ResultSet.
     * @throws SQLException If accessing the ResultSet fails.
     */
    private Series extractSeriesFromResultSet(ResultSet rs) throws SQLException {
        Series s = new Series();
        s.setSeriesId(rs.getInt("series_id"));
        s.setAuthorId(rs.getInt("author_id"));
        s.setSeriesTitle(rs.getString("series_title"));
        s.setStatus(rs.getString("status"));
        s.setSummary(rs.getString("summary"));
        s.setCoverImageUrl(rs.getString("cover_image_url"));
        s.setCreatedAt(rs.getTimestamp("created_at"));
        return s;
    }
}