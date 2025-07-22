/**
 *
 * Servlet to handle deleting a chapter.
 *
 *
 *
 * @author HaiDD-dev
 *
 */
package controller.chapterController;
import dao.ChapterDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import static utils.Validator.isValidInteger;

/**
 *
 * Servlet to handle deleting a chapter.
 *
 */
@WebServlet(name = "DeleteChapterServlet", urlPatterns = {"/deleteChapter"})
public class DeleteChapterServlet extends HttpServlet {

    private ChapterDAO chapterDAO;

    /**
     *
     * Initializes the servlet and sets up the ChapterDAO instance.
     *
     */
    @Override
    public void init() throws ServletException {
        try {
            chapterDAO = new ChapterDAO(DBConnection.getConnection());
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Failed to initialize ChapterDAO.", e);
        }
    }

    /**
     *
     * Handles the HTTP GET method (not supported for deletion).
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     *
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET method is not supported for deletion.");

    }

    /**
     *
     * Handles the HTTP POST method to delete a chapter.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Uncomment and adjust for role-based access control if needed
            /*
            User user = (User) request.getSession().getAttribute("user");
            if (user == null || !"admin".equalsIgnoreCase(user.getRoleName())) {
                request.setAttribute("error", "Unauthorized access.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }

             */
            String chapterId = request.getParameter("chapterId");
            String seriesId = request.getParameter("seriesId");

            // Validate chapterId and seriesId
            if (!isValidInteger(chapterId)) {
                request.setAttribute("error", "Invalid chapter ID.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }

            if (!isValidInteger(seriesId)) {
                request.setAttribute("error", "Invalid series ID.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }

            // Attempt to delete the chapter
            int id = Integer.parseInt(chapterId);
            boolean isDeleted = chapterDAO.deleteChapter(id);
            // Set flash message using session to display after redirect
            if (isDeleted) {
                request.getSession().setAttribute("message", "Chapter with " + chapterId + " deleted successfully.");
            } else {
                request.getSession().setAttribute("message", "Chapter not found or could not be deleted.");
            }
            // Redirect to viewSeriesInfo with seriesId
            response.sendRedirect(request.getContextPath() + "/viewSeriesInfo?seriesId=" + seriesId);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "A database error occurred while deleting the chapter.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }

    }

    /**
     *
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Handles deletion of chapter records.";
    }

}
