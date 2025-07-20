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
import java.util.ArrayList;

@WebServlet("/history")
public class ViewHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;

        if (username == null) {
            response.sendRedirect("login"); 
            return;
        }

        try {
            UserDAO userDAO = new UserDAO(DBConnection.getConnection());
            User user = userDAO.getUserByName(username);

            if (user == null) {
                response.sendRedirect("login");
                return;
            }

            HistoryReadingDAO dao = new HistoryReadingDAO(DBConnection.getConnection());
            ArrayList<HistoryReading> historyList = dao.getAllByUser(user.getUserId());

            request.setAttribute("historyList", historyList);
            request.getRequestDispatcher("/WEB-INF/views/user/history.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Unable to load reading history.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
