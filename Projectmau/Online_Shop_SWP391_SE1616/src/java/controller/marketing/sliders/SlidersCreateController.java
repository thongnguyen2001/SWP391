/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.marketing.sliders;

import controller.authentication.BaseAuthController;
import dal.SliderDBContext;
import dal.UserDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Setting;
import model.Slider;
import model.User;
import utils.FileUtility;

/**
 *
 * @author Vu Dai Luong
 */
@MultipartConfig
public class SlidersCreateController extends BaseAuthController {

    @Override
    protected boolean isPermission(HttpServletRequest request) {
        UserDBContext userDB = new UserDBContext();
        User user = (User) request.getSession().getAttribute("user");
        int numRead = userDB.hasPermission(user.getUserID(), "Marketing");
        return numRead >= 1;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SliderDBContext sliderDB = new SliderDBContext();
        String Title = request.getParameter("title");
        String link = request.getParameter("link");
        int Status = Integer.parseInt(request.getParameter("status"));
        Setting set = new Setting();
        set.setSettingID(Status);
        Slider slider = new Slider();
        slider.setTitle(Title);
        slider.setStatus(set);
        slider.setContent(link);
        
        Part part = request.getPart("Img");
        FileUtility fileUtility = new FileUtility();

        String folder = request.getServletContext().getRealPath("/View/imgslider");
        String filename = null;

        if (part.getSize() != 0) {
            if (slider.getImg() != null && !slider.getImg().trim().isEmpty()) {
                fileUtility.delete(slider.getImg(), folder);
            }
            filename = fileUtility.upLoad(part, folder);
        }

        if (filename != null) {
            slider.setImg(filename);
        }

        sliderDB.insertSlider(slider);

//        response.getWriter().println(img);
//        response.getWriter().println(Title);
//        response.getWriter().println(Status);
        response.sendRedirect("http://localhost:8080/Online_Shop_SWP391_TuanVM/sliders");

    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
