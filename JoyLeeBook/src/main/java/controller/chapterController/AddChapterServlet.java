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
import model.Series;

/**
 *
 * @author PC
 */
@WebServlet(name="AddChapterServlet", urlPatterns={"/addChapter"})
public class AddChapterServlet extends HttpServlet {

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
        try {
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            
        } catch (Exception e) {
        }
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
