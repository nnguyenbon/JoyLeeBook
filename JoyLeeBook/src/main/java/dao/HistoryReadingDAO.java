/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import model.HistoryReading;
import db.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * not done yet
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

    /**
     * Saves or updates the user's reading history.
     *
     * If the user has already started reading the given series, this method
     * updates the latest chapter they read and the timestamp. Otherwise, it
     * creates a new history record.
     *
     * @param history The HistoryReading object containing user ID, series ID,
     * chapter ID, and last read timestamp.
     */
    public void saveOrUpdateHistory(HistoryReading history) {
        String checkExistQuery = "SELECT history_id FROM HistoryReading WHERE user_id = ? AND series_id = ?";
        String updateQuery = "UPDATE HistoryReading SET chapter_id = ?, last_read_at = ? WHERE user_id = ? AND series_id = ?";
        String insertQuery = "INSERT INTO HistoryReading (user_id, series_id, chapter_id, last_read_at) VALUES (?, ?, ?, ?)";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkExistQuery)) {
            // Check if the history record already exists for this user and series
            checkStmt.setInt(1, history.getUserId());
            checkStmt.setInt(2, history.getSeriesId());

            try (ResultSet rs = checkStmt.executeQuery();) {
                if (rs.next()) {
                    // Record exists – update the existing entry
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                        updateStmt.setInt(1, history.getChapterId());
                        updateStmt.setTimestamp(2, history.getLastReadAt());
                        updateStmt.setInt(3, history.getUserId());
                        updateStmt.setInt(4, history.getSeriesId());
                        updateStmt.executeUpdate();
                    }
                } else {
                    // Record does not exist – insert a new entry
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                        insertStmt.setInt(1, history.getUserId());
                        insertStmt.setInt(2, history.getSeriesId());
                        insertStmt.setInt(3, history.getChapterId());
                        insertStmt.setTimestamp(4, history.getLastReadAt());
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
     * @param userId The ID of the user.
     * @param seriesId The ID of the series.
     * @return An Optional containing the HistoryReading object if found;
     * otherwise, Optional.empty().
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
}
