/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;
/**
 *
 * @author PC
 */
public class Chapter {
    private int chapterId;
    private int seriesId;
    private int chapterIndex;
    private String chapterTitle;
    private Date createdAt;
    private String content;

    // Additional fields for joined data
    private String seriesTitle;

    /**
     *
     */
    public Chapter() {
    }

    /**
     *
     * @param seriesId
     * @param chapterIndex
     * @param chapterTitle
     * @param content
     */
    public Chapter(int seriesId, int chapterIndex, String chapterTitle, String content) {
        this.seriesId = seriesId;
        this.chapterIndex = chapterIndex;
        this.chapterTitle = chapterTitle;
        this.content = content;
        this.createdAt = new Date();
    }

    /**
     *
     * @return
     */
    public int getChapterId() {
        return chapterId;
    }

    /**
     *
     * @param chapterId
     */
    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
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
    public int getChapterIndex() {
        return chapterIndex;
    }

    /**
     *
     * @param chapterIndex
     */
    public void setChapterIndex(int chapterIndex) {
        this.chapterIndex = chapterIndex;
    }

    /**
     *
     * @return
     */
    public String getChapterTitle() {
        return chapterTitle;
    }

    /**
     *
     * @param chapterTitle
     */
    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
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
    public String getContent() {
        return content;
    }

    /**
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
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
}
