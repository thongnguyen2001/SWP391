/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.marketing.post;

import controller.authentication.BaseAuthController;
import dal.PostDBContext;
import dal.SettingDBContext;
import dal.UserDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Post;
import model.Setting;
import model.User;

/**
 *
 * @author Hieuhihi
 */
public class PostChangeStatusController extends BaseAuthController {

    @Override
    protected boolean isPermission(HttpServletRequest request) {
        UserDBContext userDB = new UserDBContext();
        User user = (User)request.getSession().getAttribute("user");
        int numRead = userDB.hasPermission(user.getUserID(), "Marketing");
        return numRead >= 1;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int status = Integer.parseInt(request.getParameter("status"));
        PostDBContext postDB = new PostDBContext();
        Post post = postDB.getById(id);
        SettingDBContext settingDB = new SettingDBContext();
        Setting setting = settingDB.getById(status);
        post.setPostStatus(setting);
        postDB.update(post);
        response.sendRedirect("http://localhost:8080/Online_Shop_SWP391_TuanVM/postmarketing");
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


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
