package controller.seriesController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import dao.SeriesDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static utils.Validator.isValidInteger;

/**
 *
 * @author HaiDD-dev
 */

@WebServlet(name = "DeleteSeriesServlet", urlPatterns = { "/deleteSeries" })
public class DeleteSeriesServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            SeriesDAO seriesDao = new SeriesDAO(conn);

            String seriesIdRaw = request.getParameter("seriesId");
            if (!isValidInteger(seriesIdRaw)) {
                request.setAttribute("error", "Invalid series ID.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }
            int seriesId = Integer.parseInt(seriesIdRaw);

            // Bây giờ bạn chỉ cần xóa Series, CSDL sẽ tự động xóa các dữ liệu liên quan
            boolean isDeleted = seriesDao.deleteSeries(seriesId);

            if (!isDeleted) {
                request.setAttribute("error", "Delete failed. The series might not exist.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }

            response.sendRedirect(request.getContextPath() + "/adminDashboard");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred during deletion: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}