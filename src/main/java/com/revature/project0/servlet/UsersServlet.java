package com.revature.project0.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project0.models.User;
import com.revature.project0.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/users/*")
public class UsersServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserService userService   = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonResponse = objectMapper.writeValueAsString(userService.getAllUsers());
        resp.getWriter().append(jsonResponse);
        resp.setContentType("application/json");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader br = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String jsonString = sb.toString();
        // now we have got the body...

        try {
            User newUser = objectMapper.readValue(jsonString , User.class);
            String insertedJson = objectMapper.writeValueAsString(userService.insertUser(newUser));
            resp.getWriter().append(insertedJson);
            resp.setContentType("application/json");

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

