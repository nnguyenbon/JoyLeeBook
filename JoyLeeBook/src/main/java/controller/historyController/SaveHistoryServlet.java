package controller.historyController;

import dao.HistoryReadingDAO;
import dao.UserDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.HistoryReading;
import model.User;
import static utils.Validator.*;

@WebServlet(name = "SaveHistoryServlet", urlPatterns = {"/saveHistory"})
public class SaveHistoryServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;

        if (username == null) {
            response.sendRedirect("views/authorization/login");
            return;
        }

        try {
            UserDAO userDAO = new UserDAO(DBConnection.getConnection());
            User user = userDAO.getUserByUsername(username);

            if (user == null) {
                response.sendRedirect("views/authorization/login");
                return;
            }

            String seriesIdStr = request.getParameter("seriesId");
            String chapterIdStr = request.getParameter("chapterId");
            String seriesTitle = request.getParameter("seriesTitle");
            String chapterTitle = request.getParameter("chapterTitle");

            if (!isValidInteger(seriesIdStr) || !isValidInteger(chapterIdStr) || 
                !isValidString(seriesTitle) || !isValidString(chapterTitle)) {
                request.setAttribute("error", "Missing or invalid parameters.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }

            int seriesId = Integer.parseInt(seriesIdStr);
            int chapterId = Integer.parseInt(chapterIdStr);
            int userId = user.getUserId();

            HistoryReading history = new HistoryReading();
            history.setUserId(userId);
            history.setSeriesId(seriesId);
            history.setChapterId(chapterId);
            history.setSeriesTitle(seriesTitle.trim());
            history.setChapterTitle(chapterTitle.trim());

            HistoryReadingDAO dao = new HistoryReadingDAO(DBConnection.getConnection());
            dao.saveOrUpdateHistory(history);

            response.sendRedirect("readChapter?chapterId=" + chapterId);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cannot save reading history.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
}
