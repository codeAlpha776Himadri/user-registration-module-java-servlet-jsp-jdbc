<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" session="false" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Welcome</title>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    </head>
    <% HttpSession session=request.getSession(); if(session.getAttribute("username")==null &&
        session.getAttribute("useremail")==null ) { response.sendRedirect("form.jsp"); } %>

        <body>
            <div class="container">

                <h2>Welcome to login success page</h2>
                <% HttpSession sessionsa=request.getSession(false); %>
                    <% %>
                        <h4>SessionId= <%= (String) sessionsa.getId() %></h4>
                        <h4>Name = <%=(String) sessionsa.getAttribute("username") %>
                        </h4>
                        <h4>Email = <%=(String) sessionsa.getAttribute("useremail") %>
                        </h4>
                        <h4>Password = <%=(String) sessionsa.getAttribute("userpassword") %>
                        </h4>

                        <button id="logout-btn" type="button" value="logout">Logout</button>

            </div>

            <script>

                $(document).ready(() => {


                    $("#logout-btn").click(e => {

                        e.preventDefault() ; 

                        $.ajax({

                            url: "LogoutUser",
                            type: "POST",
                            timeout: 50000,
                            dataType: "json",
                            data: {},
                            success: (data) => {
                                if (data != null && data.redirect) {
                                    window.location.href= data.redirect ;
                                    console.log("Logout success");
                                }
                                else {
                                    console.log("Logout faliure");
                                }
                            },
                            error: () => {
                                console.log("logout failure");
                            }

                        })

                    })

                })


            </script>
        </body>

    </html>