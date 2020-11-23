package com.revature.project0.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public UserServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String uri = req.getRequestURI();
        char uri = req.getPathInfo().charAt(1);
        int id = Character.getNumericValue(uri);
        UserService userService = new UserService();
        resp.getWriter().append(objectMapper.writeValueAsString(userService.getUser(id)));
        resp.setContentType("application/json");
        resp.getWriter().append(uri);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }


}
