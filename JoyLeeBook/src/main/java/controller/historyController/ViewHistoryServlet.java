package controller.historyController;

import dao.HistoryReadingDAO;
import dao.UserDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.HistoryReading;
import model.User;

import java.io.IOException;
import java.util.List;

/**
 * Servlet that handles displaying a user's reading history.
 * It checks if the user is logged in, retrieves the user's reading history from the database,
 * and forwards the data to the JSP page for display.
 *
 * URL pattern: /history
 */
@WebServlet("/history")
public class ViewHistoryServlet extends HttpServlet {

    /**
     * Handles GET requests for viewing the reading history.
     * If the user is logged in, retrieves the user's reading history from the database
     * and forwards it to the history.jsp page. If not logged in, redirects to the login page.
     *
     * @param request  The HTTP request object.
     * @param response The HTTP response object.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the current session without creating a new one
        HttpSession session = request.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;

        // If user is not logged in, redirect to login page
        if (username == null) {
            response.sendRedirect("views/authorization/login");
            return;
        }

        try {
            // Retrieve user information using the provided username
            UserDAO userDAO = new UserDAO(DBConnection.getConnection());
            User user = userDAO.getUserByName(username);

            // If user is not found, redirect to login
            if (user == null) {
                response.sendRedirect("views/authorization/login");
                return;
            }

            // Retrieve the list of reading history records for the user
            HistoryReadingDAO dao = new HistoryReadingDAO(DBConnection.getConnection());
            List<HistoryReading> historyList = dao.getAllByUser(user.getUserId());

            // Set the history list as a request attribute and forward to JSP page
            request.setAttribute("historyList", historyList);
            request.getRequestDispatcher("/views/user/history.jsp").forward(request, response);

        } catch (Exception e) {
            // Handle unexpected exceptions and forward to the error page
            e.printStackTrace();
            request.setAttribute("error", "Cannot get the Chapter Information.");
            request.getRequestDispatcher("views/error.jsp").forward(request, response);
        }
    }
}
