package controller.seriesController;

import dao.CategoryDAO;
import db.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Series;
import dao.SeriesDAO;

import java.io.*;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;

import static utils.Validator.isValidString;

/**
 * Servlet to handle adding a new series.
 *
 * @author HaiDD-dev
 */
@WebServlet(name = "AddSeriesServlet", urlPatterns = {"/addSeries"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,    // 1MB
        maxFileSize = 5 * 1024 * 1024,      // 5MB
        maxRequestSize = 10 * 1024 * 1024   // 10MB
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
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String seriesTitle = request.getParameter("seriesTitle");
        String authorName = request.getParameter("authorName");
        String seriesStatus = request.getParameter("seriesStatus");
        String seriesDescription = request.getParameter("seriesDescription");
        String[] genreParamValues = request.getParameterValues("genres");
        List<Integer> genreIDs = new ArrayList<>();

        // Validate input parameters
        if (!isValidString(authorName)) {
            request.setAttribute("error", "Author name cannot be empty");
            request.getRequestDispatcher("views/series/addSeries.jsp").forward(request, response);
            return;
        }

        if (!isValidString(seriesTitle)) {
            request.setAttribute("error", "Series title cannot be empty");
            request.getRequestDispatcher("views/series/addSeries.jsp").forward(request, response);
            return;
        }

        if (!isValidString(seriesStatus)) {
            request.setAttribute("error", "Series status cannot be empty");
            request.getRequestDispatcher("views/series/addSeries.jsp").forward(request, response);
            return;
        }

        if (!isValidString(seriesDescription)) {
            request.setAttribute("error", "Series description cannot be empty");
            request.getRequestDispatcher("views/series/addSeries.jsp").forward(request, response);
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

        // Xử lý ảnh upload
        Part filePart = request.getPart("coverImage");
        String submittedFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        if (submittedFileName.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng chọn ảnh bìa.");
            request.getRequestDispatcher("/view/addSeries.jsp").forward(request, response);
            return;
        }

        // Tạo thư mục lưu file nếu chưa tồn tại
        String uploadPath = getServletContext().getRealPath("/assets/images/") + File.separator;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        // Lấy tên file gốc không có phần mở rộng
        String baseName;
        int lastDotIndex = submittedFileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < submittedFileName.length() - 1) {
            baseName = submittedFileName.substring(0, lastDotIndex);
        } else {
            baseName = submittedFileName;
        }

        // Tạo một mã định danh duy nhất ngắn gọn
        String uniqueID = UUID.randomUUID().toString().substring(0, 8);

        // Ghép tên gốc với mã duy nhất để tạo tên file mới
        String avifFileName = baseName + "-" + uniqueID + ".avif";
        String tempFileName = baseName + "-" + uniqueID + "_temp." + getExtension(submittedFileName);

        File tempImageFile = new File(uploadPath + tempFileName);
        filePart.write(tempImageFile.getAbsolutePath());

        // Convert ảnh sang AVIF yêu cầu avifenc đã được cài đặt
        File avifImageFile = new File(uploadPath + avifFileName);
        ProcessBuilder pb = new ProcessBuilder("avifenc", tempImageFile.getAbsolutePath(), avifImageFile.getAbsolutePath());

        try {
            Process process = pb.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                request.setAttribute("error", "Error! cannot convert image to AVIF format.");
                request.getRequestDispatcher("/view/addSeries.jsp").forward(request, response);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi xử lý ảnh: " + e.getMessage());
            request.getRequestDispatcher("/view/addSeries.jsp").forward(request, response);
            return;
        } finally {
            // Xóa ảnh tạm
            tempImageFile.delete();
        }

        // Gán đường dẫn ảnh vào DB (tương đối, vd: uploads/abc.avif)
        String imageUrl = "assets/images/" + avifFileName;

        // Lưu vào DB (giả sử đã có SeriesDAO)
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

        // Lưu genres nếu có
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
