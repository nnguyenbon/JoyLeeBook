package model;

/**
 * Represents a genre that can be assigned to a series.
 * A genre includes an ID and a name.
 */
public class Genre {
    private int genreId;
    private String genreName;

    /**
     * Default constructor.
     * Creates an empty Genre object.
     */
    public Genre() {
    }

    /**
     * Constructor that initializes the genre with a name.
     *
     * @param genreName The name of the genre.
     */
    public Genre(String genreName) {
        this.genreName = genreName;
    }

    /**
     * Returns the ID of the genre.
     *
     * @return The genre ID.
     */
    public int getGenreId() {
        return genreId;
    }

    /**
     * Sets the ID of the genre.
     *
     * @param genreId The genre ID to set.
     */
    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    /**
     * Returns the name of the genre.
     *
     * @return The genre name.
     */
    public String getGenreName() {
        return genreName;
    }

    /**
     * Sets the name of the genre.
     *
     * @param genreName The genre name to set.
     */
    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
