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
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/users/*")
public class UsersServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserService userService   = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (((Integer) session.getAttribute("role")) == 1) {
            String jsonResponse = objectMapper.writeValueAsString(userService.getAllUsers());
            resp.getWriter().append(jsonResponse);
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED , "You must be logged in with and admin user to get all users");
        }
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
            if (newUser.getEmail() == null || newUser.getRole_id() == 0) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST , "you seem to be missing some fields if you wish to add a new user");
            } else {
                String insertedJson = objectMapper.writeValueAsString(userService.insertUser(newUser));
                if (insertedJson.equals("null")) {
                    resp.getWriter().append("sorry, something went wrong...no new user was added...");
                } else {
                    resp.getWriter().append(insertedJson);
                }
            }
            resp.setContentType("application/json");

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

