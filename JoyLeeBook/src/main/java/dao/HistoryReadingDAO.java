package dao;

import model.HistoryReading;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
     * Save or update reading history for a user and series.
     * If exists → update chapter + time, else insert new.
     */
    public void saveOrUpdate(HistoryReading history) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM history_reading WHERE user_id = ? AND series_id = ?";
        PreparedStatement checkStmt = connection.prepareStatement(checkSql);
        checkStmt.setInt(1, history.getUserId());
        checkStmt.setInt(2, history.getSeriesId());
        ResultSet rs = checkStmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        rs.close();

        if (count > 0) {
            // UPDATE
            String updateSql = "UPDATE history_reading SET chapter_id = ?, chapter_title = ?, last_read_at = GETDATE() WHERE user_id = ? AND series_id = ?";
            PreparedStatement updateStmt = connection.prepareStatement(updateSql);
            updateStmt.setInt(1, history.getChapterId());
            updateStmt.setString(2, history.getChapterTitle());
            updateStmt.setInt(3, history.getUserId());
            updateStmt.setInt(4, history.getSeriesId());
            updateStmt.executeUpdate();
        } else {
            // INSERT
            String insertSql = "INSERT INTO history_reading (user_id, series_id, chapter_id, series_title, chapter_title, last_read_at) VALUES (?, ?, ?, ?, ?, GETDATE())";
            PreparedStatement insertStmt = connection.prepareStatement(insertSql);
            insertStmt.setInt(1, history.getUserId());
            insertStmt.setInt(2, history.getSeriesId());
            insertStmt.setInt(3, history.getChapterId());
            insertStmt.setString(4, history.getSeriesTitle());
            insertStmt.setString(5, history.getChapterTitle());
            insertStmt.executeUpdate();
        }
    }

    /**
     * Get all reading history for a user.
     */
    public ArrayList<HistoryReading> getAllByUser(int userId) throws SQLException {
        ArrayList<HistoryReading> list = new ArrayList<>();
        String sql = "SELECT * FROM history_reading WHERE user_id = ? ORDER BY last_read_at DESC";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            HistoryReading h = new HistoryReading();
            h.setUserId(rs.getInt("user_id"));
            h.setSeriesId(rs.getInt("series_id"));
            h.setChapterId(rs.getInt("chapter_id"));
            h.setSeriesTitle(rs.getString("series_title"));
            h.setChapterTitle(rs.getString("chapter_title"));
            h.setLastReadAt(rs.getTimestamp("last_read_at"));
            list.add(h);
        }
        return list;
    }

    /**
     * Get reading history of a user for a specific series.
     */
    public HistoryReading getByUserAndSeries(int userId, int seriesId) throws SQLException {
        String sql = "SELECT * FROM history_reading WHERE user_id = ? AND series_id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, userId);
        stmt.setInt(2, seriesId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            HistoryReading h = new HistoryReading();
            h.setUserId(rs.getInt("user_id"));
            h.setSeriesId(rs.getInt("series_id"));
            h.setChapterId(rs.getInt("chapter_id"));
            h.setSeriesTitle(rs.getString("series_title"));
            h.setChapterTitle(rs.getString("chapter_title"));
            h.setLastReadAt(rs.getTimestamp("last_read_at"));
            return h;
        }
        return null;
    }
}
