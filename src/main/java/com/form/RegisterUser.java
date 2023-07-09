package com.form;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.dao.UserDaoImpl;
import com.entities.User;

/**
 * 
 * @author Himadri Shekhar Chakraborty
 * @since July 2023
 * @version 1.0.0
 * 
 **/

// @MultipartConfig // required when we are recieving files in request
public class RegisterUser extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");
        // resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        ApplicationContext context = new AnnotationConfigApplicationContext("com/dao");
        UserDaoImpl userDao = context.getBean("Dao", UserDaoImpl.class);

        try {

            String username = req.getParameter("username") ;
            String userpassword = req.getParameter("userpassword");
            String useremail = req.getParameter("useremail");

            User user = new User();
            user.setUserName(username == null || "".equals(username) ? "Guest" : username);
            user.setUserPassword(userpassword == null || "".equals(userpassword) ? "Guest" : userpassword );
            user.setUserEmail(useremail == null || "".equals(useremail) ? "Guest" : useremail);

            /**
             * ===============================================================
             * TODO: things when we are also recieving files in the post requst
             * ===============================================================
             * 
             * Part part = req.getPart("<file-field-name>") ;
             * 
             * String filename = part.getSubmittedFileName() ;
             * sout(filename) ;
             * 
             * also at the start of the class name we need to include the below annotationn
             * 
             * @MultipartConfig
             * 
             *                  we dont save the complete file in the db, but we only store
             *                  the name of file
             *                  in db and the file in any foldor with prorper mapping
             *                  strategy
             * 
             *                  InputStream stream = part.getInputStream() ;
             *                  byte data[] = new byte[stream.available] ;
             *                  stream.read(data) ;
             * 
             *                  String path =
             *                  request.getRealPath("/")+"destinationFoldor"+File.separator+filename
             *                  ;
             * 
             *                  FileOutputStream op = new FileOutputStream(path) ;
             * 
             *                  op.write(data) ;
             * 
             *                  op.close() ;
             * 
             *                  ===============================================================
             */

            /**
             * TEST: just to elongate animation time on client side
             */

            List<User> users = userDao.getUserByEmail(useremail);

            if (users.size() != 0 && users != null) {

                out.print("Email exists, please login!");

            } else {

                try {

                    userDao.addUser(user);
                    // convert to json
                    // String response = new Gson().toJson(user);
                    // // send json response
                    // out.print(response);

                    out.print("user registered, please login!");

                } catch (Exception e) {

                    throw e;

                }
            }

        } catch (Exception e) {

            // out.print(new Gson().toJson(e));

            out.print("Internal server error!!");

            throw e ;

        } finally {

            ((AnnotationConfigApplicationContext) context).close();
            out.flush();
        }
    }

    // For Testing Jdbc Conn
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com/dao");
        UserDaoImpl userDao = context.getBean("Dao", UserDaoImpl.class);
        try {
            userDao.addUser(new User("Himadri", "mypass1245&&", "himadri@amdocs.com"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ((AnnotationConfigApplicationContext) context).close();
        }
    }


}
