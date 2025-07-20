package model;

/**
 *
 * @author KHAI TOAN
 */
public class Library {
    private int userId;
    private int seriesId;

    public Library() {
    }

    public Library(int userId, int seriesId) {
        this.userId = userId;
        this.seriesId = seriesId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }
}
