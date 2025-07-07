package model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a series with its core information, such as title, author,
 * status, description, and related metadata like genres and chapters.
 */
public class Series {

    private int seriesId;
    private String authorName;
    private String seriesTitle;
    private String status;
    private String description;
    private String coverImageUrl;
    private Date createdAt;

    // Additional fields for joined data
    private ArrayList<Genre> genres;
    private ArrayList<Chapter> chapter;
    private int totalChapters;
    private Date latestChapterDate;

    /**
     * Default constructor for the Series class.
     */
    public Series() {
    }

    /**
     * Constructs a Series object with specified author name, title, status,
     * description, and cover image URL. Sets the creation date to the current time.
     */
    public Series(String authorName, String seriesTitle, String status, String description, String coverImageUrl) {
        this.authorName = authorName;
        this.seriesTitle = seriesTitle;
        this.status = status;
        this.description = description;
        this.coverImageUrl = coverImageUrl;
        this.createdAt = new Date();
    }

    /**
     * Returns the unique ID of the series.
     */
    public int getSeriesId() {
        return seriesId;
    }

    /**
     * Sets the unique ID for the series.
     */
    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }

    /**
     * Returns the title of the series.
     */
    public String getSeriesTitle() {
        return seriesTitle;
    }

    /**
     * Sets the title of the series.
     */
    public void setSeriesTitle(String seriesTitle) {
        this.seriesTitle = seriesTitle;
    }

    /**
     * Returns the current status of the series, such as ongoing or completed.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the series.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Sets the description of the series.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the description of the series.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the URL of the cover image.
     */
    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    /**
     * Sets the URL of the cover image.
     */
    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    /**
     * Returns the creation date of the series record.
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation date of the series record.
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns the name of the author of the series.
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * Sets the author's name of the series.
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     * Returns the list of genres associated with the series.
     */
    public ArrayList<Genre> getGenres() {
        return genres;
    }

    /**
     * Sets the list of genres associated with the series.
     */
    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    /**
     * Returns the total number of chapters in the series.
     */
    public int getTotalChapters() {
        return totalChapters;
    }

    /**
     * Sets the total number of chapters in the series.
     */
    public void setTotalChapters(int totalChapters) {
        this.totalChapters = totalChapters;
    }

    /**
<<<<<<< HEAD
     * Returns the list of chapters belonging to the series.
     */
    public ArrayList<Chapter> getChapter() {
        return chapter;
    }

    /**
     * Sets the list of chapters for the series.
     */
    public void setChapter(ArrayList<Chapter> chapter) {
        this.chapter = chapter;
=======
     * 
     * @return
     */
    public Date getLatestChapterDate() {
        return latestChapterDate;
    }

    /**
     * 
     * @param latestChapterDate
     */
    public void setLatestChapterDate(Date latestChapterDate) {
        this.latestChapterDate = latestChapterDate;
>>>>>>> 988c95e51b19c9334bc6c4bf21e5b3ac9ce40372
    }
}
