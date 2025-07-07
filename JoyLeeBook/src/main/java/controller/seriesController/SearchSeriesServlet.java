package controller.seriesController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.SeriesDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Series;

/**
 * Servlet to handle searching for series.
 *
 * @author HaiDD-dev
 */
@WebServlet(name = "SearchSeriesServlet", urlPatterns = {"/search"})
public class SearchSeriesServlet extends HttpServlet {

    private SeriesDAO seriesDAO;

    /**
     * Initializes the servlet and sets up the SeriesDAO.
     */
    @Override
    public void init() {
        try {
            seriesDAO = new SeriesDAO(DBConnection.getConnection());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        try {
            // Retrieve the search query from the request
            String searchQuery = request.getParameter("searchQuery");
            List<Series> seriesList;
    
            // If searchQuery is not null or empty, search for series; otherwise, get all series
            if (searchQuery != null && !searchQuery.isEmpty()) {
                seriesList = seriesDAO.searchSeries(searchQuery);
            } else {
                seriesList = seriesDAO.getAllSeries();
            }
    
            // Set the series list as a request attribute and forward to the index page
            request.setAttribute("seriesList", seriesList);
            request.getRequestDispatcher("/views/series/searchSeries.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cannot get the Series Information.");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
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
            processRequest(request, response);
        } catch (SQLException e) {
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
            processRequest(request, response);
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
