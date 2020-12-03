package com.revature.project0.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (req.isRequestedSessionIdValid()) {
            session.invalidate();
            resp.getWriter().append("logged out");
        } else {
            resp.getWriter().append("you are not logged in...");
        }
        resp.setContentType("application/json");
    }
}
