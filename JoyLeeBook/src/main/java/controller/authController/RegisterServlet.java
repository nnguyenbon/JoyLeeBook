package controller.authController;

import java.io.IOException;
import java.sql.SQLException;

import dao.UserDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

import static utils.Validator.isValidString;

/**
 * Servlet to handle user registration.
 * @author HaiDD-dev
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO;

    /**
     * Initializes the servlet and sets up the SeriesDAO instance.
     * This method is called by the servlet container to indicate that the servlet
     * is being placed into service.
     */
    @Override
    public void init() {
        try {
            userDAO = new UserDAO(DBConnection.getConnection());
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
            response.sendRedirect("views/authorization/register.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cannot redirect to registration page.");
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
            String username = request.getParameter("txtUsername");
            String password = request.getParameter("txtPassword");
            String email = request.getParameter("email");

            // Validate input parameters
            if (!isValidString(username)) {
                request.setAttribute("error", "Username cannot be empty.");
                request.getRequestDispatcher("views/authorization/register.jsp").forward(request, response);
                return;
            }
            if (!isValidString(password)) {
                request.setAttribute("error", "Password cannot be empty.");
                request.getRequestDispatcher("views/authorization/register.jsp").forward(request, response);
                return;
            }
            if (!isValidString(email)) {
                request.setAttribute("error", "Email cannot be empty.");
                request.getRequestDispatcher("views/authorization/register.jsp").forward(request, response);
                return;
            }
            // Check if the email is already in use
            if (userDAO.isDuplicateUsername(username)) {
                request.setAttribute("error", "Username is already in use.");
                request.getRequestDispatcher("views/authorization/register.jsp").forward(request, response);
                return;
            }
            // Check if the email is already in use
            if (userDAO.isDuplicateEmail(email)) {
                request.setAttribute("error", "Email is already in use.");
                request.getRequestDispatcher("views/authorization/register.jsp").forward(request, response);
                return;
            }

            // Create a new user object
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setRoleName("user"); // Default role
            // Insert the user into the database
            if (userDAO.insertUser(user)) {
                request.setAttribute("message", "Registration successful. Please log in.");
                request.getRequestDispatcher("views/authorization/login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Registration failed. Please try again.");
                request.getRequestDispatcher("views/authorization/register.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cannot add the Series Information.");
            request.getRequestDispatcher("views/error.jsp").forward(request, response);
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
