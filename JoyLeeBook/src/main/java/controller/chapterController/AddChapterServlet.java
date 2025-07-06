/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.chapterController;

import dao.CategoryDAO;
import dao.ChapterDAO;
import dao.SeriesDAO;
import db.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Chapter;
import model.Series;

/**
 *
 * @author PC
 */
@WebServlet(name = "AddChapterServlet", urlPatterns = {"/addChapter"})
public class AddChapterServlet extends HttpServlet {

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
            //Chua check admin
            // Check id if user operate with url
            String idParam = request.getParameter("seriesId");
            if (idParam == null || idParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/error.jsp");
                return;
            }

            SeriesDAO seriesDAO = new SeriesDAO(DBConnection.getConnection());
            ChapterDAO chapterDAO = new ChapterDAO(DBConnection.getConnection());
            CategoryDAO categoryDAO = new CategoryDAO(DBConnection.getConnection());

            int seriesId = Integer.parseInt(idParam);
            request.setAttribute("seriesId", seriesId);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cannot get the Series Id.");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
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
            // Get parameters from the form submission
            String seriesIdParam = request.getParameter("seriesId");
            String chapterIndexParam = request.getParameter("chapterIndex");
            String chapterTitle = request.getParameter("chapterTitle");
            String content = request.getParameter("content");

            // Validate parameters (basic check)
            if (seriesIdParam == null || chapterIndexParam == null || chapterTitle == null || content == null
                    || seriesIdParam.isEmpty() || chapterIndexParam.isEmpty() || chapterTitle.isEmpty() || content.isEmpty()) {
                request.setAttribute("error", "All fields are required.");
                request.getRequestDispatcher("/views/addChapterForm.jsp").forward(request, response);
                return;
            }

            // Parse integers
            int seriesId = Integer.parseInt(seriesIdParam);
            int chapterIndex = Integer.parseInt(chapterIndexParam);

            // Create Chapter object
            Chapter newChapter = new Chapter(seriesId, chapterIndex, chapterTitle, content);

            // Call DAO to insert chapter into DB
            ChapterDAO chapterDAO = new ChapterDAO(DBConnection.getConnection());
            chapterDAO.insertChapter(newChapter);

            // Redirect to the chapter list or series detail page
            response.sendRedirect(request.getContextPath() + "/seriesDetail?seriesId=" + seriesId);
        } catch (NumberFormatException e) {
            // Handle number format errors from parsing integers
            e.printStackTrace();
            request.setAttribute("error", "Invalid chapter index or series ID.");
            request.getRequestDispatcher("/views/addChapterForm.jsp").forward(request, response);
        } catch (Exception e) {
            // Handle any other exception
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while adding the chapter.");
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
