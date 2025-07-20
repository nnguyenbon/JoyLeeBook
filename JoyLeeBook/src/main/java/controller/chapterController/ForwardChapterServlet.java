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
            String chapterIdStr = request.getParameter("chapterId");
    
            if (!isValidInteger(chapterIdStr)) {
                request.setAttribute("error", "Invalid or missing chapterId parameter.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }
            int chapterId = Integer.parseInt(chapterIdStr);
            
            ChapterDAO chapterDAO = new ChapterDAO(DBConnection.getConnection());
            Chapter currentChapter = chapterDAO.getChapterById(chapterId);

            if (currentChapter == null) {
                request.setAttribute("error", "Chapter with ID " + chapterId + " not found.");
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
            request.setAttribute("error", "An error occurred while processing your request.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
