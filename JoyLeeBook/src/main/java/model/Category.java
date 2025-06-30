/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author PC
 */
public class Category {
    private int genreId;
    private int seriesId;

    /**
     *
     */
    public Category() {
    }

    /**
     *
     * @param genreId
     * @param seriesId
     */
    public Category(int genreId, int seriesId) {
        this.genreId = genreId;
        this.seriesId = seriesId;
    }

    /**
     *
     * @return
     */
    public int getGenreId() {
        return genreId;
    }

    /**
     *
     * @param genreId
     */
    public void setGenreId(int genreId) {
        this.genreId = genreId;
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
}
