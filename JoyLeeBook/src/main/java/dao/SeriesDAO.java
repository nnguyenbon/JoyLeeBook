package dao;

import java.sql.*;
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
        String sql = "INSERT INTO Series (author_name, series_title, status, description, cover_image_url) " + "VALUES (?, ?, ?, ?, ?)";
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
        String sql = "UPDATE Series SET author_name= ?, series_title = ?, status = ?, description = ?, cover_image_url = ? " + "WHERE series_id = ?";
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
     * Searches for series by keyword with pagination support.
     *
     * @param keyword    The keyword to search for in the title or author name.
     * @param pageNumber The current page to retrieve (starting from 1).
     * @param pageSize   The number of results per page.
     * @return A list of Series objects for the current page.
     * @throws SQLException If a database access error occurs.
     */
    public List<Series> searchSeries(String keyword, int pageNumber, int pageSize) throws SQLException {
        List<Series> list = new ArrayList<>();

        // Base SQL query for searching
        String baseSql = "SELECT * FROM Series";
        if (keyword != null && !keyword.trim().isEmpty()) {
            baseSql += " WHERE series_title LIKE ? OR author_name LIKE ?";
        }

        // Append ordering and pagination clauses for SQL Server
        String sql = baseSql + " ORDER BY series_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int paramIndex = 1;
            if (keyword != null && !keyword.trim().isEmpty()) {
                String searchPattern = "%" + keyword + "%";
                stmt.setString(paramIndex++, searchPattern);
                stmt.setString(paramIndex++, searchPattern);
            }

            // Calculate the offset based on the page number and page size
            int offset = (pageNumber - 1) * pageSize;
            stmt.setInt(paramIndex++, offset);
            stmt.setInt(paramIndex++, pageSize);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Reuse your mapping method to avoid code duplication
                    list.add(mappingSeriesFromResultSet(rs));
                }
            }
        }
        return list;
    }

    /**
     * Counts the total number of series that match the search keyword.
     *
     * @param keyword The keyword to search for in the series title and author name.
     * @return The total number of series found.
     * @throws SQLException If a database access error occurs.
     */
    public int getTotalSeriesCount(String keyword) throws SQLException {
        // If the keyword is null or empty, we count all series.
        String sql = "SELECT COUNT(*) FROM Series";
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += " WHERE series_title LIKE ? OR author_name LIKE ?";
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            if (keyword != null && !keyword.trim().isEmpty()) {
                String searchPattern = "%" + keyword + "%";
                stmt.setString(1, searchPattern);
                stmt.setString(2, searchPattern);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); // Return the value of the first column (the COUNT result)
                }
            }
        }
        return 0; // Return 0 if no results are found
    }

    /**
     * Checks if a series title is available (i.e., not already used in the
     * database).
     *
     * @param seriesTitle The title of the series to check.
     * @return true if the title is not used (available), false if it already
     * exists.
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

    public int insertAndReturnId(Series series) throws SQLException {
        String sql = "INSERT INTO Series (author_name, series_title, status, description, cover_image_url) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, series.getAuthorName());
            ps.setString(2, series.getSeriesTitle());
            ps.setString(3, series.getStatus());
            ps.setString(4, series.getDescription());
            ps.setString(5, series.getCoverImageUrl());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting series failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // return generated series_id
                } else {
                    throw new SQLException("Inserting series failed, no ID obtained.");
                }
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
