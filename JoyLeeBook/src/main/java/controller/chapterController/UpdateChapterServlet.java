package controller.chapterController;

import dao.ChapterDAO;
import dao.SeriesDAO;
import db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Chapter;
import model.Series;
import static utils.Validator.*;

/**
 * Servlet xử lý cập nhật thông tin chapter.
 * Hiển thị form sửa (GET) và xử lý cập nhật (POST).
 * 
 * @author PC
 */
@WebServlet(name = "UpdateChapterServlet", urlPatterns = {"/updateChapter"})
public class UpdateChapterServlet extends HttpServlet {
    
    /**
     * Handles the HTTP <code>GET</code> method.
     * 
     * Lấy thông tin chapter theo ID để hiển thị form sửa.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String chapterIdParam = request.getParameter("chapterId");

            if (!isValidInteger(chapterIdParam)) {
                request.setAttribute("errorMessage", "Invalid chapter ID.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }

            int chapterId = Integer.parseInt(chapterIdParam);
            ChapterDAO chapterDAO = new ChapterDAO(DBConnection.getConnection());
            Chapter chapter = chapterDAO.getChapterById(chapterId);

            if (chapter == null) {
                request.setAttribute("error", "Chapter not found.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }

            SeriesDAO seriesDAO = new SeriesDAO(DBConnection.getConnection());
            Series series = seriesDAO.getSeriesById(chapter.getSeriesId());

            if (series != null) {
                chapter.setSeriesTitle(series.getSeriesTitle());
            }

            request.setAttribute("chapter", chapter);
            request.getRequestDispatcher("/WEB-INF/views/chapter/editChapter.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cannot get Chapter detail.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * 
     * Xử lý cập nhật dữ liệu chapter sau khi người dùng submit form.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String seriesIdParam = request.getParameter("seriesId");
        String chapterIdParam = request.getParameter("chapterId");
        String seriesTitle = request.getParameter("seriesTitle");
        String chapterIndexParam = request.getParameter("chapterIndex");
        String chapterTitle = request.getParameter("chapterTitle");
        String content = request.getParameter("chapterContent");

        Chapter chapter = new Chapter();

        try {
            if (!isValidInteger(seriesIdParam) || !isValidInteger(chapterIdParam) || !isValidInteger(chapterIndexParam)
                    || !isValidString(chapterTitle) || !isValidString(content)) {

                chapter.setChapterId(isValidInteger(chapterIdParam) ? Integer.parseInt(chapterIdParam) : 0);
                chapter.setChapterIndex(isValidInteger(chapterIndexParam) ? Integer.parseInt(chapterIndexParam) : 0);
                chapter.setSeriesId(isValidInteger(seriesIdParam) ? Integer.parseInt(seriesIdParam) : 0);
                chapter.setSeriesTitle(seriesTitle);
                chapter.setChapterTitle(chapterTitle);
                chapter.setContent(content);

                request.setAttribute("message", "All fields must be valid and not empty.");
                request.setAttribute("chapter", chapter);
                request.getRequestDispatcher("/WEB-INF/views/chapter/editChapter.jsp").forward(request, response);
                return;
            }

            int seriesId = Integer.parseInt(seriesIdParam);
            int chapterId = Integer.parseInt(chapterIdParam);
            int chapterIndex = Integer.parseInt(chapterIndexParam);

            chapter = new Chapter(seriesId, chapterIndex, chapterTitle.trim(), content.trim());
            chapter.setChapterId(chapterId);
            chapter.setSeriesTitle(seriesTitle);

            ChapterDAO chapterDAO = new ChapterDAO(DBConnection.getConnection());

            if (chapterDAO.updateChapter(chapter)) {
                request.setAttribute("message", "Updated successfully!");
                request.setAttribute("chapter", chapter);
                request.getRequestDispatcher("/WEB-INF/views/chapter/readChapter.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "Update failed!");
                request.setAttribute("chapter", chapter);
                request.getRequestDispatcher("/WEB-INF/views/chapter/editChapter.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            chapter.setChapterId(isValidInteger(chapterIdParam) ? Integer.parseInt(chapterIdParam) : 0);
            chapter.setChapterIndex(isValidInteger(chapterIndexParam) ? Integer.parseInt(chapterIndexParam) : 0);
            chapter.setSeriesId(isValidInteger(seriesIdParam) ? Integer.parseInt(seriesIdParam) : 0);
            chapter.setSeriesTitle(seriesTitle);
            chapter.setChapterTitle(chapterTitle);
            chapter.setContent(content);

            request.setAttribute("chapter", chapter);
            request.setAttribute("message", "Update failed due to internal error.");
            request.getRequestDispatcher("/WEB-INF/views/chapter/editChapter.jsp").forward(request, response);
        }
    }
}