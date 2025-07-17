package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Chapter;

/**
 * DAO class for handling database operations related to Chapter entity.
 * Provides methods to insert, update, delete, and retrieve chapter records from
 * the database.
 *
 * Assumes the Chapter table has columns: chapter_id (PK), series_id (FK),
 * chapter_index, chapter_title, content, created_at
 *
 * @author Trunguyen
 */
public class ChapterDAO {

    private final Connection connection;

    /**
     * Constructor to initialize ChapterDAO with a database connection.
     *
     * @param connection Active SQL connection to be used in DAO methods.
     */
    public ChapterDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Retrieves all chapters associated with a specific series, sorted by
     * chapter_index.
     *
     * @param seriesId the ID of the series
     * @return a list of chapter objects belonging to the specified series.
     * @throws SQLException If a database access error occurs.
     */
    public List<Chapter> getAllChaptersBySeriesId(int seriesId) throws SQLException {
        List<Chapter> list = new ArrayList<>();
        String sql = "SELECT * FROM Chapters WHERE series_id = ? ORDER BY chapter_index ASC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, seriesId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Chapter c = new Chapter();
                    c.setChapterId(rs.getInt("chapter_id"));
                    c.setSeriesId(rs.getInt("series_id"));
                    c.setChapterIndex(rs.getInt("chapter_index"));
                    c.setChapterTitle(rs.getString("chapter_title"));
                    c.setCreatedAt(rs.getTimestamp("created_at"));
                    c.setContent(rs.getString("content"));
                    list.add(c);
                }
            }
        }
        return list;
    }

    /**
     * Retrieves a chapter by its unique ID.
     *
     * @param chapterId The ID of the chapter to retrieve.
     * @return The Chapter object if found, or null if not found.
     * @throws SQLException If a database access error occurs.
     */
    public Chapter getChapterById(int chapterId) throws SQLException {
        String sql = " SELECT c.*, s.series_title FROM Chapters c JOIN Series s ON c.series_id = s.series_id WHERE c.chapter_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, chapterId);
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    Chapter chapter = new Chapter();
                    chapter.setChapterId(rs.getInt("chapter_id"));
                    chapter.setSeriesId(rs.getInt("series_id"));
                    chapter.setChapterIndex(rs.getInt("chapter_index"));
                    chapter.setChapterTitle(rs.getString("chapter_title"));
                    chapter.setCreatedAt(rs.getTimestamp("created_at"));
                    chapter.setContent(rs.getString("content"));
                    chapter.setSeriesTitle(rs.getString("series_title"));
                    return chapter;
                }
            }
        }
        return null;
    }

    /**
     * Insert a new chapter into the database.
     *
     * @param chapter the chapter object containing the data to insert.
     * @throws SQLException If a database access error occurs.
     */
    public boolean insertChapter(Chapter chapter) throws SQLException {
        String sql = "INSERT INTO Chapters (series_id, chapter_index, chapter_title, content) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, chapter.getSeriesId());
            ps.setInt(2, chapter.getChapterIndex());
            ps.setString(3, chapter.getChapterTitle());
            ps.setString(4, chapter.getContent());
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Updates the information of an existing chapter.
     *
     * @param chapter The Chapter object containing updated information.
     * @return
     * @throws SQLException If a database access error occurs.
     */
    public boolean updateChapter(Chapter chapter) throws SQLException {
        String sql = "UPDATE Chapters SET chapter_index = ?, chapter_title = ?, content = ? WHERE chapter_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, chapter.getChapterIndex());
            ps.setString(2, chapter.getChapterTitle());
            ps.setString(3, chapter.getContent());
            ps.setInt(4, chapter.getChapterId());
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a chapter from the database by its ID.
     *
     * @param chapterId The ID of the chapter to delete.
     * @throws SQLException If a database access error occurs.
     */
    public boolean deleteChapter(int chapterId) throws SQLException {
        String sql = "DELETE FROM Chapters WHERE chapter_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, chapterId);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Counts the total number of chapters in a specific series.
     *
     * @param seriesId The ID of the series to count chapters for.
     * @return The total number of chapters.
     * @throws SQLException If a database access error occurs.
     */
    public int getTotalChaptersBySeriesId(int seriesId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Chapters WHERE series_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, seriesId);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    /**
     * Get latest date chapter of Series
     *
     * @param seriesId id of series
     * @return latest date or null
     * @throws SQLException If a database access error occurs.
     */
    public Date getLatestDate(int seriesId) throws SQLException {
        String sql = "SELECT MAX(created_at) as latestDate FROM Chapters WHERE series_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, seriesId);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    return rs.getDate("latestDate");
                }
            }
        }
        return null;
    }

}
