package controller.seriesController;

import dao.CategoryDAO;
import dao.SeriesDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Series;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static utils.Validator.*;

@WebServlet(name = "AddSeriesServlet", urlPatterns = {"/addSeries"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024
)
public class AddSeriesServlet extends HttpServlet {

    private SeriesDAO seriesDAO;
    private CategoryDAO categoryDAO;

    @Override
    public void init() {
        try {
            seriesDAO = new SeriesDAO(DBConnection.getConnection());
            categoryDAO = new CategoryDAO(DBConnection.getConnection());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String seriesTitle = request.getParameter("seriesTitle");
        String authorName = request.getParameter("authorName");
        String seriesStatus = request.getParameter("seriesStatus");
        String seriesDescription = request.getParameter("seriesDescription");
        String[] genreParamValues = request.getParameterValues("genres");
        ArrayList<Integer> genreIDs = new ArrayList<>();

        if (!isValidString(authorName)) {
            request.setAttribute("message", "Author name cannot be empty");
            request.getRequestDispatcher("/WEB-INF/views/series/addSeries.jsp").forward(request, response);
            return;
        }

        if (!isValidString(seriesTitle)) {
            request.setAttribute("message", "Series title cannot be empty");
            request.getRequestDispatcher("/WEB-INF/views/series/addSeries.jsp").forward(request, response);
            return;
        }

        if (!isValidString(seriesStatus)) {
            request.setAttribute("message", "Series status cannot be empty");
            request.getRequestDispatcher("/WEB-INF/views/series/addSeries.jsp").forward(request, response);
            return;
        }

        if (!isValidString(seriesDescription)) {
            request.setAttribute("message", "Series description cannot be empty");
            request.getRequestDispatcher("/WEB-INF/views/series/addSeries.jsp").forward(request, response);
            return;
        }

        if (genreParamValues != null) {
            for (String idStr : genreParamValues) {
                try {
                    genreIDs.add(Integer.parseInt(idStr));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        Part filePart = request.getPart("coverImage");
        String submittedFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        if (submittedFileName.trim().isEmpty()) {
            request.setAttribute("message", "Please select a cover image.");
            request.getRequestDispatcher("/WEB-INF/views/series/addSeries.jsp").forward(request, response);
            return;
        }

        String uploadPath = getServletContext().getRealPath("/assets/images/") + File.separator;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        String baseName;
        int lastDotIndex = submittedFileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < submittedFileName.length() - 1) {
            baseName = submittedFileName.substring(0, lastDotIndex);
        } else {
            baseName = submittedFileName;
        }

        String uniqueID = UUID.randomUUID().toString().substring(0, 8);
        String avifFileName = baseName + "-" + uniqueID + ".avif";
        String tempFileName = baseName + "-" + uniqueID + "_temp." + getExtension(submittedFileName);

        File tempImageFile = new File(uploadPath + tempFileName);
        filePart.write(tempImageFile.getAbsolutePath());

        File avifImageFile = new File(uploadPath + avifFileName);
        ProcessBuilder pb = new ProcessBuilder("avifenc", tempImageFile.getAbsolutePath(), avifImageFile.getAbsolutePath());

        try {
            Process process = pb.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                request.setAttribute("message", "Error! Cannot convert image to AVIF format.");
                request.getRequestDispatcher("/WEB-INF/views/series/addSeries.jsp").forward(request, response);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Image processing error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/series/addSeries.jsp").forward(request, response);
            return;
        } finally {
            tempImageFile.delete();
        }

        String imageUrl = "assets/images/" + avifFileName;

        Series newSeries = new Series();
        newSeries.setSeriesTitle(seriesTitle);
        newSeries.setAuthorName(authorName);
        newSeries.setStatus(seriesStatus);
        newSeries.setDescription(seriesDescription);
        newSeries.setCoverImageUrl(imageUrl);

        int insertedId = 0;
        try {
            insertedId = seriesDAO.insertAndReturnId(newSeries);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (!genreIDs.isEmpty()) {
            try {
                categoryDAO.addCategories(insertedId, genreIDs);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        response.sendRedirect(request.getContextPath() + "/adminDashboard");
    }

    private String getExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex >= 0) return filename.substring(dotIndex + 1).toLowerCase();
        return "";
    }
}
