<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Login & Signup Form</title>
  <link rel="stylesheet" href="./styles/form.css" />
  <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>

<body>
  <section class="wrapper">
    <div class="loading">
      <span class="loader"></span>
      <h5 class="loading-text">Please wait ....</h5>
    </div>
    <div class="form signup">
      <header>Signup</header>
      <p id="info" class="submit-success">registered</p>
      <div class="signup-form-container">
        <form id="signup-form" action="RegisterUser" method="post">
          <input id="signup-name" type="text" name="username" placeholder="Full name" required />
          <input id="signup-password" type="password" name="userpassword" placeholder="Password" required />
          <input id="signup-email" type="text" name="useremail" placeholder="Email address" required />
          <div class="checkbox">
            <input type="checkbox" name="useragreement" id="signupCheck" value="checked" />
            <label for="signupCheck">I accept all terms & conditions</label>
          </div>
          <div class="buttons">
            <input type="submit" id="submit-btn" value="Signup" />
            <input type="reset" id="reset-btn" value="Reset" />
          </div>
        </form>
      </div>
    </div>


    <div class="form login">
      <header>Login</header>
      <div class="login-loading">
        <span class="login-loader"></span>
        <h5 class="login-loading-text">Please wait ....</h5>
      </div>
      <p id="info" class="login-submit-success">success</p>
      <div class="login-form-container">
        <form id="login-form" action="LoginUser" method="post">
          <input id="login-email" type="text" name="useremail" placeholder="Email address" required />
          <input id="login-password" type="password" name="userpassword" placeholder="Password" required />
          <a href="#">Forgot password?</a>
          <input type="submit" id="login-submit-btn" value="Login" />
        </form>
      </div>

    </div>


    <script>

      const wrapper = document.querySelector(".wrapper");
      const signupHeader = document.querySelector(".signup header");
      const loginHeader = document.querySelector(".login header");

      loginHeader.addEventListener("click", () => {
        wrapper.classList.add("active");
      });
      signupHeader.addEventListener("click", () => {
        wrapper.classList.remove("active");
      });

      // ---------- jquery ----------

      $(document).ready(() => {

        console.log("Running.....");

        var loader = document.querySelector(".loading");
        var topInfo = document.querySelector(".submit-success");
        var form = document.querySelector(".signup-form-container");
        var login_loader = document.querySelector(".login-loading");
        var login_topInfo = document.querySelector(".login-submit-success");
        var login_form = document.querySelector(".login-form-container");

        loader.classList.add("hide");
        login_loader.classList.add("hide");
        topInfo.classList.add("hide");  
        login_topInfo.classList.add("hide");


        $("#signup-form").submit(e => {

          e.preventDefault();

          /*
           * forms with only input fields in text format is serializable
           * in case the from contains file fields we need to create FormData
           * object of the form and then send it in the post request
           * 
           * EX: let data = new FormData(this) ;
           * 
           * also inside ajax request (POST) some additional attributes are needed
           * 
           * processData: false, 
           * contentType: false
           */


          /* serialize() is not wotrking with my browser */

          // var data = $("#signup-form").serialize(); // as we have only text data we can serialize
          // console.log(data) ;
          // window.alert(data) ;

          loader.classList.remove("hide");
          form.classList.add("hide");

          /* ---------- ajax post request ---------- */

          $.ajax({

            url: "RegisterUser",
            data: {
              username: $("#signup-name").val(),
              userpassword: $("#signup-password").val(),
              useremail: $("#signup-email").val(),
              signupcheck: $("signupCheck").val()
            },
            type: "POST",
            timeout: 50000,
            success: (data, textStatus, jqXHR) => {
              console.log("successfully submitted the form......", jqXHR, data);
              topInfo.innerHTML = data + '<span id="close" class="close">&#x2715</span>';
              topInfo.classList.remove("error");
              topInfo.classList.remove("hide");
              form.classList.remove("hide");
              loader.classList.add("hide");
            },
            error: (data, textStatus, jqXHR) => {
              console.log("Error encountered submitting the form......", jqXHR, data);
              topInfo.innerHTML = data + '<span id="close" class="close">&#x2715</span>';
              topInfo.classList.add("error");
              topInfo.classList.remove("hide");
              form.classList.remove("hide");
              loader.classList.add("hide");
            }

          })
        })


        $("#reset-btn").click(e => {

          topInfo.classList.add("hide");

        })
        

        $("#login-form").submit(e => {

          e.preventDefault();

          login_loader.classList.remove("hide");
          login_form.classList.add("hide");


          // ---------- ajax post request ----------

          $.ajax({

            url: "LoginUser",
            data: {
              useremail: $("#login-email").val(),
              userpassword: $("#login-password").val()
            },
            type: "POST",
            timeout: 50000,
            dataType: "json",
            success: (data, textStatus, jqXHR) => {
              console.log("successfully submitted the form......", jqXHR, data);
              if (data.redirect) {
                console.log("data.redirect= "+data.redirect) ;
                window.location.replace(data.redirect) ;
              }
              login_topInfo.innerHTML = data.msg + '<span id="close" class="close" style="color : black">&#x2715</span>';
              login_topInfo.classList.remove("error");
              login_topInfo.classList.remove("hide");
              login_form.classList.remove("hide");
              login_loader.classList.add("hide");
              
            },
            error: (data, textStatus, jqXHR) => {
              console.log("Error encountered submitting the form......", jqXHR, data);
              login_topInfo.innerHTML = data.msg + '<span id="close" class="close" style="color : black">&#x2715</span>';
              login_topInfo.classList.add("error");
              login_topInfo.classList.remove("hide");
              login_form.classList.remove("hide");
              login_loader.classList.add("hide");
            }

          })
        })


        $(topInfo).click(e => {
          topInfo.classList.add("hide");
        })

        $(login_topInfo).click(e => {
          login_topInfo.classList.add("hide");
        })


      })

    </script>
  </section>
</body>

</html>