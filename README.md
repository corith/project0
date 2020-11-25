###Project0 Java api

- this is a simple java api that keeps track of users, their roles, and "magic cards" that they own.
- It uses maven and tomcat.
- currently, this was hacked together to be "ready" for a presentation, and it will be undergoing a major refactor in the upcoming days...
    - there are several inconsistencies in naming convetions throughout the project as well as with endpoints.
    - currently the users password is stored in plain text on the database and will be instead hashed in the next couple of days.
    - several servlet endpoints are wildy inconsistent as it pertains to which servelts hold what endpoints.
        - example: most of the user servlets contain one endpoint per servlet while the card servlets use one messy servlet route all the end points
        - the user servlets will be refactored to more closely match the card servlets...and both will be cleaned up drastically.
    - there are several endpoints that currently return a string response and will be returning a HttpServletResponse error soon.
    - the current way that a user role is both represented and accessed is super goofy and will be getting a refactor in the next couple days.
    - there is admin validation for the get all users endpoint but not for the get all cards end point and that as well will be getting an updated in the next couple of days.
    - currently, there are no unit tests, but they will be implemented by the end of weekend.
    - several constructors that are unnecessary will be changed
    - the current way that a users role and its id are accessed are wildy goofy 
    - there are several other simple but tedious refactors that will be occurring in the next couple of days...stay tuned.
    
- the end points that are available are as follow
    - /project0/login
    - /project0/logout
    - /project0/users (a get request as an admin user returns all users, there roles, and all the cards they own.A post request with the necessary parameters inserts a new user)
    - /project0/user/* (gets a user by its id)
    - /project0/user/update/* (updates a user by id...can only update a users name and email currently - will be adding update password soon)
    - /project0/user/delete/* (deletes a user by id)
    - /project0/cards (returns all cards...currently can be accessed by anyone)
    - /project0/cards/* (returns a card by id)
    - /project0/cards/add (inserts a card into the database)
    - /project0/card/update/* (updates a card by id...can update a cards name and type - will be adding the ability to update owner that way users can trade cards if they would like)
    
    