/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.chapterController;

import dao.ChapterDAO;
import dao.SeriesDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Chapter;
import model.Series;

/**
 *
 * @author PC
 */
@WebServlet(name = "UpdateChapterServlet", urlPatterns = {"/updateChapter"})
public class UpdateChapterServlet extends HttpServlet {

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
            int chapterId = Integer.parseInt(request.getParameter("chapterId"));
            ChapterDAO chapterDAO = new ChapterDAO(DBConnection.getConnection());
            Chapter chapter = chapterDAO.getChapterById(chapterId);

            if (chapter == null) {
                response.sendRedirect("views/error.jsp");
                return;
            }

            SeriesDAO seriesDAO = new SeriesDAO(DBConnection.getConnection());
            Series series = seriesDAO.getSeriesById(chapter.getSeriesId());
            request.setAttribute("chapter", chapter);
            request.setAttribute("seriesTitle", series.getSeriesTitle());

            request.getRequestDispatcher("views/chapter/editChapter.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cannot get Chapter detail.");
            request.getRequestDispatcher("views/error.jsp").forward(request, response);
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

        try {
            int chapterId = Integer.parseInt(request.getParameter("chapterId"));
            String seriesTitle = request.getParameter("seriesTitle");
            int chapterIndex = Integer.parseInt(request.getParameter("chapterIndex"));
            String chapterTitle = request.getParameter("chapterTitle");
            String content = request.getParameter("content");
            Chapter chapter = new Chapter(chapterId, chapterIndex, chapterTitle, content);

            if (chapterTitle == null || chapterTitle.trim().isEmpty()) {
                request.setAttribute("message", "Chapter Title cannot empty");
                request.setAttribute("chapter", chapter);
                request.setAttribute("seriesTitle", seriesTitle);
                request.getRequestDispatcher("views/chapter/editChapter.jsp").forward(request, response);
            }
            ChapterDAO chapterDAO = new ChapterDAO(DBConnection.getConnection());
            chapterDAO.updateChapter(chapter);
            request.setAttribute("message", "updated successfully!");
            request.getRequestDispatcher("views/chapter/readChapter.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();

            int chapterId = Integer.parseInt(request.getParameter("chapterId"));
            String seriesTitle = request.getParameter("seriesTitle");
            int chapterIndex = Integer.parseInt(request.getParameter("chapterIndex"));
            String chapterTitle = request.getParameter("chapterTitle");
            String content = request.getParameter("content");

            Chapter chapter = new Chapter(chapterId, chapterIndex, chapterTitle, content);

            request.setAttribute("chapter", chapter);
            request.setAttribute("seriesTitle", seriesTitle);
            request.setAttribute("message", "update failed");
            request.getRequestDispatcher("views/chapter/editChapter.jsp").forward(request, response);
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
