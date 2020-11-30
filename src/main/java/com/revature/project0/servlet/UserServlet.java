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

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {

    private final ObjectMapper objectMapper     = new ObjectMapper();
    private final UserService userService       = new UserService();
    private final ServletService servletService = new ServletService();

    public UserServlet() {
        super();
    }

    /**
     * will get a user by id or all the users...to get all users requires authentication
     * @param req the request
     * @param resp the response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String path = req.getRequestURI().substring(9);
        try {
            int id = Integer.parseInt(path.substring(7));
            User foundUser = userService.getUser(id);
            if (foundUser.getUserName().equals("blank")) {
                resp.getWriter().append("there is no user with an id of " ).append(String.valueOf(id));
            } else {
                resp.getWriter().append(objectMapper.writeValueAsString(foundUser));
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            if (!req.isRequestedSessionIdValid()) {
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED , "You need to be logged in with an admin account to access this page");
            } else {
                if (((Integer) session.getAttribute("role")) == 1) {
                    if (path.equals("/users")) {
                        resp.getWriter().append(objectMapper.writeValueAsString(userService.getAllUsers()));
                    }
                }
                else {
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You must be logged in with an admin account to access all the users");
                }
            }
        }
        resp.setContentType("application/json");
    }


    /**
     * Handles both updating a user and adding a new user
     * @param req the request
     * @param resp the response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonString = servletService.getPostReqBody(req);
        String path = req.getRequestURI().substring(9);

        try {
            int id = Integer.parseInt(path.substring(14));
            String correctPath = path.substring(6 , 13);
            if (correctPath.equals("/update")) {
                User userToBeUpdated = userService.getUser(id);
                if (userToBeUpdated.getUserName().equals("blank")) {
                    resp.getWriter().append("No user with id of ").append(String.valueOf(id));
                } else {
                    User newUserFields = objectMapper.readValue(jsonString , User.class);
                    userService.updateUser(userToBeUpdated , newUserFields);
                    resp.getWriter().append("The user ").append(userToBeUpdated.getUserName()).append(" has been updated..")
                            .append("\nnew name: " + newUserFields.getUserName())
                            .append("\nnew email: " + userToBeUpdated);
                }
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            if (path.equals("/users/add")) {
                User newUser = objectMapper.readValue(jsonString , User.class);
                if (newUser.getEmail() == null || newUser.getRole_id() == 0) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST , "Some required fields are missing...");
                } else {
                    String insertedJson = objectMapper.writeValueAsString(userService.insertUser(newUser));
                    if (insertedJson.equals("null")) {
                        resp.getWriter().append("could not add new user");
                    } else {
                        resp.getWriter().append("new user has been added");
                    }
                }
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        resp.setContentType("application/json");
    }

    /**
     * Deletes a user from the database
     * @param req the request
     * @param resp the response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI().substring(9);

        try {
            int id = Integer.parseInt(path.substring(14));
            String correctPath = path.substring(6 , 13);
            if (correctPath.equals("/delete")) {
                String deletedUserName = userService.getUser(id).getUserName();
                userService.deleteUser(id);
                if (deletedUserName.equals("blank")) {
                    resp.getWriter().append("No user with id of ").append(String.valueOf(id));
                } else {
                    resp.getWriter().append("The user ").append(deletedUserName).append(" has been deleted..");
                }
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        resp.setContentType("application/json");
    }
}
