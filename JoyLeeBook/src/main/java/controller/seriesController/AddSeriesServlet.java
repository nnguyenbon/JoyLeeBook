package controller.seriesController;

import dao.CategoryDAO;
import dao.GenreDAO; // Thêm import này
import dao.SeriesDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Genre; // Thêm import này
import model.Series;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection; // Thêm import này
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import static utils.Validator.*;

/**
 * @author HaiDD-dev
 */

@WebServlet(name = "AddSeriesServlet", urlPatterns = {"/addSeries"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 10 * 1024 * 1024)
public class AddSeriesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection conn = DBConnection.getConnection()) {
            GenreDAO genreDAO = new GenreDAO(conn);
            ArrayList<Genre> genres = genreDAO.getAll();
            request.setAttribute("genres", genres);
            request.getRequestDispatcher("/WEB-INF/views/series/addSeries.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Could not load data for the add series page.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ... (Phần lấy parameters và validation giữ nguyên) ...
        String seriesTitle = request.getParameter("seriesTitle");
        String authorName = request.getParameter("authorName");
        String seriesStatus = request.getParameter("seriesStatus");
        String seriesDescription = request.getParameter("seriesDescription");
        String[] genreParamValues = request.getParameterValues("genres");
        ArrayList<Integer> genreIDs = new ArrayList<>();

        // ... (Validation logic giữ nguyên) ...
        if (!isValidString(authorName) || !isValidString(seriesTitle) || !isValidString(seriesStatus) || !isValidString(seriesDescription)) {
            request.setAttribute("message", "All text fields must be filled out.");
            doGet(request, response);
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
        if (genreIDs.isEmpty()) {
            request.setAttribute("message", "Please select at least one genre.");
            doGet(request, response);
            return;
        }
        Part filePart = request.getPart("coverImage");
        String submittedFileName = (filePart != null) ? Paths.get(filePart.getSubmittedFileName()).getFileName().toString() : "";
        if (submittedFileName.trim().isEmpty()) {
            request.setAttribute("message", "Please select a cover image.");
            doGet(request, response);
            return;
        }


        Connection conn = null;
        Process process = null;
        File tempImageFile = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // === ĐỊNH NGHĨA 2 ĐƯỜNG DẪN LƯU TRỮ ===
            // 1. Đường dẫn trong thư mục server (để chạy ứng dụng)
            String deployedPath = getServletContext().getRealPath("/assets/images/") + File.separator;

            // 2. Đường dẫn trong thư mục mã nguồn (để tiện phát triển)
            // LƯU Ý: Thay đổi đường dẫn này cho phù hợp với máy của bạn.
            String projectSourcePath = "/home/haishelby/DATA/FPT University/2025_b_Summer/PRJ301/JoyLeeBook/JoyLeeBook/src/main/webapp/assets/images" + File.separator;
            // String uploadPath = "D:\\Projects\\JoyLeeBook\\src\\main\\webapp\\assets\\images\\" + File.separator; // windows

            // Tạo cả hai thư mục nếu chúng chưa tồn tại
            new File(deployedPath).mkdirs();
            new File(projectSourcePath).mkdirs();
            // ==========================================

            int lastDotIndex = submittedFileName.lastIndexOf('.');
            String baseName = (lastDotIndex > 0) ? submittedFileName.substring(0, lastDotIndex) : submittedFileName;
            String uniqueID = UUID.randomUUID().toString().substring(0, 8);
            String avifFileName = baseName + "-" + uniqueID + ".avif";
            String tempFileName = baseName + "-" + uniqueID + "_temp." + getExtension(submittedFileName);

            // Lưu file tạm vào thư mục của server để xử lý
            tempImageFile = new File(deployedPath + tempFileName);
            filePart.write(tempImageFile.getAbsolutePath());

            File avifImageFileInDeployedPath = new File(deployedPath + avifFileName);
            String avifencPath = "avifenc";

            ProcessBuilder pb = new ProcessBuilder(
                    avifencPath,
                    tempImageFile.getAbsolutePath(),
                    avifImageFileInDeployedPath.getAbsolutePath());

            process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                // ... (Xử lý lỗi AVIF giữ nguyên) ...
                InputStream errorStream = process.getErrorStream();
                String errorOutput = new BufferedReader(new InputStreamReader(errorStream))
                        .lines().collect(Collectors.joining("\n"));
                request.setAttribute("error", "Failed to convert image to AVIF. Is 'avifenc' installed? Details: " + errorOutput);
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                conn.rollback();
                return;
            }

            // === SAO CHÉP FILE .AVIF VỀ THƯ MỤC MÃ NGUỒN ===
            Path sourceFile = avifImageFileInDeployedPath.toPath();
            Path destinationFile = new File(projectSourcePath + avifFileName).toPath();
            Files.copy(sourceFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            // =============================================

            String imageUrl = "assets/images/" + avifFileName;

            // ... (Phần code lưu vào CSDL giữ nguyên) ...
            Series newSeries = new Series();
            newSeries.setSeriesTitle(seriesTitle);
            newSeries.setAuthorName(authorName);
            newSeries.setStatus(seriesStatus);
            newSeries.setDescription(seriesDescription);
            newSeries.setCoverImageUrl(imageUrl);

            SeriesDAO seriesDAO = new SeriesDAO(conn);
            CategoryDAO categoryDAO = new CategoryDAO(conn);
            int insertedId = seriesDAO.insertAndReturnId(newSeries);
            categoryDAO.addCategories(insertedId, genreIDs);

            conn.commit();
            response.sendRedirect(request.getContextPath() + "/adminDashboard");

        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            request.setAttribute("message", "An error occurred while saving the series: " + e.getMessage());
            doGet(request, response);
        } finally {
            // ... (Phần dọn dẹp tài nguyên giữ nguyên) ...
            if (process != null) {
                process.destroy();
            }
            if (tempImageFile != null && tempImageFile.exists()) {
                tempImageFile.delete();
            }
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex >= 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }
}