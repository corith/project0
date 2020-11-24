package com.revature.project0.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project0.models.User;
import com.revature.project0.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();
    private UserService userService = new UserService();

    public UserServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String uri = req.getRequestURI();
        char uri = req.getPathInfo().charAt(1);
        int id = Character.getNumericValue(uri);
        User theGottenUser = userService.getUser(id);
        if (theGottenUser.getUserName().equals("blank")) {
            resp.getWriter().append("no match found for a user with an id of ").append(String.valueOf(id));
        } else {
            resp.getWriter().append(objectMapper.writeValueAsString(theGottenUser));
        }
        resp.setContentType("application/json");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }


}
