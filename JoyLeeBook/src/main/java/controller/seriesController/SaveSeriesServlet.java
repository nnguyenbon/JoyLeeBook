/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.seriesController;

import dao.ChapterDAO;
import dao.LibraryDAO;
import java.io.IOException;

import dao.SeriesDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Library;
import model.Series;
import model.User;

/**
 *
 * @author PC
 */
@WebServlet(name = "SaveSeriesServlet", urlPatterns = {"/saveSeries"})
public class SaveSeriesServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        java.sql.Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            LibraryDAO libraryDao = new LibraryDAO(conn);
            SeriesDAO seriesDao = new SeriesDAO(conn);
            ChapterDAO chapterDao = new ChapterDAO(conn);
            ArrayList<Library> libraryOfUser = libraryDao.getLibrariesByUserId(((User) (request.getSession().getAttribute("loggedInUser"))).getUserId());
            ArrayList<Series> librarySeries = new ArrayList<>();
            for (Library library : libraryOfUser) {
                Series series = seriesDao.getSeriesById(library.getSeriesId());
                librarySeries.add(series);
            }
            for (Series series : librarySeries) {
                series.setTotalChapters(chapterDao.getTotalChaptersBySeriesId(series.getSeriesId()));
            }

            int itemsPerPage = 10;
            int totalItems = librarySeries.size();
            int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

            String pageParam = request.getParameter("page");
            int currentPage = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
            if (currentPage < 1) {
                currentPage = 1;
            }
            if (currentPage > totalPages) {
                currentPage = totalPages;
            }

            int startIndex = (currentPage - 1) * itemsPerPage;
            int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

            List<Series> paginatedSeries = librarySeries.subList(startIndex, endIndex);
            request.setAttribute("librarySeries", paginatedSeries);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
        } catch (Exception e) {
            request.setAttribute("errorMessage", e);
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
        request.getRequestDispatcher("/WEB-INF/views/user/library.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
                if (isSeriesSaved) {
                    request.getSession().setAttribute("errorMessage", "This series is already saved in your library.");
                    response.sendRedirect(request.getContextPath() + "/viewSeriesInfo?seriesId=" + seriesId);
                    return;
                }
                boolean isSaved = seriesDAO.saveSeries(seriesId, userId);
                if (isSaved) {
                    request.getSession().setAttribute("successMessage", "Series saved successfully!");
                    response.sendRedirect(request.getContextPath() + "/saveSeries");
                } else {
                    request.setAttribute("errorMessage", "Failed to save the series.");
                    request.setAttribute("seriesId", seriesId);
                    request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                }
            } catch (Exception e) {
                request.setAttribute("errorMessage", e);
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
