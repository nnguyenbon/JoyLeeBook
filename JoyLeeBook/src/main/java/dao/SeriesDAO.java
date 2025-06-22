package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Chapter;
import model.Genre;
import model.Series;

/**
 * DAO class for handling database operations related to Series entity. Provides
 * methods to insert, update, delete, and retrieve Series records from the
 * database.
 *
 * Assumes the Series table has columns: series_id (PK), author_name,
 * series_title, status, description, cover_image_url, created_at.
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
        List<Series> listSeries = new ArrayList<>();
        String sql = "SELECT * FROM Series";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Series series = extractSeriesFromResultSet(rs);
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
                    return extractSeriesFromResultSet(rs);
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
     * Searches for series by a partial title match.
     *
     * @param keyword Keyword to search in series titles.
     * @return List of Series matching the keyword.
     */
    public List<Series> searchSeries(String keyword) throws SQLException {
        List<Series> list = new ArrayList<>();
        String sql = "SELECT * FROM Series WHERE series_title LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(extractSeriesFromResultSet(rs));
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

    /**
     * Get genre by series id.
     *
     * @param seriesId id of series.
     * @return list of genre object.
     * @throws SQLException If a database access error occurs.
     */
    public List<Genre> getGenresBySeriesId(int seriesId) throws SQLException {
        GenreDAO genreDAO = new GenreDAO();
        return genreDAO.getGenresBySeriesId(seriesId);
    }

    /**
     * Get all Chapter by Series id.
     *
     * @param seriesId id of series.
     * @return list of chapter object.
     * @throws SQLException If a database access error occurs.
     */
    public List<Chapter> getAllChaptersBySeriesId(int seriesId) throws SQLException {
        ChapterDAO chapterDAO = new ChapterDAO(connection);
        return chapterDAO.getAllChaptersBySeriesId(seriesId);

    }

    /**
     * get Total chapter of series.
     *
     * @param seriesId id of series.
     * @return the number of chapters of a series
     * @throws SQLException
     */
    public int getTotalChaptersBySeriesId(int seriesId) throws SQLException {
        String sql = "SELECT COUNT(*) AS total_chapters FROM Chapter WHERE series_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, seriesId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total_chapters");
                }
            }
        }
        return 0;
    }

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
        s.setAuthorName(rs.getString("author_name"));
        s.setSeriesTitle(rs.getString("series_title"));
        s.setStatus(rs.getString("status"));
        s.setDescription(rs.getString("description"));
        s.setCoverImageUrl(rs.getString("cover_image_url"));
        s.setCreatedAt(rs.getTimestamp("created_at"));
        s.setGenres(getGenresBySeriesId(s.getSeriesId()));
        s.setTotalChapters(getTotalChaptersBySeriesId(s.getSeriesId()));
        return s;
    }
}
