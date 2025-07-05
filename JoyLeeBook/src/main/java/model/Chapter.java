package model;

import java.util.Date;

/**
 * Represents a single chapter of a series. Includes details such as chapter index,
 * title, content, creation date, and the associated series information.
 */
public class Chapter {
    private int chapterId;
    private int seriesId;
    private int chapterIndex;
    private String chapterTitle;
    private Date createdAt;
    private String content;

    // Additional field for joined data
    private String seriesTitle;

    /**
     * Default constructor for the Chapter class.
     */
    public Chapter() {
    }

    /**
     * Constructor that initializes a new Chapter with the given series ID,
     * chapter index, title, and content. Automatically sets the creation date.
     *
     * @param seriesId The ID of the series this chapter belongs to.
     * @param chapterIndex The index or order of the chapter within the series.
     * @param chapterTitle The title of the chapter.
     * @param content The textual content of the chapter.
     */
    public Chapter(int seriesId, int chapterIndex, String chapterTitle, String content) {
        this.seriesId = seriesId;
        this.chapterIndex = chapterIndex;
        this.chapterTitle = chapterTitle;
        this.content = content;
        this.createdAt = new Date();
    }

    /**
     * Returns the unique ID of the chapter.
     *
     * @return The chapter ID.
     */
    public int getChapterId() {
        return chapterId;
    }

    /**
     * Sets the unique ID for the chapter.
     *
     * @param chapterId The ID to assign to the chapter.
     */
    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    /**
     * Returns the ID of the series this chapter belongs to.
     *
     * @return The series ID.
     */
    public int getSeriesId() {
        return seriesId;
    }

    /**
     * Sets the ID of the series this chapter belongs to.
     *
     * @param seriesId The series ID.
     */
    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }

    /**
     * Returns the index or order number of the chapter.
     *
     * @return The chapter index.
     */
    public int getChapterIndex() {
        return chapterIndex;
    }

    /**
     * Sets the index or order number of the chapter.
     *
     * @param chapterIndex The index to assign to the chapter.
     */
    public void setChapterIndex(int chapterIndex) {
        this.chapterIndex = chapterIndex;
    }

    /**
     * Returns the title of the chapter.
     *
     * @return The chapter title.
     */
    public String getChapterTitle() {
        return chapterTitle;
    }

    /**
     * Sets the title of the chapter.
     *
     * @param chapterTitle The title to assign to the chapter.
     */
    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    /**
     * Returns the date the chapter was created.
     *
     * @return The creation date.
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation date of the chapter.
     *
     * @param createdAt The date to assign.
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns the content of the chapter.
     *
     * @return The chapter content.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the chapter.
     *
     * @param content The textual content to assign.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns the title of the series this chapter belongs to.
     *
     * @return The series title.
     */
    public String getSeriesTitle() {
        return seriesTitle;
    }

    /**
     * Sets the title of the series this chapter belongs to.
     *
     * @param seriesTitle The series title to assign.
     */
    public void setSeriesTitle(String seriesTitle) {
        this.seriesTitle = seriesTitle;
    }
}
