package controller.chapterController;

import java.io.IOException;
import java.sql.SQLException;

import dao.ChapterDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static utils.Validator.isValidInteger;

/**
 * Servlet to handle deleting a chapter.
 *
 * @author HaiDD-dev
 */
@WebServlet(name = "DeleteChapterServlet", urlPatterns = {"/deleteChapter"})
public class DeleteChapterServlet extends HttpServlet {

    private ChapterDAO chapterDAO;

    /**
     * Initializes the servlet and sets up the ChapterDAO instance.
     * This method is called by the servlet container to indicate that the servlet
     * is being placed into service.
     */
    @Override
    public void init() throws ServletException {
        try {
            chapterDAO = new ChapterDAO(DBConnection.getConnection());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            response.sendRedirect("views/chapter/deleteChapter.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cannot redirect to delete chapter page.");
            request.getRequestDispatcher("views/error.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String chapterId = request.getParameter("chapterId");

            // Validate chapterId
            if (!isValidInteger(chapterId)) {
                request.setAttribute("error", "Invalid chapter ID.");
                request.getRequestDispatcher("views/error.jsp").forward(request, response);
                return;
            }

            // Attempt to delete the chapter
            int id = Integer.parseInt(chapterId);
            boolean isDeleted = chapterDAO.deleteChapter(id);
            if (isDeleted) {
                request.setAttribute("message", "Chapter deleted successfully.");
            } else {
                request.setAttribute("error", "Chapter not found or could not be deleted.");
            }
            request.getRequestDispatcher("views/chapter/deleteChapter.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    }
}