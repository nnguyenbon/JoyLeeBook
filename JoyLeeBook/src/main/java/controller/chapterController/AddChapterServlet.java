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
import java.sql.Connection;
import model.Chapter;
import model.Series;
import static utils.Validator.*;

/**
 *
 * @author PC
 */
@WebServlet(name = "AddChapterServlet", urlPatterns = {"/addChapter"})
public class AddChapterServlet extends HttpServlet {

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
        Connection conn = null;
        try {
            // Check id if user operate with url
            String seriesIdParam = request.getParameter("seriesId");
            if (!isValidInteger(seriesIdParam)) {
                request.setAttribute("errorMessage", "Invalid seriesId or chapterIndex.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }

            int seriesId = Integer.parseInt(seriesIdParam);
            conn = DBConnection.getConnection();
            SeriesDAO seriesDao = new SeriesDAO(conn);
            
            Series series = seriesDao.getSeriesById(seriesId);
            request.setAttribute("series", series);
            request.getRequestDispatcher("/WEB-INF/views/chapter/addChapter.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cannot get the Series Id.");
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
        try {
            // Get parameters from the form submission
            String seriesIdParam = request.getParameter("seriesId");
            String chapterIndexParam = request.getParameter("chapterIndex");
            String chapterTitle = request.getParameter("chapterTitle");
            String content = request.getParameter("content");

            // Validate parameters (basic check)
            if (!isValidInteger(seriesIdParam) || !isValidInteger(chapterIndexParam) || !isValidString(content)) {

                request.setAttribute("message", "All fields are required and must be valid.");
                request.getRequestDispatcher("/WEB-INF/views/chapter/addChapter.jsp").forward(request, response);
                return;
            }

            // Parse integers
            int seriesId = Integer.parseInt(seriesIdParam);
            int chapterIndex = Integer.parseInt(chapterIndexParam);

            if (chapterIndex <= 0) {
                request.setAttribute("message", "Chapter index must be greater than 0.");
                request.getRequestDispatcher("/WEB-INF/views/chapter/addChapter.jsp").forward(request, response);
                return;
            }

            // Create Chapter object
            Chapter newChapter = new Chapter(seriesId, chapterIndex, chapterTitle, content);

            // Call DAO to insert chapter into DB
            ChapterDAO chapterDAO = new ChapterDAO(DBConnection.getConnection());
            chapterDAO.insertChapter(newChapter);

            // Redirect to the chapter list or series detail page
            response.sendRedirect(request.getContextPath() + "/viewSeriesInfo?seriesId=" + seriesId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("message", "Invalid chapter index or series ID.");
            request.getRequestDispatcher("/WEB-INF/views/chapter/addChapter.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while adding the chapter.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

}
