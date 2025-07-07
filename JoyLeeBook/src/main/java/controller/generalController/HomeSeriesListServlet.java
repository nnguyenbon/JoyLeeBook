/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.generalController;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

import dao.ChapterDAO;
import dao.SeriesDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Series;

/**
 *
 * @author PC
 */
@WebServlet(name = "HomeSeriesListServlet", urlPatterns = { "/home" })
public class HomeSeriesListServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * 
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection conn = new DBConnection().getConnection()) {
            
            SeriesDAO seriesDAO = new SeriesDAO(conn);
            ChapterDAO chapterDAO = new ChapterDAO(conn);
            List<Series> seriesList = seriesDAO.getAllSeries();
            for (Series series : seriesList) {
                series.setTotalChapters(chapterDAO.getTotalChaptersBySeriesId(series.getSeriesId()));
                series.setLatestChapterDate(chapterDAO.getLatestDate(series.getSeriesId()));
            }
            request.setAttribute("seriesList", seriesList);
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cannot get the Series List.");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
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
