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

        String path = req.getRequestURI().substring(9);

        try {
            int id = Integer.parseInt(path.substring(14));
            String updateOrDelete = path.substring(6 , 13);
            if (updateOrDelete.equals("/delete")) {
                String deletedCard = cardService.getCard(id).getName();
                cardService.deleteCard(id);
                if (deletedCard.equals("blank")) {
                    resp.getWriter().append("there was no card found with an id of ").append(String.valueOf(id));
                } else {
                    resp.getWriter().append("the card with an id of " + id + " has been deleted");
                }
            } else if (updateOrDelete.equals("/update")) {
                // run update code
                Card cardToBeUpdated = cardService.getCard(id);
                Card newFields = objectMapper.readValue(jsonString , Card.class);
                cardService.updateCard(cardToBeUpdated , newFields);
                if (cardToBeUpdated.getName().equals("blank")) {
                    resp.getWriter().append("no card with an id of ").append(String.valueOf(id)).append(" found...");
                } else {
                    resp.getWriter().append("successfully updated card ")
                            .append(cardToBeUpdated.getName())
                            .append("\nnew name: " + newFields.getName())
                            .append("\nnew type: " + newFields.getType());
                }
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {

            if (path.equals("/cards/add")) {
                Card newCard = objectMapper.readValue(jsonString, Card.class);
                String insertedJson = objectMapper.writeValueAsString(cardService.insertCard(newCard));
                if (insertedJson.equals("null")) {
                    resp.getWriter().append("nah could not add that card, sorry dude...");
                } else {
                    resp.getWriter().append("added new card\n").append(insertedJson);
                }
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } // end catch
        resp.setContentType("application/json");
    }

}
