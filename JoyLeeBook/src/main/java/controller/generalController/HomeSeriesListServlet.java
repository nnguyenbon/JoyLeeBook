package controller.generalController;

import dao.ChapterDAO;
import dao.SeriesDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
// Thêm import này
import java.util.ArrayList;
import model.Series;

@WebServlet(name = "HomeSeriesListServlet", urlPatterns = {"/home"})
public class HomeSeriesListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection conn = DBConnection.getConnection()) {
            SeriesDAO seriesDAO = new SeriesDAO(conn);
            ChapterDAO chapterDAO = new ChapterDAO(conn);

            ArrayList<Series> seriesList = seriesDAO.getAllSeries();
            for (Series series : seriesList) {
                series.setTotalChapters(chapterDAO.getTotalChaptersBySeriesId(series.getSeriesId()));
                series.setLatestChapterDate(chapterDAO.getLatestDate(series.getSeriesId()));
            }

            // === SỬA LỖI Ở ĐÂY: Sắp xếp một cách an toàn với Comparator ===
            // Logic này sẽ xử lý trường hợp ngày tháng là null.
            // Series có ngày mới nhất sẽ ở trên cùng, series không có chapter (ngày null) sẽ ở cuối cùng.
            seriesList.sort((s1, s2) -> {
                java.util.Date d1 = s1.getLatestChapterDate();
                java.util.Date d2 = s2.getLatestChapterDate();

                if (d1 == null && d2 == null) {
                    return 0; // Cả hai đều null, coi như bằng nhau
                }
                if (d1 == null) {
                    return 1; // s1 không có ngày, đẩy xuống cuối
                }
                if (d2 == null) {
                    return -1; // s2 không có ngày, đẩy xuống cuối
                }
                // Sắp xếp giảm dần (ngày mới nhất lên đầu)
                return d2.compareTo(d1);
            });

            request.setAttribute("seriesList", seriesList);
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cannot get the Series List.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}