package com.revature.project0.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project0.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/user/delete/*")
public class DeleteUserServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader br = req.getReader();
        StringBuilder sb  = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String jsonString = sb.toString();
        char uri = req.getPathInfo().charAt(1);
        int id = Character.getNumericValue(uri);
        String deletedUserName = userService.getUser(id).getUserName();
        userService.deleteUser(id);
//        String deletedUserName = "tre";
        if (deletedUserName.equals("blank")) {
            resp.getWriter().append("No user matches that id so no user was deleted");
        } else {
            resp.getWriter().append("user ").append(deletedUserName).append(" has been deleted");
        }
        resp.setContentType("application/json");
    }
}
