package com.revature.project0.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project0.models.User;
import com.revature.project0.service.ServletService;
import com.revature.project0.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    public LoginServlet() {
        super();
    }

    private final ObjectMapper objMapper = new ObjectMapper();
    private final UserService userService = new UserService();
    private final ServletService servletService = new ServletService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonString = servletService.getPostReqBody(request);
        User userTryingToLogin = objMapper.readValue(jsonString , User.class);
        if (userService.authUser(userTryingToLogin , userTryingToLogin.getPassword())) {
            User currentUser = userService.getUserByName(userTryingToLogin.getUserName());
            HttpSession session = request.getSession();
            session.setAttribute("user" , currentUser.getUserName());
            session.setAttribute("role" , currentUser.getRole().getId());
            response.getWriter().append("Successful login ")
                        .append(userTryingToLogin.getUserName())
                        .append(" id is: " + currentUser.getId());
        } else {
            response.getWriter().append("username or password does not match for user ")
                    .append(userTryingToLogin.getUserName());
        }
        response.setContentType("application/json");

    }
}
