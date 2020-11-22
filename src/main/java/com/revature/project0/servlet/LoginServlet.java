package com.revature.project0.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project0.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    public LoginServlet() {
        super();
    }

    private ObjectMapper objMapper = new ObjectMapper();
    //TODO switch it to post request and start a user session
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        UserRepository uR = new UserRepository();
//        String json = objMapper.writeValueAsString(uR.readUser());
//        response.getWriter().append(json);
        User currentUser = new User(request.getParameter("name") , request.getParameter("password"));
//        BufferedReader br = request.getReader();
//        String userName = br.readLine();
//        userName = br.readLine();
//        System.out.println(userName);
//        String password = br.readLine();
//        User currentUser = new User(userName , password);
        try {
            if(currentUser.login(request.getParameter("name") , request.getParameter("password"))) {
                response.getWriter().append(objMapper.writeValueAsString("Succesful login " + request.getParameter("name")));
                response.setContentType("application/json");
            }
            else {
                response.getWriter().append(objMapper.writeValueAsString("username or password does not match " + request.getParameter("name")));
                response.setContentType("application/json");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
