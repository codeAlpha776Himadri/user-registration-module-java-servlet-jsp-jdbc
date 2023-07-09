package com.form;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.dao.UserDaoImpl;
import com.entities.User;
import com.google.gson.JsonObject;

public class LoginUser extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) 
        throws ServletException, IOException {
        
        PrintWriter printWriter = resp.getWriter() ; 
        resp.setContentType("text/html");
        // resp.setCharacterEncoding("UTF-8");

        ApplicationContext ctx ; 
        UserDaoImpl dao ;

        try {

            ctx = new AnnotationConfigApplicationContext("com/dao") ;
            dao = ctx.getBean("Dao",UserDaoImpl.class) ;

            // TODO: check for email existence then for password match

            String useremail = req.getParameter("useremail") ;
            String userpassword = req.getParameter("userpassword") ;

            // check for existence of email 

            boolean correctPassword= false ;

            List<User> users= dao.getUserByEmail(useremail) ;
            User currentSessionUser= null ;  
            JsonObject json= null ;

            if (users.size() != 0 && users != null) {
                for (User user : users) {
                    if (user.getUserPassword().equals(userpassword)) {
                        correctPassword= true ;
                        currentSessionUser = user ;
                    }
                }

                 

                if (correctPassword) {
                    

                    // TODO: successfull login tasks


                    // create a session

                    HttpSession session  = req.getSession(true) ;
                    session.setAttribute("username",currentSessionUser.getUserName());
                    session.setAttribute("useremail",currentSessionUser.getUserEmail());
                    session.setAttribute("userpassword",currentSessionUser.getUserPassword());

                    Cookie cookie = new Cookie("username", currentSessionUser.getUserName()) ;
                    resp.addCookie(cookie);

                    json = new JsonObject() ; 
                    json.addProperty("msg", "login success!");
                    json.addProperty("redirect","welcome.jsp");
                    printWriter.print(json) ; 
 
                }
                else {
                    json = new JsonObject() ;
                    json.addProperty("msg","Incorrect password!") ;
                    printWriter.print(json) ;
                }
            }
            else {
                System.out.println("\nUser dont exist....\nLogin failed!");
                json = new JsonObject() ;
                json.addProperty("msg","User does't exist, Signup!") ;
                printWriter.print(json) ; 
            }

            ((AnnotationConfigApplicationContext) ctx).close();

        } catch (Exception e) {
            
            e.printStackTrace() ;
            JsonObject json = new JsonObject() ;
            json.addProperty("msg", e.getMessage());
            printWriter.print(json) ;

        } finally {

            printWriter.flush(); 

        }

    }


    // For Testing db conn
    public static void main(String[] args) {
        
        ApplicationContext context = new AnnotationConfigApplicationContext("com/dao") ; 

        try {
            
            UserDaoImpl dao = context.getBean("Dao", UserDaoImpl.class) ;
            List<User> users = dao.getAllUsers() ;
            if (users.size() != 0 && users != null) {
                for (User user : users) {
                    System.out.println(user);
                }
            }
            else {
                System.out.println("\nUser dont exist....\nLogin failed!!!!");
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            ((AnnotationConfigApplicationContext) context).close();
        }

    }

}
