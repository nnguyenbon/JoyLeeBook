/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.seriesController;

import dao.CategoryDAO;
import dao.ChapterDAO;
import dao.SeriesDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Chapter;
import model.Genre;
import model.Series;

/**
 *
 * @author PC
 */
@WebServlet(name = "ViewSeriesInfoServlet", urlPatterns = {"/viewSeriesInfo"})
public class ViewSeriesInfoServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        try {
            int seriesId = Integer.parseInt(request.getParameter("seriesId"));

            SeriesDAO seriesDAO = new SeriesDAO(DBConnection.getConnection());
            ChapterDAO chapterDAO = new ChapterDAO(DBConnection.getConnection());
            CategoryDAO categoryDAO = new CategoryDAO(DBConnection.getConnection());

            Series series = seriesDAO.getSeriesById(seriesId);

            List<Chapter> listChapter = chapterDAO.getAllChaptersBySeriesId(seriesId);
            series.setTotalChapters(listChapter.size());
            List<Genre> listGenre = categoryDAO.getGenresBySeriesId(seriesId);
            series.setGenres(listGenre);
            request.setAttribute("listChapter", listChapter);
            request.setAttribute("series", series);
            request.getRequestDispatcher("WEB-INF/views/series/viewInfo.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cannot get the Series Information.");
            request.getRequestDispatcher("WEB-INF/views/error.jsp").forward(request, response);
        }
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
        processRequest(request, response);
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
