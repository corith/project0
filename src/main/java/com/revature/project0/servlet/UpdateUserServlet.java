package com.revature.project0.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.revature.project0.models.User;
import com.revature.project0.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/user/update/*")
public class UpdateUserServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();
    private UserService userService   = new UserService();

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
        User userToBeUpdated = userService.getUser(id);
        User newFields = objectMapper.readValue(jsonString , User.class);

        userService.updateUser(userToBeUpdated , newFields);

        resp.getWriter().append("succesfully updated user ")
                .append(userToBeUpdated.getUserName())
                .append("\nnew email: " + newFields.getEmail())
                .append("\nnew name: " + newFields.getUserName());
        resp.setContentType("application/json");
    }
}
