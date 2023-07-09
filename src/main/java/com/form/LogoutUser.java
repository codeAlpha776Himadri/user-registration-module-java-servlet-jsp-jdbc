package com.form;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

@WebServlet("/LogoutUser")
public class LogoutUser extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        PrintWriter out= resp.getWriter() ; 
        resp.setContentType("application/json");
        HttpSession currentSession = req.getSession(false) ; 
        
        if (currentSession != null) {

            String sess_id = currentSession.getId() ; 
            try {
                
                currentSession.invalidate();
                System.out.println("session with id="+sess_id+" ended..");
                JsonObject res = new JsonObject() ;
                res.addProperty("msg","session invalidated");
                res.addProperty("redirect","http://localhost:8080/demo-servlet-registration-form-project/form.jsp");
                out.print(res) ;
            

            } catch (Exception e) {
                
                throw e ; 

            } 

        }

    }
    
}
