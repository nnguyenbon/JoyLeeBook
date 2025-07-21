/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ArrayList;
import model.HistoryReading;

/**
 * not done yet
 *
 * @author PC
 */
public class HistoryReadingDAO {

    private final Connection connection;

    /**
     *
     * This class provides methods to: - Save or update the reading progress of
     * a user in a series. - Retrieve the last read chapter for a user in a
     * specific series.
     *
     * @param connection - for receive connection when execute in servlet
     */
    public HistoryReadingDAO(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<HistoryReading> getAllHistoryByUserId(int userId) {
        ArrayList<HistoryReading> histories = new ArrayList<>();

        String query = "SELECT h.user_id, s.series_id, c.chapter_id, c.chapter_index, s.series_title, "
                + "c.chapter_title, h.last_read_at "
                + "FROM HistoryReading h "
                + "JOIN Series s ON h.series_id = s.series_id "
                + "JOIN Chapters c ON h.chapter_id = c.chapter_id "
                + "WHERE h.user_id = ? "
                + "ORDER BY h.last_read_at DESC";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    HistoryReading history = new HistoryReading();
                    history.setUserId(rs.getInt("user_id"));
                    history.setSeriesId(rs.getInt("series_id"));
                    history.setChapterId(rs.getInt("chapter_id"));
                    history.setSeriesTitle(rs.getString("series_title"));
                    history.setChapterTitle(rs.getString("chapter_title"));
                    history.setLastReadAt(rs.getTimestamp("last_read_at"));
                    history.setChapterId(rs.getInt("chapter_index"));
                    histories.add(history);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return histories;
    }

    /**
     * Saves or updates the user's reading history.
     *
     * If the user has already started reading the given series, this method
     * updates the latest chapter they read and the timestamp. Otherwise, it
     * creates a new history record.
     *
     * @param history The HistoryReading object containing user ID, series ID,
     *                chapter ID.
     */
    public void saveOrUpdateHistory(HistoryReading history) {
        String checkExistQuery = "SELECT history_id FROM HistoryReading WHERE user_id = ? AND series_id = ?";
        String updateQuery = "UPDATE HistoryReading SET chapter_id = ?, last_read_at = GETDATE() WHERE user_id = ? AND series_id = ?";
        String insertQuery = "INSERT INTO HistoryReading (user_id, series_id, chapter_id) VALUES (?, ?, ?)";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkExistQuery)) {
            checkStmt.setInt(1, history.getUserId());
            checkStmt.setInt(2, history.getSeriesId());

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    // Đã tồn tại
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                        updateStmt.setInt(1, history.getChapterId());
                        updateStmt.setInt(2, history.getUserId());
                        updateStmt.setInt(3, history.getSeriesId());
                        updateStmt.executeUpdate();
                    }
                } else {
                    // Chưa có
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                        insertStmt.setInt(1, history.getUserId());
                        insertStmt.setInt(2, history.getSeriesId());
                        insertStmt.setInt(3, history.getChapterId());
                        insertStmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the last chapter a user read in a specific series.
     *
     * This is used to resume reading from the last saved point.
     *
     * @param userId   The ID of the user.
     * @param seriesId The ID of the series.
     * @return An Optional containing the HistoryReading object if found;
     *         otherwise, Optional.empty().
     */
    public Optional<HistoryReading> getLastReadChapter(int userId, int seriesId) {
        String query = "SELECT h.user_id, h.series_id, h.chapter_id, s.title AS series_title, c.title AS chapter_title, h.last_read_at "
                + "FROM HistoryReading h "
                + "JOIN Series s ON h.series_id = s.series_id "
                + "JOIN Chapter c ON h.chapter_id = c.chapter_id "
                + "WHERE h.user_id = ? AND h.series_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, seriesId);

            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    // Construct and return the HistoryReading object
                    HistoryReading history = new HistoryReading();
                    history.setUserId(rs.getInt("user_id"));
                    history.setSeriesId(rs.getInt("series_id"));
                    history.setChapterId(rs.getInt("chapter_id"));
                    history.setSeriesTitle(rs.getString("series_title"));
                    history.setChapterTitle(rs.getString("chapter_title"));
                    history.setLastReadAt(rs.getTimestamp("last_read_at"));
                    return Optional.of(history);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public boolean deleteBySeriesId(int seriesId) {
        String sql = "DELETE FROM HistoryReading WHERE series_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, seriesId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Inserts a new reading history record for a user.No update logic included
     * — use saveOrUpdateHistory if needed.
     *
     * @param history
     */
    public void insertHistory(HistoryReading history) {
        String sql = "INSERT INTO HistoryReading (user_id, series_id, chapter_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, history.getUserId());
            stmt.setInt(2, history.getSeriesId());
            stmt.setInt(3, history.getChapterId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all reading history records for a user, ordered by series and
     * chapter.
     *
     * @param userId
     * @return
     */
    public ArrayList<HistoryReading> getHistoryByUser(int userId) {
        ArrayList<HistoryReading> historyList = new ArrayList<>();
        String sql = "SELECT h.user_id, h.series_id, h.chapter_id, s.title AS series_title, "
                + "c.title AS chapter_title, h.last_read_at "
                + "FROM HistoryReading h "
                + "JOIN Series s ON h.series_id = s.series_id "
                + "JOIN Chapter c ON h.chapter_id = c.chapter_id "
                + "WHERE h.user_id = ? "
                + "ORDER BY h.series_id ASC, h.chapter_id DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    HistoryReading history = new HistoryReading();
                    history.setUserId(rs.getInt("user_id"));
                    history.setSeriesId(rs.getInt("series_id"));
                    history.setChapterId(rs.getInt("chapter_id"));
                    history.setSeriesTitle(rs.getString("series_title"));
                    history.setChapterTitle(rs.getString("chapter_title"));
                    history.setLastReadAt(rs.getTimestamp("last_read_at"));
                    historyList.add(history);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyList;
    }
}