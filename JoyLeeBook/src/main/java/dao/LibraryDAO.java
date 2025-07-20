package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List; 

import model.Library; 

/**
 *
 *
 * @author KHAI TOAN 
 */
public class LibraryDAO {

    private final Connection connection;

    public LibraryDAO(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<Library> getAllLibraries() {
        ArrayList<Library> libraries = new ArrayList<>();
        String sql = "SELECT user_id, series_id FROM UserLibraries";
        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Library library = new Library();
                library.setUserId(resultSet.getInt("user_id"));
                library.setSeriesId(resultSet.getInt("series_id"));
                libraries.add(library);
            }
        } catch (SQLException e) {

        }
        return libraries; 
    }

    public ArrayList<Library> getLibrariesByUserId(int userId) {
        ArrayList<Library> userLibraries = new ArrayList<>();
        String sql = "SELECT user_id, series_id FROM UserLibraries WHERE user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Library library = new Library();
                    library.setUserId(rs.getInt("user_id"));
                    library.setSeriesId(rs.getInt("series_id"));
                    userLibraries.add(library);
                }
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return userLibraries;
    }

    public Library getLibrary(int userId, int seriesId) {
        String sql = "SELECT user_id, series_id FROM UserLibraries WHERE user_id = ? AND series_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, seriesId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Library library = new Library();
                    library.setUserId(rs.getInt("user_id"));
                    library.setSeriesId(rs.getInt("series_id"));
                    return library;
                }
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return null;
    }

    public boolean addLibrary(Library library) {

        if (isLibraryExists(library.getUserId(), library.getSeriesId())) {

            return false;
        }

        String sql = "INSERT INTO UserLibraries (user_id, series_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, library.getUserId());
            pstmt.setInt(2, library.getSeriesId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteLibrary(int userId, int seriesId) {
        String sql = "DELETE FROM UserLibraries WHERE user_id = ? AND series_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, seriesId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    public boolean isLibraryExists(int userId, int seriesId) {
        String sql = "SELECT COUNT(*) FROM UserLibraries WHERE user_id = ? AND series_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, seriesId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteBySeriesId(int seriesId) {
        String sql = "DELETE FROM UserLibraries WHERE series_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, seriesId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // Trả về true nếu có ít nhất một hàng bị xóa
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }
}
