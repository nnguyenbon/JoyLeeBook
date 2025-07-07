/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author PC
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
    private List<Genre> genres;
    private int totalChapters;
    private Date latestChapterDate;

    /**
     *
     */
    public Series() {
    }

    /**
     *
     * @param authorName
     * @param seriesTitle
     * @param status
     * @param description
     * @param coverImageUrl
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
     *
     * @return
     */
    public int getSeriesId() {
        return seriesId;
    }

    /**
     *
     * @param seriesId
     */
    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }

    /**
     *
     * @return
     */
    public String getSeriesTitle() {
        return seriesTitle;
    }

    /**
     *
     * @param seriesTitle
     */
    public void setSeriesTitle(String seriesTitle) {
        this.seriesTitle = seriesTitle;
    }

    /**
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return this.description;
    }

    /**
     *
     * @return
     */
    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    /**
     *
     * @param coverImageUrl
     */
    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    /**
     *
     * @return
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     *
     * @param authorName
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     *
     * @return
     */
    public List<Genre> getGenres() {
        return genres;
    }

    /**
     *
     * @param genres
     */
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    /**
     *
     * @return
     */
    public int getTotalChapters() {
        return totalChapters;
    }

    /**
     *
     * @param totalChapters
     */
    public void setTotalChapters(int totalChapters) {
        this.totalChapters = totalChapters;
    }

    /**
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
    }
}
