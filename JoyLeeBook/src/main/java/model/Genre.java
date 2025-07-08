/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author PC
 */
public class Genre {
    private int genreId;
    private String genreName;

    /**
     *
     */
    public Genre() {
    }

    /**
     *
     * @param genreName
     */
    public Genre(String genreName) {
        this.genreName = genreName;
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
    public String getGenreName() {
        return genreName;
    }

    /**
     *
     * @param genreName
     */
    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
