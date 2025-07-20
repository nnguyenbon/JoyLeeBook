package controller.seriesController;

import java.io.IOException;
import java.sql.SQLException;

import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Series;
import dao.SeriesDAO;
import static utils.Validator.isValidInteger;
import static utils.Validator.isValidString;


/**
 * Servlet to handle adding a new series.
 *
 * @author HaiDD-dev
 */
@WebServlet(name = "AddSeriesServlet", urlPatterns = {"/addSeries"})
public class AddSeriesServlet extends HttpServlet {

    private SeriesDAO seriesDAO;

    @Override
    public void init() {
        try {
            seriesDAO = new SeriesDAO(DBConnection.getConnection());
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
        try {
            request.getRequestDispatcher("/WEB-INF/views/series/addSeries.jsp").forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
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
            // Retrieve parameters from the request
            String authorName = request.getParameter("authorName");
            String seriesTitle = request.getParameter("seriesTitle");
            String seriesStatus = request.getParameter("seriesStatus");
            String seriesDescription = request.getParameter("seriesDescription");
            String seriesCoverImageURL = request.getParameter("seriesCoverImageURL");
    
            // Validate input parameters
            if (!isValidString(authorName)) {
                request.setAttribute("error", "Author name cannot be empty");
                request.getRequestDispatcher("/WEB-INF/views/series/addSeries.jsp").forward(request, response);
                return;
            }
            
            if (!isValidString(seriesTitle)) {
                request.setAttribute("error", "Series title cannot be empty");
                request.getRequestDispatcher("/WEB-INF/views/series/addSeries.jsp").forward(request, response);
                return;
            }
            
            if (!isValidInteger(seriesStatus)) {
                request.setAttribute("error", "Series status must be a valid integer");
                request.getRequestDispatcher("/WEB-INF/views/series/addSeries.jsp").forward(request, response);
                return;
            }
            
            if (!isValidString(seriesDescription)) {
                request.setAttribute("error", "Series description cannot be empty");
                request.getRequestDispatcher("/WEB-INF/views/series/addSeries.jsp").forward(request, response);
                return;
            }
            
            if (!isValidString(seriesCoverImageURL)) {
                request.setAttribute("error", "Series cover image URL cannot be empty");
                request.getRequestDispatcher("/WEB-INF/views/series/addSeries.jsp").forward(request, response);
                return;
            }
    
            // Create a new Series object and insert it into the database
            Series series = new Series(authorName, seriesTitle, seriesStatus, seriesDescription, seriesCoverImageURL);
            boolean success = seriesDAO.insertSeries(series);
    
            // Check if the insertion was successful
            if (success) {
                request.setAttribute("message", "Series added successfully");
                response.sendRedirect("/WEB-INF/views/adminDashboard");
            } else {
                request.setAttribute("message", "An error occurred while adding the series");
                request.getRequestDispatcher("/WEB-INF/views/series/addSeries.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cannot add the Series Information.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
