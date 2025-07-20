package controller.seriesController;

import java.io.IOException;

import dao.SeriesDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author PC
 */
@WebServlet(name = "RemoveSavedSeriesServlet", urlPatterns = { "/removeSavedSeries" })
public class RemoveSavedSeriesServlet extends HttpServlet {
    /**
     * Handles the HTTP <code>POST</code> method.
     * 
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("loggedInUser");
            int userId = user.getUserId();
            int seriesId = Integer.parseInt(request.getParameter("seriesId"));
            try {
                SeriesDAO seriesDAO = new SeriesDAO(DBConnection.getConnection());
                boolean isSeriesSaved = seriesDAO.isSeriesSaved(seriesId, userId);
                if (!isSeriesSaved) {
                    request.getSession().setAttribute("message", "This series is not saved in your library.");
                    response.sendRedirect(request.getContextPath() + "/saveSeries");
                    return;
                }
                boolean isRemoved = seriesDAO.deleteSavedSeries(seriesId, userId);
                if (isRemoved) {
                    request.getSession().setAttribute("message", "Series removed successfully!");
                    response.sendRedirect(request.getContextPath() + "/saveSeries");
                } else {
                    request.setAttribute("error", "Failed to remove the series.");
                    request.setAttribute("seriesId", seriesId);
                    request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                }
            } catch (Exception e) {
                request.setAttribute("error", e);
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login"); //cho nay quay ve login.jsp hay /login (= controller)?
        }
    }
}
