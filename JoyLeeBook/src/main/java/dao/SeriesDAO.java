package dao;

import model.Series;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeriesDAO {
    private final Connection connection;

    public SeriesDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Series> getAll() throws SQLException {
        List<Series> seriesList = new ArrayList<>();
        String sql = "SELECT * FROM Series";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Series series = mapResultSetToSeries(rs);
                seriesList.add(series);
            }
        }
        return seriesList;
    }

    public Series getById(int seriesId) throws SQLException {
        String sql = "SELECT * FROM Series WHERE series_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, seriesId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToSeries(rs);
                }
            }
        }
        return null;
    }

    public boolean insert(Series series) throws SQLException {
        String sql = "INSERT INTO Series (author_id, series_title, status, summary, cover_image_url, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, series.getAuthorId());
            stmt.setString(2, series.getSeriesTitle());
            stmt.setString(3, series.getStatus());
            stmt.setString(4, series.getSummary());
            stmt.setString(5, series.getCoverImageUrl());
            stmt.setTimestamp(6, new Timestamp(series.getCreatedAt().getTime()));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        series.setSeriesId(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean update(Series series) throws SQLException {
        String sql = "UPDATE Series SET author_id = ?, series_title = ?, status = ?, "
                + "summary = ?, cover_image_url = ? WHERE series_id = ?";
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

    public boolean delete(int seriesId) throws SQLException {
        String sql = "DELETE FROM Series WHERE series_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, seriesId);
            return stmt.executeUpdate() > 0;
        }
    }

    private Series mapResultSetToSeries(ResultSet rs) throws SQLException {
        Series series = new Series();
        series.setSeriesId(rs.getInt("series_id"));
        series.setAuthorId(rs.getInt("author_id"));
        series.setSeriesTitle(rs.getString("series_title"));
        series.setStatus(rs.getString("status"));
        series.setSummary(rs.getString("summary"));
        series.setCoverImageUrl(rs.getString("cover_image_url"));
        series.setCreatedAt(rs.getTimestamp("created_at"));
        return series;
    }
}