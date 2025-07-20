package controller.seriesController;

import dao.CategoryDAO;
import dao.GenreDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import dao.SeriesDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import model.Genre;
import model.Series;
import static utils.Validator.*;

/**
 *
 * @author PC
 */
@WebServlet(name = "UpdateSeriesServlet", urlPatterns = {"/updateSeries"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 5 * 1024 * 1024, // 5MB
        maxRequestSize = 10 * 1024 * 1024 // 10MB
)
public class UpdateSeriesServlet extends HttpServlet {
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
        java.sql.Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            SeriesDAO seriesDao = new SeriesDAO(conn);
            GenreDAO genreDao = new GenreDAO(conn);
            CategoryDAO categoryDao = new CategoryDAO(conn);

         String seriesIdStr = request.getParameter("seriesId");
         if (!isValidInteger(seriesIdStr)) {
            request.setAttribute("errorMessage", "Invalid series ID.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }
        
        int seriesId = Integer.parseInt(seriesIdStr);
        Series series = seriesDao.getSeriesById(seriesId);
            series.setGenres(categoryDao.getGenresBySeriesId(series.getSeriesId()));
            ArrayList<Genre> genres = genreDao.getAll();
            request.setAttribute("genres", genres);
            request.setAttribute("series", series);
            request.getRequestDispatcher("/WEB-INF/views/series/editSeries.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
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

        java.sql.Connection conn = null;
        Series series = new Series();
        try {
            conn = DBConnection.getConnection();
            CategoryDAO categoryDao = new CategoryDAO(conn);
            SeriesDAO seriesDao = new SeriesDAO(conn);
            GenreDAO genreDao = new GenreDAO(conn);

            String seriesIdStr = request.getParameter("seriesId");
        if (!isValidInteger(seriesIdStr)) {
            forwardWithError(request, response, "Invalid series ID", series, genreDao);
            return;
        }
        int seriesId = Integer.parseInt(seriesIdStr);

            String authorName = request.getParameter("authorName");
            String seriesTitle = request.getParameter("seriesTitle");
            String status = request.getParameter("status");
            String description = request.getParameter("description");
            String existingImageUrl = request.getParameter("coverImageUrl");
            String imageUrl = existingImageUrl;
            String[] selectedGenre = request.getParameterValues("genres");

            ArrayList<Integer> genreIDs = new ArrayList<>();
            if (selectedGenre != null) {
                for (String idStr : selectedGenre) {
                    try {
                        genreIDs.add(Integer.parseInt(idStr));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }

            ArrayList<Genre> genres = new ArrayList<>();
            for (Integer genreId : genreIDs) {
                genres.add(genreDao.getGenreById(genreId));
            }

            // Chuẩn bị dữ liệu gửi về nếu lỗi
            series.setSeriesId(seriesId);
            series.setAuthorName(authorName);
            series.setSeriesTitle(seriesTitle);
            series.setStatus(status);
            series.setDescription(description);
            series.setCoverImageUrl(imageUrl);
            series.setGenres(genres);
            request.setAttribute("series", series);
            request.setAttribute("genres", genreDao.getAll());

            if (!isValidString(authorName) || !isValidString(seriesTitle)
                || !isValidString(status) || !isValidString(description)) {
            request.setAttribute("message", "All fields are required.");
            request.getRequestDispatcher("/WEB-INF/views/series/editSeries.jsp").forward(request, response);
            return;
        }

            Part filePart = request.getPart("coverImage");
            imageUrl = existingImageUrl;
            if (filePart != null && filePart.getSize() > 0 && filePart.getSubmittedFileName() != null
                    && !filePart.getSubmittedFileName().trim().isEmpty()) {

                String uploadPath = getServletContext().getRealPath("/assets/images/") + File.separator;
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String submittedFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                int lastDotIndex = submittedFileName.lastIndexOf('.');
                String baseName = (lastDotIndex > 0) ? submittedFileName.substring(0, lastDotIndex) : submittedFileName;
                String uniqueID = UUID.randomUUID().toString().substring(0, 8);

                String avifFileName = baseName + "-" + uniqueID + ".avif";
                String tempFileName = baseName + "-" + uniqueID + "_temp." + getExtension(submittedFileName);

                File tempImageFile = new File(uploadPath + tempFileName);
                filePart.write(tempImageFile.getAbsolutePath());

                File avifImageFile = new File(uploadPath + avifFileName);
                String avifencPath = "H:\\InstallApp\\windows-artifacts\\avifenc.exe";

                ProcessBuilder pb = new ProcessBuilder(
                        avifencPath,
                        tempImageFile.getAbsolutePath(),
                        avifImageFile.getAbsolutePath()
                );
                Process process = null;
                try {
                    process = pb.start();
                    int exitCode = process.waitFor();

                    if (exitCode != 0) {
                        InputStream errorStream = process.getErrorStream();
                        String errorOutput = new BufferedReader(new InputStreamReader(errorStream))
                                .lines().collect(Collectors.joining("\n"));

                        request.setAttribute("errorMessage", "Không thể chuyển ảnh sang AVIF. Chi tiết: " + errorOutput + "Lỗi! Không thể chuyển ảnh sang định dạng AVIF.");
                        request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                        return;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("errorMessage", "Lỗi xử lý ảnh: " + e.getMessage());
                    request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                    return;

                } finally {
                    // Quản lý process và file tạm
                    if (process != null) {
                        try {
                            process.getInputStream().close();
                            process.getErrorStream().close();
                            process.getOutputStream().close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        process.destroy();
                    }

                    if (tempImageFile.exists()) {
                        tempImageFile.delete();
                    }
                }
                imageUrl = "assets/images/" + avifFileName;
            }

            series.setCoverImageUrl(imageUrl);

            boolean isUpdated = seriesDao.updateSeries(series);
            boolean isUpdatedGenres = categoryDao.updateGenreOfSeries(seriesId, genreIDs);

            if (isUpdated && isUpdatedGenres) {
                request.setAttribute("successMessage", "Series updated successfully.");
                request.getRequestDispatcher("/WEB-INF/views/series/editSeries.jsp").forward(request, response);
            } else {
                forwardWithError(request, response, "Failed to update series.", series, genreDao);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.setAttribute("series", series);
            try {
                if (conn != null) {
                    GenreDAO genreDao = new GenreDAO(conn);
                    request.setAttribute("genres", genreDao.getAll());
                }
            } catch (Exception ignored) {
            }
            request.getRequestDispatcher("/WEB-INF/views/series/editSeries.jsp").forward(request, response);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void forwardWithError(HttpServletRequest request, HttpServletResponse response, String message, Series series, GenreDAO genreDao)
            throws ServletException, IOException, SQLException {
        request.setAttribute("errorMessage", message);
        request.setAttribute("series", series);
        request.setAttribute("genres", genreDao.getAll());
        request.getRequestDispatcher("/WEB-INF/views/series/editSeries.jsp").forward(request, response);
    }

    private String getExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex >= 0) {
            return filename.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }
}
