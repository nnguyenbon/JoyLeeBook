/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.chapterController;

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
import java.util.ArrayList;
import model.Chapter;

/**
 *
 * @author PC
 */
@WebServlet(name="ReadChapterServlet", urlPatterns={"/readChapter"})
public class ReadChapterServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            // Check id if user operate with url
            String chapterIdParam = request.getParameter("chapterId");
            String seriesIdParam = request.getParameter("seriesId");
            if (chapterIdParam == null || chapterIdParam.isEmpty() 
                    || seriesIdParam == null || seriesIdParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/error.jsp");
                return;
            }

            SeriesDAO seriesDAO = new SeriesDAO(DBConnection.getConnection());
            ChapterDAO chapterDAO = new ChapterDAO(DBConnection.getConnection());

            int chapterId = Integer.parseInt(request.getParameter("chapterId"));
            int seriesId = Integer.parseInt(request.getParameter("seriesId"));
            
            Chapter chapter = chapterDAO.getChapterById(chapterId);
            ArrayList<Chapter> listChapter = chapterDAO.getAllChaptersBySeriesId(seriesId);
            request.setAttribute("chapter", chapter);
            request.setAttribute("chapterList", listChapter);
            request.getRequestDispatcher("/views/chapter/readChapter.jsp").forward(request, response);
        } catch (Exception e) {
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
