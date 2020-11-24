package com.revature.project0.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project0.models.Card;
import com.revature.project0.service.CardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/cards/*")
public class CardServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CardService cardService   = new CardService();

    /**
     * doGet(req,resp) gets all the users and a single user by id depending on uri path.
     * the first try catch basically is checking to see if the wild card is a number and
     * if it is it returns a card that has an id == to that number.
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI().substring(9);
        try {
            int id = Integer.parseInt(path.substring(7));
            Card foundCard = cardService.getCard(id);
            if (foundCard.getName().equals("blank")) {
                resp.getWriter().append("there was no card found with an id of ").append(String.valueOf(id));
            } else {
                resp.getWriter().append(objectMapper.writeValueAsString(foundCard));
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            if (path.equals("/cards")) {
                resp.getWriter().append(objectMapper.writeValueAsString(cardService.getAllCards()));
            }
        }
        resp.setContentType("applications/json");
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
    }

}
