package org.yourcart.controller.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.yourcart.beans.Advertisement;
import org.yourcart.model.AdvertisementModel;
import org.yourcart.utilize.FileUpload;

/**
 *
 * @author OsamaPC
 */
@WebServlet("/admin/AddAdvertisement")
@MultipartConfig
public class AddAdvertisement extends HttpServlet {

    Advertisement add;
    AdvertisementModel model;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
        model = new AdvertisementModel();
        String url = request.getParameter("url");
        
        Part filePart = request.getPart("image");
        if (filePart.getSize() != 0) {      //if photo uploaded
            String path = request.getServletContext().getRealPath("");

            try {
                String uploadedpath = FileUpload.uploadImage(filePart, path);
                model.addAdvertisment(uploadedpath,url);
            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("message", "please choose image only");
                request.getRequestDispatcher("/Failed.jsp").forward(request, response);
                return;
            }

        } else {                          //no photo uploaded
            model.addAdvertisment(request.getParameter("photo"),url);
        }

    }

}