package virinchi.servlet;

import virinchi.controller.UserController;
import virinchi.controller.UserControllerImplement;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form data from register.jsp
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Call controller to handle database logic
        UserController uc = new UserControllerImplement();
        boolean isRegistered = uc.userSignup(username, email, password);

        if (isRegistered) {
            // Registration successful → redirect to login page
            response.sendRedirect("login.jsp");
        } else {
            // Registration failed → show error on register.jsp
            request.setAttribute("signupError", "Username or Email already exists!");
            RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
            rd.forward(request, response);
        }
    }
}
