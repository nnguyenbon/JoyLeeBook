package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Genre;

/**
 * Data Access Object for handling operations related to the Categories table.
 * This class manages the relationship between series and genres.
 */
public class CategoryDAO {

    private final Connection connection;

    /**
     * Constructor to initialize CategoryDAO with a database connection.
     *
     * @param connection Active SQL connection to be used in DAO methods.
     */
    public CategoryDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Adds multiple category relationships to the database. Each category links
     * a series with a genre.
     *
     * @param seriesId The ID of the series to associate genres with.
     * @param genreIds A list of genre IDs to be associated with the series.
     * @throws SQLException If a database access error occurs.
     */
    public void addCategories(int seriesId, ArrayList<Integer> genreIds) throws SQLException {
        String sql = "INSERT INTO Categories (series_id, genre_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int genreId : genreIds) {
                ps.setInt(1, seriesId);
                ps.setInt(2, genreId);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a list of genres associated with a specific series.
     *
     * @param seriesId The ID of the series.
     * @return A list of Genre objects linked to the series.
     * @throws SQLException If a database access error occurs.
     */
    public ArrayList<Genre> getGenresBySeriesId(int seriesId) throws SQLException {
        ArrayList<Genre> genres = new ArrayList<>();
        String sql = "SELECT g.genre_id, g.genre_name FROM Genres g "
                + "JOIN Categories c ON g.genre_id = c.genre_id "
                + "WHERE c.series_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, seriesId);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Genre genre = new Genre();
                    genre.setGenreId(rs.getInt("genre_id"));
                    genre.setGenreName(rs.getString("genre_name"));
                    genres.add(genre);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return genres;

    }

    /**
     * Deletes all category associations for a given series.
     *
     * @param seriesId The ID of the series whose categories should be removed.
     * @throws SQLException If a database access error occurs.
     */
    public boolean deleteBySeriesId(int seriesId) throws SQLException {
        String sql = "DELETE FROM Categories WHERE series_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, seriesId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateGenreOfSeries(int seriesId, ArrayList<Integer> newGenresId) throws SQLException {
        try {
            deleteBySeriesId(seriesId);
            addCategories(seriesId, newGenresId);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
