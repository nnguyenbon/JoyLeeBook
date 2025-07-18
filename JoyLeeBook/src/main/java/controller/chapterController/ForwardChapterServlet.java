/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.chapterController;

import java.io.IOException;
import java.io.PrintWriter;

import dao.ChapterDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Chapter;

/**
 *
 * @author KHAI TOAN
 */
@WebServlet(name = "ForwardChapterServlet", urlPatterns = {"/forwardChapter"})
public class ForwardChapterServlet extends HttpServlet {

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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
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
            ChapterDAO chapterDAO = new ChapterDAO(DBConnection.getConnection());
            int chapterId = Integer.parseInt(request.getParameter("chapterId"));
            Chapter currentChapter = chapterDAO.getChapterById(chapterId);

            if (currentChapter == null) {
                request.setAttribute("errorMessage", "Chapter with ID " + chapterId + " not found.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }

            ArrayList<Chapter> allChapterOfSeries = chapterDAO.getAllChaptersBySeriesId(currentChapter.getSeriesId());
            Chapter newChapter = chapterDAO.getNextChapter(currentChapter.getSeriesId(), currentChapter.getChapterIndex());
            request.setAttribute("firstIndex", chapterDAO.getFirstChapterIndex(currentChapter.getSeriesId()));
            request.setAttribute("lastIndex", chapterDAO.getLastChapterIndex(currentChapter.getSeriesId()));
            request.setAttribute("chapter", newChapter);
            request.setAttribute("chapters", allChapterOfSeries);
            request.getRequestDispatcher("/WEB-INF/views/chapter/readChapter.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while processing your request.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
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
