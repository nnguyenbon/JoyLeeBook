package model;

/**
 * Represents the relationship between a genre and a series.
 * Used to associate a specific series with a specific genre.
 */
public class Category {
    private int genreId;
    private int seriesId;

    /**
     * Default constructor.
     * Creates an empty Category object with no values set.
     */
    public Category() {
    }

    /**
     * Constructor that initializes the category with a genre ID and a series ID.
     *
     * @param genreId  The ID of the genre.
     * @param seriesId The ID of the series.
     */
    public Category(int genreId, int seriesId) {
        this.genreId = genreId;
        this.seriesId = seriesId;
    }

    /**
     * Returns the genre ID.
     *
     * @return The genre ID.
     */
    public int getGenreId() {
        return genreId;
    }

    /**
     * Sets the genre ID.
     *
     * @param genreId The genre ID to set.
     */
    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    /**
     * Returns the series ID.
     *
     * @return The series ID.
     */
    public int getSeriesId() {
        return seriesId;
    }

    /**
     * Sets the series ID.
     *
     * @param seriesId The series ID to set.
     */
    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }
}
