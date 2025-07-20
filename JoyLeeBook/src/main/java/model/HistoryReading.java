package model;

import java.sql.Timestamp;

/**
 * Represents a user's reading history, including information about the series,
 * chapter, and the timestamp of the last read.
 * 
 * This model is useful for tracking user activity and resuming reading progress.
 * 
 * @author PC
 */
public class HistoryReading {
    private int userId;
    private int seriesId;
    private int chapterId;
    private String seriesTitle;
    private String chapterTitle;
    private Timestamp lastReadAt;

    /**
     * Constructs a HistoryReading object with the specified details.
     *
     * @param userId       the ID of the user
     * @param seriesId     the ID of the series
     * @param chapterId    the ID of the chapter
     * @param seriesTitle  the title of the series
     * @param chapterTitle the title of the chapter
     * @param lastReadAt   the timestamp of the last time the chapter was read
     */
    public HistoryReading(int userId, int seriesId, int chapterId, String seriesTitle, String chapterTitle, Timestamp lastReadAt) {
        this.userId = userId;
        this.seriesId = seriesId;
        this.chapterId = chapterId;
        this.seriesTitle = seriesTitle;
        this.chapterTitle = chapterTitle;
        this.lastReadAt = lastReadAt;
    }
    
    /**
     * Constructs a HistoryReading object with the specified details.
     *
     * @param userId       the ID of the user
     * @param seriesId     the ID of the series
     * @param chapterId    the ID of the chapter
     * @param seriesTitle  the title of the series
     * @param chapterTitle the title of the chapter
     * @param lastReadAt   the timestamp of the last time the chapter was read
     */
    public HistoryReading(int userId, int seriesId, int chapterId, String seriesTitle, String chapterTitle) {
        this.userId = userId;
        this.seriesId = seriesId;
        this.chapterId = chapterId;
        this.seriesTitle = seriesTitle;
        this.chapterTitle = chapterTitle;
    }

    /**
     * Default constructor for HistoryReading.
     */
    public HistoryReading() {
    }

    /**
     * Gets the user ID.
     *
     * @return the ID of the user
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId the ID of the user
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the series ID.
     *
     * @return the ID of the series
     */
    public int getSeriesId() {
        return seriesId;
    }

    /**
     * Sets the series ID.
     *
     * @param seriesId the ID of the series
     */
    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }

    /**
     * Gets the chapter ID.
     *
     * @return the ID of the chapter
     */
    public int getChapterId() {
        return chapterId;
    }

    /**
     * Sets the chapter ID.
     *
     * @param chapterId the ID of the chapter
     */
    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    /**
     * Gets the series title.
     *
     * @return the title of the series
     */
    public String getSeriesTitle() {
        return seriesTitle;
    }

    /**
     * Sets the series title.
     *
     * @param seriesTitle the title of the series
     */
    public void setSeriesTitle(String seriesTitle) {
        this.seriesTitle = seriesTitle;
    }

    /**
     * Gets the chapter title.
     *
     * @return the title of the chapter
     */
    public String getChapterTitle() {
        return chapterTitle;
    }

    /**
     * Sets the chapter title.
     *
     * @param chapterTitle the title of the chapter
     */
    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    /**
     * Gets the timestamp of the last time the chapter was read.
     *
     * @return the last read timestamp
     */
    public Timestamp getLastReadAt() {
        return lastReadAt;
    }

    /**
     * Sets the timestamp of the last time the chapter was read.
     *
     * @param lastReadAt the last read timestamp
     */
    public void setLastReadAt(Timestamp lastReadAt) {
        this.lastReadAt = lastReadAt;
    }
}
